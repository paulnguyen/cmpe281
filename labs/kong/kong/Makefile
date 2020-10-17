
all: clean 

clean:
	docker stop kong
	docker rm kong 
	docker stop kong-database
	docker rm kong-database
	docker stop starbucks
	docker rm starbucks

docker-shell:
	docker exec -it starbucks bash 

kong-shell:
	docker exec -it kong bash 

network-create:
	docker network create --driver bridge kong-network

network-inspect:
	docker network inspect kong-network

starbucks:
	docker run -d --name starbucks --network kong-network -td starbucks

#
# Kong v0.9.9
#

kong-v1-db:
	docker run -d --name kong-database --network kong-network -p 9042:9042 cassandra:2.2

kong-v1:
	docker run -d --name kong \
	          --network kong-network \
              -e "KONG_DATABASE=cassandra" \
              -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
              -e "KONG_PG_HOST=kong-database" \
              -p 8000:8000 \
              -p 8443:8443 \
              -p 8001:8001 \
              -p 7946:7946 \
              -p 7946:7946/udp \
              kong:0.9.9

#
# Kong v2.0.x
#

kong-v2-db:
	docker run -d --name kong-database \
               --network=kong-network \
               -p 5432:5432 \
               -e "POSTGRES_USER=kong" \
               -e "POSTGRES_DB=kong" \
               -e "POSTGRES_PASSWORD=kong" \
               postgres:9.6

kong-v2-db-setup:
	docker run --rm \
     --network=kong-network \
     -e "KONG_DATABASE=postgres" \
     -e "KONG_PG_HOST=kong-database" \
     -e "KONG_PG_USER=kong" \
     -e "KONG_PG_PASSWORD=kong" \
     -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
     kong:2 kong migrations bootstrap

kong-v2:
	docker run -d --name kong \
     --network=kong-network \
     -e "KONG_DATABASE=postgres" \
     -e "KONG_PG_HOST=kong-database" \
     -e "KONG_PG_USER=kong" \
     -e "KONG_PG_PASSWORD=kong" \
     -e "KONG_CASSANDRA_CONTACT_POINTS=kong-database" \
     -e "KONG_PROXY_ACCESS_LOG=/dev/stdout" \
     -e "KONG_ADMIN_ACCESS_LOG=/dev/stdout" \
     -e "KONG_PROXY_ERROR_LOG=/dev/stderr" \
     -e "KONG_ADMIN_ERROR_LOG=/dev/stderr" \
     -e "KONG_ADMIN_LISTEN=0.0.0.0:8001, 0.0.0.0:8444 ssl" \
     -p 8000:8000 \
     -p 8443:8443 \
     -p 127.0.0.1:8001:8001 \
     -p 127.0.0.1:8444:8444 \
     kong:2


#
# Install Curl 
#

install-curl:
	apt-get update; apt-get install curl
	yum install curl
	apk --no-cache add curl






	
