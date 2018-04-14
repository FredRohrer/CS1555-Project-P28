import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.*;


public class BoutiqueCoffee {
	
  private Connection connection;
  private Statement statement;
  private ResultSet resultSet;
  
  //Put your username and password for your particular database here
  private String username = "";
  private String password = "";
  
  
  //Init, I think this is how it should work, but idk.
  //pretty sure its fine this way
  public BoutiqueCoffee() {
	try {
		//Register oracle driver, whatever that means
		DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
		
		//location of database, this is the line in the demo code but it seems wrong
		String url = "jdbc:oracle:thin:@class3.cs.pitt.edu:1521:dbclass";
		
		//create the actual connection
		connection = DriverManager.getConnection(url, username, password);
	}
	catch (Exception Ex) {
	  System.out.println("Error connecting to database.  Machine Error: " + Ex.toString());
      Ex.printStackTrace();
	}
	
	//problem: I don't know when to close the connection
  }
  
  
  public int addStore(String name, String address, String storeType, long gpsLong, long gpsLat) {
	//statement.setAutoCommit(false);
	//not sure what the isolation should be for this one
	//probably not that serious
	//connection.setTransactionIsolation(?????);
	int out = -1;
	try {
		//statement = connection.createStatement();
		String query = "select Store_seq.NEXTVAL from dual";
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery(query);
		
		if (rs.next()) {
			int storeSeq = rs.getInt(1);
	
			query = "insert into STORE values(?, ?, ?, ?, ?, ?)";
			//not sure how to deal with the store id
			PreparedStatement insertStatement = connection.prepareStatement(query);
			insertStatement.setInt(1, storeSeq);
			insertStatement.setString(2, name);
			insertStatement.setString(3, address);
			insertStatement.setString(4, storeType);
			insertStatement.setLong(5, gpsLong);
			insertStatement.setLong(6, gpsLat);
			if (insertStatement.executeUpdate() > 0) {
				out = storeSeq;
			}
			stat.close();
			insertStatement.close();
		}
		
	}
	catch (Exception Ex) {
	  System.out.println("Error adding Store: " + Ex.toString());
      //Ex.printStackTrace();
	}

	
    return out;
  }

  public int addCoffee(String name, String description, int intensity, double price, double rewardPoints, double redeemPoints) {
    int out = -1;
	try {
		String query = "select Coffee_seq.NEXTVAL from dual";
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery(query);
		
		if (rs.next()) {
			int cofSeq = rs.getInt(1);
			
			query = "insert into Coffee values (?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(query);
			insertStatement.setInt(1, cofSeq);
			insertStatement.setString(2, name);
			insertStatement.setString(3, description);
			insertStatement.setInt(4, intensity);
			insertStatement.setDouble(5, price);
			insertStatement.setDouble(6, rewardPoints);
			insertStatement.setDouble(7, redeemPoints);
			if (insertStatement.executeUpdate() > 0) {
				out = cofSeq;
			}
			stat.close();
			insertStatement.close();
		}
	}
	catch (Exception Ex) {
	  System.out.println("Error adding Coffee: " + Ex.toString());
      Ex.printStackTrace();
	}

	
	return out;
  }

  public int offerCoffee(int storeId, int coffeeId) throws SQLException {
    String query = "insert into OfferCoffee values(?. ?);";

    PreparedStatement insertStatement = connection.prepareStatement(query);
    insertStatement.setInt(1, storeId);
    insertStatement.setInt(2, coffeeId);
    if(insertStatement.executeUpdate() > 0)
        return 1;
    else
        return -1;
  }

