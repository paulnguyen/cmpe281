FROM openjdk:8-jre

EXPOSE 8080
ADD	 ./build/libs/helloworld-1.0.war /srv/helloworld.war
CMD java -jar /srv/helloworld.war


