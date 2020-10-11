package Hello;

import java.util.ArrayList;
import java.util.List;

public class AddressBook {
	private String AddressBookName;
	public List <Person> personList;
	
	public List<Person> getPersonList(){
		return personList;
	}
	
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
	

	public void displayAllContacts() {
		System.out.println("CONTACT DETAILS FROM ADDRESS BOOK");
		for(int i = 0; i < personList.size(); i++) {
			System.out.println(personList.get(i));
		}
	}


}
