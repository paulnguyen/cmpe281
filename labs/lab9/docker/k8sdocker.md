
# Running Kubernetes in Docker for Mac


* https://docs.docker.com/docker-for-mac/kubernetes/
* https://rominirani.com/tutorial-getting-started-with-kubernetes-with-docker-on-mac-7f58467203fd

## See Also

* https://docs.docker.com/docker-for-windows/kubernetes/
* https://kubernetes.io/docs/setup/pick-right-solution/#local-machine-solutions


## Enabled Kubernetes in Docker for Mac

* which kubectl

	/usr/local/bin/kubectl

* kubectl version

	```
	Client Version: version.Info{Major:"1", Minor:"10", GitVersion:"v1.10.3", GitCommit:"2bba0127d85d5a46ab4b778548be28623b32d0b0", GitTreeState:"clean", BuildDate:"2018-05-21T09:17:39Z", GoVersion:"go1.9.3", Compiler:"gc", Platform:"darwin/amd64"}
	Server Version: version.Info{Major:"1", Minor:"10", GitVersion:"v1.10.3", GitCommit:"2bba0127d85d5a46ab4b778548be28623b32d0b0", GitTreeState:"clean", BuildDate:"2018-05-21T09:05:37Z", GoVersion:"go1.9.3", Compiler:"gc", Platform:"linux/amd64"}
	```

* kubectl cluster-info

	```
	Kubernetes master is running at https://localhost:6443
	KubeDNS is running at https://localhost:6443/api/v1/namespaces/kube-system/services/kube-dns:dns/proxy
	```

* kubectl get nodes

	```
	NAME                 STATUS    ROLES     AGE       VERSION
	docker-for-desktop   Ready     master    5m        v1.10.3
	macpro:kubernetes pnguyen$ 
	```


## Install Kubernetes Dashboard

* install kubernetes dashboard

	```
	kubectl create -f kubernetes-dashboard.yaml
	```

*  kubectl get pods --namespace=kube-system

	```
	NAME                                         READY     STATUS    RESTARTS   AGE
	etcd-docker-for-desktop                      1/1       Running   0          13h
	kube-apiserver-docker-for-desktop            1/1       Running   0          13h
	kube-controller-manager-docker-for-desktop   1/1       Running   0          13h
	kube-dns-86f4d74b45-p69qm                    3/3       Running   0          13h
	kube-proxy-n8lq8                             1/1       Running   0          13h
	kube-scheduler-docker-for-desktop            1/1       Running   0          13h
	kubernetes-dashboard-7b9c7bc8c9-njfhm        1/1       Running   0          2m
	```

* port forward dashboard pod

	```
	kubectl port-forward kubernetes-dashboard-7b9c7bc8c9-njfhm 8443:8443 --namespace=kube-system
	Forwarding from 127.0.0.1:8443 -> 8443
	Forwarding from [::1]:8443 -> 8443
	```

* access kubernetes dashboard

	```
	https://localhost:8443  (click "skip" to bypass auth)
	```

## Deploy Hello World Pod

* kubectl run hello-nginx --image=nginx --port=80

	```
	deployment.apps "hello-nginx" created
	```

* kubectl get pods

	```
	NAME                           READY     STATUS    RESTARTS   AGE
	hello-nginx-6f9f4fc7dd-slnp2   1/1       Running   0          54s
	```

