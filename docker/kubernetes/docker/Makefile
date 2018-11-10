
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

run-helloworld:
	 kubectl run hello-nginx --image=nginx --port=80

list-deployments:
	kubectl get deployments

describe-pod:
	kubectl describe pod $(pod)

expose-helloworld-service:
	kubectl expose deployment hello-nginx --type=NodePort

list-services:
	kubectl get services

describe-service:
	kubectl describe service $(service)

scale-helloworld-service:
	 kubectl scale --replicas=3 deployment/hello-nginx

start-api-proxy:
	kubectl proxy --port=8080 

ping-api:
	curl -X GET http://localhost:8080/api

	
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









