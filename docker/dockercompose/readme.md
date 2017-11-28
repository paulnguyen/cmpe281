
  _____                __ _ _                                 
 |_   _| __ __ _  ___ / _(_) | __                             
   | || '__/ _` |/ _ \ |_| | |/ /                             
   | || | | (_| |  __/  _| |   <                              
   |_||_|  \__,_|\___|_| |_|_|\_\  



# Resources

	https://traefik.io/ (Go Based HTTP reverse proxy and load balancer)
	https://docs.traefik.io/  (Getting Started)


# Getting Started

    https://docs.traefik.io/  (Getting Started)

## Run Traefik via Local Docker Imgage

    docker run -d -p 8080:8080 -p 80:80 -v $PWD/traefik.toml:/etc/traefik/traefik.toml traefik

## Run Traefik via Docker Compose (traefik/docker-compose.yml):

    version: '2'
    services:
      proxy:
        image: traefik
        command: --web --docker --docker.domain=docker.localhost --logLevel=DEBUG
        networks:
          - webgateway
        ports:
          - "80:80"
          - "8080:8080"
        volumes:
          - /var/run/docker.sock:/var/run/docker.sock
          - /dev/null:/traefik.toml
    networks:
      webgateway:
        driver: bridge

## Start up via Docker Compose

  docker-compose up -d

  Note: Traefik web console:  http://localhost:8080 

## Sample Traefik Backend (whoami/docker-compose.yml):

    version: '2'
    services:
      whoami:
        image: emilevauge/whoami
        networks:
          - web
        labels:
          - "traefik.backend=whoami"
          - "traefik.frontend.rule=Host:whoami.docker.localhost"
    networks:
      web:
        external:
        name: traefik_webgateway

## Start and Scale Backend

  docker-compose up -d
  docker-compose scale whoami=2


## Test & Load Balance

  curl -H Host:whoami.docker.localhost http://127.0.0.1













