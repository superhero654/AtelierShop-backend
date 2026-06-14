# 阶段 1：使用 Maven 打包
FROM maven:3.8.5-openjdk-17 AS build
WORKDIR /app
COPY . .
# 跳过测试以加快在 Render 上的构建速度
RUN mvn clean package -DskipTests

# 阶段 2：使用轻量级 JDK 运行
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Render 会注入 PORT 环境变量，application.yml 中 server.port=${PORT:8080} 会自动读取
ENTRYPOINT ["java", "-jar", "app.jar"]
