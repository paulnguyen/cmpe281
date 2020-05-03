

	  _  __     _                          _            
	 | |/ /    | |                        | |           
	 | ' /_   _| |__   ___ _ __ _ __   ___| |_ ___  ___ 
	 |  <| | | | '_ \ / _ \ '__| '_ \ / _ \ __/ _ \/ __|
	 | . \ |_| | |_) |  __/ |  | | | |  __/ ||  __/\__ \
	 |_|\_\__,_|_.__/ \___|_|  |_| |_|\___|\__\___||___/
	                                                    
                                                                                        
                                                   
# Kubernetes Basic 

* https://kubernetes.io/docs/tutorials/kubernetes-basics/

This tutorial provides a walkthrough of the basics of the Kubernetes cluster
orchestration system. Each module contains some background information on
major Kubernetes features and concepts, and includes an interactive online
tutorial. These interactive tutorials let you manage a simple cluster and its
containerized applications for yourself.

# What can Kubernetes do for you?

With modern web services, users expect applications to be available 24/7, and
developers expect to deploy new versions of those applications several times a
day. Containerization helps package software to serve these goals, enabling
applications to be released and updated in an easy and fast way without
downtime. Kubernetes helps you make sure those containerized applications run
where and when you want, and helps them find the resources and tools they need
to work. Kubernetes is a production-ready, open source platform designed with
Google's accumulated experience in container orchestration, combined with
best-of-breed ideas from the community.

# Kubernetes Basics Modules

1. Create a Kubernetes cluster
2. Deploy an app
3. Explore your app
4. Expose your app publicly
5. Scale up your app
6. Update your app

## Module 1 - Create Kubernetes Cluster 

* https://kubernetes.io/docs/tutorials/kubernetes-basics/cluster-intro/

### Objectives

* Learn what a Kubernetes cluster is.
* Learn what Minikube is.
* Start a Kubernetes cluster using an online terminal.

### Kubernetes Clusters

Kubernetes coordinates a highly available cluster of computers that are
connected to work as a single unit. The abstractions in Kubernetes allow you
to deploy containerized applications to a cluster without tying them
specifically to individual machines. To make use of this new model of
deployment, applications need to be packaged in a way that decouples them from
individual hosts: they need to be containerized. Containerized applications
are more flexible and available than in past deployment models, where
applications were installed directly onto specific machines as packages deeply
integrated into the host. Kubernetes automates the distribution and scheduling
of application containers across a cluster in a more efficient way. Kubernetes
is an open-source platform and is production-ready.

A Kubernetes cluster consists of two types of resources:

1. The Master coordinates the cluster
2. Nodes are the workers that run applications

### Master

The Master is responsible for managing the cluster. The master coordinates all activities in your cluster, such as scheduling applications, maintaining applications' desired state, scaling applications, and rolling out new updates.

![Cluster Diagram](images/module_01_cluster.svg)

### Node

A node is a VM or a physical computer that serves as a worker machine in a Kubernetes cluster. Each node has a Kubelet, which is an agent for managing the node and communicating with the Kubernetes master. The node should also have tools for handling container operations, such as Docker or rkt. A Kubernetes cluster that handles production traffic should have a minimum of three nodes.

Masters manage the cluster and the nodes are used to host the running applications.

### Kubernetes API

When you deploy applications on Kubernetes, you tell the master to start the application containers. The master schedules the containers to run on the cluster's nodes. The nodes communicate with the master using the Kubernetes API, which the master exposes. End users can also use the Kubernetes API directly to interact with the cluster.

### Kubernetes Deployments

A Kubernetes cluster can be deployed on either physical or virtual machines. To get started with Kubernetes development, you can use Minikube. Minikube is a lightweight Kubernetes implementation that creates a VM on your local machine and deploys a simple cluster containing only one node. Minikube is available for Linux, macOS, and Windows systems. The Minikube CLI provides basic bootstrapping operations for working with your cluster, including start, stop, status, and delete. For this tutorial, however, you'll use a provided online terminal with Minikube pre-installed.

Now that you know what Kubernetes is, let's go to the online tutorial and start our first cluster!

### Interactive Tutorial - Creating a Cluster 

* https://kubernetes.io/docs/tutorials/kubernetes-basics/cluster-interactive/

#### Start Minikube

	minikube version
	minikube start

Example:

	> minikube version
	minikube version: v0.25.2

#### Check Kubectl Version

	kubectl version

Example:

	> kubectl version
	Client Version:version.Info {
	   Major:"1",
	   Minor:"10",
	   GitVersion:"v1.10.0",
	   GitCommit:"fc32d2f3698e36b93322a3465f63a14e9f0eaead",
	   GitTreeState:"clean",
	   BuildDate:"2018-03-27T00:14:31Z",
	   GoVersion:"go1.9.4",
	   Compiler:"gc",
	   Platform:"darwin/amd64"
	} Server Version:version.Info{
	   Major:"",
	   Minor:"",
	   GitVersion:"v1.9.4",
	   GitCommit:"bee2d1505c4fe820744d26d41ecd3fdd4a3d6546",
	   GitTreeState:"clean",
	   BuildDate:"2018-03-21T21:48:36Z",
	   GoVersion:"go1.9.1",
	   Compiler:"gc",
	   Platform:"linux/amd64"
	}

#### View Cluster Details

We have a running master and a dashboard. The Kubernetes dashboard allows you
to view your applications in a UI. During this tutorial, we’ll be focusing on
the command line for deploying and exploring our application.

	kubectl cluster-info

Example:

	> kubectl cluster-info
	Kubernetes master is running at https://192.168.99.100:8443


#### View Cluster Nodes

To view the nodes in the cluster, run the kubectl get nodes command.  This
command shows all nodes that can be used to host our applications. Now we have
only one node, and we can see that it’s status is ready (it is ready to accept
applications for deployment).

	kubectl get nodes

Example:

	> kubectl get nodes
	NAME       STATUS    ROLES     AGE       VERSION
	minikube   Ready     <none>    14h       v1.9.4


## Module 2 - Deploy an Application

* https://kubernetes.io/docs/tutorials/kubernetes-basics/deploy-intro/

### Objectives

* Learn about application Deployments.
* Deploy your first app on Kubernetes with kubectl.

### Kubernetes Deployments

Once you have a running Kubernetes cluster, you can deploy your containerized
applications on top of it. To do so, you create a Kubernetes Deployment
configuration. The Deployment instructs Kubernetes how to create and update
instances of your application. Once you've created a Deployment, the
Kubernetes master schedules mentioned application instances onto individual
Nodes in the cluster.

Once the application instances are created, a Kubernetes Deployment Controller
continuously monitors those instances. If the Node hosting an instance goes
down or is deleted, the Deployment controller replaces it. This provides a
self-healing mechanism to address machine failure or maintenance.

In a pre-orchestration world, installation scripts would often be used to
start applications, but they did not allow recovery from machine failure. By
both creating your application instances and keeping them running across
Nodes, Kubernetes Deployments provide a fundamentally different approach to
application management.

### Deploying your first Application on Kubernetes

![first app](images/module_02_first_app.svg)

You can create and manage a Deployment by using the Kubernetes command line
interface, Kubectl. Kubectl uses the Kubernetes API to interact with the
cluster. In this module, you'll learn the most common Kubectl commands needed
to create Deployments that run your applications on a Kubernetes cluster.

When you create a Deployment, you'll need to specify the container image for
your application and the number of replicas that you want to run. You can
change that information later by updating your Deployment; Modules 5 and 6 of
the bootcamp discuss how you can scale and update your Deployments.

Applications need to be packaged into one of the supported container formats
in order to be deployed on Kubernetes

For our first Deployment, we'll use a Node.js application packaged in a Docker
container. The source code and the Dockerfile are available in the GitHub
repository for the Kubernetes Bootcamp.

* https://github.com/kubernetes/kubernetes-bootcamp

Now that you know what Deployments are, let's go to the online tutorial and
deploy our first app!

### Check Setup

Check that kubectl is configured to talk to your cluster, by running the
kubectl version command:

	kubectl version

OK, kubectl is installed and you can see both the client and the server
versions. To view the nodes in the cluster, run the kubectl get nodes command:

	kubectl get nodes

Here we see the available nodes (1 in our case). Kubernetes will choose where
to deploy our application based on Node available resources.

### Deploy App

Let’s run our first app on Kubernetes with the kubectl run command. The run
command creates a new deployment. We need to provide the deployment name and
app image location (include the full repository url for images hosted outside
Docker hub). We want to run the app on a specific port so we add the --port
parameter:

	kubectl run kubernetes-bootcamp --image=gcr.io/google-samples/kubernetes-bootcamp:v1 --port=8080

	Great! You just deployed your first application by creating a deployment. This
