import java.util.Scanner;

public class AddressBookMain {
	public static void main(String[] args) {
		//variables
		String firstName;
		String lastName;
		String address;
		String city;
		String state;
		int zip;
		long  phoneNum;
		String email;
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Welcome to Address Book");
		System.out.println("Enter following details : ");
		
		System.out.println("First Name : ");
		firstName = sc.nextLine();
		System.out.println("Last Name : ");
		lastName = sc.nextLine();
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
		
		Person person1 = new Person(firstName, lastName, address, city, state, zip, phoneNum, email);		
	}
}

