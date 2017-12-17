FROM java:8-alpine

RUN mkdir /app
WORKDIR /app
ADD ./target/promotion-api.jar /app/

CMD java -jar promotion-api.jar
