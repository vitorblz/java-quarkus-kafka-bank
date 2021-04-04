## 1) Instalar e configurar minikube e kubectl
Ver 1,2,3 do repositorio https://github.com/vitorblz/k8s-ecommerce-spring-kafka

## 2) Criar namespace
kubectl apply -f k8s/namespace.ym

## 3) Cria acesso: hello-world.info
kubectl apply -f k8s/ingress.yml;

Run de code below to get ip address
kubectl get ingress -n java-quarkus-kafka-bank

Replace the ip address and run this code to enable the address: docker.info
echo  "192.168.49.2 docker.info" >> /etc/hosts

### 4) Build transaction app
cd transaction-service;
./mvnw package -Pnative -DskipTests=true;
docker build -f src/main/docker/Dockerfile.native -t vitoroc/transaction-service .


### 5) Enviar imagem para minikube
minikube -p dev.to image load vitoroc/transaction-service:latest

### 6) Configure kafka
kubectl create -f 'https://strimzi.io/install/latest?namespace=java-quarkus-kafka-bank' -n java-quarkus-kafka-bank

