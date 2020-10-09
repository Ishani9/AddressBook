package Hello;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AddressBook {
	private String AddressBookName;
	public List <Person> personList;
	
	public AddressBook(String AddressBookName) {
		personList = new ArrayList<Person>();
		this.AddressBookName = AddressBookName;
	}
	
	public AddressBook() {
		personList = new ArrayList<Person>();
	}
	
	public String getName() {
		return AddressBookName;
	}
	
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


}
