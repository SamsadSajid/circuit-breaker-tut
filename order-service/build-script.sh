echo "Running build for order service..."
mvn clean
mvn package
docker build -t order-service-app:1.0 .
#kubectl apply -f order-service-deployment.yaml