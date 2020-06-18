Steps to get the Web application running 

1. Run backend 
	a. navigate to critico dir --> cd critico
	b. run --> mvn clean install
	c. run --> mvn spring-boot:run
 
2. Run frontend
	a. navigate to critico-ui dir --> cd critico-ui
	b. run --> npm install 
	c. run --> npm run dev 

How to run tests -

Test 1 - 
Hit the endpoint - http://localhost:9090/api/getAllPaths/A/H
Output - [[0,1,2,5,6,7],[0,1,4,6,7],[0,1,5,6,7],[0,2,1,4,6,7],[0,2,1,5,6,7],[0,2,5,1,4,6,7],[0,2,5,6,7],[0,3,6,7]]


Test 2 - 
Hit the endpoint - http://localhost:9090/api/getShortestPathLength/A/H
Output - 3

Test 3 - 

Navigate to http://localhost:8080/
