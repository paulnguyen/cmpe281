

	  _  __     _                          _            
	 | |/ /    | |                        | |           
	 | ' /_   _| |__   ___ _ __ _ __   ___| |_ ___  ___ 
	 |  <| | | | '_ \ / _ \ '__| '_ \ / _ \ __/ _ \/ __|
	 | . \ |_| | |_) |  __/ |  | | | |  __/ ||  __/\__ \
	 |_|\_\__,_|_.__/ \___|_|  |_| |_|\___|\__\___||___/
	                                                    
                                                                                        

# Kubernetes Stateful Application

* https://kubernetes.io/docs/tasks/run-application/run-single-instance-stateful-application/
* https://kubernetes.io/docs/concepts/workloads/controllers/statefulset/
* https://kubernetes.io/docs/concepts/configuration/secret/

# mysql-deployment.yaml

```
apiVersion: v1
kind: Service
metadata:
  name: mysql
spec:
  ports:
  - port: 3306
  selector:
    app: mysql
  clusterIP: None
---
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: mysql-pv-claim
spec:
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 20Gi
---
apiVersion: apps/v1 # for versions before 1.9.0 use apps/v1beta2
kind: Deployment
metadata:
  name: mysql
spec:
  selector:
    matchLabels:
      app: mysql
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        app: mysql
    spec:
      containers:
      - image: mysql:5.6
        name: mysql
        env:
          # Use secret in real usage
        - name: MYSQL_ROOT_PASSWORD
          value: password
        ports:
        - containerPort: 3306
          name: mysql
        volumeMounts:
        - name: mysql-persistent-storage
          mountPath: /var/lib/mysql
      volumes:
      - name: mysql-persistent-storage
        persistentVolumeClaim:
          claimName: mysql-pv-claim
```


# MySQL Steps

```
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
```








