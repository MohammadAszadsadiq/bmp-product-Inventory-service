FROM adoptopenjdk/openjdk8:latest
ADD target/bmp-product-inventory-business-service-1.0.jar bmp-product-inventory-business-service.jar
RUN sh -c 'touch /bmp-product-inventory-business-service.jar'
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/bmp-product-inventory-business-service.jar"]