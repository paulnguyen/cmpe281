FROM openjdk
EXPOSE 8080
ADD ./dist/app.jar /srv/app.jar
ADD ./dist/org.restlet-2.3.7.jar /srv/restlet.jar
ADD ./dist/org.restlet.ext.json-2.3.7.jar /srv/restlet-json.jar
ADD ./dist/json.jar /srv/json.jar
CMD java -cp /srv/restlet.jar:/srv/restlet-json.jar:/srv/json.jar:/srv/app.jar api.GumballServer