* kubectl describe pod hello-nginx-6f9f4fc7dd-slnp2

	```
	Name:           hello-nginx-6f9f4fc7dd-slnp2
	Namespace:      default
	Node:           docker-for-desktop/192.168.65.3
	Start Time:     Thu, 08 Nov 2018 06:41:30 -0800
	Labels:         pod-template-hash=2959097388
	                run=hello-nginx
	Annotations:    <none>
	Status:         Running
	IP:             10.1.0.5
	Controlled By:  ReplicaSet/hello-nginx-6f9f4fc7dd
	Containers:
	  hello-nginx:
	    Container ID:   docker://8c8e8c565a5d7828ea061c35a3861a791546625a4e63b6ef5d39c7fc37181057
	    Image:          nginx
	    Image ID:       docker-pullable://nginx@sha256:d59a1aa7866258751a261bae525a1842c7ff0662d4f34a355d5f36826abc0341
	    Port:           80/TCP
	    Host Port:      0/TCP
	    State:          Running
	      Started:      Thu, 08 Nov 2018 06:41:40 -0800
	    Ready:          True
	    Restart Count:  0
	    Environment:    <none>
	    Mounts:
	      /var/run/secrets/kubernetes.io/serviceaccount from default-token-wlnp4 (ro)
	Conditions:
	  Type           Status
	  Initialized    True 
	  Ready          True 
	  PodScheduled   True 
	Volumes:
	  default-token-wlnp4:
	    Type:        Secret (a volume populated by a Secret)
	    SecretName:  default-token-wlnp4
	    Optional:    false
	QoS Class:       BestEffort
	Node-Selectors:  <none>
	Tolerations:     node.kubernetes.io/not-ready:NoExecute for 300s
	                 node.kubernetes.io/unreachable:NoExecute for 300s
	Events:
	  Type    Reason                 Age   From                         Message
	  ----    ------                 ----  ----                         -------
	  Normal  Scheduled              2m    default-scheduler            Successfully assigned hello-nginx-6f9f4fc7dd-slnp2 to docker-for-desktop
	  Normal  SuccessfulMountVolume  2m    kubelet, docker-for-desktop  MountVolume.SetUp succeeded for volume "default-token-wlnp4"
	  Normal  Pulling                2m    kubelet, docker-for-desktop  pulling image "nginx"
	  Normal  Pulled                 2m    kubelet, docker-for-desktop  Successfully pulled image "nginx"
	  Normal  Created                2m    kubelet, docker-for-desktop  Created container
	  Normal  Started                2m    kubelet, docker-for-desktop  Started container
	```	

## Expose Pod as a Service

* kubectl get deployments

	```
	NAME          DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
	hello-nginx   1         1         1            1           5m
	```

* kubectl expose deployment hello-nginx --type=NodePort

	```
	service "hello-nginx" exposed
	```

* kubectl get services

	```
	NAME          TYPE        CLUSTER-IP     EXTERNAL-IP   PORT(S)        AGE
	hello-nginx   NodePort    10.98.232.74   <none>        80:31799/TCP   1m
	kubernetes    ClusterIP   10.96.0.1      <none>        443/TCP        14h
	```

* kubectl describe service hello-nginx

	```
	Name:                     hello-nginx
	Namespace:                default
	Labels:                   run=hello-nginx
	Annotations:              <none>
	Selector:                 run=hello-nginx
	Type:                     NodePort
	IP:                       10.98.232.74
	LoadBalancer Ingress:     localhost
	Port:                     <unset>  80/TCP
	TargetPort:               80/TCP
	NodePort:                 <unset>  31799/TCP
	Endpoints:                10.1.0.5:80
	Session Affinity:         None
	External Traffic Policy:  Cluster
	Events:                   <none>
	```

## Scaling the Service

* kubectl scale --replicas=3 deployment/hello-nginx

	```
	deployment.extensions "hello-nginx" scaled
	```

*  kubectl get deployments

	```
	NAME          DESIRED   CURRENT   UP-TO-DATE   AVAILABLE   AGE
	hello-nginx   3         3         3            3           11m
	```


## Access Kubernetes API

* kubectl proxy --port=8080 

	```
	Starting to serve on 127.0.0.1:8080
	```

* Ping

	```
	curl -X GET http://localhost:8080/api
	{
	  "kind": "APIVersions",
	  "versions": [
	    "v1"
	  ],
	  "serverAddressByClientCIDRs": [
	    {
	      "clientCIDR": "0.0.0.0/0",
	      "serverAddress": "192.168.65.3:6443"
	    }
	  ]
	}
	```






