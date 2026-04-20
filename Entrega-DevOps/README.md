# Projeto DevOps - Aplicação Java com Docker

Aplicação Java com Spring Boot 3.2.4 e Java 21, containerizada com Docker e CI/CD com GitHub Actions.

## Pré-requisitos

- Java 21+
- Gradle 8.0+
- Docker 20.10+
- Git

## Estrutura do Projeto

```
.
├── src/
│   ├── main/
│   │   ├── java/com/devops/api/
│   │   │   ├── Application.java
│   │   │   └── controller/
│   │   │       └── HealthController.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── build.gradle
├── Dockerfile
├── .dockerignore
└── README.md
```

## Como Executar Localmente

### 1. Compilar o projeto

```bash
./gradlew build
```

### 2. Executar a aplicação

```bash
./gradlew bootRun
```

A aplicação estará disponível em `http://localhost:8080`

### 3. Testar os endpoints

```bash
# Health check
curl http://localhost:8080/api/health

# Informações da aplicação
curl http://localhost:8080/api/info
```

## Docker

### Build da imagem

```bash
docker build -t devops-app:1.0.0 .
```

### Executar o container

```bash
docker run -p 8080:8080 devops-app:1.0.0
```

### Verificar o container

```bash
docker ps
```

## CI/CD com GitHub Actions

O projeto está configurado com GitHub Actions para:
1. Compilar a aplicação
2. Executar testes
3. Build da imagem Docker
4. Push para DockerHub

### Configuração de Secrets no GitHub

Você precisa adicionar os seguintes secrets no repositório:

- `DOCKERHUB_USERNAME`: Seu usuário do DockerHub
- `DOCKERHUB_TOKEN`: Seu token de acesso do DockerHub

## Endpoints Disponíveis

- `GET /api/health` - Verifica a saúde da aplicação
- `GET /api/info` - Informações da aplicação
- `GET /actuator/health` - Health check do Spring Actuator

## Tecnologias Utilizadas

- **Java 21**: Linguagem de programação
- **Spring Boot 3.2.4**: Framework web
- **Gradle**: Ferramenta de build
- **Docker**: Containerização
- **GitHub Actions**: CI/CD

## Autor

Gregory Simioni

## Licença

MIT
