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
import java.util.HashMap;
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
		String sql = String.format("select addressbook_table.ID, addressbookType_table.addressbookName, "
				+ "addressbookType_table.addressBookType, addressbook_table.firstName, addressbook_table.lastName, \r\n"
				+ "addressbook_table.address, addressbook_table.city, addressbook_table.state, addressbook_table.zip, "
				+ "addressbook_table.phoneNum, addressbook_table.email, addressbook_table.dateAdded\r\n"
				+ "from addressbook_table\r\n"
				+ "inner join addressbookType_table on addressBookType_table.ID = addressbook_table.ID\r\n"
				+ "WHERE dateAdded between '%s' and '%s'", Date.valueOf(start), Date.valueOf(end));
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
	
	/**
	 * UC 20
	 * 
	 * Adding contact to the address book database and returning added records
	 * 
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param city
	 * @param state
	 * @param zip
	 * @param phone
	 * @param email
	 * @param addbookName
	 * @param dateAdded
	 * @return
	 * @throws DatabaseException
	 * @throws SQLException
	 */
	@SuppressWarnings("static-access")
	public List<Person> addContactToDatabase(String firstName, String lastName, String address, String city,
			String state, int zip, long phone, String email, List<String> types, LocalDate dateAdded)
			throws DatabaseException, SQLException {
		int contactId = -1;
		Connection connection = null;
		List<Person> addedContacts = new ArrayList<>();
		connection = this.getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException exception) {
			throw new DatabaseException(exception.getMessage());
		}

		try (Statement statement = (Statement) connection.createStatement()) { // adding to addressbook_table
			String sql = String.format(
					"insert into addressbook_table (firstName, lastName, address, city, state, zip, phoneNum, email, date_added) "
					+ "values ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')",
					firstName, lastName, address, city, state, zip, phone, email, Date.valueOf(dateAdded));
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					contactId = resultSet.getInt(1);
			}
		} catch (SQLException exception) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			}
			throw new DatabaseException("Unable to add to addressbook_table");
		}

		HashMap<String, String> typeAddbookNameMap = new HashMap<>();
		try (Statement tempStatement = (Statement) connection.createStatement()) { // getting addressbook_types
			String sqlGetType = String.format("select * from addressbookType_table");
			ResultSet resultSet = tempStatement.executeQuery(sqlGetType);
			while (resultSet.next()) {
				typeAddbookNameMap.put(resultSet.getString(" addressBookType"), resultSet.getString(" addressBookName"));
			}
		} catch (Exception e) {
			throw new DatabaseException(e.getMessage());
		}
		final int id = contactId;
		try (Statement statement = (Statement) connection.createStatement()) { // adding to addressbook_table
			types.forEach(type -> {
				String sql = String.format(
						"insert into addressbookType_table (id, addressbookName) values ('%s', '%s')", id,
						typeAddbookNameMap.get(type));
				try {
					statement.executeUpdate(sql);
				} catch (SQLException e) {
				}
			});
			types.forEach(type -> addedContacts.add(new Person(id, firstName, lastName, address, city, state,
					zip, phone, email, typeAddbookNameMap.get(type), type, dateAdded)));

		} catch (SQLException exception) {
			try {
				connection.rollback();
			} catch (SQLException e) {
				throw new DatabaseException(e.getMessage());
			}
			throw new DatabaseException("Unable to add to addressbookType_table");
		}

		try {
			connection.commit();
		} catch (SQLException e) {
			throw new DatabaseException(e.getMessage());
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
		return addedContacts;
	}
	
}

