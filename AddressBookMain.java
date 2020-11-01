package assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Scanner;

public class AddressBookMain extends AddressBook {
		
	public static HashMap<String, AddressBook> StateAddressBookMap = new HashMap<>();
	
	public enum IOServices {
		CONSOLE_IO, 
		FILE_IO, 
		DB_IO, 
		REST_IO;
	}
	
	@SuppressWarnings("resource")
	public void addPersonDetails() {
		Scanner scanner = new Scanner(System.in);
		
		String checkToAdd = "y";
		
		while(checkToAdd.equalsIgnoreCase("y")) {
			//variables
			String firstName;
			String lastName;
			String address;
			String city;
			String state;
			int zip;
			long phoneNum;
			String email;
			
			System.out.println("Enter following details : ");
			System.out.println("First Name : ");
			firstName = scanner.nextLine();
			System.out.println("Last Name : ");
			lastName = scanner.nextLine();
			System.out.println("Address : ");
			address = scanner.nextLine();
			System.out.println("City : ");
			city = scanner.nextLine();
			System.out.println("State : ");
			state = scanner.nextLine();
			System.out.println("ZIP : ");
			zip = scanner.nextInt();
			System.out.println("Phone number : ");
			phoneNum = scanner.nextLong();
			scanner.nextLine();
			System.out.println("Email ID : ");
			String email1 = scanner.nextLine();
			email = scanner.nextLine();
			
			Person new_person = new Person(firstName, lastName, address, city, state, zip, phoneNum, email);
			boolean duplicate = false;
			duplicate = StateAddressBookMap.get(state).personList.stream().anyMatch(n -> n.equals(new_person));
			if(!duplicate) {
				StateAddressBookMap.get(state).personList.add(new_person);
			}
			else {
				System.out.println("Same contact is already present Hence WILL NOT BE ADDED to address book");
			}
			
			System.out.println("Enter 'y' to ADD NEW PERSON'S DETAILS.\nEnter any other key to STOP ADDING.");
			checkToAdd = "y";
			String checkToAdd1 = scanner.nextLine();
			checkToAdd = scanner.nextLine();
		}
		
	}
	
	public void searchPersonByCity(String name, String city) {
		List<Person> list = new ArrayList<Person>();
		for(Map.Entry<String, AddressBook> entry : StateAddressBookMap.entrySet()) {
			list = entry.getValue().getPersonList().stream()
					.filter(c -> c.getCity().equals(city))
					.filter(c -> (c.getFirstName() + " " + c.getLastName())
					.equals(name)).collect(Collectors.toList());
		}
		for(Person c : list) {
			System.out.println(c);
		}
	}	
	
	public void searchPersonByState(String name, String state) {
		List<Person> list = new ArrayList<Person>();
		for(Map.Entry<String, AddressBook> entry : StateAddressBookMap.entrySet()) {
			list = entry.getValue().getPersonList().stream()
					.filter(c -> c.getState().equals(state))
					.filter(c -> (c.getFirstName() + " " + c.getLastName())
					.equals(name)).collect(Collectors.toList());
		}
		for(Person c : list) {
			System.out.println(c);
		}
	}

	public void viewPersonsByCity(String city) {
		List<Person> list = new ArrayList<Person>();
		for(Map.Entry<String, AddressBook> entry : StateAddressBookMap.entrySet()) {
			list = entry.getValue().getPersonList().stream().filter(c -> c.getState().equals(city))
					.collect(Collectors.toList());
		}
		for(Person c : list) {
			System.out.println(c);
		}
	}
	
	public void viewPersonsByState(String state) {
		List<Person> list = new ArrayList<Person>();
		for(Map.Entry<String, AddressBook> entry : StateAddressBookMap.entrySet()) {
			list = entry.getValue().getPersonList().stream().filter(s -> s.getState().equals(state))
					.collect(Collectors.toList());
		}
		for(Person c : list) {
			System.out.println(c);
		}
	}
	
	/**
	 * UC 10
	 * @param city
	 */
	public void countByCity(String city) {
		long count = 0;
		for(Map.Entry<String,AddressBook> entry : StateAddressBookMap.entrySet()) {
			count = entry.getValue().getPersonList().stream()
					.filter(c -> c.getCity().equals(city))
					.count();
		}
		System.out.println("Number of contacts in '" + city + "' : " + count);
	}
	
	public void countByState(String state) {
		long count = 0;
		for(Map.Entry<String,AddressBook> entry : StateAddressBookMap.entrySet()) {
			count = entry.getValue().getPersonList().stream()
					.filter(c -> c.getCity().equals(state))
					.count();
		}
		System.out.println("Number of contacts in '" + state + "' : " + count);
	}
	
	/**
	 * UC 11
	 */
	public void sortByName() {
		List<Person> personList = new ArrayList<>();
		for (Map.Entry<String, AddressBook> entry : StateAddressBookMap.entrySet()) {
			personList = entry.getValue().getPersonList().stream()
					.sorted((p1, p2) -> p1.getName().compareTo(p2.getName())).collect(Collectors.toList());
		}

		System.out.println("Sorted list : ");
		for (Person list : personList) {
			System.out.println(list.getName());
		}
	}
	
