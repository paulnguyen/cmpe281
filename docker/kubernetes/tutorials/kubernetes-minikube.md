

	  _  __     _                          _            
	 | |/ /    | |                        | |           
	 | ' /_   _| |__   ___ _ __ _ __   ___| |_ ___  ___ 
	 |  <| | | | '_ \ / _ \ '__| '_ \ / _ \ __/ _ \/ __|
	 | . \ |_| | |_) |  __/ |  | | | |  __/ ||  __/\__ \
	 |_|\_\__,_|_.__/ \___|_|  |_| |_|\___|\__\___||___/
	                                                    
                                                                                        
                                                    

# Kubernetes Getting Started

* http://kubernetes.io/docs/
* https://kubernetes.io/docs/tutorials/kubernetes-basics/
* https://kubernetes.io/docs/getting-started-guides/aws/
* https://docs.docker.com/docker-cloud/migration/kube-primer/


# Kubernetes Management Tools
	
* https://kubernetes.io/docs/tools/


# References

* https://www.docker.com/
* https://kubernetes.io
                                                             

# Kubernetes Local Sandbox - Using Minikube

* https://kubernetes.io/docs/getting-started-guides/minikube/
* https://kubernetes.io/docs/tasks/tools/install-minikube/
* https://kubernetes-v1-4.github.io/docs/getting-started-guides/minikube

## Step 1.  Install VirtualBox

* https://www.virtualbox.org/wiki/Downloads

## Step 2.  Install Minikube

* https://github.com/kubernetes/minikube/releases

OSX:

	curl -Lo minikube https://storage.googleapis.com/minikube/releases/v0.25.2/minikube-darwin-amd64 && chmod +x minikube && sudo mv minikube /usr/local/bin/


Linux:

	curl -Lo minikube https://storage.googleapis.com/minikube/releases/v0.25.2/minikube-linux-amd64 && chmod +x minikube && sudo mv minikube /usr/local/bin/


## Step 3.  Install Kubectl (OSX Example)

* https://kubernetes.io/docs/tasks/tools/install-kubectl
* https://kubernetes.io/docs/tasks/tools/install-kubectl/#install-with-homebrew-on-macos

	brew install kubectl


## Step 4.  Start the Cluster


## Using Minikube's Docker Environment


Minikube contains a built-in Docker daemon that for running containers. If
you use another Docker daemon for building your containers, you will have
to publish them to a registry before minikube can pull them. You can use
minikube’s built in Docker daemon to avoid this extra step of pushing your
images. Use the built-in Docker daemon with:

	eval $(minikube docker-env)

This command sets up the Docker environment variables so a Docker client
can communicate with the minikube Docker daemon. Minikube currently
supports only docker version 1.11.1 on the server, which is what is
supported by Kubernetes 1.3. With a newer docker version you’ll get this
issue.

	docker ps  	



# Hello Minikube 

* https://kubernetes.io/docs/tutorials/stateless-application/hello-minikube/


## Node.js Sample App

	var http = require('http');
	var handleRequest = function(request, response) {
	  console.log('Received request for URL: ' + request.url);
	  response.writeHead(200);
	  response.end('Hello World!');
	};
	var www = http.createServer(handleRequest);


## Dockerfile

	FROM node:6.9.2
	EXPOSE 8080
	COPY server.js .
	CMD node server.js


## Build Docker Image

	docker build -t hello-node:v1 .


## Create a Deployment

A Kubernetes Pod is a group of one or more Containers, tied together for
the purposes of administration and networking. The Pod in this tutorial
has only one Container. A Kubernetes Deployment checks on the health of
your Pod and restarts the Pod’s Container if it terminates. Deployments
are the recommended way to manage the creation and scaling of Pods.

Use the kubectl run command to create a Deployment that manages a Pod. The
Pod runs a Container based on your hello-node:v1 Docker image:

	kubectl run hello-node --image=hello-node:v1 --port=8080


## Create a Service

By default, the Pod is only accessible by its internal IP address within
the Kubernetes cluster. To make the hello-node Container accessible from
outside the Kubernetes virtual network, you have to expose the Pod as a
Kubernetes Service.

From your development machine, you can expose the Pod to the public
internet using the kubectl expose command:

	kubectl expose deployment hello-node --type=LoadBalancer


View the Service you just created:

	kubectl get services


The --type=LoadBalancer flag indicates that you want to expose your Service outside of the cluster. On cloud providers that support load balancers, an external IP address would be provisioned to access the Service. On Minikube, the LoadBalancer type makes the Service accessible through the minikube service command.

	minikube service hello-node


## Update your app

Edit your *server.js* file to return a new message:

	response.end('Hello World Again!');

Build a new version of your image (mind the trailing dot):

	docker build -t hello-node:v2 .

Update the image of your Deployment:

	kubectl set image deployment/hello-node hello-node=hello-node:v2

Run your app again to view the new message:

	minikube service hello-node

## Clean Up

Now you can clean up the resources you created in your cluster:

	kubectl delete service hello-node
	kubectl delete deployment hello-node

Optionally, force removal of the Docker images created:

	docker rmi hello-node:v1 hello-node:v2 -f

Optionally, stop the Minikube VM:

	minikube stop
	eval $(minikube docker-env -u)

Optionally, delete the Minikube VM:

	minikube delete


## More

Deployments:

* https://kubernetes.io/docs/concepts/workloads/controllers/deployment/

Run a Stateless Application Using a Deployment:

* https://kubernetes.io/docs/tasks/run-application/run-stateless-application-deployment/

Services:

* https://kubernetes.io/docs/concepts/services-networking/service/

Reference Documentation:

* https://kubernetes.io/docs/reference/

Tools:

* https://kubernetes.io/docs/tools/




