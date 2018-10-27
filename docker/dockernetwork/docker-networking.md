

# Docker Networking

* https://docs.docker.com/network/
* https://docs.docker.com/network/bridge/
* https://docs.docker.com/network/host/
* https://docs.docker.com/network/overlay/
* https://docs.docker.com/network/links/  (legacy)


# Using the Host Network

* https://docs.docker.com/network/network-tutorial-host/


* Create and start the container as a detached process. The --rm option means to remove the container once it exits/stops. The -d flag means to start the container detached (in the background).

  docker run --rm -d --network host --name my_nginx nginx

* Access Nginx by browsing to:

   http://localhost:80/  

* Check Network

  ip addr show
  netstat | grep 80

* Stop Container

  docker container stop my_nginx


# Using Default Bridge Network

* https://docs.docker.com/network/network-tutorial-standalone/

* List current docker networks:

  docker network ls

  NETWORK ID          NAME                DRIVER              SCOPE
  3b1d2a022b3a        bridge              bridge              local
  15c7a001a8fa        host                host                local
  3f71b45ec340        none                null                local

* Start two alpine containers running ash, which is Alpine’s default shell rather than bash. The -dit flags mean to start the container detached (in the background), interactive (with the ability to type into it), and with a TTY (so you can see the input and output). Since you are starting it detached, you won’t be connected to the container right away. Instead, the container’s ID will be printed. Because you have not specified any --network flags, the containers connect to the default bridge network.

  docker run -dit --name alpine1 alpine ash
  docker run -dit --name alpine2 alpine ash

* Check that both containers are actually started:

  docker container ls

* Inspect the bridge network to see what containers are connected to it.

  docker network inspect bridge

  Containers": {
            "1acd5f38adfbd77d09a2f1e0aee355fdaa862f0a506d4aac770051bff258a2a5": {
                "Name": "alpine1",
                "EndpointID": "17efadd5a5574a529dc195c1541488db29a5c56d913654fdcb2ef20819612279",
                "MacAddress": "02:42:ac:11:00:02",
                "IPv4Address": "172.17.0.2/16",
                "IPv6Address": ""
            },
            "58e76d33dc2dde8d2676cb8afa09ca9ed5fc24d081656cd7253d9f16e3e75492": {
                "Name": "alpine2",
                "EndpointID": "146e4e521f677629d89bbaedf516cc46658ac901e858cd82b3e2df13f4aefc06",
                "MacAddress": "02:42:ac:11:00:03",
                "IPv4Address": "172.17.0.3/16",
                "IPv6Address": ""
            }
        }

* Use the docker attach command to connect to alpine1.

  docker attach alpine1
  ip addr show
  ping -c 2 google.com
  ping -c 2 172.17.0.3

  cat /etc/hosts

    127.0.0.1 localhost
    ::1 localhost ip6-localhost ip6-loopback
    fe00::0 ip6-localnet
    ff00::0 ip6-mcastprefix
    ff02::1 ip6-allnodes
    ff02::2 ip6-allrouters
    172.17.0.2  1acd5f38adfb

  ping -c 2 alpine2
  ping -c 2 1acd5f38adfb


# Using User Defined Bridge Networks

* https://docs.docker.com/network/network-tutorial-standalone/

* Create the alpine-net network. You do not need the --driver bridge flag since it’s the default, but this example shows how to specify it.

  docker network create --driver bridge alpine-net

* List Docker’s networks:

   docker network ls

* Inspect the alpine-net network. This shows you its IP address and the fact that no containers are connected to it:

  docker network inspect alpine-net


* Create your four containers. Notice the --network flags. You can only connect to one network during the docker run command, so you need to use docker network connect afterward to connect alpine4 to the bridge network as well.

  docker run -dit --name alpine1 --network alpine-net alpine ash
  docker run -dit --name alpine2 --network alpine-net alpine ash
  docker run -dit --name alpine3 alpine ash
  docker run -dit --name alpine4 --network alpine-net alpine ash
  docker network connect bridge alpine4

* Verify that all containers are running:

  docker container ls

* Inspect the bridge network and the alpine-net network again:

  docker network inspect bridge

    "Containers": {
            "4955f4264188bd5a4ac1dec495874aa5931e17d2f55e0794f7edb8c34b087c4a": {
                "Name": "alpine4",
                "EndpointID": "fb1a7addc9c55f46becd535f715e2acf0d821c2ad6d6130b009948d943a09789",
                "MacAddress": "02:42:ac:11:00:03",
                "IPv4Address": "172.17.0.3/16",
                "IPv6Address": ""
            },
            "af6853062b0d01b414728c67616b9e71df89b1f8fe10d692d025589aaea8b636": {
                "Name": "alpine3",
                "EndpointID": "aba20aaf903cdfe93cb927ba2845185f6de45c00e97a3777e538980eeaa30ec1",
                "MacAddress": "02:42:ac:11:00:02",
                "IPv4Address": "172.17.0.2/16",
                "IPv6Address": ""
            }
        },

  docker network inspect alpine-net

  "Containers": {
            "1927af629fad17c97b8a993e1fa3afef79d8571ef91c004b68647a44b24bc4b4": {
                "Name": "alpine1",
                "EndpointID": "f6cbc7bcfd87e19784b9c97979dc27d30ab2d5ae0dbee7a79f3ba098618862e1",
                "MacAddress": "02:42:ac:12:00:02",
                "IPv4Address": "172.18.0.2/16",
                "IPv6Address": ""
            },
            "4955f4264188bd5a4ac1dec495874aa5931e17d2f55e0794f7edb8c34b087c4a": {
                "Name": "alpine4",
                "EndpointID": "5221604e6ba484784ef50a749534ac6e24152747bf0a1d395501d772c7b582f0",
                "MacAddress": "02:42:ac:12:00:04",
                "IPv4Address": "172.18.0.4/16",
                "IPv6Address": ""
            },
            "8231f77999f47911851ff5a8387739109872e1f3ed75e88af405c43455b57ed9": {
                "Name": "alpine2",
                "EndpointID": "b2ad6857fd6589a89984f07290f7edd11debe0da67e1621722cbcf292329fc13",
                "MacAddress": "02:42:ac:12:00:03",
                "IPv4Address": "172.18.0.3/16",
                "IPv6Address": ""
            }
        },
 
* On user-defined networks like alpine-net, containers can not only communicate by IP address, but can also resolve a container name to an IP address. This capability is called automatic service discovery. Let’s connect to alpine1 and test this out. alpine1 should be able to resolve alpine2 and alpine4 (and alpine1, itself) to IP addresses.

  docker container attach alpine1
  ping -c 2 alpine2




# Using Overlay Networks

* https://docs.docker.com/network/network-tutorial-overlay/