	/**
	 * UC 12
	 * @param args
	 */
	public void sortByZip() {
		List<Person> personList = new ArrayList<>();
		for (Map.Entry<String, AddressBook> entry : StateAddressBookMap.entrySet()) {
			personList = entry.getValue().getPersonList().stream()
					.sorted((p1, p2) -> Integer.compare(p1.getZip(), p2.getZip())).collect(Collectors.toList());
		}

		System.out.println("Sorted list of ZIPs : ");
		for (Person list : personList) {
			System.out.println(list.getZip());
		}
	}
	
	/**
	 * UC 13
	 * @param ioService
	 */
	public void readData(IOServices ioService) {
		if (ioService.equals(IOServices.FILE_IO))
			System.out.println("reading data from file");
			new AddressBookFileIOService().readData();
	}

	public void writeData(IOServices ioService) {
		if (ioService.equals(IOServices.FILE_IO)) {
			new AddressBookFileIOService().writeData(StateAddressBookMap);
		}
	}
	
	/**
	 * UC 14
	 * 
	 * @param ioService
	 */
	public void readDataCSV(IOServices ioService) {
		if (ioService.equals(IOServices.FILE_IO)) {
			new AddressBookFileIOService().readDataCSV();
		}
	}

	public void writeDataCSV(IOServices ioService) {
		if (ioService.equals(IOServices.FILE_IO)) {
			new AddressBookFileIOService().writeDataCSV(StateAddressBookMap);
		}

	}
	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		
		System.out.println("WELCOME TO ADDRESS BOOK");
		
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter first Address Book name : ");
		String addressBookName = scanner.nextLine();
		AddressBookMain addressBookMain = new AddressBookMain();
		AddressBook NewAddressBook = new AddressBook(addressBookName);
		StateAddressBookMap.put(addressBookName, NewAddressBook);
		addressBookMain.addPersonDetails();
		String name;
		String name1;
		
		String yes = "y";
		
		do {
			System.out.println("1.to add contact to book");
			System.out.println("2.to edit contact");
			System.out.println("3.to delete contact");
			System.out.println("4. Add new Address Book");
			System.out.println("5. Search person in city");
			System.out.println("6. Search person in state");
			System.out.println("7. View person in city");
			System.out.println("8. View person in state");
			System.out.println("9. Count by city");
			System.out.println("10. Count by state");
			System.out.println("11. Sort by name");
			System.out.println("12. Sort by zip code");
			System.out.println("13. Write to file");
			System.out.println("14. Read from console");
			System.out.println("15. Write to CSV file");
			System.out.println("16. Read from CSV file");
			
			int option = scanner.nextInt();
			switch(option) {
			case 1:
				addressBookMain.addPersonDetails();
				break;
			case 2:
				System.out.println("Enter name of address book");
				name1 = scanner.nextLine();
				name = scanner.nextLine();
				StateAddressBookMap.get(name).editPersonDetails();
				break;
			case 3:
				System.out.println("Enter name of address book");
				name1 = scanner.nextLine();
				name = scanner.nextLine();
				StateAddressBookMap.get(name).deletePersonDetails();
				break;
			case 4:
				System.out.println("Enter Address Book name : ");
				name1 = scanner.nextLine();
				name = scanner.nextLine();
				NewAddressBook = new AddressBook(name);
				StateAddressBookMap.put(name, NewAddressBook);
				break;
			case 5:
				System.out.println("Enter the name to search : ");
				String person = scanner.nextLine();
				System.out.println("Enter the city : ");
				String city = scanner.nextLine();
				addressBookMain.searchPersonByCity(person, city);
				break;
			case 6:
				System.out.println("Enter the name to search : ");
				person = scanner.nextLine();
				System.out.println("Enter the state : ");
				String state = scanner.nextLine();
				addressBookMain.searchPersonByCity(person, state);
				break;
			case 7:
				System.out.println("Enter the city : ");
				name1 = scanner.nextLine();
				city = scanner.nextLine();
				addressBookMain.viewPersonsByCity(city);
				break;
			case 8:
				System.out.println("Enter the state : ");
				name1 = scanner.nextLine();
				state = scanner.nextLine();
				addressBookMain.viewPersonsByState(state);
				break;
			case 9:
				System.out.println("Enter the city : ");
				name1 = scanner.nextLine();
				city = scanner.nextLine();
				addressBookMain.countByCity(city);
				break;
			case 10:
				System.out.println("Enter the state : ");
				name1 = scanner.nextLine();
				state = scanner.nextLine();
				addressBookMain.countByState(state);
				break;
			case 11:
				addressBookMain.sortByName();
				break;
			case 12:
				addressBookMain.sortByZip();
			case 13:
				addressBookMain.writeData(IOServices.FILE_IO);
				break;
			case 14:
				addressBookMain.readData(IOServices.FILE_IO);
				break;
			case 15:
				addressBookMain.writeDataCSV(IOServices.FILE_IO);
				break;
			case 16:
				addressBookMain.readDataCSV(IOServices.FILE_IO);
				break;
		
			default:
				System.out.println("Select correct choice");
				break;
			}
			System.out.println("Enter 'y' if you want to PERFORM NEW ACTION \nEnter any other key to EXIT");
			String yes1 = scanner.nextLine();
			yes = scanner.nextLine();
		}
		while(yes.equalsIgnoreCase("y"));
	scanner.close();	
	}
}