performed a few things for you:

    searched for a suitable node where an instance of the application could be run (we have only 1 available node)
    scheduled the application to run on that Node
    configured the cluster to reschedule the instance on a new Node when needed

To list your deployments use the get deployments command:

	kubectl get deployments
	
### View App

Pods that are running inside Kubernetes are running on a private, isolated
network. By default they are visible from other pods and services within the
same kubernetes cluster, but not outside that network. When we use kubectl,
we're interacting through an API endpoint to communicate with our application.

We will cover other options on how to expose your application outside the
kubernetes cluster in Module 4.

The kubectl command can create a proxy that will forward communications into the cluster-wide, private network. The proxy can be terminated by pressing control-C and won't show any output while its running.

We will open a second terminal window to run the proxy.

	kubectl proxy

We now have a connection between our host (the online terminal) and the Kubernetes cluster. The proxy enables direct access to the API from these terminals.

You can see all those APIs hosted through the proxy endpoint, now available at through http://localhost:8001. 

For example, we can query the version directly through the API using the curl command:

	curl http://localhost:8001/version


## Module 3 - Viewing Pods and Nodes

* https://kubernetes.io/docs/tutorials/kubernetes-basics/explore-intro/

### Objectives

* Learn about Kubernetes Pods.
* Learn about Kubernetes Nodes.
* Troubleshoot deployed applications.

### Kubernetes Pods

<img src="images/module_03_pods.svg" alt="Pods Overview" style="width: 500px;"/>

When you created a Deployment in Module 2, Kubernetes created a Pod to host
your application instance. A Pod is a Kubernetes abstraction that represents a
group of one or more application containers (such as Docker or rkt), and some
shared resources for those containers. Those resources include:

* Shared storage, as Volumes
* Networking, as a unique cluster IP address
* Information about how to run each container, 
  such as the container image version or specific 
  ports to use

A Pod models an application-specific "logical host" and can contain different
application containers which are relatively tightly coupled. For example, a
Pod might include both the container with your Node.js app as well as a
different container that feeds the data to be published by the Node.js
webserver. The containers in a Pod share an IP Address and port space, are
always co-located and co-scheduled, and run in a shared context on the same
Node.

Pods are the atomic unit on the Kubernetes platform. When we create a
Deployment on Kubernetes, that Deployment creates Pods with containers inside
them (as opposed to creating containers directly). Each Pod is tied to the
Node where it is scheduled, and remains there until termination (according to
restart policy) or deletion. In case of a Node failure, identical Pods are
scheduled on other available Nodes in the cluster.

### Kubernetes Nodes

<img src="images/module_03_nodes.svg" alt="Nodes Overview" style="width: 500px;"/>

A Pod always runs on a Node. A Node is a worker machine in Kubernetes and may
be either a virtual or a physical machine, depending on the cluster. Each Node
is managed by the Master. A Node can have multiple pods, and the Kubernetes
master automatically handles scheduling the pods across the Nodes in the
cluster. The Master's automatic scheduling takes into account the available
resources on each Node.

Every Kubernetes Node runs at least:

* Kubelet, a process responsible for communication between the Kubernetes 
  Master and the Node; it manages the Pods and the containers running on 
  a machine.  

* A container runtime (like Docker, rkt) responsible for pulling the container 
  image from a registry, unpacking the container, and running the application.

Containers should only be scheduled together in a single Pod if they are
tightly coupled and need to share resources such as disk.

### Troubleshooting with kubectl

In Module 2, you used Kubectl command-line interface. You'll continue to use
it in Module 3 to get information about deployed applications and their
environments. The most common operations can be done with the following
kubectl commands:

    kubectl get - list resources
    kubectl describe - show detailed information about a resource
    kubectl logs - print the logs from a container in a pod
    kubectl exec - execute a command on a container in a pod

You can use these commands to see when applications were deployed, what their
current statuses are, where they are running and what their configurations
are.

Now that we know more about our cluster components and the command line, let's
explore our application.


#### Step 1: Check application configuration

Let’s verify that the application we deployed in the previous scenario is
running. We’ll use the kubectl get command and look for existing Pods:

	kubectl get pods

Next, to view what containers are inside that Pod and what images are used to
build those containers we run the describe pods command:

	kubectl describe pods

We see here details about the Pod’s container: IP address, the ports used and
a list of events related to the lifecycle of the Pod.

The output of the describe command is extensive and covers some concepts that
we didn’t explain yet, but don’t worry, they will become familiar by the end
of this bootcamp.

Note: the describe command can be used to get detailed information about most
of the kubernetes primitives: node, pods, deployments. The describe output is
designed to be human readable, not to be scripted against.

Example:

````
macbox:kubernetes pnguyen$ kubectl get pods
NAME                                   READY     STATUS    RESTARTS   AGE
kubernetes-bootcamp-5dbf48f7d4-rcswq   1/1       Running   0          11h
macbox:kubernetes pnguyen$ 

macbox:kubernetes pnguyen$ kubectl describe pods
Name:           kubernetes-bootcamp-5dbf48f7d4-rcswq
Namespace:      default
Node:           minikube/192.168.99.100
Start Time:     Tue, 03 Apr 2018 18:07:20 -0700
Labels:         pod-template-hash=1869049380
                run=kubernetes-bootcamp
Annotations:    <none>
Status:         Running
IP:             172.17.0.4
Controlled By:  ReplicaSet/kubernetes-bootcamp-5dbf48f7d4
Containers:
  kubernetes-bootcamp:
    Container ID:   docker://75d0982a5241ea5aee1c847bbbb19e0ae6b42882a42bbb29a51ce6be76f4328f
    Image:          gcr.io/google-samples/kubernetes-bootcamp:v1
    Image ID:       docker-pullable://gcr.io/google-samples/kubernetes-bootcamp@sha256:0d6b8ee63bb57c5f5b6156f446b3bc3b3c143d233037f3a2f00e279c8fcc64af
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Tue, 03 Apr 2018 18:13:11 -0700
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-vfcgq (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          True 
  PodScheduled   True 
Volumes:
  default-token-vfcgq:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-vfcgq
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     <none>
Events:          <none>
macbox:kubernetes pnguyen$ 
````

#### Step 2: Show the app in the terminal

Recall that Pods are running in an isolated, private network - so we need to
proxy access to them so we can debug and interact with them. To do this, we'll
use the kubectl proxy command to run a proxy in a second terminal window.
Click on the command below to automatically open a new terminal and run the
proxy:

	kubectl proxy

Example:

	macbox:kubernetes pnguyen$ kubectl proxy
	Starting to serve on 127.0.0.1:8001

Now again, we'll get the Pod name and query that pod directly through the
proxy. To get the Pod name and store it in the POD_NAME environment variable:

	export POD_NAME=$(kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}}{{"\n"}}{{end}}')
	echo Name of the Pod: $POD_NAME

Example:

```
	macbox:kubernetes pnguyen$ export POD_NAME=$(kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}}{{"\n"}}{{end}}')

	macbox:kubernetes pnguyen$ echo Name of the Pod: $POD_NAME
	Name of the Pod: kubernetes-bootcamp-5dbf48f7d4-rcswq
```

To see the output of our application, run a curl request.

	curl http://localhost:8001/api/v1/proxy/namespaces/default/pods/$POD_NAME/

The url is the route to the API of the Pod.

Example:

```
macbox:kubernetes pnguyen$ curl http://localhost:8001/api/v1/proxy/namespaces/default/pods/$POD_NAME/
Hello Kubernetes bootcamp! | Running on: kubernetes-bootcamp-5dbf48f7d4-rcswq | v=1
```


#### Step 3: View the container logs

Anything that the application would normally send to STDOUT becomes logs for
the container within the Pod. We can retrieve these logs using the kubectl
logs command:

	kubectl logs $POD_NAME

Note: We don’t need to specify the container name, because we only have one
container inside the pod.

Example:

```
macbox:kubernetes pnguyen$ kubectl logs $POD_NAME
Kubernetes Bootcamp App Started At: 2018-04-04T01:13:11.669Z | Running On:  kubernetes-bootcamp-5dbf48f7d4-rcswq 

Running On: kubernetes-bootcamp-5dbf48f7d4-rcswq | Total Requests: 1 | App Uptime: 43434.914 seconds | Log Time: 2018-04-04T13:17:06.584Z
macbox:kubernetes pnguyen$ 
```


#### Step 4: Executing command on the container

We can execute commands directly on the container once the Pod is up and
running. For this, we use the exec command and use the name of the Pod as a
parameter. Let’s list the environment variables:

	kubectl exec $POD_NAME env

