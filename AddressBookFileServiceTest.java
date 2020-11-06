package assignmenttest;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;

import assignment.AddressBookFileIOService;
import assignment.AddressBookFileIOService.IOService;
import assignment.DatabaseException;
import assignment.Person;

public class AddressBookFileServiceTest {
	
	@Test
	public void givenContactDataInDB_WhenRetrieved_ShouldMatchContactCount() throws DatabaseException {
		AddressBookFileIOService addressBookService = new AddressBookFileIOService();
		List<Person> contactData = addressBookService.readContactData(IOService.DB_IO);
		assertEquals(8, contactData.size());
	}
}
