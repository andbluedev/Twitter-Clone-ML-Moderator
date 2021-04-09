./mvnw clean
export REALM_NAME=twitter-clone-ml-project
export KEYCLOAK_SERVER_URL=https://keycloak.pouretadev.com:8443
export CLIENT_ID=springboot-microservice-dev
export CLIENT_SECRET=fa7a6531-020c-4d3a-a652-d2275d39909c
./mvnw spring-boot:run -Dspring.profiles.active=dev
