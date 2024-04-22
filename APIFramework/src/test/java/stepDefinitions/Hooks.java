package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		StepDefinition stepDefinition = new StepDefinition();
		if (StepDefinition.place_Id == null) {
			stepDefinition.add_place_payload("John", "Telugu", "address");
			stepDefinition.user_calls_add_place_api_with_post_http_request("AddPlaceAPI", "POST");
			stepDefinition.verify_place_id_created_maps_to_using("John", "getPlaceAPI");
		}
	}
}
