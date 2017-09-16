# Code Challenge -RESTful API for N26

## Application
N26Application is an Restful APIs for transactions. The following are the features-

- POST /n26app/transactions -
This Rest endpoint is to post transaction and it returns codes : 

	a) 201 - Successfully Created 
	
	b) 204 - No Content - Transaction is not posted as it is older than 60 secs
	
	c) 400 - Bad Request -  If the input Data is invalid like amount <=0 and timestamp is invalid
	
Sample request JSON -
 {
	"amount":12.44,
	"timestamp" : 1505564636849
 }

- GET /n26app/transactions -
This Rest endpoint returns the list of transactions posted to the application

Sample response JSON - 
 [
  {
    "amount": 12.44,
    "timestamp": 1505588558079
  }
 ]

- GET /n26app/transactions -
This Rest endpoint returns statistics of the transactions happened not before 60 secs

Sample response JSON - 
{
  "sum": 12.44,
  "avg": 12.44,
  "max": 12.44,
  "min": 12.44,
  "count": 1
}


## Author
Tarun Agarwal 
tatarunaggarwal@gmail.com

## Pre-requisite 
JAVA 1.8

Maven

Demo Setup:
------------------------------
1. Checkout project from https://github.com/tarun38716/N26Assignment.git
2. Maven build application: mvn clean install.
3. Run : java -jar N26Assignment-0.0.1-SNAPSHOT.jar
4. API can be accessed using any REST Client or refer point 5
5. API Documentation is done using Swagger- Goto - http://<HOST>:8080/swagger-ui.html# - All the APIs can be accessed directly from here
