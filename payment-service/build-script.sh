echo "Running build for payment service..."
mvn clean
mvn package
docker build -t payment-service-app:1.0 .
#kubectl apply -f payment-service-deployment.yaml