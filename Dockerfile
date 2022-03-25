FROM openjdk:8-jdk-alpine

COPY build/libs/pfit-0.0.1.war pfit-0.0.1.war

ENTRYPOINT ["java","-jar","/pfit-0.0.1.war"]