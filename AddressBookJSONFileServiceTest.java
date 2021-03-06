package assignmenttest;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bl.jdbcassignment.EmployeePayrollData;
import com.google.gson.Gson;

import assignment.AddressBookFileIOService;
import assignment.AddressBookFileIOService.IOService;
import assignment.DatabaseException;
import assignment.Person;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookJSONFileServiceTest {

	@Before
	public void setUp() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	/**
	 * 
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

	/**
	 * UC 22
	 * 
	 */
	@Test
	public void givenContactDataInJSONServer_WhenRetrieved_ShouldMatchTheCount() {
		Person[] arrayOfContact = getContactList();
		AddressBookFileIOService addressBookFileService = new AddressBookFileIOService(Arrays.asList(arrayOfContact));
		long entries = addressBookFileService.countEntries();
		assertEquals(5, entries);
	}
	
	
	/**
	 * UC 23
	 * 
	 * adds new contacts to JSON server and returns response
	 * 
	 * @param newContacts
	 * @return
	 */
	private Response addContactToJsonServer(Person contact) {
		String json = new Gson().toJson(contact);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(json);
		return request.post("/contacts");
	}
	
	@Test
	public void givenMultipleNewContacts_WhenAdded_ShouldMatch201ResponseAndCount() {
		List<Person> newContacts = Arrays.asList(
				new Person("Joe", "Biden", "USA", "WA", "DC", 525252, 045600, "joeb@gmail.com",
						LocalDate.now()),
				new Person("Kamala", "Harris", "USA", "LA", "CA", 858585, 00110000, "kamala@gmail.com",
						LocalDate.now()));
		AddressBookFileIOService addressBookFileService = new AddressBookFileIOService(Arrays.asList(getContactList()));
		newContacts.forEach(contact -> {
			Runnable task = () -> {
				Response response = addContactToJsonServer(contact);
				int statusCode = response.getStatusCode();
				assertEquals(201, statusCode);
				addressBookFileService.addToApplicationMemory(contact);
			};
			Thread thread = new Thread(task, contact.getFirstName());
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		long entries = addressBookFileService.countEntries();
		assertEquals(7, entries);
	}
	
	/**
	 * UC 24
	 * 
	 * updating phone number of person in JSON server as well as application memory
	 * 
	 * @param name
	 * @param phone
	 * @return
	 * @throws DatabaseException
	 */
	private Response updatePhone(String name, long phone) throws DatabaseException {
		Person[] arrayOfContact = getContactList();
		AddressBookFileIOService addressbookService = new AddressBookFileIOService(Arrays.asList(arrayOfContact));
		addressbookService.updatePersonsPhone(name, phone, IOService.REST_IO);
		Person contactToUpdate = addressbookService.getContact(name);
		String contactJson = new Gson().toJson(contactToUpdate);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactJson);
		return request.put("/contacts/" + contactToUpdate.getId());

	}

	@Test
	public void givenMultipleEmployees_WhenUpdatedSalary_ShouldSyncWithDB() throws DatabaseException {
		int statusCode = updatePhone("Bill Gates", 8888855555L).getStatusCode();
		assertEquals(200, statusCode);
	}
	
	/**
	 * UC 25
	 * 
	 * deleting contact from JSON server and application memory
	 * 
	 * @param name
	 * @return
	 */
	private Response deleteContact(String name) {
		Person[] arrayOfContact = getContactList();
		AddressBookFileIOService addressbookService = new AddressBookFileIOService(Arrays.asList(arrayOfContact));
		Person contact = addressbookService.getContact(name);
		addressbookService.deleteFromApplicationMemory(contact);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		return request.delete("/contacts/" + contact.getId());
	}

	@Test
	public void givenContactToDelete_WhenDeleted_ShouldMatch200ResponseAndCount() {
		int statusCode = deleteContact("Bill Gates").getStatusCode();
		assertEquals(200, statusCode);
		Person[] arrayOfContact = getContactList();
		int count = arrayOfContact.length;
		assertEquals(6, count);
	}



}
