
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
# MySQL Deployment
#

mysql-deploy:
	kubectl create -f mysql-deployment.yaml

mysql-describe:
	kubectl describe deployment mysql

mysql-pods:
	kubectl get pods -l app=mysql

mysql-volume:
	kubectl describe pvc mysql-pv-claim

mysql-console:
	kubectl run -it --rm --image=mysql:5.6 --restart=Never mysql-client -- mysql -h mysql -ppassword

mysql-delete:
	kubectl delete deployment,svc mysql
	kubectl delete pvc mysql-pv-claim

	





