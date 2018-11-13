

	  _  __     _                          _            
	 | |/ /    | |                        | |           
	 | ' /_   _| |__   ___ _ __ _ __   ___| |_ ___  ___ 
	 |  <| | | | '_ \ / _ \ '__| '_ \ / _ \ __/ _ \/ __|
	 | . \ |_| | |_) |  __/ |  | | | |  __/ ||  __/\__ \
	 |_|\_\__,_|_.__/ \___|_|  |_| |_|\___|\__\___||___/
	                                                    
                                                                                        

# Kubernetes 201 

* https://kubernetes-v1-4.github.io/docs/user-guide/

# Labels

Having already learned about Pods and how to create them, you may be struck by
an urge to create many, many Pods. Please do! But eventually you will need a
system to organize these Pods into groups. The system for achieving this in
Kubernetes is Labels. Labels are key-value pairs that are attached to each
object in Kubernetes. Label selectors can be passed along with a RESTful list
request to the apiserver to retrieve a list of objects which match that label
selector.

To add a label, add a labels section under metadata in the Pod definition:

```
 labels:
    app: nginx
```

For example, here is the nginx Pod definition with labels 

## pod-labels.yaml

```
apiVersion: v1
kind: Pod
metadata:
  name: nginx
  labels:
    app: nginx
spec:
  containers:
  - name: nginx
    image: nginx
    ports:
    - containerPort: 80
```

## Create the labeled Pod

	kubectl create -f pod-labels.yaml

## List all Pods with the label app=nginx

	kubectl get pods -l app=nginx

## Delete the Pod by label:

	kubectl delete pod -l app=nginx

For more information, see Labels. They are a core concept used by two
additional Kubernetes building blocks: Deployments and Services.

* https://kubernetes.io/docs/concepts/overview/working-with-objects/labels/


# Deployments

Now that you know how to make awesome, multi-container, labeled Pods and you
want to use them to build an application, you might be tempted to just start
building a whole bunch of individual Pods, but if you do that, a whole host of
operational concerns pop up. For example: how will you scale the number of
Pods up or down? How will you roll out a new release?

The answer to those questions and more is to use a Deployment to manage
maintaining and updating your running Pods.

A Deployment object defines a Pod creation template (a “cookie-cutter” if you
will) and desired replica count. The Deployment uses a label selector to
identify the Pods it manages, and will create or delete Pods as needed to meet
the replica count. Deployments are also used to manage safely rolling out
changes to your running Pods.

Here is a Deployment that instantiates two nginx Pods:

## deployment.yaml

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 2 # tells deployment to run 2 pods matching the template
  template: # create pods using pod definition in this template
    metadata:
      # unlike pod-nginx.yaml, the name is not included in the meta data as a unique name is
      # generated from the deployment name
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.7.9
        ports:
        - containerPort: 80
 ```

# Deployment Management

## Create an nginx Deployment:

	kubectl create -f deployment.yaml

## List all Deployments:

	kubectl get deployment

## List the Pods created by the Deployment:

	kubectl get pods -l app=nginx

## deployment-update.yaml

Upgrade the nginx container from 1.7.9 to 1.8 by changing the Deployment and
calling apply. The following config contains the desired changes:

```
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
spec:
  selector:
    matchLabels:
      app: nginx
  replicas: 2
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.8 # Update the version of nginx from 1.7.9 to 1.8
        ports:
        - containerPort: 80
```

## Upgrade Pod/Containers

	kubectl apply -f https://k8s.io/docs/user-guide/walkthrough//deployment-update.yaml

## Watch the Deployment create Pods with new names and delete the old Pods:

	kubectl get pods -l app=nginx

## Delete the Deployment by name:

	kubectl delete deployment nginx-deployment

For more information, such as how to rollback Deployment changes to a previous
version, see Deployments.

* https://kubernetes.io/docs/concepts/workloads/controllers/deployment/


# Services

Services

Once you have a replicated set of Pods, you need an abstraction that enables
connectivity between the layers of your application. For example, if you have
a Deployment managing your backend jobs, you don’t want to have to reconfigure
your front-ends whenever you re-scale your backends. Likewise, if the Pods in
your backends are scheduled (or rescheduled) onto different machines, you
can’t be required to re-configure your front-ends. In Kubernetes, the service
abstraction achieves these goals. A service provides a way to refer to a set
of Pods (selected by labels) with a single static IP address. It may also
provide load balancing, if supported by the provider.

For example, here is a service that balances across the Pods created in the
previous nginx Deployment example (service.yaml):

## service.yaml

```
apiVersion: v1
kind: Service
metadata:
  name: nginx-service
spec:
  ports:
  - port: 8000 # the port that this service should serve on
    # the container on each pod to connect to, can be a name
    # (e.g. 'www') or a number (e.g. 80)
    targetPort: 80
    protocol: TCP
  # just like the selector in the deployment,
  # but this time it identifies the set of pods to load balance
  # traffic to.
  selector:
    app: nginx
