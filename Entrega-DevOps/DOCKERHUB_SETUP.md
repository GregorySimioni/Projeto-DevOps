# Guia: Configuração do DockerHub e GitHub Actions

Este guia explica como configurar o DockerHub e o GitHub Actions para publicar automaticamente sua imagem Docker.

## Parte 1: Criar Conta no DockerHub

### Passo 1: Registrar-se no DockerHub

1. Acesse https://hub.docker.com/signup
2. Preencha o formulário com:
   - Username (seu nome de usuário)
   - Email
   - Senha
3. Confirme seu email
4. Faça login

### Passo 2: Gerar Token de Acesso

1. Acesse https://hub.docker.com/settings/security
2. Clique em "New Access Token"
3. Dê um nome descritivo (ex: "GitHub Actions")
4. Selecione "Read, Write, Delete" como permissão
5. Clique em "Generate"
6. **Copie o token** (você não conseguirá vê-lo novamente)

## Parte 2: Configurar GitHub Actions

### Passo 1: Adicionar Secrets no GitHub

1. Acesse seu repositório no GitHub: https://github.com/seu-usuario/Projeto-DevOps
2. Vá para **Settings** → **Secrets and variables** → **Actions**
3. Clique em **New repository secret**
4. Adicione o primeiro secret:
   - **Name**: `DOCKERHUB_USERNAME`
   - **Value**: Seu username do DockerHub
   - Clique em **Add secret**

5. Clique novamente em **New repository secret**
6. Adicione o segundo secret:
   - **Name**: `DOCKERHUB_TOKEN`
   - **Value**: O token que você copiou do DockerHub
   - Clique em **Add secret**

### Passo 2: Verificar o Workflow

O arquivo `.github/workflows/docker-build-push.yml` já está configurado. Ele fará:

1. Compilar a aplicação com Gradle
2. Fazer build da imagem Docker
3. Fazer login no DockerHub
4. Fazer push da imagem

## Parte 3: Ativar o GitHub Actions

### Passo 1: Fazer um Commit/Push

Após adicionar os secrets, faça um novo commit:

```bash
git add .
git commit -m "Adicionar secrets do DockerHub"
git push origin main
```

### Passo 2: Monitorar a Execução

1. Vá para seu repositório no GitHub
2. Clique na aba **Actions**
3. Você verá o workflow "Docker Build and Push" em execução
4. Clique nele para ver os detalhes

### Passo 3: Verificar no DockerHub

Após o workflow ser concluído com sucesso:

1. Acesse https://hub.docker.com/repositories
2. Você verá um novo repositório `devops-api`
3. Clique nele para ver as imagens publicadas

## Como Usar a Imagem Docker Publicada

### Puxar a Imagem

```bash
docker pull seu-usuario/devops-api:latest
```

### Executar a Imagem

```bash
docker run -p 8080:8080 seu-usuario/devops-api:latest
```

### Usar em Docker Compose

```yaml
version: '3.8'
services:
  devops-api:
    image: seu-usuario/devops-api:latest
    ports:
      - "8080:8080"
```

## Troubleshooting

### Erro: "Authentication failed"

- Verifique se os secrets estão corretos
- Regenere o token do DockerHub se necessário

### Erro: "Workflow failed"

1. Vá para a aba **Actions**
2. Clique no workflow que falhou
3. Veja os logs para identificar o problema
4. Problemas comuns:
   - Gradle build falhou: Verifique o código Java
   - Docker build falhou: Verifique o Dockerfile
   - Push falhou: Verifique os secrets

### Imagem não aparece no DockerHub

- Aguarde alguns minutos
- Atualize a página do DockerHub
- Verifique se o workflow foi concluído com sucesso

## Próximas Execuções

A partir de agora, toda vez que você fizer push para a branch `main`:

1. O GitHub Actions executará automaticamente
2. A imagem será compilada
3. A imagem será publicada no DockerHub
4. Você poderá usar a imagem em qualquer lugar

## Referências

- [DockerHub Documentation](https://docs.docker.com/docker-hub/)
- [GitHub Actions Documentation](https://docs.github.com/en/actions)
- [Docker Build and Push Action](https://github.com/docker/build-push-action)
