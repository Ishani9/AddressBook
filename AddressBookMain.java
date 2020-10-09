package Hello;

import java.util.*;
public class AddressBookMain{
	
	public static Map<String,AddressBook> addressBookMap;
	
	public AddressBookMain() {
		addressBookMap = new HashMap<>();
	}
	
	public void addAddressBook(String city) {
		AddressBook addBook = new AddressBook(city);
		addressBookMap.put(city,addBook);
	} 
	
	public static List<Person> Book = new ArrayList<Person>();	
	
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		
		
		//call to previous UCs
		
		int choice;
		do {
			AddressBookMain addBookMain = new AddressBookMain();
			System.out.println("1.to create addbook");
			System.out.println("2.to add contact to book");
			System.out.println("3.to edit contact");
			System.out.println("4.to delete contact");
			System.out.println("5.to view addbook");
			System.out.println("any other interger to exit");
			choice = scanner.nextInt();
			scanner.nextLine();
			switch(choice) {
				case 1:
					System.out.println("Enter the city name to create addressBook");
					String City = scanner.nextLine();
					addBookMain.addAddressBook(City);
					break;
				case 2:  
					System.out.println("Enter the details of person");
					System.out.println("Enter the first name");
					String firstName = scanner.nextLine();
					System.out.println("Enter the last name");
					String lastName = scanner.nextLine();
					System.out.println("Enter the address");
					String address = scanner.nextLine();
					System.out.println("Enter the city name");
					String city = scanner.nextLine();
					System.out.println("Enter the state name");
					String state = scanner.next();
					System.out.println("Enter the ZIP code");
					int zip = scanner.nextInt();
					System.out.println("Enter the phone number");
					long phoneNumber = scanner.nextLong();
					scanner.nextLine();
					System.out.println("Enter the email");
					String email = scanner.nextLine();
					Person person = new Person(firstName, lastName, address, city, state, zip, phoneNumber, email);
					
					for (Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {   
						if(entry.getKey().equalsIgnoreCase(city)) {
							entry.getValue().addContact(person);
						}
						else {
							System.out.println("The addressbook does not exist, please create addressbook for that city");
						}
					}
					break;
				case 3:
					System.out.println("Enter the name to edit contact");
					String name3 = scanner.nextLine();
					System.out.println("Enter the last name to edit contact");
					String lastname3 = scanner.nextLine();
					System.out.println("Enter the city");
					String city3 = scanner.nextLine();
					for (Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {   
						if(entry.getKey().equalsIgnoreCase(city3)) {
							entry.getValue().editContact(name3 + lastname3);
						}
						else {
							System.out.println("The addressbook does not exist, please create addressbook for that city");
						}
					}
					break;
				case 4:
					System.out.println("Enter the name to delete");
					String name4 = scanner.nextLine();
					System.out.println("Enter the city");
					String city4 = scanner.nextLine();
					for (Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {   
						if(entry.getKey().equalsIgnoreCase(city4)) {
							entry.getValue().deleteContact(name4);
						}
						else {
							System.out.println("The addressbook does not exist, please create addressbook for that city");
						}
					}
					break;
				case 5:
					for (Map.Entry<String,AddressBook> entry : addressBookMap.entrySet()) {
						System.out.println("The addressbook for city "+entry.getKey()+" is :");
						entry.getValue().viewList();
						
					}
					
					
					break;
			}
		}
		while(choice == 1 || choice == 2 || choice == 3 || choice == 4 || choice == 5);
	}
}