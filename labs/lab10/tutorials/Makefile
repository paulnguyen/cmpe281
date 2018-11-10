
##
## Kubernetes Basics & Minikube
##

start:
	minikube start 

stop:
	minikube stop

list:
	kubectl get pods --all-namespaces

cluster:
	kubectl cluster-info

gui:
	minikube dashboard

nodes:
	kubectl get nodes

docker-env:
	minikube docker-env

docker-set:
	# Run this:
	# eval $(minikube docker-env)
	
docker-unset:
	# Run this:
	# eval $(minikube docker-env -u)

docker-images:
	docker images

list-deployments:
	kubectl get deployments

check-api:
	curl http://localhost:8001/api/v1/proxy/namespaces/default/pods/kubernetes-bootcamp/

proxy:
	kubectl proxy

help-list-resources:
	kubectl get --help

help-show-resource:
	kubectl describe --help

help-container-logs:
	kubectl logs --help

help-container-cmd:
	kubectl exec --help

##
## Kubernetes 101	
##

create-pod:
	kubectl create -f pod-nginx.yaml

get-pod:
	kubectl get pod nginx

pod-shell:
	kubectl exec -it nginx -- /bin/bash

list-pods:
	kubectl get pods

delete-pod:
	kubectl delete pod nginx

create-redis:
	kubectl create -f pod-redis.yaml

delete-redis:
	kubectl delete pod redis

##
## Kubernetes 201	
##	

create-labeled-pod:
	kubectl create -f pod-labels.yaml

list-labeled-pod:
	kubectl get pods -l app=nginx

delete-labeled-pod:
	kubectl delete pod -l app=nginx

deployment-create:
	kubectl create -f deployment.yaml

deployment-list:
	kubectl get deployment

deployment-pods:
	kubectl get pods -l app=nginx

deployment-upgrade:
	kubectl apply -f deployment-update.yaml

deployment-delete:
	kubectl delete deployment nginx-deployment

service-create:
	kubectl create -f service.yaml

service-list:
	kubectl get services

service-ip:
	export SERVICE_IP=$(kubectl get service nginx-service -o go-template='{{.spec.clusterIP}}')
	export SERVICE_PORT=$(kubectl get service nginx-service -o go-template='{{(index .spec.ports 0).port}}')
	echo "$SERVICE_IP:$SERVICE_PORT"

service-delete:
	kubectl delete service nginx-service









