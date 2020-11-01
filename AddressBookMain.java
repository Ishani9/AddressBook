package assignment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import java.util.*;
import java.util.Scanner;

import java.util.stream.Collectors;

public class AddressBookMain extends AddressBook {
		
	public static HashMap<String, AddressBook> StateAddressBookMap = new HashMap<>();
	public static HashMap<Object, Object> CityaddressBookMap = new HashMap<>();
	
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
			long  phoneNum;
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
			email = scanner.nextLine();
			
			Person new_person = new Person(firstName, lastName, address, city, state, zip, phoneNum, email);
					
			boolean duplicate = StateAddressBookMap.get(state).personList.stream().anyMatch(n -> n.equals(new_person));
			if(!duplicate) {
				StateAddressBookMap.get(state).personList.add(new_person);
			}
			else {
				System.out.println("Same contact is already present Hence WILL NOT BE ADDED to address book");
			}
			
			System.out.println("Enter 'y' to ADD NEW PERSON'S DETAILS.\nEnter any other key to STOP ADDING.");
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
		//	System.out.println("9. Display Address Book details");
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
			default:
				System.out.println("Select correct choice");
				break;
			}
			System.out.println("Enter 'y' if you want to PERFORM NEW ACTION \nEnter any other key to EXIT");
			name1 = scanner.nextLine();
			yes = scanner.nextLine();
		}while(yes.equalsIgnoreCase("y"));
		
	}
	
}