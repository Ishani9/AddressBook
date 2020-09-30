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
		int check = 0;
		
		do {
		
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
			
			System.out.println("Enter 1 to add new details");
			check = sc.nextInt();
			
			Person person2 = new Person(firstName, lastName, address, city, state, zip, phoneNum, email);
			personList.add(person2);
			sc.nextLine();
		}
		while(check == 1);
			
	}
	
	//UC 3: EDIT DETAILS
	
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
	
	//UC 4: DELETE DETAILS
	
	public void deletePersonDetails() {
		Scanner sc = new Scanner(System.in);
		String FirstName;
		String LastName;
		
		System.out.println("Enter name of a person to DELETE contact details");
		System.out.println("First Name : ");
		FirstName = sc.nextLine();
		System.out.println("Last Name : ");
		LastName = sc.nextLine();
		
		for(int i = 0; i < personList.size(); i++) {
			if(FirstName.equalsIgnoreCase(personList.get(i).firstName) && LastName.equalsIgnoreCase(personList.get(i).lastName)) {
				personList.remove(personList.get(i));
			}
		}
	}
	
	
	public static void main(String[] args) {
		System.out.println("WELCOME TO ADDRESS BOOK");
		
		AddressBookMain book = new AddressBookMain();
		book.addPersonDetails();
		
		//Displaying all contacts from Address book
		book.displayAllContacts();
		
		//UC 3
		
		//editing details
		book.editPersonDetails();
		
		//Displaying all contacts from Address book after editing
		book.displayAllContacts();
		
		//UC 4
		
		//deleting details
		book.deletePersonDetails();
				
		//Displaying all contacts from Address book after deleting
		book.displayAllContacts();
		
	}
}