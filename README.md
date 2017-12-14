# MyGCD

Project tech stack involves 

Java 8,
Maven 3.5,
Spring 4.1.4,
MySQL 5 +,
ActiveMQ 5.15.2,
JBOSS AS 7.0.0 Final

The project exposes two APIS - one is a restful webservice and the other is SOAP webservice.

Restful webservice exposes two apis - 
•	push-	which returns the status of the request to the caller as a String. The two parameters will be added to a JMS queue.
• list-	which returns a list of all the elements ever added to the queue from a database in the order added as a JSON structure. 

SOAP webservice exposes three apis
•	gcd- which returns the greatest common divisor* of the two integers at the head of the queue. These two elements will subsequently be discarded from the queue and the head replaced by the next two in line.
•	gcdList- which returns a list of all the computed greatest common divisors from a database. 
•	gcdSum- which returns the sum of all computed greatest common divisors from a database.

Compile the whole project from the root folder MyGcd using "mvn clean install". and the .ear file will be created in GCDEar project.<target> folder
Deploy the file into JBOSS AS 7.0.0 server and type the following URL

For local deployment:

Rest services

http://localhost:8080/GCDService/rest/list
http://localhost:8080/GCDService/rest/push

Soap Services
http://localhost:8080/GCDService/endpoint/gcdService.wsdl