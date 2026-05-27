FROM eclipse-temurin:21-jre
WORKDIR /app
ENV LANG=C.UTF-8 LC_ALL=C.UTF-8 TZ=Asia/Shanghai

COPY mallchat-chat-server/target/mallchat-chat-server-1.0-SNAPSHOT.jar app.jar

EXPOSE 8080 8090

ENTRYPOINT ["java", "-Dfile.encoding=UTF-8", "-jar", "app.jar"]
