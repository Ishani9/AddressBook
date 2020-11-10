package assignmenttest;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;

import assignment.AddressBookFileIOService;
import assignment.Person;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AddressBookJSONFileServiceTest {

	@Before
	public void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	/**
	 * sending get request and retrieving all data from JSON server
	 * 
	 * @return
	 */
	private Person[] getContactList() {
		Response response = RestAssured.get("/contacts");
		System.out.println("Contact entries in JSONServer:\n" + response.asString());
		String responseBody = response.getBody().asString();
		System.out.println("Response Body is =>  " + responseBody);
		Person[] arrayOfContact = new Gson().fromJson(response.asString(), Person[].class);
		return arrayOfContact;
	}

	@Test
	public void givenContactDataInJSONServer_WhenRetrieved_ShouldMatchTheCount() {
		Person[] arrayOfContact = getContactList();
		AddressBookFileIOService addressBookFileService = new AddressBookFileIOService(Arrays.asList(arrayOfContact));
		long entries = addressBookFileService.countEntries();
		assertEquals(5, entries);
	}

}
