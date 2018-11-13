
##
## Kubernetes (Minikube)
##

start:
	minikube start 

stop:
	minikube stop

config:
	kubectl config view
	
api:
	kubectl proxy --port=8080 

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
	
# kubectl get pods my-pod -o jsonpath --template={.status.podIP}
# kubectl describe <resource-name> <obj-name>
# kubectl apply -f obj.yaml
# kubectl edit <resource-name> <obj-name>
# kubectl delete -f obj.yaml
# kubectl label pods bar color=red
# kubectl label pods bar color-
# kubectl logs <pod-name>
# kubectl exec -it <pod-name> -- bash
# kubectl help
# kubectl help command-name






