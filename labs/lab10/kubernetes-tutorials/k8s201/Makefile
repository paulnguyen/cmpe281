
##
## Kubernetes 201 
##

clean-up:
	kubectl delete pod -l app=nginx
	kubectl delete deployment nginx-deployment
	kubectl delete service nginx-service
	kubectl delete pods pod-with-http-healthcheck
	kubectl delete pods pod-with-tcp-socket-healthcheck	

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

# Pod Labels 

labels-create-pod:
	kubectl create -f pod-labels.yaml

labels-list-using:
	kubectl get pods -l app=nginx

labels-delete-using:
	kubectl delete pod -l app=nginx

# Deployments 

deployments-create:
	kubectl create -f deployment.yaml

deployments-get:
	kubectl get deployment

deployments-get-pods:
	kubectl get pods -l app=nginx

deployments-upgrade:
	kubectl apply -f deployment-update.yaml

deployments-delete:
	kubectl delete deployment nginx-deployment

# Services

services-create:
	kubectl create -f service.yaml

services-get-list:
	kubectl get services

services-get-ip:
	kubectl get service nginx-service -o wide

services-delete:
	kubectl delete service nginx-service


# Health Check Pods 

health-check-example-1:
	kubectl create -f pod-with-http-healthcheck.yaml 

health-check-example-2:
	kubectl create -f pod-with-tcp-socket-healthcheck.yaml 

health-check-list-pods:
	kubectl get pods -o wide

health-check-delete-pods:
	kubectl delete pods pod-with-http-healthcheck
	kubectl delete pods pod-with-tcp-socket-healthcheck







