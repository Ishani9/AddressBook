package assignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class AddressBookFileIOService {
	
	public enum IOService {
		CONSOLE_IO, FILE_IO, DB_IO, REST_IO
	};
	
	public static String FILE_NAME = "AddressBook.txt";
	
	private List<Person> contactList = new ArrayList<>();
	private AddressbookDBService addressBookDB;

	public AddressBookFileIOService() {
		addressBookDB = AddressbookDBService.getInstance();
	}

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
	
	/**
	 * UC 14
	 * 
	 */
	public void writeDataCSV(Map<String, AddressBook> stateAddressBookMap) {

		File file = new File(
				"C:\\Users\\Ishani\\eclipse-workspace\\gradleAssignment\\Addressbook.csv");

		try {
			FileWriter outputfile = new FileWriter(file);
			CSVWriter writer = new CSVWriter(outputfile);
			List<String[]> data = new ArrayList<String[]>();
			String[] header = { "First Name", "Last Name", "Address", "City", "State", "ZIP", "Phone Number",
					"Email ID" };
			data.add(header);
			stateAddressBookMap.values().stream().map(entry -> entry.getPersonList())
					.forEach(entry2 -> entry2.forEach(person -> {
						String[] personData = { person.getFirstName(), person.getLastName(), person.getAddress(),
								person.getCity(), person.getState(), Integer.toString(person.getZip()),
								Long.toString(person.getPhoneNumber()), person.getEmail() };
						data.add(personData);
					}));

			writer.writeAll(data);
			writer.close();
			System.out.println("Data entered successfully to Addressbook.csv file.");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readDataCSV() {
		try {
			FileReader filereader = new FileReader(
					"C:\\Users\\Ishani\\eclipse-workspace\\gradleAssignment\\Addressbook.csv");
			CSVReader csvReader = new CSVReader(filereader);
			String[] nextRecord;

			System.out.println("Reading from CSV file line by line...");
			while ((nextRecord = csvReader.readNext()) != null) {
				for (String cell : nextRecord) {
					System.out.print(cell + "\t  ");
				}
				System.out.println();
			}
			csvReader.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void writeDataGSON(Map<String, AddressBook> stateAddressBookMap) {
		try {
			Gson gson = new Gson();
			FileWriter writer = new FileWriter(
					"C:\\Users\\Ishani\\eclipse-workspace\\gradleAssignment\\AddressBook.json");
			stateAddressBookMap.values().stream().map(entry -> entry.getPersonList())
					.forEach(listEntry -> listEntry.forEach(person -> {
						String json = gson.toJson(person);
						try {
							writer.write(json);
						} catch (IOException exception) {
							exception.printStackTrace();
						}
					}));
			writer.close();
			System.out.println("Data entered successfully to AddressBook.json file.");
		} 
		catch (IOException exception) {
			exception.printStackTrace();
		}
	}

	public void readDataGSON() {
		Gson gson = new Gson();
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(
					"C:\\Users\\Ishani\\eclipse-workspace\\gradleAssignment\\AddressBook.json"));
			System.out.println("Reading from JSON file ...");
			JsonStreamParser parser = new JsonStreamParser(bufferedReader);
			while (parser.hasNext()) {
				JsonElement json = parser.next();
				if (json.isJsonObject()) {
					Person person = gson.fromJson(json, Person.class);
					System.out.println(person);
				}
			}
		} 
		catch (IOException exception) {
			exception.printStackTrace();
		}
	}
	
	/**
	 * UC 16
	 * 
	 * reads all data from database
	 * 
	 * @param ioService
	 * @return
	 * @throws DatabaseException
	 */
	public List<Person> readContactData(IOService ioService) throws DatabaseException {
		if (ioService.equals(IOService.DB_IO)) {
			this.contactList = addressBookDB.readData();
		}
		return this.contactList;
	}
	
	/**
	 * UC 17
	 * 
	 * @param name
	 * @param phone
	 * @throws DatabaseException
	 */
	public void updatePersonsPhone(String name, long phone) throws DatabaseException {
		int result = addressBookDB.updatePersonsData(name, phone);
		if (result == 0)
			return;
		this.contactList = addressBookDB.readData();
		Person contact = this.getContact(name);
		if (contact != null)
			contact.setPhoneNumber(phone);
	}

	private Person getContact(String name) {
		Person contact = this.contactList.stream().filter(contactData -> contactData.getName().equals(name)).findFirst()
				.orElse(null);
		return contact;
	}

	public boolean checkContactDataSync(String name) throws DatabaseException {
		List<Person> employeeList = addressBookDB.getContactFromDatabase(name);
		return employeeList.get(0).equals(getContact(name));
	}
	
	/**
	 * UC 18
	 * 
	 * returns list of contacts added between given dates
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws DatabaseException
	 */
	public List<Person> getContactsByDate(LocalDate start, LocalDate end) throws DatabaseException {
		List<Person> contactByDateList = addressBookDB.readDataForGivenDateRange(start, end);
		return contactByDateList;
	}
	
	/**
	 * UC 19
	 * 
	 * returns list of contacts belonging to given city
	 * 
	 * @param city
	 * @return
	 * @throws DatabaseException
	 */
	public List<Person> getContactsByCity(String city) throws DatabaseException {
		return addressBookDB.getContactsByCity(city);
	}

	/**
	 * returns list of contacts belonging to given state
	 * 
	 * @param state
	 * @return
	 * @throws DatabaseException
	 */
	public List<Person> getContactsByState(String state) throws DatabaseException {
		return addressBookDB.getContactsByState(state);
	}
	

}
