## Prerequisites:
    Java: Version 17 or higher is required.
    Maven: Ensure you have the latest stable version of Maven installed.

## Directory Structure Overview:
    pact_contract_tesing/src/main/java/pact/api/IncidentController.java: Contains the Spring Boot application with a simple api with end point http://localhost:8081/api/dv-prc-finctrl-trsy-api/1/incidents/1 .
    pact_contract_tesing/src/test/java/pact/consumer/ConsumerTest.java: Holds the Consumer tests to validate interactions with the provider.
    pact_contract_tesing/src/test/java/pact/provider/ProviderTest.java: Contains the Provider tests to verify the provider's conformance to the pact contract.

## Execution Sequence:
    1. Navigate to the project directory (Folder where you see pom file)	
    2. Compile and build the project using command : ** mvn clean install ** from IDE terminal 
	3. Now open the commnad prompt from the project directory project directory
	4. type command ** mvn spring-boot:run ** 
	5. Wait till the command is successfull
	6. go to browser and open the url : http://localhost:8081/api/dv-prc-finctrl-trsy-api/1/incidents/1 
	7. this should give json response        
    8. Run Consumer Tests & Generate Pact File from the using commnad : ** mvn verify -Pconsumer-tests **  from IDE terminal 
    9. Verify that the Pact contract file in the target/pacts folder:
    10. Now run provider test using commnad : ** mvn verify -Pconsumer-tests **  from IDE terminal 
    
