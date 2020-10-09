package Hello;

import java.util.Scanner;
import java.util.*;


public class AddressBook {
	Scanner sc = new Scanner(System.in);
	public List<Person> Book = new ArrayList<Person>();	
	public String city;
	
	public AddressBook(String city) {
		this.city = city;
	}
	public List<Person> getBook(){
		return Book;
	}
	public void addContact(Person person) {
		for(int i = 0; i<Book.size(); i++) {	
			if(Book.get(i).equals(person)) {
				System.out.println("The person already exists!!!");
				return;
			}
		}
		Book.add(person);
	}
	
	public void editContact(String name){
		//String fullName = "";
		int i = 0;
		for(Person person : Book) {
			//fullName = person.getFirstName() +" "+ person.getLastName();
			if(name.equals(person.firstName)) {
				System.out.println("Enter new  Address ");
				String address = sc.nextLine();
				person.setAddress(address);
				System.out.println("Enter new  ZIP : ");
				int zip = sc.nextInt();
				person.setZip(zip);
				System.out.println("Enter new phone number");
			 	long phone = sc.nextLong();
				//sc.nextLine();
				person.setPhoneNumber(phone);
				System.out.println("Enter new Email id");
				String email = sc.nextLine();
	 			person.setEmail(email);
				 			
				}
					
			else {
				System.out.println("Enter correct name");
			}
			Book.set(i,person);
			i++;
		}
	}
	public void deleteContact(String name){
		String fullname = "";
		for(Person person : Book) {
			fullname = person.getFirstName() + " " + person.getLastName();
			if(name.equals(fullname)) {
				Book.remove(person);
			}
		}
	}
	
	public void viewList() {
		for(Person c : Book) {
			System.out.println("First Name : " + c.getFirstName() + "Last Name : " + c.getLastName() + " Address : " + c.getAddress() + " City : " + c.getCity() 
							+ " State : " + c.getState() + " ZIP : " + c.getZip() + " Phone Number : " + c.getPhoneNumber() + " Email ID : " + c.getEmail() + "\n");
		}

	}

}