This is a scheduler microservice API that can accept tasks, distribute them to task consumers, and return a scheduling policy when requested.
### Assumptions:
1. A consumer won't query for more tasks until it has finished all the tasks it has been assigned.

### Issues and Resolutions: 
1. Concurrency: 
    1. What happens if two consumers query for tasks simultaneously? 
    - As of MongoDB 3.2, the WiredTiger storage engine is used and by default enables optimistic concurrency control. So if two consumers query at the same time, one query will incur a write conflict causing MongoDB to transparently retry the operation. 
    
### Improvements Needed:
- The integration tests currently only test for correct JSON payload of the object created as opposed to the entire Resource wrapper (with links). This is due to some object mapper conflicts that I haven't resolved yet. 