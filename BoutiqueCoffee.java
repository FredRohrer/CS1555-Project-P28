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
  
  private java.sql.Date convertSqltoJavaDate( Date jDate) {
	  return new java.sql.Date(jDate.getTime()); 
  }
  
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
      //Ex.printStackTrace();
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
      //Ex.printStackTrace();
	}

	
	return out;
  }

  public int offerCoffee(int storeId, int coffeeId) {
	try {
		String query = "insert into OfferCoffee values(?, ?)";

		PreparedStatement insertStatement = connection.prepareStatement(query);
		insertStatement.setInt(1, storeId);
		insertStatement.setInt(2, coffeeId);
		if(insertStatement.executeUpdate() > 0)
			return 1;
		else
			return -1;
	}
	catch (Exception Ex) {
		System.out.println("Error Adding Offer Coffee: " + Ex.toString());
		return -1;
	}
  }

  public int addPromotion(String name, Date startDate, Date endDate) {
	  try {
		  String query = "SELECT Promotion_seq.NEXTVAL from dual";
		  PreparedStatement insertStatement = connection.prepareStatement(query);
		  ResultSet rs = insertStatement.executeQuery();

		  if(rs.next()){
			  int promoseq = rs.getInt(1);
			  query = "insert into Promotion values(?, ?, ?, ?)";

			  insertStatement = connection.prepareStatement(query);
			  insertStatement.setInt(1, promoseq);
			  insertStatement.setString(2, name);
			  insertStatement.setDate(3, convertSqltoJavaDate(startDate));
			  insertStatement.setDate(4, convertSqltoJavaDate(endDate));

			  if(insertStatement.executeUpdate() > 0)
				  return promoseq;
			  else
				  return -1;
		  }
		  else
			  return -1;
	  }
	  catch (Exception Ex) {
		  System.out.println("Error adding Promotion: " + Ex.toString());
		  return -1;
	  }
  }

  public int promoteFor(int promotionId, int coffeeId) {
	  try {
		  String query = "insert into PromoteFor values(?, ?)";
		  PreparedStatement insertStatement = connection.prepareStatement(query);
		  insertStatement.setInt(1, promotionId);
		  insertStatement.setInt(2, coffeeId);
		  if(insertStatement.executeUpdate() > 0)
			  return 1;
		  else
			  return -1;
	  }
	  catch (Exception Ex) {
		  System.out.println("Error adding promoteFor: " + Ex.toString());
		  return -1;
	  }
  }

  public int hasPromotion(int storeId, int promotionId) {
	  try {
		String query = "insert into HasPromotion values(?, ?)";
		PreparedStatement insertStatement = connection.prepareStatement(query);
		insertStatement.setInt(1, storeId);
		insertStatement.setInt(2, promotionId);
		if(insertStatement.executeUpdate() > 0)
			return 1;
		else
			return -1;
	  }
	  catch (Exception Ex) {
		  System.out.println("Error adding hasPromotion: " + Ex.toString());
		  return -1;
	  }
  }

  public int addMemberLevel(String name, double boosterFactor) {
	  try {
		  String query = "SELECT MemberLevel_seq.NEXTVAL from dual";
		  PreparedStatement insertStatement = connection.prepareStatement(query);
		  ResultSet rs = insertStatement.executeQuery();

		  if(rs.next()){
			  int memberseq = rs.getInt(1);
			  query = "insert into MemberLevel values(?, ?, ?)";

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
	  catch (Exception Ex) {
		System.out.println("MemberLevel Machine Error: " + Ex.toString());
		return -1;
	  }
  }

  public int addCustomer(String firstName, String lastName, String email, int memberLevelId, double totalPoints) {
	try {
        String query = "SELECT Customer_seq.NEXTVAL from dual";
        PreparedStatement insertStatement = connection.prepareStatement(query);
        ResultSet rs = insertStatement.executeQuery();

        if(rs.next()){
            int customerseq = rs.getInt(1);
            query = "insert into Customer values(?, ?, ?, ?, ?, ?)";

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
	catch (Exception Ex) {
		System.out.println("Machine Error: " + Ex.toString());
		return -1;
	}
  }

  public int addPurchase(int customerId, int storeId, Date purchaseTime, List<Integer> coffeeIds, List<Integer> purchaseQuantities, List<Integer> redeemQuantities) {
	  

	try {
		
		connection.setAutoCommit(false);
		
		connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		//this probably should be changed, idk to what^^^
		
		String query = "select Purchase_seq.nextval from dual";
		Statement stat = connection.createStatement();
		ResultSet rs = stat.executeQuery(query);
		if (rs.next()){
			int purchSeq = rs.getInt(1);
			stat.close();
			query = "insert into Purchase values (?, ?, ?, ?)";
			PreparedStatement insertStatement = connection.prepareStatement(query);
			insertStatement.setInt(1, purchSeq);
			insertStatement.setInt(2, customerId);
			insertStatement.setInt(3, storeId);
			insertStatement.setDate(4, convertSqltoJavaDate(purchaseTime));
			if (!(insertStatement.executeUpdate() > 0)) {
				System.out.println("Failed to update Purchase");
				connection.rollback();
				return -1;
			}
			insertStatement.close();
			query = "insert into BuyCoffee values (?, ?, ?, ?)";
			
			insertStatement = connection.prepareStatement(query);
			for (int i = 0; i < coffeeIds.size(); i++) {
				insertStatement.setInt(1, purchSeq);
				insertStatement.setInt(2, coffeeIds.get(i));
				insertStatement.setInt(3, purchaseQuantities.get(i));
				insertStatement.setInt(4, redeemQuantities.get(i));
				if (!(insertStatement.executeUpdate() > 0)) {
					System.out.println("Failed to update BuyCoffee");
					connection.rollback();
					return -1;
				}
			}
			insertStatement.close();
			connection.commit();
			return purchSeq;
		}
		else {
			System.out.println("Failed to generate purchase id");
			connection.rollback();
			return -1;
		}
		
	}
	catch (Exception Ex) {
		System.out.println("Machine Failure: " + Ex.toString());
		
		return -1;
	}
	
	
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
	  
	  int store = test.addStore("Test", "TestAdd", "TestType", 1, 2);
	  System.out.println("Store ID: " + store);
	  
	  int cof = test.addCoffee("testCoffee", "good I guess", 1, 10, 10, 10);
	  System.out.println("Coffee ID: " + cof);
	  
	  System.out.println("offerCoffee success:" + test.offerCoffee(store, cof));
	  
	  int promo = test.addPromotion("TestPromo", new Date(100000), new Date());
	  System.out.println("Promotion: " + promo);
	  
	  System.out.println("PromoteFor success: " + test.promoteFor(store, cof));
	  
	  System.out.println("hasPromotion success: " + test.hasPromotion(store, promo));
	  
	  int memLevel = test.addMemberLevel("TestLevel", 2);
	  System.out.println("Member ID: " + memLevel);
	  
	  int cust = test.addCustomer("TestFName", "TestLName", "TestMail", memLevel, 0);
	  System.out.println("Customer ID:" + cust);
	  
	  ArrayList<Integer> cofIds = new ArrayList<Integer>();
	  cofIds.add(cof);
	  ArrayList<Integer> purchs = new ArrayList<Integer>();
	  purchs.add(0);
	  ArrayList<Integer> redeems = new ArrayList<Integer>();
	  redeems.add(1);
	  System.out.println(test.addPurchase(cust, store, new Date(), cofIds, purchs, redeems));
	  //should fail because the customer does not have enough redeem points
	  
  }
  
}
