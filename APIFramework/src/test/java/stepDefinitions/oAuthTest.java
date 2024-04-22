package stepDefinitions;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

import Pojo.Api;
import Pojo.GetCourse;
import Pojo.WebAutomation;
import io.restassured.path.json.JsonPath;

public class oAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] courseTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };
        // Getting Access TOken 
		String response = given()
				.formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W").formParams("grant_type", "client_credentials")
				.formParams("scope", "trust").when().log().all()
				.post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
		System.out.println(response);
		JsonPath jsonPath = new JsonPath(response);
		String accessToken = jsonPath.getString("access_token");

		GetCourse gc = given().queryParams("access_token", accessToken).when().log().all()
				.get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
		
        // Getting needed Strings using Deserialization
		System.out.println(gc.getLinkedIn());
		System.out.println(gc.getInstructor());
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

		// Getting Particular course form the JSON
		List<Api> getApi = gc.getCourses().getApi();
		for (int i = 0; i < getApi.size(); i++) {
			if (getApi.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
				System.out.println(getApi.get(i).getPrize());
		}
        
		// Comparing Expected and Actual Course titles
		ArrayList<String> a = new ArrayList<String>();
		List<WebAutomation> getCourses = gc.getCourses().getWebAutomation();
		for (int i = 0; i < getCourses.size(); i++) {
			a.add(getCourses.get(i).getCourseTitle());
			System.out.println(getCourses.get(i).getCourseTitle());
		}
		
		List<String> expectedList = Arrays.asList(courseTitles);
		Assert.assertTrue(a.equals(expectedList));
		
		
	}

}
