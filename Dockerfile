FROM openjdk:8
RUN apt update; apt install -y maven 
WORKDIR /rideshare
COPY . /rideshare
RUN mvn clean package
EXPOSE 9000
CMD java -jar ./target/rideshare-user-service.jar
