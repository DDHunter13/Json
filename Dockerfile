FROM maven:3.3-jdk-8-onbuild
CMD ["java","-jar","/usr/src/app/target/Json-1.0-SNAPSHOT-jar-with-dependencies.jar"]
EXPOSE 80