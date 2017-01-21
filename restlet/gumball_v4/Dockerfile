FROM openjdk
EXPOSE 8080
ADD ./dist/app.jar /srv/a.jar
ADD ./dist/org.restlet-2.3.7.jar /srv/r.jar
ADD ./dist/org.restlet.ext.json-2.3.7.jar /srv/rj.jar
ADD ./dist/org.restlet.ext.jackson-2.3.7.jar /srv/rk.jar
ADD ./dist/json.jar /srv/j.jar
ADD	./dist/jackson-core-2.8.3.jar /srv/k.jar
ADD	./dist/jackson-annotations-2.8.3.jar /srv/ka.jar
ADD	./dist/jackson-databind-2.8.3.jar /srv/kd.jar
ADD	./dist/jackson-dataformat-csv-2.8.3.jar /srv/kcsv.jar
ADD	./dist/jackson-dataformat-smile-2.8.3.jar /srv/ksmile.jar
ADD	./dist/jackson-dataformat-xml-2.8.3.jar /srv/kxml.jar
ADD	./dist/jackson-dataformat-yaml-2.8.3.jar /srv/kyaml.jar
CMD java -cp /srv/kyaml.jar:/srv/kxml.jar:/srv/ksmile.jar:/srv/kcsv.jar:/srv/kd.jar:/srv/ka.jar:/srv/k.jar:/srv/r.jar:/srv/rj.jar:/srv/rk.jar:/srv/j.jar:/srv/a.jar api.GumballServer