Again, worth mentioning that the name of the container itself can be omitted
since we only have a single container in the Pod.

Example:

```
macbox:kubernetes pnguyen$ kubectl exec $POD_NAME env
PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin
HOSTNAME=kubernetes-bootcamp-5dbf48f7d4-rcswq
KUBERNETES_PORT_443_TCP_PORT=443
KUBERNETES_PORT_443_TCP_ADDR=10.96.0.1
KUBERNETES_SERVICE_HOST=10.96.0.1
KUBERNETES_SERVICE_PORT=443
KUBERNETES_SERVICE_PORT_HTTPS=443
KUBERNETES_PORT=tcp://10.96.0.1:443
KUBERNETES_PORT_443_TCP=tcp://10.96.0.1:443
KUBERNETES_PORT_443_TCP_PROTO=tcp
NPM_CONFIG_LOGLEVEL=info
NODE_VERSION=6.3.1
HOME=/root
macbox:kubernetes pnguyen$ 
```

Next let’s start a bash session in the Pod’s container:

	kubectl exec -ti $POD_NAME bash

We have now an open console on the container where we run our NodeJS
application. The source code of the app is in the server.js file:

	cat server.js

You can check that the application is up by running a curl command:

	curl localhost:8080

Note: here we used localhost because we executed the command inside the NodeJS
container.

To close your container connection type: 

	exit

Example:

```
macbox:kubernetes pnguyen$ kubectl exec -ti $POD_NAME bash
root@kubernetes-bootcamp-5dbf48f7d4-rcswq:/# 

root@kubernetes-bootcamp-5dbf48f7d4-rcswq:/# cat server.js
var http = require('http');
var requests=0;
var podname= process.env.HOSTNAME;
var startTime;
var host;
var handleRequest = function(request, response) {
  response.setHeader('Content-Type', 'text/plain');
  response.writeHead(200);
  response.write("Hello Kubernetes bootcamp! | Running on: ");
  response.write(host);
  response.end(" | v=1\n");
  console.log("Running On:" ,host, "| Total Requests:", ++requests,"| App Uptime:", (new Date() - startTime)/1000 , "seconds", "| Log Time:",new Date());
}
var www = http.createServer(handleRequest);
www.listen(8080,function () {
    startTime = new Date();;
    host = process.env.HOSTNAME;
    console.log ("Kubernetes Bootcamp App Started At:",startTime, "| Running On: " ,host, "\n" );
});

root@kubernetes-bootcamp-5dbf48f7d4-rcswq:/# curl localhost:8080
Hello Kubernetes bootcamp! | Running on: kubernetes-bootcamp-5dbf48f7d4-rcswq | v=1

root@kubernetes-bootcamp-5dbf48f7d4-rcswq:/# exit
exit
macbox:kubernetes pnguyen$ 
```


## Module 4 - Using a Service to Expose Your App

* https://kubernetes.io/docs/tutorials/kubernetes-basics/expose-intro/

### Objectives

* Learn about a Service in Kubernetes
* Understand how labels and LabelSelector objects relate to a Service
* Expose an application outside a Kubernetes cluster using a Service

### Overview of Kubernetes Services

Kubernetes Pods are mortal. Pods in fact have a lifecycle. When a worker node
dies, the Pods running on the Node are also lost. A ReplicationController
might then dynamically drive the cluster back to desired state via creation of
new Pods to keep your application running. As another example, consider an
image-processing backend with 3 replicas. Those replicas are fungible; the
front-end system should not care about backend replicas or even if a Pod is
lost and recreated. That said, each Pod in a Kubernetes cluster has a unique
IP address, even Pods on the same Node, so there needs to be a way of
automatically reconciling changes among Pods so that your applications
continue to function.

A Service in Kubernetes is an abstraction which defines a logical set of Pods
and a policy by which to access them. Services enable a loose coupling between
dependent Pods. A Service is defined using YAML (preferred) or JSON, like all
Kubernetes objects. The set of Pods targeted by a Service is usually
determined by a LabelSelector (see below for why you might want a Service
without including selector in the spec).

Although each Pod has a unique IP address, those IPs are not exposed outside
the cluster without a Service. Services allow your applications to receive
traffic. Services can be exposed in different ways by specifying a type in the
ServiceSpec:

* ClusterIP (default) - Exposes the Service on an internal IP in the cluster. This type makes the Service only reachable from within the cluster.
    
* NodePort - Exposes the Service on the same port of each selected Node in the cluster using NAT. Makes a Service accessible from outside the cluster using <NodeIP>:<NodePort>. Superset of ClusterIP.
    
* LoadBalancer - Creates an external load balancer in the current cloud (if supported) and assigns a fixed, external IP to the Service. Superset of NodePort.
    
* ExternalName - Exposes the Service using an arbitrary name (specified by externalName in the spec) by returning a CNAME record with the name. No proxy is used. This type requires v1.7 or higher of kube-dns.

More information about the different types of Services can be found in the
Using Source IP tutorial. Also see Connecting Applications with Services.

Additionally, note that there are some use cases with Services that involve
not defining selector in the spec. A Service created without selector will
also not create the corresponding Endpoints object. This allows users to
manually map a Service to specific endpoints. Another possibility why there
may be no selector is you are strictly using type: ExternalName.

### Services and Labels

<img src="images/module_04_services.svg" alt="Services" style="width: 400px;"/>

A Service routes traffic across a set of Pods. Services are the abstraction
that allow pods to die and replicate in Kubernetes without impacting your
application. Discovery and routing among dependent Pods (such as the frontend
and backend components in an application) is handled by Kubernetes Services.

Services match a set of Pods using labels and selectors, a grouping primitive
that allows logical operation on objects in Kubernetes. Labels are key/value
pairs attached to objects and can be used in any number of ways:

* Designate objects for development, test, and production
* Embed version tags
* Classify an object using tags

<img src="images/module_04_labels.svg" alt="Labels" style="width: 400px;"/>

Labels can be attached to objects at creation time or later on. They can be
modified at any time. Let's expose our application now using a Service and
apply some labels.

### Interactive Tutorial - Exposing Your App

#### Step 1: Create a new service

Let’s verify that our application is running. We’ll use the kubectl get
command and look for existing Pods:

	kubectl get pods

Next let’s list the current Services from our cluster:

	kubectl get services

We have a Service called kubernetes that is created by default when minikube
starts the cluster. To create a new service and expose it to external traffic
we’ll use the expose command with NodePort as parameter (minikube does not
support the LoadBalancer option yet)

	kubectl expose deployment/kubernetes-bootcamp --type="NodePort" --port 8080

Let’s run again the get services command:

	kubectl get services

We have now a running Service called kubernetes-bootcamp. Here we see that the
Service received a unique cluster-IP, an internal port and an external-IP (the
IP of the Node).

To find out what port was opened externally (by the NodePort option) we’ll run
the describe service command:

	kubectl describe services/kubernetes-bootcamp

Create an environment variable called NODE_PORT that has the value of the Node
port assigned:

	export NODE_PORT=$(kubectl get services/kubernetes-bootcamp -o go-template='{{(index .spec.ports 0).nodePort}}')
	echo NODE_PORT=$NODE_PORT

Now we can test that the app is exposed outside of the cluster using curl, the
IP of the Node and the externally exposed port:

	curl $(minikube ip):$NODE_PORT

And we get a response from the server. The Service is exposed.

Example:

```
macbox:kubernetes pnguyen$ kubectl get pods
NAME                                   READY     STATUS    RESTARTS   AGE
kubernetes-bootcamp-5dbf48f7d4-rcswq   1/1       Running   0          12h
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl expose deployment/kubernetes-bootcamp --type="NodePort" --port 8080
service "kubernetes-bootcamp" exposed
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl get services
NAME                  TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
kubernetes            ClusterIP   10.96.0.1       <none>        443/TCP          1d
kubernetes-bootcamp   NodePort    10.102.42.210   <none>        8080:32136/TCP   51s
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl describe services/kubernetes-bootcamp
Name:                     kubernetes-bootcamp
Namespace:                default
Labels:                   run=kubernetes-bootcamp
Annotations:              <none>
Selector:                 run=kubernetes-bootcamp
Type:                     NodePort
IP:                       10.102.42.210
Port:                     <unset>  8080/TCP
TargetPort:               8080/TCP
NodePort:                 <unset>  32136/TCP
Endpoints:                172.17.0.4:8080
Session Affinity:         None
External Traffic Policy:  Cluster
Events:                   <none>
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ export NODE_PORT=$(kubectl get services/kubernetes-bootcamp -o go-template='{{(index .spec.ports 0).nodePort}}')
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ echo NODE_PORT=$NODE_PORT
NODE_PORT=32136
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ curl $(minikube ip):$NODE_PORT
Hello Kubernetes bootcamp! | Running on: kubernetes-bootcamp-5dbf48f7d4-rcswq | v=1
macbox:kubernetes pnguyen$ 
```

