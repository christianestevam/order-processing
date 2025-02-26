# Utiliza a imagem do Java 21 com Alpine (leve e otimizada)
FROM eclipse-temurin:21-jdk-alpine

# Define diretório de trabalho
WORKDIR /app

# Copia os arquivos do projeto para dentro do container
COPY . /app

# Compila a aplicação
RUN ./mvnw clean package -DskipTests

# Expõe a porta 8081 para comunicação
EXPOSE 8081

# Comando para rodar a aplicação
CMD ["java", "-jar", "target/order-processing-0.0.1-SNAPSHOT.jar"]
