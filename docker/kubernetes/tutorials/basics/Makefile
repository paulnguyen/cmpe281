
##
## Kubernetes Basics 
##

clean-up:
	kubectl delete service -l run=kubernetes-bootcamp

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

# Deployments

deploy-bootcamp:
	kubectl run kubernetes-bootcamp --image=gcr.io/google-samples/kubernetes-bootcamp:v1 --port=8080

deploy-get-deployments:
	kubectl get deployments

deploy-get-pods:
	kubectl get pods

deploy-desc-pod:
	kubectl describe pods $(pod)

deploy-pod-shell:
	kubectl exec -it $(pod) -- /bin/bash

deploy-pod-env:
	kubectl exec $(pod) env

deploy-pod-logs:
	kubectl logs -f $(pod)

# Services

services:
	kubectl get services

services-create-node-port:
	kubectl expose deployment/kubernetes-bootcamp --type="NodePort" --port 8080

services-describe:
	kubectl describe services/kubernetes-bootcamp

services-test:
	curl http://localhost:$(port)

services-delete:
	kubectl delete service -l run=kubernetes-bootcamp

# Scale Deployments 

scale-up:
	kubectl scale deployments/kubernetes-bootcamp --replicas=4

scale-pods:
	kubectl get pods -o wide

scale-get-deployments:
	kubectl get deployments

scale-describe-deployment:
	kubectl describe deployments/kubernetes-bootcamp

scale-desc-services:
	kubectl describe services/kubernetes-bootcamp

scale-create-lb:
	kubectl expose deployment/kubernetes-bootcamp --port=80 --target-port=8080 --name=bootcamp-lb --type=LoadBalancer

scale-test:
	curl http://localhost

scale-down:
	kubectl scale deployments/kubernetes-bootcamp --replicas=2


# Rolling Update

rolling-get-deployments:
	kubectl get deployments

rolling-get-pods:
	kubectl get pods

rolling-desc-pods:
	kubectl get pods
	kubectl describe pods | grep Image

rolling-update-image:
	kubectl set image deployments/kubernetes-bootcamp kubernetes-bootcamp=jocatalin/kubernetes-bootcamp:v2

rolling-check-status:
	kubectl rollout status deployments/kubernetes-bootcamp

rolling-rollback:
	kubectl rollout undo deployments/kubernetes-bootcamp









