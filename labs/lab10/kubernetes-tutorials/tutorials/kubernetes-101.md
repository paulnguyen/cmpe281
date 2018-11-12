

	  _  __     _                          _            
	 | |/ /    | |                        | |           
	 | ' /_   _| |__   ___ _ __ _ __   ___| |_ ___  ___ 
	 |  <| | | | '_ \ / _ \ '__| '_ \ / _ \ __/ _ \/ __|
	 | . \ |_| | |_) |  __/ |  | | | |  __/ ||  __/\__ \
	 |_|\_\__,_|_.__/ \___|_|  |_| |_|\___|\__\___||___/
	                                                    
                                                                                        
                                                   
# Kubernetes 101

* https://kubernetes-v1-4.github.io/docs/user-guide/
* https://kubernetes.io/docs/reference/kubectl/overview/
* https://kubernetes.io/docs/tasks/tools/install-kubectl/

# Pods

In Kubernetes, a group of one or more containers is called a pod. Containers
in a pod are deployed together, and are started, stopped, and replicated as a
group.

For more information, see Pods.

* https://kubernetes.io/docs/concepts/workloads/pods/pod/

# Pod Definition

The simplest pod definition describes the deployment of a single container. For example, an nginx web server pod might be defined as:

## pid-ngix.yaml

```
apiVersion: v1
kind: Pod
metadata:
  name: nginx
spec:
  containers:
  - name: nginx
    image: nginx:1.7.9
    ports:
    - containerPort: 80
```

A pod definition is a declaration of a desired state. Desired state is a very
important concept in the Kubernetes model. Many things present a desired state
to the system, and Kubernetes’ ensures that the current state matches the
desired state. For example, when you create a pod and declare that the
containers in it to be running. If the containers happen not to be running
because of a program failure, Kubernetes continues to (re-)create the pod in
order to drive the pod to the desired state. This process continues until you
delete the pod.

For more information, see Kubernetes Design Documents and Proposals. Pod
Management:

* https://github.com/kubernetes/community/blob/master/contributors/design-proposals/README.md

# Pod Management

## Create POD

	kubectl create -f pod-nginx.yaml

## List all pods:

	kubectl get pods

On most providers, the pod IPs are not externally accessible. The easiest way
to test that the pod is working is to create a busybox pod and exec commands
on it remotely. For more information, see Get a Shell to a Running Container.

## Get a Shell to a Running Container

* https://kubernetes.io/docs/tasks/debug-application-cluster/get-shell-running-container/

### Verify that the Container is running:

	kubectl get pod nginx

## Shell into Pod

	kubectl exec -it nginx -- /bin/bash

## Opening a shell when a Pod has more than one Container

If a Pod has more than one Container, use --container or -c to specify a
Container in the kubectl exec command. For example, suppose you have a Pod
named my-pod, and the Pod has two containers named main-app and helper-app.
The following command would open a shell to the main-app Container.

	kubectl exec -it my-pod --container main-app -- /bin/bash

## Accessing POD via IP (if open)

If the IP of the pod is accessible, you can access its http endpoint with wget
on port 80:

	kubectl run busybox --image=busybox --restart=Never --tty -i --generator=run-pod/v1 --env "POD_IP=$(kubectl get pod nginx -o go-template='{{.status.podIP}}')"

	# Run in the busybox container
	u@busybox $ wget -qO- http://$POD_IP 

	# Exit the busybox container
	u@busybox $ exit 

	# Clean up the pod we created with "kubectl run"
	kubectl delete pod busybox 

## To delete a pod named nginx:

	kubectl delete pod nginx

## Volumes

The container file system only lives as long as the container does. So if your
app’s state needs to survive relocation, reboots, and crashes, you’ll need to
configure some persistent storage.

In this example you can create a Redis pod with a named volume, and a volume
mount that defines the path to mount the volume.

### Define a volume:

```
volumes:
    - name: redis-persistent-storage
      emptyDir: {}
```

### Define a volume mount within a container definition:

```
volumeMounts:
    # name must match the volume name defined in volumes
    - name: redis-persistent-storage
      # mount path within the container
      mountPath: /data/redis
```

### pod-redis.yaml:

Here is an example of Redis pod definition with a persistent storage volume 

```
apiVersion: v1
kind: Pod
metadata:
  name: redis
spec:
  containers:
  - name: redis
    image: redis
    volumeMounts:
    - name: redis-persistent-storage
      mountPath: /data/redis
  volumes:
  - name: redis-persistent-storage
    emptyDir: {}
```

#### Where:

* The volumeMounts name is a reference to a specific volumes name.

* The volumeMounts mountPath is the path to mount the volume within the container.

#### Volume Types

* EmptyDir: Creates a new directory that exists as long as the pod is running on the node, 
  but it can persist across container failures and restarts.
    
* HostPath: Mounts an existing directory on the node’s file system. For example (/var/logs).

For more information, see Volumes.

* https://kubernetes.io/docs/concepts/storage/volumes/


## Multiple Containers

Note: The examples below are syntactically correct, but some of the images
(e.g. kubernetes/git-monitor) don’t exist yet. We’re working on turning these
into working examples.

However, often you want to have two different containers that work together.
An example of this would be a web server, and a helper job that polls a git
repository for new updates:

```
apiVersion: v1
kind: Pod
metadata:
  name: www
spec:
  containers:
  - name: nginx
    image: nginx
    volumeMounts:
    - mountPath: /srv/www
      name: www-data
      readOnly: true
  - name: git-monitor
    image: kubernetes/git-monitor
    env:
    - name: GIT_REPO
      value: http://github.com/some/repo.git
    volumeMounts:
    - mountPath: /data
      name: www-data
  volumes:
  - name: www-data
    emptyDir: {}
```

Note that we have also added a volume here. In this case, the volume is
mounted into both containers. It is marked readOnly in the web server’s case,
since it doesn’t need to write to the directory.

Finally, we have also introduced an environment variable to the git-monitor
container, which allows us to parameterize that container with the particular
git repository that we want to track. 






