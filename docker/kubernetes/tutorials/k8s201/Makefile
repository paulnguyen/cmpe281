
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

# Pod (with Labels) 

pod-create:
	kubectl create -f pod-labels.yaml

pod-get:
	kubectl get pods -l app=nginx

pod-delete:
	kubectl delete pod -l app=nginx

# Deployments 

deployment-create:
	kubectl create -f deployment.yaml

deployment-get:
	kubectl get deployment

deployment-get-pods:
	kubectl get pods -l app=nginx

deployment-upgrade:
	kubectl apply -f deployment-update.yaml

deployment-delete:
	kubectl delete deployment nginx-deployment

# Services

service-create:
	kubectl create -f service.yaml

service-get-list:
	kubectl get services

service-get-ip:
	kubectl get service nginx-service -o wide

service-delete:
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







