FROM openjdk:21
EXPOSE 8087
ADD target/proxy.jar proxy.jar
ENTRYPOINT ["java","-jar","/proxy.jar"]
ENV TZ="Asia/Almaty"


