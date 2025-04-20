FROM public.ecr.aws/docker/library/maven:3.9.9-amazoncorretto-17-alpine as builder
WORKDIR /app

COPY . .
RUN mvn clean package -DskipTests

FROM public.ecr.aws/amazoncorretto/amazoncorretto:17.0.13-al2-native-jdk as runner
WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]