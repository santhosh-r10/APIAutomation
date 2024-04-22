package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class StepDefinition extends Utils {
	RequestSpecification res;
	ResponseSpecification resspec;
	Response response;
	// creating object of a Java Class(POJO)
	TestDataBuild data = new TestDataBuild();
	// Variable to store the Get value from Get Method Deserialization
	static String place_Id;

	@Given("Add Place Payload {string} {string} {string}")
	public void add_place_payload(String name, String language, String address) throws IOException {
        // Calling request Specification from utils method and parsing the created data to Load
		res = given().spec(requestSpecification()).body(data.addPlacePayLoad(name, language, address));
	}

	@When("user calls {string} with {string} http request")
	public void user_calls_add_place_api_with_post_http_request(String resource, String method) {
		// Write code here that turns the phrase above into concrete actions
		APIResources reourceAPI = APIResources.valueOf(resource);
		System.out.println(reourceAPI.getResource());
		resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		if (method.equalsIgnoreCase("POST"))
			response = res.when().post(reourceAPI.getResource());
		else if (method.equalsIgnoreCase("GET"))
			response = res.when().get(reourceAPI.getResource());

	}

	@Then("the api call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(response.getStatusCode(), 200);
	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String key, String ExpectedString) {
		// Write code here that turns the phrase above into concrete actions
		assertEquals(getJsonPath(response, key),ExpectedString);
	}

	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
		// Write code here that turns the phrase above into concrete actions
		place_Id = getJsonPath(response,"place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_Id);
		user_calls_add_place_api_with_post_http_request(resource,"GET");
		String actualName = getJsonPath(response,"name");
		assertEquals(actualName,expectedName);
		

	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    res = given().spec(requestSpecification()).body(data.deletePayLoad(place_Id));
	    
	}
}
