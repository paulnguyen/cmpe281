
all: clean 

clean:
	find . -name "*.class" -exec rm -rf {} \;
	rm -rf build/*
	rm certs/*

compile:
	gradle build

jar: compile
	gradle shadowJar


##
## Restlet Hello World 
##

run-hello: 
	echo Starting Service at:  http://localhost:8111/hello
	java -cp build/libs/tutorial-all.jar helloworld.HelloWorldServerMain

run-client: 
	echo Run Hello World Client: http://localhost:8111/hello
	java -cp build/libs/tutorial-all.jar helloworld.HelloWorldClient


##
## First Application (Restlet Wrapping / Java Serialization)
##


run-firstapp-server:
	echo Running Service: First Application 
	java -cp build/libs/tutorial-all.jar firstapplication.server.TestServerApplication

run-firstapp-client:
	echo Running Client: First Application 
	java -cp build/libs/tutorial-all.jar firstapplication.client.TestClient


##
## Restlet Authentication
##


run-basic-auth: 
	echo Starting Service at:  http://localhost:8182
	java -cp build/libs/tutorial-all.jar authentication.AuthenticationApplication

run-basic-auth-client: 
	echo Test Service at:  http://localhost:8182
	java -cp build/libs/tutorial-all.jar authentication.AuthenticationClient

run-digest-auth: 
	echo Starting Service at:  http://localhost:8182
	java -cp build/libs/tutorial-all.jar authentication.HttpDigestAuthenticationApplication

run-digest-auth-client: 
	echo Test Service at:  http://localhost:8182
	java -cp build/libs/tutorial-all.jar authentication.HttpDigestAuthenticationClient



##
## Keystore Management Operations
##

gen-server-self-sign:
	echo Generating a Self-Sign Certificate - password = password
	keytool -keystore certs/server-keystore.jks -alias server -genkey -keyalg RSA -keysize 2048 \
			-dname "CN=localhost,OU=Org Unit,O=Org,C=US" -sigalg "SHA1withRSA"

gen-server-csr:
	echo Generating a Certificate Signing Request - CSR
	keytool -certreq -keystore certs/server-keystore.jks -alias server -file certs/server.csr

import-server-keystore:
	echo Importing CA Cert - PEM or DER format
	keytool -import -keystore certs/server-keystore.jks -alias server -file certs/server.cer

export-server-cert:
	echo Exporting Public Key / Certificate
	keytool -exportcert -keystore certs/server-keystore.jks -alias server -file certs/server.cer

import-client-truststore:
	echo Importing CA Cert into Client Trust Store - password = password
	keytool -import -keystore certs/client-truststore.jks -trustcacerts -alias server -file certs/server.cer

gen-client-keystore:
	echo Generate Client Keystore and Key - password = password
	keytool -keystore certs/client-keystore.jks -alias client -genkey -keyalg RSA \
 		-keysize 2048 -dname "CN=friends.simpson.org,OU=Simpson friends,O=The Simpsons,C=US" \
        -sigalg "SHA1withRSA"

export-client-cert:
	echo Exporting Client Public Key / Certificate
	keytool -exportcert -keystore certs/client-keystore.jks -alias client -file certs/client.cer
	keytool -exportcert -rfc -keystore certs/client-keystore.jks -alias client -file certs/client.pem

import-server-truststore:
	echo Importing CA Cert into Server Trust Store - password = password
	keytool -import -keystore certs/server-truststore.jks -trustcacerts -alias client -file certs/client.cer

display-client-keystore:
	echo List Keys in Client Keystore
	keytool -list -keystore certs/client-keystore.jks
	echo Keyprinter Dump
	java -cp build/libs/tutorial-all.jar tools.KeyPrinter password certs/client-keystore.jks client password

display-client-truststore:
	echo List Keys in Client Truststore
	keytool -list -keystore certs/client-truststore.jks 
	echo Keyprinter Dump
	java -cp build/libs/tutorial-all.jar tools.KeyPrinter password certs/client-truststore.jks server

display-server-keystore:
	echo List All Keys in Server Keystore
	keytool -list -keystore certs/server-keystore.jks 
	echo Keyprinter Dump
	java -cp build/libs/tutorial-all.jar tools.KeyPrinter password certs/server-keystore.jks server password

display-server-truststore:
	echo List Keys in Server Truststore
	keytool -list -keystore certs/server-truststore.jks 
	echo Keyprinter Dump
	java -cp build/libs/tutorial-all.jar tools.KeyPrinter password certs/server-truststore.jks client

display-client-cert:
	echo Display Client Certificate File
	keytool -printcert -file certs/client.cer

display-server-cert:
	echo Display Server Certificate File
	keytool -printcert -file certs/server.cer


##
## Mail Server Security
##

run-ssl-server: 
	echo Starting SSL Service at:  https://localhost:8183/accounts/chunkylover53/mails/123
	java -cp build/libs/tutorial-all.jar security.ssl.MailServerApplication

run-ssl-client: 
	echo Connecting to SSL Service at:  https://localhost:8183/accounts/chunkylover53/mails/123
	java -cp build/libs/tutorial-all.jar security.ssl.MailClient

run-ssl-basic-server: 
	echo Starting SSL Service at:  https://localhost:8183/accounts/chunkylover53/mails/123
	java -cp build/libs/tutorial-all.jar security.basic.MailServerApplication

run-ssl-basic-client: 
	echo Connecting to SSL Service at:  https://localhost:8183/accounts/chunkylover53/mails/123
	java -cp build/libs/tutorial-all.jar security.basic.MailClient

run-ssl-digest-server: 
	echo Starting SSL Service at:  https://localhost:8183/accounts/chunkylover53/mails/123
	java -cp build/libs/tutorial-all.jar security.digest.MailServerApplication

run-ssl-digest-client: 
	echo Connecting to SSL Service at:  https://localhost:8183/accounts/chunkylover53/mails/123
	java -cp build/libs/tutorial-all.jar security.digest.MailClient

run-ssl-secret-server: 
	echo Starting SSL Service at:  https://localhost:8183/accounts/chunkylover53/mails/123
	java -cp build/libs/tutorial-all.jar security.secret.MailServerApplication

run-ssl-secret-client: 
	echo Connecting to SSL Service at:  https://localhost:8183/accounts/chunkylover53/mails/123
	java -cp build/libs/tutorial-all.jar security.secret.MailClient

run-ssl-cert-server: 
	echo Starting SSL Service at:  https://localhost:8183/accounts/chunkylover53/mails/123
	java -cp build/libs/tutorial-all.jar security.cert.MailServerApplication

run-ssl-cert-client: 
	echo Connecting to SSL Service at:  https://localhost:8183/accounts/chunkylover53/mails/123
	java -cp build/libs/tutorial-all.jar security.cert.MailClient

gen-client-p12:
	keytool -importkeystore -srckeystore certs/client-keystore.jks -destkeystore certs/client.p12 \
	-srcstoretype JKS -deststoretype PKCS12 -deststorepass password -srcalias client -destalias client

curl-test-basic:
	curl -u chunkylover53:pwd https://localhost:8183/accounts/chunkylover53/mails/123

curl-test-digest:
	curl --digest -u chunkylover53:pwd https://localhost:8183/accounts/chunkylover53/mails/123

curl-test-cert:
	curl -E ./certs/client.p12:password https://localhost:8183/accounts/chunkylover53/mails/123

curl-test-basic-cert:
	curl -u chunkylover53:pwd -E ./certs/client.p12:password https://localhost:8183/accounts/chunkylover53/mails/123

curl-test-cert-error:
	curl -X GET https://localhost:8183/accounts/chunkylover53/mails/123







