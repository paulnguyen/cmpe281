
pod-run:
	kubectl apply -f phptest-pod.yaml

pod-list:
	kubectl get pods

pod-desc:
	kubectl describe pods phptest

pod-delete:
	kubectl delete -f phptest-pod.yaml

pod-shell:
	kubectl exec -it phptest -- /bin/bash

pod-logs:
	kubectl logs -f phptest

port-forward:
	kubectl port-forward phptest 8080:80