#### Step 2: Using labels

The Deployment created automatically a label for our Pod. With describe
deployment command you can see the name of the label:

	kubectl describe deployment

Let’s use this label to query our list of Pods. We’ll use the kubectl get pods
command with -l as a parameter, followed by the label values:

	kubectl get pods -l run=kubernetes-bootcamp

You can do the same to list the existing services:

	kubectl get services -l run=kubernetes-bootcamp

Get the name of the Pod and store it in the POD_NAME environment variable:

	export POD_NAME=$(kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}}{{"\n"}}{{end}}')
	echo Name of the Pod: $POD_NAME

To apply a new label we use the label command followed by the object type,
object name and the new label:

	kubectl label pod $POD_NAME app=v1

This will apply a new label to our Pod (we pinned the application version to
the Pod), and we can check it with the describe pod command:

	kubectl describe pods $POD_NAME

We see here that the label is attached now to our Pod. And we can query now
the list of pods using the new label:

	kubectl get pods -l app=v1

And we see the Pod.

Example:

```
macbox:kubernetes pnguyen$ kubectl describe deployment
Name:                   kubernetes-bootcamp
Namespace:              default
CreationTimestamp:      Tue, 03 Apr 2018 18:07:20 -0700
Labels:                 run=kubernetes-bootcamp
Annotations:            deployment.kubernetes.io/revision=1
Selector:               run=kubernetes-bootcamp
Replicas:               1 desired | 1 updated | 1 total | 1 available | 0 unavailable
StrategyType:           RollingUpdate
MinReadySeconds:        0
RollingUpdateStrategy:  1 max unavailable, 1 max surge
Pod Template:
  Labels:  run=kubernetes-bootcamp
  Containers:
   kubernetes-bootcamp:
    Image:        gcr.io/google-samples/kubernetes-bootcamp:v1
    Port:         8080/TCP
    Host Port:    0/TCP
    Environment:  <none>
    Mounts:       <none>
  Volumes:        <none>
Conditions:
  Type           Status  Reason
  ----           ------  ------
  Available      True    MinimumReplicasAvailable
OldReplicaSets:  <none>
NewReplicaSet:   kubernetes-bootcamp-5dbf48f7d4 (1/1 replicas created)
Events:          <none>
macbox:kubernetes pnguyen$ kubectl get pods -l run=kubernetes-bootcamp
NAME                                   READY     STATUS    RESTARTS   AGE
kubernetes-bootcamp-5dbf48f7d4-rcswq   1/1       Running   0          12h
macbox:kubernetes pnguyen$ kubectl get services -l run=kubernetes-bootcamp
NAME                  TYPE       CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
kubernetes-bootcamp   NodePort   10.102.42.210   <none>        8080:32136/TCP   7m
macbox:kubernetes pnguyen$ export POD_NAME=$(kubectl get pods -o go-template --template '{{range .items}}{{.metadata.name}}{{"\n"}}{{end}}')
macbox:kubernetes pnguyen$ echo Name of the Pod: $POD_NAME
Name of the Pod: kubernetes-bootcamp-5dbf48f7d4-rcswq
macbox:kubernetes pnguyen$ kubectl label pod $POD_NAME app=v1
pod "kubernetes-bootcamp-5dbf48f7d4-rcswq" labeled
macbox:kubernetes pnguyen$ kubectl describe pods $POD_NAME
Name:           kubernetes-bootcamp-5dbf48f7d4-rcswq
Namespace:      default
Node:           minikube/192.168.99.100
Start Time:     Tue, 03 Apr 2018 18:07:20 -0700
Labels:         app=v1
                pod-template-hash=1869049380
                run=kubernetes-bootcamp
Annotations:    <none>
Status:         Running
IP:             172.17.0.4
Controlled By:  ReplicaSet/kubernetes-bootcamp-5dbf48f7d4
Containers:
  kubernetes-bootcamp:
    Container ID:   docker://75d0982a5241ea5aee1c847bbbb19e0ae6b42882a42bbb29a51ce6be76f4328f
    Image:          gcr.io/google-samples/kubernetes-bootcamp:v1
    Image ID:       docker-pullable://gcr.io/google-samples/kubernetes-bootcamp@sha256:0d6b8ee63bb57c5f5b6156f446b3bc3b3c143d233037f3a2f00e279c8fcc64af
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Tue, 03 Apr 2018 18:13:11 -0700
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-vfcgq (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          True 
  PodScheduled   True 
Volumes:
  default-token-vfcgq:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-vfcgq
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     <none>
Events:          <none>
macbox:kubernetes pnguyen$ kubectl get pods -l app=v1
NAME                                   READY     STATUS    RESTARTS   AGE
kubernetes-bootcamp-5dbf48f7d4-rcswq   1/1       Running   0          12h
macbox:kubernetes pnguyen$ 
```

#### Step 3: Deleting a service

To delete Services you can use the delete service command. Labels can be used
also here:

	kubectl delete service -l run=kubernetes-bootcamp

Confirm that the service is gone:

	kubectl get services

This confirms that our Service was removed. To confirm that route is not
exposed anymore you can curl the previously exposed IP and port:

	curl $(minikube ip):$NODE_PORT

This proves that the app is not reachable anymore from outside of the cluster.
You can confirm that the app is still running with a curl inside the pod:

	kubectl exec -ti $POD_NAME curl localhost:8080

We see here that the application is up.

Example:

```
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl delete service -l run=kubernetes-bootcamp
service "kubernetes-bootcamp" deleted
macbox:kubernetes pnguyen$ kubectl get services
NAME         TYPE        CLUSTER-IP   EXTERNAL-IP   PORT(S)   AGE
kubernetes   ClusterIP   10.96.0.1    <none>        443/TCP   1d
macbox:kubernetes pnguyen$ curl $(minikube ip):$NODE_PORT
curl: (7) Failed to connect to 192.168.99.100 port 32136: Connection refused
macbox:kubernetes pnguyen$ kubectl exec -ti $POD_NAME curl localhost:8080
Hello Kubernetes bootcamp! | Running on: kubernetes-bootcamp-5dbf48f7d4-rcswq | v=1
macbox:kubernetes pnguyen$ 
```

## Module 5 - Running Multiple Instances of Your App

* https://kubernetes.io/docs/tutorials/kubernetes-basics/scale-intro/
* https://kubernetes.io/docs/tasks/run-application/horizontal-pod-autoscale/

### Objectives

* Scale an app using kubectl.

### Scaling an application

In the previous modules we created a Deployment, and then exposed it publicly
via a Service. The Deployment created only one Pod for running our
application. When traffic increases, we will need to scale the application to
keep up with user demand.

Scaling is accomplished by changing the number of replicas in a Deployment

### Scaling overview

<img src="images/module_05_scaling1.svg" alt="scaling1" style="width: 400px;"/>
<hr/>
<img src="images/module_05_scaling2.svg" alt="scaling2" style="width: 400px;"/>

Scaling out a Deployment will ensure new Pods are created and scheduled to
Nodes with available resources. Scaling in will reduce the number of Pods to
the new desired state. Kubernetes also supports autoscaling of Pods, but it is
outside of the scope of this tutorial. Scaling to zero is also possible, and
it will terminate all Pods of the specified Deployment.

Running multiple instances of an application will require a way to distribute
the traffic to all of them. Services have an integrated load-balancer that
will distribute network traffic to all Pods of an exposed Deployment. Services
will monitor continuously the running Pods using endpoints, to ensure the
traffic is sent only to available Pods.

Scaling is accomplished by changing the number of replicas in a Deployment.

Once you have multiple instances of an Application running, you would be able
to do Rolling updates without downtime. We'll cover that in the next module.
Now, let's go to the online terminal and scale our application.

### Interactive Tutorial - Scaling Your App

#### Step 1: Scaling a deployment

To list your deployments use the get deployments command: 

	kubectl get deployments

We should have 1 Pod. If not, run the command again. This shows:

* The DESIRED state is showing the configured number of replicas
* The CURRENT state show how many replicas are running now
* The UP-TO-DATE is the number of replicas that were updated to match the desired (configured) state

The AVAILABLE state shows how many replicas are actually AVAILABLE to the
users

Next, let’s scale the Deployment to 4 replicas. We’ll use the kubectl scale
command, followed by the deployment type, name and desired number of
instances:

	kubectl scale deployments/kubernetes-bootcamp --replicas=4

To list your Deployments once again, use get deployments:

	kubectl get deployments