```

# Service Management

## Create an nginx service (service.yaml):

	kubectl create -f service.yaml

## List all services:

	kubectl get services


On most providers, the service IPs are not externally accessible. The easiest
way to test that the service is working is to create a busybox Pod and exec
commands on it remotely. See the command execution documentation for details.

* https://kubernetes.io/docs/user-guide/kubectl-overview/

Provided the service IP is accessible, you should be able to access its http
endpoint with wget on the exposed port:
```
$ export SERVICE_IP=$(kubectl get service nginx-service -o go-template='{{.spec.clusterIP}}')
$ export SERVICE_PORT=$(kubectl get service nginx-service -o go-template='{{(index .spec.ports 0).port}}')
$ echo "$SERVICE_IP:$SERVICE_PORT"
```

```
kubectl run busybox  --generator=run-pod/v1 --image=busybox --restart=Never --tty -i --env "SERVICE_IP=$SERVICE_IP" --env "SERVICE_PORT=$SERVICE_PORT"

u@busybox$ wget -qO- http://$SERVICE_IP:$SERVICE_PORT # Run in the busybox container
u@busybox$ exit # Exit the busybox container

kubectl delete pod busybox # Clean up the pod we created with "kubectl run"
```

## To delete the service by name:

	kubectl delete service nginx-service

When created, each service is assigned a unique IP address. This address is
tied to the lifespan of the Service, and will not change while the Service is
alive. Pods can be configured to talk to the service, and know that
communication to the service will be automatically load-balanced out to some
Pod that is a member of the set identified by the label selector in the
Service.

For more information, see Services:

* https://kubernetes.io/docs/concepts/services-networking/service/


# Health Checking

When I write code it never crashes, right? Sadly the Kubernetes issues list
indicates otherwise…

Rather than trying to write bug-free code, a better approach is to use a
management system to perform periodic health checking and repair of your
application. That way a system outside of your application itself is
responsible for monitoring the application and taking action to fix it. It’s
important that the system be outside of the application, since if your
application fails and the health checking agent is part of your application,
it may fail as well and you’ll never know. In Kubernetes, the health check
monitor is the Kubelet agent. 

## Process Health Checking

The simplest form of health-checking is just process level health checking.
The Kubelet constantly asks the Docker daemon if the container process is
still running, and if not, the container process is restarted. In all of the
Kubernetes examples you have run so far, this health checking was actually
already enabled. It’s on for every single container that runs in Kubernetes.

## Application Health Checking

However, in many cases this low-level health checking is insufficient.
Consider, for example, the following code:

```
lockOne := sync.Mutex{}
lockTwo := sync.Mutex{}

go func() {
  lockOne.Lock();
  lockTwo.Lock();
  ...
}()

lockTwo.Lock();
lockOne.Lock();
```

This is a classic example of a problem in computer science known as
“Deadlock”. From Docker’s perspective your application is still operating and
the process is still running, but from your application’s perspective your
code is locked up and will never respond correctly.

To address this problem, Kubernetes supports user implemented application
health-checks. These checks are performed by the Kubelet to ensure that your
application is operating correctly for a definition of “correctly” that you
provide.

Currently, there are three types of application health checks that you can
choose from:


* HTTP Health Checks - The Kubelet will call a web hook. If it returns between 
  200 and 399, it is considered success, failure otherwise. See health check 
  examples here.
    
* Container Exec - The Kubelet will execute a command inside your container. 
  If it exits with status 0 it will be considered a success. See health check examples here.
    
* TCP Socket - The Kubelet will attempt to open a socket to your container. 
  If it can establish a connection, the container is considered healthy, 
  if it can’t it is considered a failure.


In all cases, if the Kubelet discovers a failure the container is restarted.

The container health checks are configured in the livenessProbe section of
your container config. There you can also specify an initialDelaySeconds that
is a grace period from when the container is started to when health checks are
performed, to enable your container to perform any necessary initialization.

Here is an example config for a Pod with an HTTP health check

## pod-with-http-healthcheck.yaml

```
apiVersion: v1
kind: Pod
metadata:
  name: pod-with-http-healthcheck
spec:
  containers:
  - name: nginx
    image: nginx
    # defines the health checking
    livenessProbe:
      # an http probe
      httpGet:
        path: /_status/healthz
        port: 80
      # length of time to wait for a pod to initialize
      # after pod startup, before applying health checking
      initialDelaySeconds: 30
      timeoutSeconds: 1
    ports:
    - containerPort: 80
```


## pod-with-tcp-socket-healthcheck.yaml

And here is an example config for a Pod with a TCP Socket health check

```
apiVersion: v1
kind: Pod
metadata:
  name: pod-with-tcp-socket-healthcheck
spec:
  containers:
  - name: redis
    image: redis
    # defines the health checking
    livenessProbe:
      # a TCP socket probe
      tcpSocket:
        port: 6379
      # length of time to wait for a pod to initialize
      # after pod startup, before applying health checking
      initialDelaySeconds: 30
      timeoutSeconds: 1
    ports:
    - containerPort: 6379
```

For more information about health checking, see Container Probes.

* https://kubernetes.io/docs/user-guide/pod-states/#container-probes





