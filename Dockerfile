# Estágio 1: Build - Usando um nome de imagem CORRETO com JDK 21
FROM maven:3.9.7-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Estágio 2: Run - Usando um nome de imagem CORRETO com JRE 21
FROM eclipse-temurin:21-jre
WORKDIR /app
# ATENÇÃO: Verifique se o nome do arquivo .jar abaixo está correto!
COPY --from=builder /app/target/financial-manager-0.0.1-SNAPSHOT.jar .
# ATENÇÃO: Verifique se o nome do arquivo .jar abaixo está correto!
CMD ["java", "-jar", "financial-manager-0.0.1-SNAPSHOT.jar"]