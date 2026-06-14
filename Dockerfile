# 阶段 1：使用 Maven 打包
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
# 跳过测试以加快在 Render 上的构建速度
RUN mvn clean package -DskipTests

# 阶段 2：使用轻量级 JRE 运行（openjdk 官方镜像已下架，改用 eclipse-temurin）
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
# Render 会注入 PORT 环境变量，application.yml 中 server.port=${PORT:8080} 会自动读取
ENTRYPOINT ["java", "-jar", "app.jar"]