The change was applied, and we have 4 instances of the application available.
Next, let’s check if the number of Pods changed:

	kubectl get pods -o wide

There are 4 Pods now, with different IP addresses. The change was registered in
the Deployment events log. To check that, use the describe command:

	kubectl describe deployments/kubernetes-bootcamp

You can also view in the output of this command that there are 4 replicas now.

Example:

```

macbox:kubernetes pnguyen$ kubectl get deployments
NAME                  DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
kubernetes-bootcamp   1         1         1            1           13h
macbox:kubernetes pnguyen$ kubectl scale deployments/kubernetes-bootcamp --replicas=4
deployment.extensions "kubernetes-bootcamp" scaled
macbox:kubernetes pnguyen$ kubectl get deployments
NAME                  DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
kubernetes-bootcamp   4         4         4            4           14h
macbox:kubernetes pnguyen$ kubectl get pods -o wide
NAME                                   READY     STATUS    RESTARTS   AGE       IP           NODE
kubernetes-bootcamp-5dbf48f7d4-czchq   1/1       Running   0          39s       172.17.0.7   minikube
kubernetes-bootcamp-5dbf48f7d4-gv7gq   1/1       Running   0          39s       172.17.0.6   minikube
kubernetes-bootcamp-5dbf48f7d4-kzkb9   1/1       Running   0          39s       172.17.0.5   minikube
kubernetes-bootcamp-5dbf48f7d4-rcswq   1/1       Running   0          14h       172.17.0.4   minikube
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl describe deployments/kubernetes-bootcamp
Name:                   kubernetes-bootcamp
Namespace:              default
CreationTimestamp:      Tue, 03 Apr 2018 18:07:20 -0700
Labels:                 run=kubernetes-bootcamp
Annotations:            deployment.kubernetes.io/revision=1
Selector:               run=kubernetes-bootcamp
Replicas:               4 desired | 4 updated | 4 total | 4 available | 0 unavailable
StrategyType:           RollingUpdate
MinReadySeconds:        0
RollingUpdateStrategy:  1 max unavailable, 1 max surge
Pod Template:
  Labels:  run=kubernetes-bootcamp
  Containers:
   kubernetes-bootcamp:
    Image:        gcr.io/google-samples/kubernetes-bootcamp:v1
    Port:         8080/TCP
    Host Port:    0/TCP
    Environment:  <none>
    Mounts:       <none>
  Volumes:        <none>
Conditions:
  Type           Status  Reason
  ----           ------  ------
  Available      True    MinimumReplicasAvailable
OldReplicaSets:  <none>
NewReplicaSet:   kubernetes-bootcamp-5dbf48f7d4 (4/4 replicas created)
Events:
  Type    Reason             Age   From                   Message
  ----    ------             ----  ----                   -------
  Normal  ScalingReplicaSet  1m    deployment-controller  Scaled up replica set kubernetes-bootcamp-5dbf48f7d4 to 4
macbox:kubernetes pnguyen$ 
```

#### Step 2: Load Balancing

Let’s check that the Service is load-balancing the traffic. To find out the
exposed IP and Port we can use the describe service as we learned in the
previously Module:

	kubectl describe services/kubernetes-bootcamp

Create an environment variable called NODE_PORT that has a value as the Node
port:

	export NODE_PORT=$(kubectl get services/kubernetes-bootcamp -o go-template='{{(index .spec.ports 0).nodePort}}')
	echo NODE_PORT=$NODE_PORT

Next, we’ll do a curl to the exposed IP and port. Execute the command multiple
times:

	curl $(minikube ip):$NODE_PORT

We hit a different Pod with every request. This demonstrates that the load-
balancing is working.

Example:

```
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl describe services/kubernetes-bootcamp
Name:                     kubernetes-bootcamp
Namespace:                default
Labels:                   run=kubernetes-bootcamp
Annotations:              <none>
Selector:                 run=kubernetes-bootcamp
Type:                     NodePort
IP:                       10.99.245.61
Port:                     <unset>  8080/TCP
TargetPort:               8080/TCP
NodePort:                 <unset>  30135/TCP
Endpoints:                172.17.0.4:8080,172.17.0.5:8080,172.17.0.6:8080 + 1 more...
Session Affinity:         None
External Traffic Policy:  Cluster
Events:                   <none>
macbox:kubernetes pnguyen$ export NODE_PORT=$(kubectl get services/kubernetes-bootcamp -o go-template='{{(index .spec.ports 0).nodePort}}')
macbox:kubernetes pnguyen$ echo NODE_PORT=$NODE_PORT
NODE_PORT=30135
macbox:kubernetes pnguyen$ curl $(minikube ip):$NODE_PORT
Hello Kubernetes bootcamp! | Running on: kubernetes-bootcamp-5dbf48f7d4-czchq | v=1
macbox:kubernetes pnguyen$ curl $(minikube ip):$NODE_PORT
Hello Kubernetes bootcamp! | Running on: kubernetes-bootcamp-5dbf48f7d4-gv7gq | v=1
macbox:kubernetes pnguyen$ curl $(minikube ip):$NODE_PORT
Hello Kubernetes bootcamp! | Running on: kubernetes-bootcamp-5dbf48f7d4-czchq | v=1
macbox:kubernetes pnguyen$ 
```

#### Step 3: Scale Down

To scale down the Service to 2 replicas, run again the scale command:

	kubectl scale deployments/kubernetes-bootcamp --replicas=2

List the Deployments to check if the change was applied with the get
deployments command:

	kubectl get deployments

The number of replicas decreased to 2. List the number of Pods, with get pods:

	kubectl get pods -o wide

This confirms that 2 Pods were terminated.

Example:

```
macbox:kubernetes pnguyen$ kubectl scale deployments/kubernetes-bootcamp --replicas=2
deployment.extensions "kubernetes-bootcamp" scaled
macbox:kubernetes pnguyen$ kubectl get deployments
NAME                  DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
kubernetes-bootcamp   2         2         2            2           14h
macbox:kubernetes pnguyen$ kubectl get pods -o wide
NAME                                   READY     STATUS        RESTARTS   AGE       IP           NODE
kubernetes-bootcamp-5dbf48f7d4-czchq   1/1       Terminating   0          8m        172.17.0.7   minikube
kubernetes-bootcamp-5dbf48f7d4-gv7gq   1/1       Terminating   0          8m        172.17.0.6   minikube
kubernetes-bootcamp-5dbf48f7d4-kzkb9   1/1       Running       0          8m        172.17.0.5   minikube
kubernetes-bootcamp-5dbf48f7d4-rcswq   1/1       Running       0          14h       172.17.0.4   minikube
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl get pods -o wide
NAME                                   READY     STATUS    RESTARTS   AGE       IP           NODE
kubernetes-bootcamp-5dbf48f7d4-kzkb9   1/1       Running   0          9m        172.17.0.5   minikube
kubernetes-bootcamp-5dbf48f7d4-rcswq   1/1       Running   0          14h       172.17.0.4   minikube
macbox:kubernetes pnguyen$ 
```

## Module 6 - Performing a Rolling Update

* https://kubernetes.io/docs/tutorials/kubernetes-basics/update-intro/

### Objectives

* Perform a rolling update using kubectl.

### Updating an application

Users expect applications to be available all the time and developers are
expected to deploy new versions of them several times a day. In Kubernetes
this is done with rolling updates. Rolling updates allow Deployments' update
to take place with zero downtime by incrementally updating Pods instances with
new ones. The new Pods will be scheduled on Nodes with available resources.

In the previous module we scaled our application to run multiple instances.
This is a requirement for performing updates without affecting application
availability. By default, the maximum number of Pods that can be unavailable
during the update and the maximum number of new Pods that can be created, is
one. Both options can be configured to either numbers or percentages (of
Pods). In Kubernetes, updates are versioned and any Deployment update can be
reverted to previous (stable) version. Summary:

### Rolling updates overview

<img src="images/module_06_rollingupdates1.svg" alt="module_06_rollingupdates1" style="width: 400px;"/>
<hr/>
<img src="images/module_06_rollingupdates2.svg" alt="module_06_rollingupdates2" style="width: 400px;"/>
<hr/>
<img src="images/module_06_rollingupdates3.svg" alt="module_06_rollingupdates3" style="width: 400px;"/>
<hr/>
<img src="images/module_06_rollingupdates4.svg" alt="module_06_rollingupdates4" style="width: 400px;"/>

Similar to application Scaling, if a Deployment is exposed publicly, the
Service will load-balance the traffic only to available Pods during the
update. An available Pod is an instance that is available to the users of the
application.

Rolling updates allow the following actions:

