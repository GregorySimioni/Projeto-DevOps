# Entrega Final - Projeto DevOps com Docker

## 📋 Resumo do Projeto

Este projeto implementa uma **aplicação Java com Spring Boot 3.2.4 e Java 21**, containerizada com **Docker** e configurada com **CI/CD usando GitHub Actions** para publicação automática no DockerHub.

## 📁 Estrutura do Projeto Criado

```
Projeto-DevOps/
├── .github/
│   └── workflows/
│       └── docker-build-push.yml          # Workflow do GitHub Actions
├── src/
│   ├── main/
│   │   ├── java/com/devops/api/
│   │   │   ├── Application.java           # Classe principal Spring Boot
│   │   │   └── controller/
│   │   │       └── HealthController.java  # Endpoints REST
│   │   └── resources/
│   │       └── application.properties     # Configurações da aplicação
│   └── test/
├── build.gradle                           # Configuração do Gradle
├── Dockerfile                             # Multi-stage build para Docker
├── docker-compose.yml                     # Compose para testes locais
├── gradlew                                # Gradle wrapper (Unix)
├── gradlew.bat                            # Gradle wrapper (Windows)
├── gradle.properties                      # Propriedades do Gradle
├── settings.gradle                        # Configurações do Gradle
├── README.md                              # Documentação principal
├── SETUP.md                               # Guia de setup e execução
├── PUSH_MANUAL.md                         # Guia para push manual no GitHub
├── DOCKERHUB_SETUP.md                     # Guia de configuração do DockerHub
└── .gitignore                             # Arquivos ignorados pelo Git
```

## 🚀 Como Usar Este Projeto

### Passo 1: Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/Projeto-DevOps.git
cd Projeto-DevOps
```

### Passo 2: Compilar a Aplicação

```bash
./gradlew build
```

### Passo 3: Testar Localmente

#### Opção A: Usando Docker Compose (Recomendado)

```bash
docker-compose up --build
```

#### Opção B: Usando Gradle

```bash
./gradlew bootRun
```

### Passo 4: Testar os Endpoints

```bash
# Health check
curl http://localhost:8080/api/health

# Informações da aplicação
curl http://localhost:8080/api/info
```

## 📝 Arquivos Principais

### 1. **Dockerfile** - Multi-stage Build

O Dockerfile utiliza **multi-stage build** para otimizar o tamanho da imagem:

- **Stage 1 (Build)**: Usa `eclipse-temurin:21-jdk-jammy` para compilar a aplicação
- **Stage 2 (Runtime)**: Usa `eclipse-temurin:21-jre-jammy` (menor) para executar

```dockerfile
# Stage 1: Build
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY gradle.properties .
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon
COPY src src
RUN ./gradlew bootJar --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### 2. **build.gradle** - Configuração do Gradle

```gradle
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.2.4'
    id 'io.spring.dependency-management' version '1.1.4'
}

group = 'com.devops'
version = '1.0.0'
sourceCompatibility = '21'

dependencies {
    implementation 'org.springframework.boot:spring-boot-starter-web'
    implementation 'org.springframework.boot:spring-boot-starter-actuator'
    testImplementation 'org.springframework.boot:spring-boot-starter-test'
}
```

### 3. **Application.java** - Classe Principal

```java
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

### 4. **HealthController.java** - Endpoints REST

```java
@RestController
@RequestMapping("/api")
public class HealthController {
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Aplicação DevOps rodando com sucesso!");
        response.put("version", "1.0.0");
        response.put("java_version", System.getProperty("java.version"));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, String>> info() {
        Map<String, String> response = new HashMap<>();
        response.put("app_name", "DevOps Application");
        response.put("description", "Aplicação Java com Spring Boot em Docker");
        response.put("environment", System.getenv().getOrDefault("ENVIRONMENT", "development"));
        return ResponseEntity.ok(response);
    }
}
```

### 5. **.github/workflows/docker-build-push.yml** - CI/CD

O workflow automaticamente:

1. Faz checkout do código
2. Configura Java 21
3. Compila com Gradle
4. Faz build da imagem Docker
5. Faz login no DockerHub
6. Faz push da imagem

## 🔧 Configuração do GitHub Actions

### Passo 1: Adicionar Secrets

No repositório GitHub, vá para **Settings → Secrets and variables → Actions** e adicione:

| Secret | Valor |
|--------|-------|
| `DOCKERHUB_USERNAME` | Seu username do DockerHub |
| `DOCKERHUB_TOKEN` | Seu token de acesso do DockerHub |

### Passo 2: Fazer Push

```bash
git push origin main
```

O GitHub Actions executará automaticamente!

## 📊 Endpoints Disponíveis

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/api/health` | Verifica a saúde da aplicação |
| GET | `/api/info` | Informações da aplicação |
| GET | `/actuator/health` | Health check do Spring Actuator |

## 🐳 Comandos Docker Úteis

```bash
# Build da imagem
docker build -t devops-api:1.0.0 .

# Executar o container
docker run -p 8080:8080 devops-api:1.0.0

# Listar containers
docker ps

# Ver logs
docker logs <container-id>

# Parar o container
docker stop <container-id>

# Remover a imagem
docker rmi devops-api:1.0.0
```

## 📚 Documentação Adicional

- **SETUP.md**: Guia completo de setup e execução
- **PUSH_MANUAL.md**: Como fazer push manual para o GitHub
- **DOCKERHUB_SETUP.md**: Como configurar DockerHub e GitHub Actions
- **README.md**: Documentação técnica do projeto

## ✅ Checklist de Entrega

- [x] Aplicação Java com Spring Boot 3.2.4
- [x] Java 21 como versão alvo
- [x] Gradle como ferramenta de build
- [x] Dockerfile otimizado com multi-stage build
- [x] Endpoints REST funcionais
- [x] Docker Compose para testes locais
- [x] GitHub Actions para CI/CD
- [x] Documentação completa
- [x] Arquivo .gitignore configurado
- [x] Arquivo .dockerignore configurado

## 🎯 Próximos Passos

1. **Fazer Push para o GitHub**:
   - Siga as instruções em `PUSH_MANUAL.md`

2. **Configurar DockerHub**:
   - Siga as instruções em `DOCKERHUB_SETUP.md`

3. **Testar Localmente**:
   - Execute `docker-compose up --build`
   - Acesse `http://localhost:8080/api/health`

4. **Monitorar CI/CD**:
   - Vá para a aba "Actions" no GitHub
   - Acompanhe a execução do workflow

## 📞 Suporte

Se encontrar problemas:

1. Verifique a documentação em `SETUP.md`
2. Veja os logs do GitHub Actions
3. Verifique se o Docker está instalado e em execução
4. Verifique se o Java 21 está instalado

## 📄 Licença

MIT

---

**Criado com ❤️ para a atividade de DevOps**
