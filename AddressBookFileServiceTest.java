package assignmenttest;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;

import assignment.AddressBookFileIOService;
import assignment.AddressBookFileIOService.IOService;
import assignment.DatabaseException;
import assignment.Person;

public class AddressBookFileServiceTest {
	
	/**
	 * UC 16
	 * 
	 * @throws DatabaseException
	 */
	@Test
	public void givenContactDataInDB_WhenRetrieved_ShouldMatchContactCount() throws DatabaseException {
		AddressBookFileIOService addressBookService = new AddressBookFileIOService();
		List<Person> contactData = addressBookService.readContactData(IOService.DB_IO);
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
		AddressBookFileIOService addressBookService = new AddressBookFileIOService();
		List<Person> contactData = addressBookService.readContactData(IOService.DB_IO);
		addressBookService.updatePersonsPhone("Bill Games", 123456789);
		addressBookService.readContactData(IOService.DB_IO);
		boolean result = addressBookService.checkContactDataSync("Bill Games");
		assertEquals(true, result);
		assertEquals(8, contactData.size());
	}
}
