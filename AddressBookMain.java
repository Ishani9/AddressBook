package Hello;

import java.util.*;
import java.util.stream.Collectors;

public class AddressBookMain extends AddressBook{
	
public static void main(String[] args) {
		
		System.out.println("WELCOME TO ADDRESS BOOK");
		
		Scanner scanner = new Scanner(System.in);
		HashMap<String, AddressBook> addressBookMap = new HashMap<>();
		
		System.out.println("Enter first Address Book name : ");
		String addressBookName = scanner.nextLine();
		AddressBook NewAddressBook = new AddressBook(addressBookName);
		addressBookMap.put(addressBookName, NewAddressBook);
		AddressBookMain addBookMain = new AddressBookMain();
		addBookMain.addPersonDetails();
		int choice;
		
		do {
			//AddressBookMain addBookMain2 = new AddressBookMain();
			
			System.out.println("1.to add contact to book");
			System.out.println("2.to edit contact");
			System.out.println("3.to delete contact");
			System.out.println("4. Add new Address Book");
			System.out.println("5. Display Address Book details");
			System.out.println("6. Search person in city");
			System.out.println("7. Search person in state");
			System.out.println("Enter any other interger to exit");
			choice = scanner.nextInt();
			scanner.nextLine();
			switch(choice) {
			case 1:
				addBookMain.addPersonDetails();
				break;
			case 2:
				addBookMain.editPersonDetails();
				break;
			case 3:
				addBookMain.deletePersonDetails();
				break;
			case 4:
				System.out.println("Enter Address Book name : ");
				addressBookName = scanner.nextLine();
				NewAddressBook = new AddressBook(addressBookName);
				addressBookMap.put(addressBookName, NewAddressBook);
				break;
				
			case 5:
				addBookMain.displayAllContacts();
				break;
				
			case 6:
				System.out.println("Enter the name to search : ");
				String person = scanner.nextLine();
				System.out.println("Enter the city : ");
				String city = scanner.nextLine();
				addBookMain.searchPersonByCity(person , city);
				break;
			case 7:
				System.out.println("Enter the name to search : ");
				person = scanner.nextLine();
				System.out.println("Enter the state : ");
				String state = scanner.nextLine();
				addBookMain.searchPersonByState(person , state);
				break;
			}
		}
		while(choice > 0 && choice < 8  );
	}

	/**
	 * UC 2 
	 * @returns
	 */

	public void addPersonDetails() {
		Scanner scanner = new Scanner(System.in);
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
			boolean duplicate = personList.stream().anyMatch(n -> n.equals(new_person));
			if(!duplicate) {
				personList.add(new_person);
			}
			else 
				System.out.println("Contact is present. CANNOT add again. \n\n");				
	}
	
	/**
	 * UC 3
	 * @returns
	 */
	
	public void editPersonDetails() {
		String FirstName;
		String LastName;
		String address;
		String city;
		String state;
		int zip;
		long  phoneNum;
		String email;
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Enter name of a person to edit contact details");
		System.out.println("First Name : ");
		FirstName = scanner.nextLine();
		System.out.println("Last Name : ");
		LastName = scanner.nextLine();
		
		for(Person thatPerson : personList) {
			if(FirstName.equalsIgnoreCase(thatPerson.getFirstName()) && LastName.equalsIgnoreCase(thatPerson.getLastName())) {
				System.out.println("New Address : ");
				address = scanner.nextLine();
				thatPerson.setAddress(address);
				System.out.println("New City : ");
				city = scanner.nextLine();
				thatPerson.setCity(city);
				System.out.println("New State : ");
				state = scanner.nextLine();
				thatPerson.setState(state);
				System.out.println("New ZIP : ");
				zip = scanner.nextInt();
				thatPerson.setZip(zip);
				System.out.println("New Phone number : ");
				phoneNum = scanner.nextLong();
				thatPerson.setPhoneNumber(phoneNum);
				scanner.nextLine();
				System.out.println("New Email ID : ");
				email = scanner.nextLine();
				thatPerson.setEmail(email);
			}
		}
	}
	
	/**
	 * UC 3 
	 * @returns
	 */
	
	public void deletePersonDetails() {
		Scanner scanner = new Scanner(System.in);
		String FirstName;
		String LastName;
		
		System.out.println("Enter name of a person to DELETE contact details");
		System.out.println("First Name : ");
		FirstName = scanner.nextLine();
		System.out.println("Last Name : ");
		LastName = scanner.nextLine();
		
		for(Person thatPerson : personList) {
			if(FirstName.equalsIgnoreCase(thatPerson.getFirstName()) && LastName.equalsIgnoreCase(thatPerson.getLastName())) {
				personList.remove(thatPerson);
			}
		}
	}
	
	public void displayAllContacts() {
		System.out.println("CONTACT DETAILS FROM ADDRESS BOOK");
		for(int i = 0; i < personList.size(); i++) {
			System.out.println(personList.get(i));
		}
	}

	/**
	 * UC 8
	 * 
	 * @param name
	 * @param city
	 */
	 
	public void searchPersonByCity(String name, String city) {
		List<Person> list = new ArrayList<Person>();
		HashMap<String, AddressBook> addressBookMap = new HashMap<>();
		
		for(Map.Entry<String, AddressBook> entry : addressBookMap.entrySet()) {
			list = entry.getValue().getPersonList().stream()
					.filter(c -> ((c.getCity()).equals(city)))
					.filter(c -> ((c.getFirstName()).equals(name))).collect(Collectors.toList());
		}
		if(list.isEmpty()) {
			System.out.println("Person not found");
		}
		else {
			System.out.println("FOUND");
			for(Person c : list) {
				System.out.println(c);
			}
		}
	}	
	
	public void searchPersonByState(String name, String state) {
		List<Person> list = new ArrayList<Person>();
		HashMap<String, AddressBook> addressBookMap = new HashMap<>();
		for(Map.Entry<String, AddressBook> entry : addressBookMap.entrySet()) {
			list = entry.getValue().getPersonList().stream()
					.filter(c -> c.getState().equals(state))
					.filter(c -> c.getFirstName().equals(name) ).collect(Collectors.toList());
		}
		if(list.isEmpty()) {
			System.out.println("Person not found");
		}
		else {
			System.out.println("FOUND");
			for(Person c : list) {
				System.out.println(c);
			}
		}
	}	
	

}