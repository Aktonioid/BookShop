FROM eclipse-temurin:17-jre
FROM gradle:8.1
WORKDIR /bookShop
COPY . /bookShop
ENV HOST=
ENV PORT=
ENV DBNAME=
ENV USERNAME=
ENV PASSWORD=

# COPY build/libs/*.jar app.jar

EXPOSE 8090

CMD [ "gradle", "bootrun" ]
# CMD ["java", "-jar", "app.jar"]