# Estágio 1: Build com Maven
# Usamos uma imagem do Maven com JDK 17 para compilar o projeto.
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app

# Copia o pom.xml e baixa as dependências para aproveitar o cache de camadas do Docker
COPY pom.xml .
RUN mvn dependency:go-offline

# Copia o resto do código-fonte e compila a aplicação
COPY src ./src
RUN mvn clean package -DskipTests

# Estágio 2: Imagem final
# Usamos uma imagem JRE enxuta para rodar a aplicação, resultando em uma imagem final menor.
FROM eclipse-temurin:17-jre-alpine
# Instala o curl, necessário para o healthcheck do Docker Compose
RUN apk add --no-cache curl
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java"]
CMD ["-jar", "app.jar"]
