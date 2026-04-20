# Guia de Configuração - DevOps com Docker

Este documento descreve como configurar e executar o projeto DevOps com Docker.

## Pré-requisitos

- Docker 20.10+
- Docker Compose 2.0+
- Java 21 (para desenvolvimento local)
- Gradle 8.7+ (ou usar o wrapper)
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
├── .github/workflows/
│   └── docker-build-push.yml
├── build.gradle
├── Dockerfile
├── docker-compose.yml
├── gradlew
├── gradle/
└── README.md
```

## Configuração do DockerHub (Opcional)

Se você deseja usar o CI/CD com GitHub Actions para publicar no DockerHub:

1. Crie uma conta em [DockerHub](https://hub.docker.com)
2. Gere um token de acesso em Settings > Security > New Access Token
3. No seu repositório GitHub, vá para Settings > Secrets and variables > Actions
4. Adicione os seguintes secrets:
   - `DOCKERHUB_USERNAME`: Seu usuário do DockerHub
   - `DOCKERHUB_TOKEN`: Seu token de acesso

## Compilar a Aplicação

### Usando Gradle

```bash
./gradlew build
```

### Usando Docker

```bash
docker build -t devops-api:1.0.0 .
```

## Executar Localmente

### Opção 1: Usando Docker Compose (Recomendado)

```bash
docker-compose up --build
```

A aplicação estará disponível em `http://localhost:8080`

### Opção 2: Usando Docker diretamente

```bash
docker build -t devops-api:1.0.0 .
docker run -p 8080:8080 devops-api:1.0.0
```

### Opção 3: Usando Gradle

```bash
./gradlew bootRun
```

## Testar a Aplicação

### Health Check

```bash
curl http://localhost:8080/api/health
```

Resposta esperada:
```json
{
  "status": "UP",
  "message": "Aplicação DevOps rodando com sucesso!",
  "version": "1.0.0",
  "java_version": "21.0.10"
}
```

### Informações da Aplicação

```bash
curl http://localhost:8080/api/info
```

Resposta esperada:
```json
{
  "app_name": "DevOps Application",
  "description": "Aplicação Java com Spring Boot em Docker",
  "environment": "development"
}
```

### Spring Actuator

```bash
curl http://localhost:8080/actuator/health
```

## Verificar o Container

```bash
# Listar containers em execução
docker ps

# Ver logs do container
docker logs devops-api-container

# Acessar o shell do container
docker exec -it devops-api-container /bin/bash
```

## Parar o Container

```bash
docker-compose down
```

## Publicar no DockerHub

### Manualmente

```bash
# Build da imagem
docker build -t seu-usuario/devops-api:1.0.0 .

# Login no DockerHub
docker login

# Push da imagem
docker push seu-usuario/devops-api:1.0.0
```

### Automaticamente com GitHub Actions

1. Faça push para a branch `main`
2. O GitHub Actions executará automaticamente:
   - Build da aplicação com Gradle
   - Build da imagem Docker
   - Push para DockerHub

## Troubleshooting

### Erro: "Java home supplied is invalid"

Verifique se o Java 21 está instalado:
```bash
java -version
```

### Erro: "Cannot connect to Docker daemon"

Verifique se o Docker está em execução:
```bash
docker ps
```

### Erro: "Port 8080 is already in use"

Mude a porta no `docker-compose.yml`:
```yaml
ports:
  - "8081:8080"  # Mude para uma porta disponível
```

## Referências

- [Docker Documentation](https://docs.docker.com/)
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Gradle Documentation](https://docs.gradle.org/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