  public int addPromotion(String name, Date startDate, Date endDate) throws SQLException {
      String query = "SELECT Promotion_seq.NEXTVAL from dual";
      PreparedStatement insertStatement = connection.prepareStatement(query);
      ResultSet rs = insertStatement.executeQuery();

      if(rs.next()){
          int promoseq = rs.getInt(1);
          query = "insert into Promotion values(?, ?, ?, ?);";

          insertStatement = connection.prepareStatement(query);
          insertStatement.setInt(1, promoseq);
          insertStatement.setString(2, name);
          insertStatement.setDate(3, (java.sql.Date) startDate);
          insertStatement.setDate(4, (java.sql.Date) endDate);

          if(insertStatement.executeUpdate() > 0)
              return promoseq;
          else
              return -1;
      }
      else
          return -1;
  }

  public int promoteFor(int promotionId, int coffeeId) throws SQLException {
      String query = "insert into PromoteFor values(?, ?);";
      PreparedStatement insertStatement = connection.prepareStatement(query);
      insertStatement.setInt(1, promotionId);
      insertStatement.setInt(2, coffeeId);
      if(insertStatement.executeUpdate() > 0)
          return 1;
      else
          return -1;
  }

  public int hasPromotion(int storeId, int promotionId) throws SQLException {
      String query = "insert into HasPromotion values(?, ?);";
      PreparedStatement insertStatement = connection.prepareStatement(query);
      insertStatement.setInt(1, storeId);
      insertStatement.setInt(2, promotionId);
      if(insertStatement.executeUpdate() > 0)
          return 1;
      else
          return -1;
  }

  public int addMemberLevel(String name, double boosterFactor) throws SQLException {
      String query = "SELECT MemberLevel_seq.NEXTVAL from dual";
      PreparedStatement insertStatement = connection.prepareStatement(query);
      ResultSet rs = insertStatement.executeQuery();

      if(rs.next()){
          int memberseq = rs.getInt(1);
          query = "insert into MemberLevel values(?, ?, ?);";

          insertStatement = connection.prepareStatement(query);
          insertStatement.setInt(1, memberseq);
          insertStatement.setString(2, name);
          insertStatement.setFloat(3, (float) boosterFactor);

          if(insertStatement.executeUpdate() > 0)
              return memberseq;
          else
              return -1;
      }
      else
          return -1;
  }

  public int addCustomer(String firstName, String lastName, String email, int memberLevelId, double totalPoints) throws SQLException {
      String query = "SELECT Customer_seq.NEXTVAL from dual";
      PreparedStatement insertStatement = connection.prepareStatement(query);
      ResultSet rs = insertStatement.executeQuery();

      if(rs.next()){
          int customerseq = rs.getInt(1);
          query = "insert into MemberLevel values(?, ?, ?, ?, ?, ?);";

          insertStatement = connection.prepareStatement(query);
          insertStatement.setInt(1, customerseq);
          insertStatement.setString(2, firstName);
          insertStatement.setString(3, lastName);
          insertStatement.setString(4, email);
          insertStatement.setInt(5, memberLevelId);
          insertStatement.setFloat(6, (float) totalPoints);

          if(insertStatement.executeUpdate() > 0)
              return customerseq;
          else
              return -1;
      }
      else
          return -1;
  }

  public int addPurchase(int customerId, int storeId, Date purchaseTime, List<Integer> coffeeIds, List<Integer> purchaseQuantities, List<Integer> redeemQuantities) {
    return -1;
  }

  public List<Integer> getCoffees() {
    return new ArrayList<Integer>();
  }

  public List<Integer> getCoffeesByKeywords(String keyword1, String keyword2) {
    return new ArrayList<Integer>();
  }

  public double getPointsByCustomerId(int customerId) {
    return -1;
  }

  public List<Integer> getTopKStoresInPastXMonth(int k, int x) {
    return new ArrayList<Integer>();
  }

  public List<Integer> getTopKCustomersInPastXMonth(int k, int x) {
    return new ArrayList<Integer>();
  }
  
  //Use this for tests
  public static void main (String [] args) {
	  BoutiqueCoffee test = new BoutiqueCoffee();
	  System.out.println(test.addStore("Test", "TestAdd", "TestType", 1, 2));
	  System.out.println(test.addCoffee("testCoffee", "good I guess", 1, 10, 10, 10));
	  
  }
  
}
