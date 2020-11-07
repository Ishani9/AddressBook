package assignmenttest;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import assignment.AddressBookFileIOService;
import assignment.AddressBookFileIOService.IOService;
import assignment.DatabaseException;
import assignment.Person;

public class AddressBookFileServiceTest {
	
	private static AddressBookFileIOService addressBookService;
	private static List<Person> contactData;

	@Before
	public void setUp() throws DatabaseException {
		addressBookService = new AddressBookFileIOService();
		contactData = addressBookService.readContactData(IOService.DB_IO);
	}

	
	/**
	 * UC 16
	 * 
	 * @throws DatabaseException
	 */
	@Test
	public void givenContactDataInDB_WhenRetrieved_ShouldMatchContactCount() throws DatabaseException {

		assertEquals(8, contactData.size());
	}
	
	/**
	 * UC 17
	 * 
	 * checking Updated info of a persons is in sync with database
	 * 
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	@Test
	public void givenNewDataForContact_WhenUpdated_ShouldBeInSync() throws DatabaseException {
		
		addressBookService.updatePersonsPhone("Bill Games", 123456789);
		addressBookService.readContactData(IOService.DB_IO);
		boolean result = addressBookService.checkContactDataSync("Bill Games");
		assertEquals(true, result);
		assertEquals(8, contactData.size());
	}
	
	/**
	 * UC 18
	 * 
	 * checking if the getContactsByDate() method returns list of persons added
	 * between given dates
	 * 
	 * @throws DatabaseException
	 */
	@Test
	public void givenContactDataInDB_WhenRetrieved_ShouldMatchContactAddedInGivenDateRangeCount()
			throws DatabaseException {
		List<Person> contactByDateList = null;
		LocalDate start = LocalDate.of(2018, 10, 12);
		LocalDate end = LocalDate.now();
		contactByDateList = addressBookService.getContactsByDate(start, end);
		assertEquals(16, contactByDateList.size());
	}
	
	/**
	 * UC 19
	 * 
	 * checking if we get the list of contacts belonging to city or state
	 * 
	 * @throws DatabaseException
	 */
	@Test
	public void givenContactDataInDB_WhenRetrievedByCity_ShouldMatchContactInCityCount()
			throws DatabaseException {
		List<Person> contactByCity = addressBookService.getContactsByCity("Mumbai");
		assertEquals(3, contactByCity.size());
	}
	
	@Test
	public void givenContactDataInDB_WhenRetrievedByState_ShouldMatchContactInStateCount()
			throws DatabaseException {
		List<Person> contactByState = addressBookService.getContactsByState("MH");
		assertEquals(4, contactByState.size());
	}
}
