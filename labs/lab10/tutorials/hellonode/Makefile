

docker-images:
	docker images

docker-build-v1:
	docker build -t hello-node:v1 .

docker-build-v2:
	docker build -t hello-node:v2 .

deploy-v1:
	kubectl run hello-node --image=hello-node:v1 --port=8080

deploy-v2:
	kubectl set image deployment/hello-node hello-node=hello-node:v2

view:
	kubectl get deployments

pods:
	kubectl get pods

events:
	kubectl get events

config:
	kubectl config view

service-create:
	kubectl expose deployment hello-node --type=LoadBalancer

service-get:
	kubectl get services

service-view:
	minikube service hello-node

logs:
	kubectl logs $(pod)

clean:
	kubectl delete service hello-node
	kubectl delete deployment hello-node
	docker rmi hello-node:v1 hello-node:v2 -f



