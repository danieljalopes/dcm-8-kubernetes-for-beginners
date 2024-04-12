# Introduction
This code is part of Kubernetes for Beginners: Microservices, Data Persistence, and Horizontal Scaling with Minikube. 
Here you see several commands used to create this project.

## K8s

apply everything
```bash
kubectl apply -f .\namespaces.yaml
kubectl apply -f .\
```
replace with this manifest
```bash
kubectl replace -f .\<filename>.yaml
```

## minikube

open dashboard `minikube dashboard`
access minikube console `minikube ssh`

open access to nodeport - `minikube service <nodeport_name> -n <namespace> --url`
### MongoDB
Gives the connection string to be used with Compass
```bash
minikube service mongodb-np -n databases --url
```

```bash
# enter the container's shell
kubectl exec -it mongodb -n databases --container mongodb-app -- /bin/bash
```

## consumer
Accessing consumer app from outside minikube
this will give an link to use in the producer app
```
minikube service consumer-np -n microservices --url
```
```
minikube service consumer-lb -n microservices --url
```
delete nodeport
```
kubectl delete svc consumer-np -n microservices
```
delete deployment
```
kubectl delete deployment consumer-dpl -n microservices
```
list pods in microservices namespace
```
kubectl get pod -n microservices -o wide
```

## elasticsearch

Setup for single node elasticsearch
https://www.elastic.co/guide/en/elasticsearch/reference/8.11/security-minimal-setup.html

To generate enrollment token for Kibana:
Enter elasticksearch container and run 

bin/elasticsearch-create-enrollment-token --scope kibana


setting elasticksearch witout security
https://opster.com/guides/elasticsearch/security/how-to-disable-security-in-elasticsearch/


https://blog.searce.com/deploying-a-secure-elasticsearch-environment-on-kubernetes-deb0f981ddf5

## Kibana
Accessing kibana from outside minikube
this will give a link to use in a browser
```
minikube service kibana-np -n monitoring --url
```
Delete elasticsearch pod
```
kubectl delete pod elasticsearch-0 -n monitoring --force
```

## Docker
build Docker image
```bash
docker build -f .\MyImageDockerfile  -t <username>\<image name>:latest
```

Push to Docker Hub
```bash
docker login -u 'username' -p 'password'
docker push <username>\<image name>:latest
```


## Mongodb

https://www.mongodb.com/compatibility/docker

## Fluentd
FLuentd is a log aggregator
### To install
As Fluentd will collect all the logs from the cluster we need to install in the kube-system namespace. We need to create a RBAC accesses:
1. create a ServiceAccount to enable fluentd to run:
```
kubectl create -f fluentd-rbac.yaml
```
2. create the Daemonset 
```
kubectl create -f fluentd.yaml
```
3. Verify Fluentd is installed and runnning:
```bash
kubectl get pods -n kube-system
```
```bash
NAME                               READY   STATUS    RESTARTS        AGE
coredns-5d78c9869d-6wgsl           1/1     Running   0               6h57m
etcd-minikube                      1/1     Running   0               6h57m
fluentd-nxn9q                      1/1     Running   0               2m51s
kube-apiserver-minikube            1/1     Running   0               6h57m
kube-controller-manager-minikube   1/1     Running   0               6h57m
kube-proxy-hq4gl                   1/1     Running   0               6h57m
kube-scheduler-minikube            1/1     Running   0               6h57m
metrics-server-7746886d4f-8759x    1/1     Running   0               6h53m
storage-provisioner                1/1     Running   1 (6h57m ago)   6h57m
```


## create Secrets
```bash
 echo -n 'password' | base64
```