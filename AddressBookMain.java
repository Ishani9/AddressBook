package Hello;

import java.util.*;
public class AddressBookMain{
	
public static void main(String[] args) {
		
		System.out.println("WELCOME TO ADDRESS BOOK");
		
		Scanner scanner = new Scanner(System.in);
		HashMap<String, AddressBook> addressBookMap = new HashMap<>();
		
		System.out.println("Enter first Address Book name : ");
		String addressBookName = scanner.nextLine();
		AddressBook NewAddressBook = new AddressBook(addressBookName);
		addressBookMap.put(addressBookName, NewAddressBook);
		NewAddressBook.addPersonDetails();
		int choice;
		
		do {
			AddressBookMain addBookMain = new AddressBookMain();
			
			System.out.println("1.to add contact to book");
			System.out.println("2.to edit contact");
			System.out.println("3.to delete contact");
			System.out.println("4. Add new Address Book");
			System.out.println("5. Display Address Book details");
			System.out.println("any other interger to exit");
			choice = scanner.nextInt();
			scanner.nextLine();
			switch(choice) {
			case 1:
				addressBookMap.get(addressBookName).addPersonDetails();
				break;
			case 2:
				addressBookMap.get(addressBookName).editPersonDetails();
				break;
			case 3:
				addressBookMap.get(addressBookName).deletePersonDetails();
				break;
			case 4:
				System.out.println("Enter Address Book name : ");
				addressBookName = scanner.nextLine();
				NewAddressBook = new AddressBook(addressBookName);
				addressBookMap.put(addressBookName, NewAddressBook);
				break;
				
			case 5:
				addressBookMap.get(addressBookName).displayAllContacts();
				break;
			}
		}
		while(choice == 1 || choice == 2 || choice == 3 || choice == 4 || choice == 5 );
	}
}