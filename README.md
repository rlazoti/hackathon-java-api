Hackathon Java API
=============

Code challenge to create a api using Java.


## Architecture

The API's implementation was divided in 4 phases:

-1) Create the services: Product, Customer and Order
-2) Add service discovery, centralised configuration and load balancer (client-side)
-3) Create api gateway and a authentication service
-4) Configure and collect metrics and tracing from all services

![Idea](https://raw.githubusercontent.com/rlazoti/hackathon-java-api/master/services.png)

## Technologies

All services are based on Spring Boot and Spring Cloud.
Service discovery uses Netflix Eureka.
Client-side Load Balancer uses Netflix Ribbon.
Metrics will be stored into Prometheus and tracing into Zipkin.

There's a very simple [presention](https://raw.githubusercontent.com/rlazoti/hackathon-java-api/master/Presentation.pdf) about this project.


## To Do List / Improvements

- [x] Add initial services: Product, Customer and Order
- [x] Add some tests to all services
- [x] Create a service discovery service
- [x] Change order service to call the customer service using client-side load balancing
- [x] Create initial version of auth service
- [ ] Finish the API
- [ ] Add some kind of documentation (Swagger)
- [ ] Add more tests
- [ ] Add centralised configuration service
- [ ] Create API Gateway
- [ ] Finish auth service, and add tests to it
- [ ] Configure metrics on all services
- [ ] Configure tracing on all services


## How to run

To install, clone this repository:

```bash
git clone https://github.com/rlazoti/hackathon-java-api
cd hackathon-java-api
mvn clean package
```

After that, run the services using the folowing commands:

``` bash
java -jar eureka-service/target/eureka-service-1.0.0.jar
java -jar customer-service/target/customer-service-1.0.0.jar
java -jar order-service/target/order-service-1.0.0.jar
java -jar product-service/target/product-service-1.0.0.jar
```

To open Eureka, access: http://localhost:8761/

![Eureka](https://raw.githubusercontent.com/rlazoti/hackathon-java-api/master/eureka.png)


Example of requests to some services:


``` bash
curl -v http://localhost:8080/api/v1/Customer/1
curl -v http://localhost:8082/api/v1/Order/1
```


## Author

-Rodrigo Lazoti
