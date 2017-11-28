

up:
	docker-compose up -d
	docker-compose scale whoami=2

down:
	docker-compose down

network-ls:
	docker network ls

network-create:
	docker network create web

network-prune:
	docker network prune
	
test:
	curl -H Host:whoami.docker.localhost http://127.0.0.1




