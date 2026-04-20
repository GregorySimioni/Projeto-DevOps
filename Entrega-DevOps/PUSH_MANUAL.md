# Guia: Como Fazer Push Manual para o GitHub

Este guia explica como fazer push do seu projeto para o repositório GitHub de forma manual.

## Pré-requisitos

- Git instalado no seu computador
- Acesso ao repositório GitHub
- Um token de acesso pessoal (PAT) do GitHub OU chave SSH configurada

## Opção 1: Usando Token de Acesso Pessoal (Recomendado para Iniciantes)

### Passo 1: Gerar um Token de Acesso Pessoal no GitHub

1. Acesse https://github.com/settings/tokens
2. Clique em "Generate new token" → "Generate new token (classic)"
3. Configure as permissões:
   - ✅ `repo` (acesso completo aos repositórios)
   - ✅ `workflow` (para CI/CD)
4. Copie o token gerado (você não conseguirá vê-lo novamente)

### Passo 2: Fazer Push do Projeto

1. Abra o terminal na pasta do seu projeto:
```bash
cd ~/Projeto-DevOps
```

2. Configure o Git com suas informações (se ainda não fez):
```bash
git config user.email "seu-email@example.com"
git config user.name "Seu Nome"
```

3. Adicione todos os arquivos:
```bash
git add .
```

4. Faça o commit:
```bash
git commit -m "Adicionar aplicação Java com Docker e CI/CD"
```

5. Faça o push para o GitHub:
```bash
git push -u origin main
```

6. Quando pedir o username, digite: `seu-usuario-github`
7. Quando pedir a senha, **cole o token** que você gerou (não a senha da conta)

## Opção 2: Usando Chave SSH (Mais Seguro)

### Passo 1: Gerar Chave SSH (se ainda não tem)

```bash
ssh-keygen -t ed25519 -C "seu-email@github.com"
```

Pressione Enter para aceitar o local padrão e defina uma senha (opcional).

### Passo 2: Adicionar Chave SSH ao GitHub

1. Copie a chave pública:
```bash
cat ~/.ssh/id_ed25519.pub
```

2. Acesse https://github.com/settings/keys
3. Clique em "New SSH key"
4. Cole a chave e salve

### Passo 3: Fazer Push do Projeto

1. Configure o Git:
```bash
git config user.email "seu-email@example.com"
git config user.name "Seu Nome"
```

2. Adicione os arquivos:
```bash
git add .
```

3. Faça o commit:
```bash
git commit -m "Adicionar aplicação Java com Docker e CI/CD"
```

4. Altere a URL do repositório para SSH:
```bash
git remote set-url origin git@github.com:seu-usuario/Projeto-DevOps.git
```

5. Faça o push:
```bash
git push -u origin main
```

## Opção 3: Usando GitHub Desktop (Interface Gráfica)

Se preferir usar uma interface gráfica:

1. Baixe [GitHub Desktop](https://desktop.github.com/)
2. Faça login com sua conta GitHub
3. Clique em "Add" → "Clone Repository"
4. Selecione seu repositório `Projeto-DevOps`
5. Clique em "Changes" e selecione todos os arquivos
6. Escreva a mensagem de commit
7. Clique em "Commit to main"
8. Clique em "Push origin"

## Verificar se o Push foi Bem-Sucedido

Após fazer o push, verifique no GitHub:

1. Acesse https://github.com/seu-usuario/Projeto-DevOps
2. Você deve ver todos os arquivos listados
3. Verifique se o commit aparece no histórico

## Troubleshooting

### Erro: "fatal: 'origin' does not appear to be a 'git' repository"

Você pode estar na pasta errada. Verifique se está em `~/Projeto-DevOps`:
```bash
pwd
cd ~/Projeto-DevOps
```

### Erro: "Authentication failed"

- Se usando token: Verifique se o token está correto e não expirou
- Se usando SSH: Verifique se a chave SSH está adicionada ao GitHub

### Erro: "Permission denied (publickey)"

Sua chave SSH não está configurada. Use a Opção 1 (Token) em vez disso.

## Próximos Passos

Após fazer o push com sucesso:

1. Vá para o repositório no GitHub
2. Vá para Settings → Secrets and variables → Actions
3. Adicione os secrets para CI/CD:
   - `DOCKERHUB_USERNAME`: Seu usuário do DockerHub
   - `DOCKERHUB_TOKEN`: Seu token do DockerHub

4. Faça um novo commit/push para ativar o GitHub Actions

## Referências

- [GitHub Docs: Creating a personal access token](https://docs.github.com/en/authentication/keeping-your-account-and-data-secure/creating-a-personal-access-token)
- [GitHub Docs: Connecting to GitHub with SSH](https://docs.github.com/en/authentication/connecting-to-github-with-ssh)
- [Git Documentation: git push](https://git-scm.com/docs/git-push)
