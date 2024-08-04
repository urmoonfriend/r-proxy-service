#FROM openjdk:21
#EXPOSE 8087
#ADD target/proxy.jar proxy.jar
#ENTRYPOINT ["java","-jar","/proxy.jar"]
#ENV TZ="Asia/Almaty"

FROM openjdk:21-jdk-slim
EXPOSE 8087
ADD target/proxy.jar proxy.jar
ENTRYPOINT ["java", "-jar", "/proxy.jar"]
ENV TZ="Asia/Almaty"

