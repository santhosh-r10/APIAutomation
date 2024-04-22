Feature: Validating Plave API's

@AddPlace @Regression
Scenario Outline: Verify if Place is being Successfully added using AddPlaceAPI
Given Add Place Payload "<name>" "<language>" "<address>"
When user calls "AddPlaceAPI" with "post" http request
Then the api call got success with status code 200
And "status" in response body is "OK"
And "scope" in response body is "APP"
Then verify place_Id created maps to "<name>" using "getPlaceAPI" 

Examples:
     |name    |language|address               |
     |santhosh|English |World Cross Center    |
#     |Tom     |Tamil   |Sea World Cross Center|

@DeletePlace @Regression
Scenario: Verify if Delete Place functionality is working
Given DeletePlace Payload
When user calls "DeletePlaceAPI" with "post" http request
Then the api call got success with status code 200
And "status" in response body is "OK"


