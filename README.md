Reservation System Implementation

Steps Taken  
Database Structure:

Created the reservations table with the following fields:

id (BIGINT, auto-increment)

user_id (foreign key referencing the users table)

available_slot_id (foreign key referencing the available_slots table)

Added indexes on foreign keys to improve performance.

Added the following index on the available_slots table to speed up searches:
@Index(name = "start_time_and_is_reserve_indexes", columnList = "start_time, is_reserved")

API Implementation:

POST /api/reservations: To reserve the nearest available slot (no request body).

DELETE /api/reservations/{id}: To cancel a reservation by providing the reservation ID as a parameter.

Implementation Details
Concurrency Management

Initial Approach: Initially, Optimistic Locking was used. However, during heavy load testing with Apache Benchmark (high request count), the failure rate increased.

Improved Approach: By using Pessimistic Locking and setting the transaction Isolation Level to SERIALIZABLE, the failure rate was reduced to zero, improving system performance.

The following annotations were used:

@Lock(LockModeType.PESSIMISTIC_WRITE) to lock records at the database level.

@Transactional(isolation = Isolation.SERIALIZABLE) to manage transaction isolation in serializable mode.

Performance Optimization

Enabled Virtual Threads for better resource management:
spring.threads.virtual.enabled=true

Optimized HikariCP settings:
spring.datasource.hikari.maximum-pool-size=100
spring.datasource.hikari.minimum-idle=10
spring.datasource.hikari.connection-timeout=30000

Disabled open-in-view for performance improvement:
spring.jpa.open-in-view=false

Authentication

Implemented JWT for token validation, including signature verification and expiration checks.

Note: The login/logout status of the user is not fully verified and requires further development.(I didn't have enough time to implement it)

Optimizing with Redis Sorted Set
To improve the speed of managing available slots, Redis Sorted Sets were used, where the timestamp serves as the score. This prevents issues related to sorting slots efficiently.

Performance Testing

Test 1:

Total requests: 50,000

Concurrent requests: 1,000

Server Software:        
Server Hostname:        localhost
Server Port:            8080

Document Path:          /reserve/api/reservations  
Document Length:        22 bytes  
  
Concurrency Level:      1000  
Time taken for tests:   1065.392 seconds  
Complete requests:      50000  
Failed requests:        0  
Total transferred:      16250000 bytes  
Total body sent:        16500000  
HTML transferred:       1100000 bytes  
Requests per second:    46.93 [#/sec] (mean)  
Time per request:       21307.846 [ms] (mean)  
Time per request:       21.308 [ms] (mean, across all concurrent requests)  
Transfer rate:          14.90 [Kbytes/sec] received  
15.12 kb/s sent  
30.02 kb/s total  
  
Connection Times (ms)  
min  mean[+/-sd] median   max  
Connect:        0   11 104.8      0    1066  
Processing:   157 21039 5873.9  22977   31057  
Waiting:      157 21039 5873.9  22976   31057  
Total:        192 21050 5852.5  22977   31058  
  
Percentage of the requests served within a certain time (ms)
50%  22977  
66%  24872  
75%  26158  
80%  26458  
90%  27561  
95%  28237  
98%  28438  
99%  28586  
100%  31058 (longest request)

-----------------------------------------------------------------------------

Test 2:

Total requests: 1,000

Concurrent requests: 20

Server Software:          
Server Hostname:        localhost  
Server Port:            8080  
  
Document Path:          /reserve/api/reservations  
Document Length:        22 bytes  
  
Concurrency Level:      20  
Time taken for tests:   11.815 seconds  
Complete requests:      1000  
Failed requests:        0  
Total transferred:      325000 bytes  
Total body sent:        309000  
HTML transferred:       22000 bytes  
Requests per second:    84.64 [#/sec] (mean)  
Time per request:       236.294 [ms] (mean)  
Time per request:       11.815 [ms] (mean, across all concurrent requests)  
Transfer rate:          26.86 [Kbytes/sec] received  
25.54 kb/s sent  
52.40 kb/s total  
  
Connection Times (ms)  
min  mean[+/-sd] median   max  
Connect:        0    0   0.1      0       1  
Processing:    43  227  44.3    213     370  
Waiting:       42  226  44.1    213     366  
Total:         43  227  44.3    214     370  
  
Percentage of the requests served within a certain time (ms)  

50%    214  
66%    239  
75%    257  
80%    267  
90%    290  
95%    317  
98%    332  
99%    340  
100%    370 (longest request)

Note: Due to limitations on my personal system and the absence of a sandbox environment, tests were not run in optimal conditions. However, it is expected that performance will improve in an isolated, suitable environment (like a sandbox).

Conclusion
Efforts were made to develop a reservation system that incorporates concurrency management, performance optimization, and functional testing. The results were documented, and the system has shown improvements in both concurrency handling and overall performance.