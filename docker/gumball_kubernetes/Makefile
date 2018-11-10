
##
## Kubernetes (Docker for Mac)
##

version:
	kubectl version

cluster:
	kubectl cluster-info

config:
	kubectl config view

nodes:
	kubectl get nodes

list-pods:
	kubectl get pods

list-all-pods:
	kubectl get pods --all-namespaces

list-system-pods:
	kubectl get pods --namespace=kube-system

install-dashboard:
	kubectl create -f kubernetes-dashboard.yaml

run-dashboard:
	kubectl port-forward $(pod) 8443:8443 --namespace=kube-system

start-api-proxy:
	kubectl proxy --port=8080 

list-deployments:
	kubectl get deployments

describe-pod:
	kubectl describe pod $(pod)

stack-up:
	docker stack deploy --compose-file docker-compose.yml gumball_stack

stack-services:	
	docker stack services gumball_stack

stack-down:
	docker stack rm gumball_stack

create-namespace:
	kubectl create -f kubernetes-namespace.yaml

stack-up-namespace:
	docker stack deploy --namespace gumball --compose-file docker-compose.yml gumball_stack

stack-down-namespace:
	docker stack rm --namespace gumball gumball_stack

kube-namespace-services:
	kubectl get services -n gumball 

docker-ps:
	 docker ps --all --format "table {{.ID}}\t{{.Names}}\t{{.Image}}\t{{.Status}}\t"

docker-ps-ports:
	 docker ps --all --format "table {{.Names}}\t{{.Ports}}\t"

test-ping:
	curl localhost:9000/ping

test-get-inventory:
	curl localhost:9000/gumball

test-update-inventory:
	curl -X PUT \
  	http://localhost:9000/gumball \
  	-H 'Content-Type: application/json' \
  	-d '{ \
  		"CountGumballs": 1000 }' 

test-place-order:
	curl -X POST \
  	http://localhost:9000/order \
  	-H 'Content-Type: application/json'

test-order-status:
	curl -X GET \
  	http://localhost:9000/order \
  	-H 'Content-Type: application/json'

test-process-order:
	curl -X POST \
  	http://localhost:9000/orders \
  	-H 'Content-Type: application/json'






