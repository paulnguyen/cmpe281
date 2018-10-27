

nginx-start:
	docker run --rm -d --network host --name my_nginx nginx

nginx-stop:
	docker container stop my_nginx

alpine-start:
	docker run -dit --name alpine1 alpine ash
	docker run -dit --name alpine2 alpine ash

alpine1-attach:
	docker attach alpine1

alpine2-attach:
	docker attach alpine2

alpine-stop:
	docker container stop alpine1 alpine2
	docker container rm alpine1 alpine2

alpine-up-4:
	docker run -dit --name alpine1 --network alpine-net alpine ash
	docker run -dit --name alpine2 --network alpine-net alpine ash
	docker run -dit --name alpine3 alpine ash
	docker run -dit --name alpine4 --network alpine-net alpine ash
	docker network connect bridge alpine4

alpine-down-4:
	docker container stop alpine1 alpine2 alpine3 alpine4
	docker container rm alpine1 alpine2 alpine3 alpine4
	docker network rm alpine-net

ubuntu-start:
	docker run -it ubuntu bash

docker-ls:
	docker container ls

network-create:
	docker network create --driver bridge alpine-net

network-ls:
	docker network ls

network-inspect-default:
	docker network inspect bridge

network-inspect-alpine:
	docker network inspect alpine-net

version:
	docker --version