* Promote an application from one environment to another (via container image updates)
* Rollback to previous versions
* Continuous Integration and Continuous Delivery of applications with zero downtime

If a Deployment is exposed publicly, the Service will load-balance the traffic
only to available Pods during the update.

In the following interactive tutorial, we'll update our application to a new
version, and also perform a rollback.

### Interactive Tutorial - Updating Your App

#### Step 1: Update the version of the app

To list your deployments use the get deployments command: 

	kubectl get deployments

To list the running Pods use the get pods command:

	kubectl get pods

To view the current image version of the app, run a describe command against
the Pods (look at the Image field):

	kubectl describe pods

To update the image of the application to version 2, use the set image
command, followed by the deployment name and the new image version:

	kubectl set image deployments/kubernetes-bootcamp kubernetes-bootcamp=jocatalin/kubernetes-bootcamp:v2

The command notified the Deployment to use a different image for your app and
initiated a rolling update. Check the status of the new Pods, and view the old
one terminating with the get pods command:

	kubectl get pods

Example:

```

macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl get deployments
NAME                  DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
kubernetes-bootcamp   2         2         2            2           14h
macbox:kubernetes pnguyen$ kubectl get pods
NAME                                   READY     STATUS    RESTARTS   AGE
kubernetes-bootcamp-5dbf48f7d4-kzkb9   1/1       Running   0          17m
kubernetes-bootcamp-5dbf48f7d4-rcswq   1/1       Running   0          14h
macbox:kubernetes pnguyen$ kubectl describe pods
Name:           kubernetes-bootcamp-5dbf48f7d4-kzkb9
Namespace:      default
Node:           minikube/192.168.99.100
Start Time:     Wed, 04 Apr 2018 08:07:45 -0700
Labels:         pod-template-hash=1869049380
                run=kubernetes-bootcamp
Annotations:    <none>
Status:         Running
IP:             172.17.0.5
Controlled By:  ReplicaSet/kubernetes-bootcamp-5dbf48f7d4
Containers:
  kubernetes-bootcamp:
    Container ID:   docker://f1d728f2ea92823881d69ef2049fada1fee90817df321ee9163931a8308458e0
    Image:          gcr.io/google-samples/kubernetes-bootcamp:v1
    Image ID:       docker-pullable://gcr.io/google-samples/kubernetes-bootcamp@sha256:0d6b8ee63bb57c5f5b6156f446b3bc3b3c143d233037f3a2f00e279c8fcc64af
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Wed, 04 Apr 2018 08:07:46 -0700
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-vfcgq (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          True 
  PodScheduled   True 
Volumes:
  default-token-vfcgq:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-vfcgq
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     <none>
Events:
  Type    Reason                 Age   From               Message
  ----    ------                 ----  ----               -------
  Normal  Scheduled              17m   default-scheduler  Successfully assigned kubernetes-bootcamp-5dbf48f7d4-kzkb9 to minikube
  Normal  SuccessfulMountVolume  17m   kubelet, minikube  MountVolume.SetUp succeeded for volume "default-token-vfcgq"
  Normal  Pulled                 17m   kubelet, minikube  Container image "gcr.io/google-samples/kubernetes-bootcamp:v1" already present on machine
  Normal  Created                17m   kubelet, minikube  Created container
  Normal  Started                17m   kubelet, minikube  Started container


Name:           kubernetes-bootcamp-5dbf48f7d4-rcswq
Namespace:      default
Node:           minikube/192.168.99.100
Start Time:     Tue, 03 Apr 2018 18:07:20 -0700
Labels:         app=v1
                pod-template-hash=1869049380
                run=kubernetes-bootcamp
Annotations:    <none>
Status:         Running
IP:             172.17.0.4
Controlled By:  ReplicaSet/kubernetes-bootcamp-5dbf48f7d4
Containers:
  kubernetes-bootcamp:
    Container ID:   docker://75d0982a5241ea5aee1c847bbbb19e0ae6b42882a42bbb29a51ce6be76f4328f
    Image:          gcr.io/google-samples/kubernetes-bootcamp:v1
    Image ID:       docker-pullable://gcr.io/google-samples/kubernetes-bootcamp@sha256:0d6b8ee63bb57c5f5b6156f446b3bc3b3c143d233037f3a2f00e279c8fcc64af
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Tue, 03 Apr 2018 18:13:11 -0700
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-vfcgq (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          True 
  PodScheduled   True 
Volumes:
  default-token-vfcgq:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-vfcgq
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     <none>
Events:          <none>
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl set image deployments/kubernetes-bootcamp kubernetes-bootcamp=jocatalin/kubernetes-bootcamp:v2
deployment.apps "kubernetes-bootcamp" image updated
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl get pods
NAME                                   READY     STATUS        RESTARTS   AGE
kubernetes-bootcamp-5dbf48f7d4-kzkb9   1/1       Terminating   0          18m
kubernetes-bootcamp-5dbf48f7d4-rcswq   1/1       Terminating   0          14h
kubernetes-bootcamp-7689dc585d-lpszp   1/1       Running       0          8s
kubernetes-bootcamp-7689dc585d-lrspk   1/1       Running       0          8s
macbox:kubernetes pnguyen$ kubectl get pods
NAME                                   READY     STATUS        RESTARTS   AGE
kubernetes-bootcamp-5dbf48f7d4-kzkb9   0/1       Terminating   0          18m
kubernetes-bootcamp-5dbf48f7d4-rcswq   1/1       Terminating   0          14h
kubernetes-bootcamp-7689dc585d-lpszp   1/1       Running       0          33s
kubernetes-bootcamp-7689dc585d-lrspk   1/1       Running       0          33s
macbox:kubernetes pnguyen$ kubectl get pods
NAME                                   READY     STATUS    RESTARTS   AGE
kubernetes-bootcamp-7689dc585d-lpszp   1/1       Running   0          45s
kubernetes-bootcamp-7689dc585d-lrspk   1/1       Running   0          45s
macbox:kubernetes pnguyen$ 
```


#### Step 2: Verify an update

First, let’s check that the App is running. To find out the exposed IP and
Port we can use describe service:

	kubectl describe services/kubernetes-bootcamp

Create an environment variable called NODE_PORT that has the value of the Node
port assigned:

	export NODE_PORT=$(kubectl get services/kubernetes-bootcamp -o go-template='{{(index .spec.ports 0).nodePort}}')
	echo NODE_PORT=$NODE_PORT

Next, we’ll do a curl to the the exposed IP and port:

	curl $(minikube ip):$NODE_PORT

We hit a different Pod with every request and we see that all Pods are running
the latest version (v2).

The update can be confirmed also by running a rollout status command:

	kubectl rollout status deployments/kubernetes-bootcamp

To view the current image version of the app, run a describe command against
the Pods:

	kubectl describe pods

We run now version 2 of the app (look at the Image field)

Example:

