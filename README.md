# Data Analyzer with Cache API for DarkSky 
This is an example of how to build an API that will utilize the DarkSky api 
for historical data, cache results in DB, analyze results, then return 
analysis a to the client. This app can be expanded to process additional 
data from DarkSky. The cool thing about this api is that it will only call 
the Dark Sky api 1 time for a particular day. This will help to keep the number
of calls to Dark Sky down and therefor will lower your usage cost.
 </br></br>
 Here are the current endpoints:
 
| URLs        | CRUD           |  Description | 
| :------------- |:-------------|:-------------|
| http://localhost:8080/api/health | GET | Validates Health|
| http://localhost:8080/api/weather/airport?start_date={starting time in EpochSeconds}&end_date={ending time in EpochSeconds} | POST | Will return analysis of DarkSky data between the two date ranges. |

 I used the following:
- Jersey _2_
- Spring _4_
- mongo-java-driver _2_
- spring-data-mongodb _1.2_

I'm building with Gradle:
- gradle _3.2.1_

In this project I also tested with difference frameworks
- junit _4.10_
- JMockit _1.24_
- hamcrest _1.3_
