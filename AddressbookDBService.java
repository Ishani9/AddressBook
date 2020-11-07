package assignment;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddressbookDBService {
	
	private static AddressbookDBService addressBookDB;
	private static PreparedStatement contactPrepareStatement;

	public AddressbookDBService() {
	}

	public static AddressbookDBService getInstance() {
		if (addressBookDB == null) {
			addressBookDB = new AddressbookDBService();
		}
		return addressBookDB;
	}

	private Connection getConnection() throws DatabaseException {
		String jdbcURL =  "jdbc:mysql://localhost:3306/address_book?useSSL=false";
		String userName = "root";
		String password = "root";
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(jdbcURL, userName, password);
		} catch (Exception e) {
			throw new DatabaseException("Connection was unsuccessful");
		}
		return connection;

	}

	public List<Person> readData() throws DatabaseException {
		String sql = "select addressbook_table.ID, addressbookType_table.addressbookName, "
				+ "addressbookType_table.addressBookType, addressbook_table.firstName, addressbook_table.lastName, \r\n"
				+ "addressbook_table.address, addressbook_table.city, addressbook_table.state, addressbook_table.zip, "
				+ "addressbook_table.phoneNum, addressbook_table.email, addressbook_table.dateAdded\r\n"
				+ "from addressbook_table\r\n"
				+ "inner join addressbookType_table on addressBookType_table.ID = addressbook_table.ID\r\n";
				
		return this.getContactData(sql);
	}

	/**
	 * UC 16
	 * 
	 * @param sql
	 * @return
	 * @throws DatabaseException
	 */
	private List<Person> getContactData(String sql) throws DatabaseException {
		List<Person> contactList = new ArrayList<>();
		try (Connection connection = this.getConnection()) {
			Statement statement = (Statement) connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			contactList = this.getData(resultSet);
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		}
		return contactList;
	}

	/**
	 * when result set passed returns list of persons
	 * 
	 * @param resultSet
	 * @return
	 * @throws SQLException
	 */
	private List<Person> getData(ResultSet resultSet) throws SQLException {
		List<Person> contactList = new ArrayList<>();
		while (resultSet.next()) {
			int contactId = resultSet.getInt("ID");
			String fname = resultSet.getString("firstName");
			String lname = resultSet.getString("lastName");
			String address = resultSet.getString("address");
			int zip = resultSet.getInt("zip");
			String city = resultSet.getString("city");
			String state = resultSet.getString("state");
			long phoneNumber = resultSet.getLong("phoneNum");
			String email = resultSet.getString("email");
			String addbookName = resultSet.getString("addressBookName");
			String type = resultSet.getString("addressBookType");
			contactList.add(new Person(contactId, fname, lname, address, city, state, zip, phoneNumber, email,
					addbookName, type));
		}
		return contactList;
	}
	
	/**
	 * UC 17
	 * 
	 * updates person's data
	 * 
	 * @param name
	 * @param phone
	 * @return
	 * @throws DatabaseException
	 */
	@SuppressWarnings("static-access")
	public int updatePersonsData(String name, long phone) throws DatabaseException {
		String sql = "update addressbook_table set phoneNum = ? where firstName = ?";
		int result = 0;
		try {
			if (this.contactPrepareStatement == null) {
				Connection connection = this.getConnection();
				contactPrepareStatement = (PreparedStatement) connection.prepareStatement(sql);
			}
			contactPrepareStatement.setLong(1, phone);
			contactPrepareStatement.setString(2, name);
			result = contactPrepareStatement.executeUpdate();
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
		return result;
	}

	/**
	 * returns list of persons whose full name matches with input name
	 * 
	 * @param name
	 * @return
	 * @throws DatabaseException
	 */
	public List<Person> getContactFromDatabase(String name) throws DatabaseException {
		String[] fullName = name.split("[ ]");
		String sql = String.format("SELECT * FROM addressbook_table, addressbookType_table WHERE "
				+ "firstName = '%s' and lastName = '%s'",
				fullName[0], fullName[1]);		
		return this.getContactData(sql);
	}
	
	/**
	 * UC 18
	 * 
	 * returns list of contacts added between given dates
	 * 
	 * @param start
	 * @param end
	 * @return
	 * @throws DatabaseException
	 */
	public List<Person> readDataForGivenDateRange1(LocalDate start, LocalDate end) throws DatabaseException {
		List<Person> contactAddedBetweenDates = addressBookDB.readData().stream()
				.filter(person -> (person.getDateAdded().compareTo(start) >= 0 && 
				person.getDateAdded().compareTo(end) <= 0))
				.collect(Collectors.toList());
		return contactAddedBetweenDates;
	}
	
	public List<Person> readDataForGivenDateRange(LocalDate start, LocalDate end) throws DatabaseException {
		String sql = String.format("SELECT * FROM addressbook_table, addressbookType_table WHERE "
				+ "dateAdded between '%s' and '%s'", Date.valueOf(start), Date.valueOf(end));
		return this.getContactData(sql).stream().distinct().collect(Collectors.toList());
	}
	
	/**
	 * returns list of contacts belonging to given city
	 * 
	 * @param city
	 * @return
	 * @throws DatabaseException
	 */
	public List<Person> getContactsByCity(String city) throws DatabaseException {
		String sql = String.format("select addressbook_table.ID, addressbookType_table.addressbookName, "
				+ "addressbookType_table.addressBookType, addressbook_table.firstName, addressbook_table.lastName, \r\n"
				+ "addressbook_table.address, addressbook_table.city, addressbook_table.state, addressbook_table.zip, "
				+ "addressbook_table.phoneNum, addressbook_table.email, addressbook_table.dateAdded\r\n"
				+ "from addressbook_table\r\n"
				+ "inner join addressbookType_table on addressBookType_table.ID = addressbook_table.ID\r\n"
				+ "WHERE city = '%s'", city);
		return getContactData(sql).stream().distinct().collect(Collectors.toList());
	}
	
	/**
	 * returns list of contacts belonging to given state
	 * 
	 * @param state
	 * @return
	 * @throws DatabaseException
	 */
	public List<Person> getContactsByState(String state) throws DatabaseException {
		String sql = String.format("select addressbook_table.ID, addressbookType_table.addressbookName, "
				+ "addressbookType_table.addressBookType, addressbook_table.firstName, addressbook_table.lastName, \r\n"
				+ "addressbook_table.address, addressbook_table.city, addressbook_table.state, addressbook_table.zip, "
				+ "addressbook_table.phoneNum, addressbook_table.email, addressbook_table.dateAdded\r\n"
				+ "from addressbook_table\r\n"
				+ "inner join addressbookType_table on addressBookType_table.ID = addressbook_table.ID\r\n"
				+ "WHERE state = '%s'", state);
		return getContactData(sql).stream().distinct().collect(Collectors.toList());
	}
}