```
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl describe services/kubernetes-bootcamp
Name:                     kubernetes-bootcamp
Namespace:                default
Labels:                   run=kubernetes-bootcamp
Annotations:              <none>
Selector:                 run=kubernetes-bootcamp
Type:                     NodePort
IP:                       10.99.245.61
Port:                     <unset>  8080/TCP
TargetPort:               8080/TCP
NodePort:                 <unset>  30135/TCP
Endpoints:                172.17.0.6:8080,172.17.0.7:8080
Session Affinity:         None
External Traffic Policy:  Cluster
Events:                   <none>
macbox:kubernetes pnguyen$ export NODE_PORT=$(kubectl get services/kubernetes-bootcamp -o go-template='{{(index .spec.ports 0).nodePort}}')
macbox:kubernetes pnguyen$ echo NODE_PORT=$NODE_PORT
NODE_PORT=30135
macbox:kubernetes pnguyen$ curl $(minikube ip):$NODE_PORT
Hello Kubernetes bootcamp! | Running on: kubernetes-bootcamp-7689dc585d-lpszp | v=2
macbox:kubernetes pnguyen$ curl $(minikube ip):$NODE_PORT
Hello Kubernetes bootcamp! | Running on: kubernetes-bootcamp-7689dc585d-lrspk | v=2
macbox:kubernetes pnguyen$ curl $(minikube ip):$NODE_PORT
Hello Kubernetes bootcamp! | Running on: kubernetes-bootcamp-7689dc585d-lpszp | v=2
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl rollout status deployments/kubernetes-bootcamp
deployment "kubernetes-bootcamp" successfully rolled out
macbox:kubernetes pnguyen$ kubectl describe pods
Name:           kubernetes-bootcamp-7689dc585d-lpszp
Namespace:      default
Node:           minikube/192.168.99.100
Start Time:     Wed, 04 Apr 2018 08:26:08 -0700
Labels:         pod-template-hash=3245871418
                run=kubernetes-bootcamp
Annotations:    <none>
Status:         Running
IP:             172.17.0.6
Controlled By:  ReplicaSet/kubernetes-bootcamp-7689dc585d
Containers:
  kubernetes-bootcamp:
    Container ID:   docker://48ecb1ecbb4f54c73028ae0802f3bde457090f61686a5a45cee4e9eba39e83e2
    Image:          jocatalin/kubernetes-bootcamp:v2
    Image ID:       docker-pullable://jocatalin/kubernetes-bootcamp@sha256:fb1a3ced00cecfc1f83f18ab5cd14199e30adc1b49aa4244f5d65ad3f5feb2a5
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Wed, 04 Apr 2018 08:26:12 -0700
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-vfcgq (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          True 
  PodScheduled   True 
Volumes:
  default-token-vfcgq:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-vfcgq
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     <none>
Events:
  Type    Reason                 Age   From               Message
  ----    ------                 ----  ----               -------
  Normal  Scheduled              3m    default-scheduler  Successfully assigned kubernetes-bootcamp-7689dc585d-lpszp to minikube
  Normal  SuccessfulMountVolume  3m    kubelet, minikube  MountVolume.SetUp succeeded for volume "default-token-vfcgq"
  Normal  Pulling                3m    kubelet, minikube  pulling image "jocatalin/kubernetes-bootcamp:v2"
  Normal  Pulled                 3m    kubelet, minikube  Successfully pulled image "jocatalin/kubernetes-bootcamp:v2"
  Normal  Created                3m    kubelet, minikube  Created container
  Normal  Started                3m    kubelet, minikube  Started container


Name:           kubernetes-bootcamp-7689dc585d-lrspk
Namespace:      default
Node:           minikube/192.168.99.100
Start Time:     Wed, 04 Apr 2018 08:26:08 -0700
Labels:         pod-template-hash=3245871418
                run=kubernetes-bootcamp
Annotations:    <none>
Status:         Running
IP:             172.17.0.7
Controlled By:  ReplicaSet/kubernetes-bootcamp-7689dc585d
Containers:
  kubernetes-bootcamp:
    Container ID:   docker://5a1affa7b953840f958eeebb7f47e0891d9ae9ba84b84a570729e32a2f5da78e
    Image:          jocatalin/kubernetes-bootcamp:v2
    Image ID:       docker-pullable://jocatalin/kubernetes-bootcamp@sha256:fb1a3ced00cecfc1f83f18ab5cd14199e30adc1b49aa4244f5d65ad3f5feb2a5
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Wed, 04 Apr 2018 08:26:10 -0700
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-vfcgq (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          True 
  PodScheduled   True 
Volumes:
  default-token-vfcgq:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-vfcgq
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     <none>
Events:
  Type    Reason                 Age   From               Message
  ----    ------                 ----  ----               -------
  Normal  Scheduled              3m    default-scheduler  Successfully assigned kubernetes-bootcamp-7689dc585d-lrspk to minikube
  Normal  SuccessfulMountVolume  3m    kubelet, minikube  MountVolume.SetUp succeeded for volume "default-token-vfcgq"
  Normal  Pulling                3m    kubelet, minikube  pulling image "jocatalin/kubernetes-bootcamp:v2"
  Normal  Pulled                 3m    kubelet, minikube  Successfully pulled image "jocatalin/kubernetes-bootcamp:v2"
  Normal  Created                3m    kubelet, minikube  Created container
  Normal  Started                3m    kubelet, minikube  Started container
macbox:kubernetes pnguyen$ 
```

#### Step 3: Rollback an update

Let’s perform another update, and deploy image tagged as v10 :

	kubectl set image deployments/kubernetes-bootcamp kubernetes-bootcamp=gcr.io/google-samples/kubernetes-bootcamp:v10

Use get deployments to see the status of the deployment:

	kubectl get deployments

And something is wrong… We do not have the desired number of Pods available.
List the Pods again:

	kubectl get pods

A describe command on the Pods should give more insights:

	kubectl describe pods

There is no image called v10 in the repository. Let’s roll back to our
previously working version. We’ll use the rollout undo command:

	kubectl rollout undo deployments/kubernetes-bootcamp

The rollout command reverted the deployment to the previous known state (v2 of
the image). Updates are versioned and you can revert to any previously know
state of a Deployment. List again the Pods:

	kubectl get pods

Four Pods are running. Check again the image deployed on the them:

	kubectl describe pods

We see that the deployment is using a stable version of the app (v2). The
Rollback was successful.

Example:

