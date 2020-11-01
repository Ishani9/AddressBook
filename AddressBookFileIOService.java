package assignment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
