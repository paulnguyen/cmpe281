
## Kubernetes GKE

* Build and push Gumball image to Docker Hub

	use docker.sh
	- login
	- build
	- push

* Run Pod

	kubectl apply -f phptest-pod.yaml
	kubectl get pods
	kubectl describe pods phptest

	k8s / workload / phpterst / expose --> port 80
	kubectl create -f pod-frontend.yaml
	kubectl get services --watch

	curl $host:80/index.html
	curl $host:80/loadtest.php



