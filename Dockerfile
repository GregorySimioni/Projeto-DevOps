# Stage 1: Build
FROM eclipse-temurin:21-jdk-jammy AS build

WORKDIR /app

# Copiar arquivos de configuração do Gradle
COPY gradle gradle
COPY gradlew .
COPY gradlew.bat .
COPY build.gradle .
COPY settings.gradle .

# Copiar código fonte
COPY src src

# Compilar (sem criar JAR)
RUN chmod +x gradlew && ./gradlew build -x bootJar --no-daemon

# Stage 2: Runtime
FROM eclipse-temurin:21-jre-jammy

WORKDIR /app

# Copiar o JAR compilado do stage anterior
COPY --from=build /app/build/libs/*.jar app.jar

# Expor porta
EXPOSE 8080

# Iniciar aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
