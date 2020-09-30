package Hello;

import java.util.*;

public class AddressBookMain {
	public List <Person> personList;
	public AddressBookMain() {
		personList = new ArrayList<Person>();
	}
	
	public void displayAllContacts() {
		System.out.println("CONTACT DETAILS FROM ADDRESS BOOK");
		for(int i = 0; i < personList.size(); i++) {
			System.out.println(personList.get(i));
		}
	}
	
	
	public void addPersonDetails() {
		Scanner sc = new Scanner(System.in);
		
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
			firstName = sc.nextLine();
			System.out.println("Last Name : ");
			lastName= sc.nextLine();
			System.out.println("Address : ");
			address = sc.nextLine();
			System.out.println("City : ");
			city = sc.nextLine();
			System.out.println("State : ");
			state = sc.nextLine();
			System.out.println("ZIP : ");
			zip = sc.nextInt();
			System.out.println("Phone number : ");
			phoneNum = sc.nextLong();
			sc.nextLine();
			System.out.println("Email ID : ");
			email = sc.nextLine();
			
			Person person2 = new Person(firstName, lastName, address, city, state, zip, phoneNum, email);
			personList.add(person2);
			
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
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter name of a person to edit contact details");
		System.out.println("First Name : ");
		FirstName = sc.nextLine();
		System.out.println("Last Name : ");
		LastName = sc.nextLine();
		
		for(int i = 0; i < personList.size(); i++) {
			if(FirstName.equalsIgnoreCase(personList.get(i).firstName) && LastName.equalsIgnoreCase(personList.get(i).lastName)) {
				System.out.println("Address : ");
				address = sc.nextLine();
				System.out.println("City : ");
				city = sc.nextLine();
				System.out.println("State : ");
				state = sc.nextLine();
				System.out.println("ZIP : ");
				zip = sc.nextInt();
				System.out.println("Phone number : ");
				phoneNum = sc.nextLong();
				sc.nextLine();
				System.out.println("Email ID : ");
				email = sc.nextLine();
				
				Person person3 = new Person(FirstName, LastName, address, city, state, zip, phoneNum, email);
				personList.set(i, person3);
			}
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("WELCOME TO ADDRESS BOOK");
		
		AddressBookMain address_book_main = new AddressBookMain();
		address_book_main.addPersonDetails();
		
		//Displaying all contacts from Address book
		address_book_main.displayAllContacts();
		
		//editing details
		address_book_main.editPersonDetails();
		
		//Displaying all contacts from Address book after editing
		address_book_main.displayAllContacts();
		
	}
}