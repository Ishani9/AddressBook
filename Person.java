package Hello;

public class Person {
	public String firstName;
	public String lastName;
	public String address;
	public String city;
	public String state;
	int zip;
	public long  phoneNum;
	public String email;
	
	public Person(String firstName, String lastName, String address, String city, String state, int zip, long phoneNum, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNum = phoneNum;
		this.email = email;
	}
	
	public String toString() {
		String details = "First Name : " + firstName + "\nLast Name : " + lastName + "\nAddress : " + address + "\nCity : " + city 
							+ "\nState : " + state + "\nZIP : " + zip + "\nPhone Number : " + phoneNum + "\nEmail ID : " + email + "\n";
		return details;
	}

	
}