```

macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl set image deployments/kubernetes-bootcamp kubernetes-bootcamp=gcr.io/google-samples/kubernetes-bootcamp:v10
deployment.apps "kubernetes-bootcamp" image updated
macbox:kubernetes pnguyen$ kubectl get deployments
NAME                  DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
kubernetes-bootcamp   2         3         2            1           14h
macbox:kubernetes pnguyen$ kubectl describe pods
Name:           kubernetes-bootcamp-5569c6b8d6-lqf5z
Namespace:      default
Node:           minikube/192.168.99.100
Start Time:     Wed, 04 Apr 2018 08:32:07 -0700
Labels:         pod-template-hash=1125726482
                run=kubernetes-bootcamp
Annotations:    <none>
Status:         Pending
IP:             172.17.0.5
Controlled By:  ReplicaSet/kubernetes-bootcamp-5569c6b8d6
Containers:
  kubernetes-bootcamp:
    Container ID:   
    Image:          gcr.io/google-samples/kubernetes-bootcamp:v10
    Image ID:       
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Waiting
      Reason:       ImagePullBackOff
    Ready:          False
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-vfcgq (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          False 
  PodScheduled   True 
Volumes:
  default-token-vfcgq:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-vfcgq
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     <none>
Events:
  Type     Reason                 Age               From               Message
  ----     ------                 ----              ----               -------
  Normal   Scheduled              24s               default-scheduler  Successfully assigned kubernetes-bootcamp-5569c6b8d6-lqf5z to minikube
  Normal   SuccessfulMountVolume  24s               kubelet, minikube  MountVolume.SetUp succeeded for volume "default-token-vfcgq"
  Normal   BackOff                22s               kubelet, minikube  Back-off pulling image "gcr.io/google-samples/kubernetes-bootcamp:v10"
  Warning  Failed                 22s               kubelet, minikube  Error: ImagePullBackOff
  Normal   Pulling                8s (x2 over 23s)  kubelet, minikube  pulling image "gcr.io/google-samples/kubernetes-bootcamp:v10"
  Warning  Failed                 2s (x2 over 22s)  kubelet, minikube  Failed to pull image "gcr.io/google-samples/kubernetes-bootcamp:v10": rpc error: code = Unknown desc = Error response from daemon: manifest for gcr.io/google-samples/kubernetes-bootcamp:v10 not found
  Warning  Failed                 2s (x2 over 22s)  kubelet, minikube  Error: ErrImagePull


Name:           kubernetes-bootcamp-5569c6b8d6-nwb64
Namespace:      default
Node:           minikube/192.168.99.100
Start Time:     Wed, 04 Apr 2018 08:32:07 -0700
Labels:         pod-template-hash=1125726482
                run=kubernetes-bootcamp
Annotations:    <none>
Status:         Pending
IP:             172.17.0.4
Controlled By:  ReplicaSet/kubernetes-bootcamp-5569c6b8d6
Containers:
  kubernetes-bootcamp:
    Container ID:   
    Image:          gcr.io/google-samples/kubernetes-bootcamp:v10
    Image ID:       
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Waiting
      Reason:       ImagePullBackOff
    Ready:          False
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-vfcgq (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          False 
  PodScheduled   True 
Volumes:
  default-token-vfcgq:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-vfcgq
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     <none>
Events:
  Type     Reason                 Age               From               Message
  ----     ------                 ----              ----               -------
  Normal   Scheduled              24s               default-scheduler  Successfully assigned kubernetes-bootcamp-5569c6b8d6-nwb64 to minikube
  Normal   SuccessfulMountVolume  24s               kubelet, minikube  MountVolume.SetUp succeeded for volume "default-token-vfcgq"
  Normal   BackOff                23s               kubelet, minikube  Back-off pulling image "gcr.io/google-samples/kubernetes-bootcamp:v10"
  Warning  Failed                 23s               kubelet, minikube  Error: ImagePullBackOff
  Normal   Pulling                9s (x2 over 24s)  kubelet, minikube  pulling image "gcr.io/google-samples/kubernetes-bootcamp:v10"
  Warning  Failed                 8s (x2 over 23s)  kubelet, minikube  Failed to pull image "gcr.io/google-samples/kubernetes-bootcamp:v10": rpc error: code = Unknown desc = Error response from daemon: manifest for gcr.io/google-samples/kubernetes-bootcamp:v10 not found
  Warning  Failed                 8s (x2 over 23s)  kubelet, minikube  Error: ErrImagePull


Name:                      kubernetes-bootcamp-7689dc585d-lpszp
Namespace:                 default
Node:                      minikube/192.168.99.100
Start Time:                Wed, 04 Apr 2018 08:26:08 -0700
Labels:                    pod-template-hash=3245871418
                           run=kubernetes-bootcamp
Annotations:               <none>
Status:                    Terminating (lasts <invalid>)
Termination Grace Period:  30s
IP:                        172.17.0.6
Controlled By:             ReplicaSet/kubernetes-bootcamp-7689dc585d
Containers:
  kubernetes-bootcamp:
    Container ID:   docker://48ecb1ecbb4f54c73028ae0802f3bde457090f61686a5a45cee4e9eba39e83e2
    Image:          jocatalin/kubernetes-bootcamp:v2
    Image ID:       docker-pullable://jocatalin/kubernetes-bootcamp@sha256:fb1a3ced00cecfc1f83f18ab5cd14199e30adc1b49aa4244f5d65ad3f5feb2a5
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Wed, 04 Apr 2018 08:26:12 -0700
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-vfcgq (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          True 
  PodScheduled   True 
Volumes:
  default-token-vfcgq:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-vfcgq
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     <none>
Events:
  Type    Reason                 Age   From               Message
  ----    ------                 ----  ----               -------
  Normal  Scheduled              6m    default-scheduler  Successfully assigned kubernetes-bootcamp-7689dc585d-lpszp to minikube
  Normal  SuccessfulMountVolume  6m    kubelet, minikube  MountVolume.SetUp succeeded for volume "default-token-vfcgq"
  Normal  Pulling                6m    kubelet, minikube  pulling image "jocatalin/kubernetes-bootcamp:v2"
  Normal  Pulled                 6m    kubelet, minikube  Successfully pulled image "jocatalin/kubernetes-bootcamp:v2"
  Normal  Created                6m    kubelet, minikube  Created container
  Normal  Started                6m    kubelet, minikube  Started container


Name:           kubernetes-bootcamp-7689dc585d-lrspk
Namespace:      default
Node:           minikube/192.168.99.100
Start Time:     Wed, 04 Apr 2018 08:26:08 -0700
Labels:         pod-template-hash=3245871418
                run=kubernetes-bootcamp
Annotations:    <none>
Status:         Running
IP:             172.17.0.7
Controlled By:  ReplicaSet/kubernetes-bootcamp-7689dc585d
Containers:
  kubernetes-bootcamp:
    Container ID:   docker://5a1affa7b953840f958eeebb7f47e0891d9ae9ba84b84a570729e32a2f5da78e
    Image:          jocatalin/kubernetes-bootcamp:v2
    Image ID:       docker-pullable://jocatalin/kubernetes-bootcamp@sha256:fb1a3ced00cecfc1f83f18ab5cd14199e30adc1b49aa4244f5d65ad3f5feb2a5
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Wed, 04 Apr 2018 08:26:10 -0700
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-vfcgq (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          True 
  PodScheduled   True 
Volumes:
  default-token-vfcgq:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-vfcgq
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     <none>
Events:
  Type    Reason                 Age   From               Message
  ----    ------                 ----  ----               -------
  Normal  Scheduled              6m    default-scheduler  Successfully assigned kubernetes-bootcamp-7689dc585d-lrspk to minikube
  Normal  SuccessfulMountVolume  6m    kubelet, minikube  MountVolume.SetUp succeeded for volume "default-token-vfcgq"
  Normal  Pulling                6m    kubelet, minikube  pulling image "jocatalin/kubernetes-bootcamp:v2"
  Normal  Pulled                 6m    kubelet, minikube  Successfully pulled image "jocatalin/kubernetes-bootcamp:v2"
  Normal  Created                6m    kubelet, minikube  Created container
  Normal  Started                6m    kubelet, minikube  Started container
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl rollout undo deployments/kubernetes-bootcamp
deployment.apps "kubernetes-bootcamp" 
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl get pods
NAME                                   READY     STATUS    RESTARTS   AGE
kubernetes-bootcamp-7689dc585d-lrspk   1/1       Running   0          7m
kubernetes-bootcamp-7689dc585d-zrl4p   1/1       Running   0          26s
macbox:kubernetes pnguyen$ 
macbox:kubernetes pnguyen$ kubectl describe pods
Name:           kubernetes-bootcamp-7689dc585d-lrspk
Namespace:      default
Node:           minikube/192.168.99.100
Start Time:     Wed, 04 Apr 2018 08:26:08 -0700
Labels:         pod-template-hash=3245871418
                run=kubernetes-bootcamp
Annotations:    <none>
Status:         Running
IP:             172.17.0.7
Controlled By:  ReplicaSet/kubernetes-bootcamp-7689dc585d
Containers:
  kubernetes-bootcamp:
    Container ID:   docker://5a1affa7b953840f958eeebb7f47e0891d9ae9ba84b84a570729e32a2f5da78e
    Image:          jocatalin/kubernetes-bootcamp:v2
    Image ID:       docker-pullable://jocatalin/kubernetes-bootcamp@sha256:fb1a3ced00cecfc1f83f18ab5cd14199e30adc1b49aa4244f5d65ad3f5feb2a5
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Wed, 04 Apr 2018 08:26:10 -0700
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-vfcgq (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          True 
  PodScheduled   True 
Volumes:
  default-token-vfcgq:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-vfcgq
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     <none>
Events:
  Type    Reason                 Age   From               Message
  ----    ------                 ----  ----               -------
  Normal  Scheduled              7m    default-scheduler  Successfully assigned kubernetes-bootcamp-7689dc585d-lrspk to minikube
  Normal  SuccessfulMountVolume  7m    kubelet, minikube  MountVolume.SetUp succeeded for volume "default-token-vfcgq"
  Normal  Pulling                7m    kubelet, minikube  pulling image "jocatalin/kubernetes-bootcamp:v2"
  Normal  Pulled                 7m    kubelet, minikube  Successfully pulled image "jocatalin/kubernetes-bootcamp:v2"
  Normal  Created                7m    kubelet, minikube  Created container
  Normal  Started                7m    kubelet, minikube  Started container


Name:           kubernetes-bootcamp-7689dc585d-zrl4p
Namespace:      default
Node:           minikube/192.168.99.100
Start Time:     Wed, 04 Apr 2018 08:33:09 -0700
Labels:         pod-template-hash=3245871418
                run=kubernetes-bootcamp
Annotations:    <none>
Status:         Running
IP:             172.17.0.6
Controlled By:  ReplicaSet/kubernetes-bootcamp-7689dc585d
Containers:
  kubernetes-bootcamp:
    Container ID:   docker://3034eb57c94bbef2b2b8078239cd8b4f4bf366b3226479d777af766629c9dca4
    Image:          jocatalin/kubernetes-bootcamp:v2
    Image ID:       docker-pullable://jocatalin/kubernetes-bootcamp@sha256:fb1a3ced00cecfc1f83f18ab5cd14199e30adc1b49aa4244f5d65ad3f5feb2a5
    Port:           8080/TCP
    Host Port:      0/TCP
    State:          Running
      Started:      Wed, 04 Apr 2018 08:33:10 -0700
    Ready:          True
    Restart Count:  0
    Environment:    <none>
    Mounts:
      /var/run/secrets/kubernetes.io/serviceaccount from default-token-vfcgq (ro)
Conditions:
  Type           Status
  Initialized    True 
  Ready          True 
  PodScheduled   True 
Volumes:
  default-token-vfcgq:
    Type:        Secret (a volume populated by a Secret)
    SecretName:  default-token-vfcgq
    Optional:    false
QoS Class:       BestEffort
Node-Selectors:  <none>
Tolerations:     <none>
Events:
  Type    Reason                 Age   From               Message
  ----    ------                 ----  ----               -------
  Normal  Scheduled              37s   default-scheduler  Successfully assigned kubernetes-bootcamp-7689dc585d-zrl4p to minikube
  Normal  SuccessfulMountVolume  36s   kubelet, minikube  MountVolume.SetUp succeeded for volume "default-token-vfcgq"
  Normal  Pulled                 36s   kubelet, minikube  Container image "jocatalin/kubernetes-bootcamp:v2" already present on machine
  Normal  Created                36s   kubelet, minikube  Created container
  Normal  Started                36s   kubelet, minikube  Started container
macbox:kubernetes pnguyen$ 
```






