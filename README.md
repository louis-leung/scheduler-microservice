# Overview
This is a scheduler microservice API that can accept tasks, distribute them to task consumers, and return a scheduling policy when requested. This is comprised of an application (backed by MongoDB) and a Spring Cloud Config Server.

## Config Server
You must launch the config server before running the Application.
To run:
- ``` cd scheduler-microservice-cloud-config ```
- ``` mvn spring-boot:run ```

The config server is now launched on ```http://localhost:8090``` and pulling `.properties` files from my Github at https://github.com/louis-leung/scheduler-microservice

## Application
Start up a MongoDB instance on localhost listening on default port 27017.

Now we can run the application.
To run:
- ``` cd scheduler-microservice```
- ``` mvn spring-boot:run ```

Navigate to ```http://localhost:8080/swagger-ui.html``` for Swagger API documentation and usage.

## Testing
- ``` cd scheduler-microservice```
- ``` mvn test```



#### Assumptions:
1. A consumer won't query for more tasks until it has finished all the tasks it has been assigned.
2. Task due dates are entered in UTC time.

#### Issues and Resolutions: 
1. Concurrency: 
    1. What happens if two consumers query for tasks simultaneously? 
    - As of MongoDB 3.2, the WiredTiger storage engine is used and by default enables optimistic concurrency control. So if two consumers query at the same time, one query will incur a write conflict causing MongoDB to transparently retry the operation. 
    
#### Improvements Needed:
- The integration tests currently only test for correct JSON payload of the object created as opposed to the entire Resource wrapper (with links). This is due to some object mapper conflicts that I haven't resolved yet. 