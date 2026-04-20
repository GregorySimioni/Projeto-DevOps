# Stage 1: Build da aplicação usando Gradle
FROM eclipse-temurin:21-jdk-jammy AS build

# Diretório de trabalho no container
WORKDIR /app

# Copiar apenas os arquivos de configuração do Gradle primeiro para aproveitar o cache
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .
COPY gradle.properties .

# Dar permissão de execução ao wrapper do Gradle
RUN chmod +x gradlew

# Baixar dependências (aproveita cache do Docker se build.gradle não mudar)
RUN ./gradlew dependencies --no-daemon

# Copiar o código fonte
COPY src src

# Fazer o build da aplicação (gera o JAR)
RUN ./gradlew build -x bootJar --no-daemon

# Stage 2: Imagem final de produção
FROM eclipse-temurin:21-jre-jammy

# Diretório de trabalho
WORKDIR /app

# Adicionar usuário não-root para segurança
RUN groupadd -r spring && useradd -r -g spring spring
USER spring:spring

# Copiar o JAR gerado do stage de build
COPY --from=build /app/build/libs/*.jar app.jar

# Expor a porta que a aplicação usa
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
