
##
## Kubernetes 101 
##

clean-up:
	kubectl delete pod nginx
	kubectl delete pod redis

# Basics 

docker-images:
	docker images

version:
	kubectl version

cluster:
	kubectl cluster-info

nodes:
	kubectl get nodes

pods:
	kubectl get pods --all-namespaces

pod-shell:
	kubectl exec -it $(pod) --container $(container) -- /bin/bash

deployments:
	kubectl get deployments

api-proxy:
	kubectl proxy

api-ping:
	curl -X GET http://localhost:8001/api 

api-version:
	curl http://localhost:8001/version

help-list-resources:
	kubectl get --help

help-show-resource:
	kubectl describe --help

help-container-logs:
	kubectl logs --help

help-container-cmd:
	kubectl exec --help

# NGINX Pod 

nginx-pod-create:
	kubectl create -f pod-nginx.yaml

nginx-pod-get:
	kubectl get pod nginx

nginx-pod-get-ip:
	kubectl get pod nginx -o wide

nginx-pod-shell:
	kubectl exec -it nginx -- /bin/bash

nginx-pod-delete:
	kubectl delete pod nginx

# Redis Pod 

redis-pod-create:
	kubectl create -f pod-redis.yaml

redis-pod-get:
	kubectl get pod redis

redis-pod-get-ip:
	kubectl get pod redis -o wide

redis-pod-shell:
	kubectl exec -it redis -- /bin/bash

redis-pod-delete:
	kubectl delete pod redis












