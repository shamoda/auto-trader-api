FROM adoptopenjdk/openjdk11
WORKDIR /opt
ADD /target/auto-trader.jar .
CMD ["java", "-jar", "auto-trader.jar"]