package assignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.io.File;

public class AddressBookFileIOService {
	
	public static String FILE_NAME = "AddressBook.txt";

	public void writeData(Map<String, AddressBook> stateAddressBookMap) {
		StringBuffer personBuffer = new StringBuffer();
		stateAddressBookMap.values().stream().map(book -> book.getPersonList()).forEach(list -> {
			list.forEach(person -> {
				String empString = person.toString().concat("\n");
				personBuffer.append(empString);
			});
		});
		try
		{
			Files.write(Paths.get(FILE_NAME), personBuffer.toString().getBytes());
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readData() {
		try {
			Files.lines(new File(FILE_NAME).toPath()).forEach(System.out::println);
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
