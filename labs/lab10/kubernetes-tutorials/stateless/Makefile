
##
## Kubernetes 
##

start:
	minikube start 

stop:
	minikube stop

list:
	kubectl get pods

list-all:
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

deploy-list:
	kubectl get deployments

#
# Nginx Deployment
#

nginx-deploy:
	kubectl apply -f deployment.yaml

nginx-display:
	kubectl describe deployment nginx-deployment

nginx-pods-list:
	kubectl get pods -l app=nginx

nginx-deploy-update:
	kubectl apply -f deployment-update.yaml

nginx-deploy-scale:
	kubectl apply -f deployment-scale.yaml

nginx-delete:
	kubectl delete deployment nginx-deployment


#
# Redis Deployment
#

redis-deploy:
	kubectl apply -f redis-master-deployment.yaml

redis-pods-list:
	kubectl get pods -l app=redis

redis-pods-logs:
	kubectl logs -f $(pod)

redis-master:
	kubectl apply -f redis-master-service.yaml

redis-services:
	kubectl get service

redis-slaves:
	kubectl apply -f redis-slave-deployment.yaml

redis-delete:
	kubectl delete deployment -l app=redis
	kubectl delete service -l app=redis






