## 1) Instalar e configurar minikube e kubectl

Ver 1,2,3 do repositorio https://github.com/vitorblz/k8s-ecommerce-spring-kafka

## 2) Criar namespace

kubectl apply -f k8s/namespace.ym

## 3) Cria acesso: hello-world.info

kubectl apply -f k8s/ingress.yml;

Run de code below to get ip address
kubectl get ingress -n java-quarkus-kafka-bank

Replace the ip address and run this code to enable the address: docker.info
echo "192.168.49.2 docker.info" >> /etc/hosts

### 4) Build transaction app

cd transaction-service;

./mvnw package -Pnative -DskipTests=true;

docker build -f src/main/docker/Dockerfile.native -t vitoroc/transaction-service .

### 5) Enviar imagem para minikube

minikube -p dev.to image load vitoroc/transaction-service:latest

### 6) Configure kafka

kubectl create -f 'https://strimzi.io/install/latest?namespace=java-quarkus-kafka-bank' -n java-quarkus-kafka-bank;
kubectl apply -f k8s/kafka.yml;

### 7) Configure database

kubectl apply -f transaction-service/k8s/db-transaction-service-deployment.yaml;
kubectl apply -f transaction-service/k8s/db-transaction-service-service.yaml;

### 7) deploy transaction app

kubectl apply -f transaction-service/k8s/deployment.yml;

### 8) Build balance app

./mvnw package -Pnative -DskipTests=true;
docker build -f src/main/docker/Dockerfile.native -t vitoroc/balance-service .;
minikube -p dev.to image load vitoroc/balance-service:latest;

### 9) Deploy balance app

kubectl apply -f balance-service/k8s/db-balance-service-deployment.yaml ;
kubectl apply -f balance-service/k8s/db-balance-service-service.yaml;
kubectl apply -f balance-service/k8s/deployment.yml;

### 9) Testando app

curl -X POST -H "Content-Type: application/json" -d @income-transaction.json http://docker.info/transaction-service/transactions
