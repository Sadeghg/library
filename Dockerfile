FROM openjdk:17

WORKDIR /app

COPY target/*jar library.jar

EXPOSE 8080

CMD ["java", "-jar", "library.jar"]