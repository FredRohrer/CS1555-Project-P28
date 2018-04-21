import java.sql.Date;
import java.util.*;
import java.sql.*;



public class BoutiqueCoffee {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String query;


    //Put your username and password for your particular database here
    private String username = "";
    private String password = "";
	
	//PreparedStatement insertStatement;
	//Statement stat; 
	//ResultSet rs;

    private java.sql.Date convertSqltoJavaDate( java.util.Date jDate) {
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
	
	


    public int addStore(String name, String address, String storeType, double gpsLong, double gpsLat) {
        //statement.setAutoCommit(false);
        //not sure what the isolation should be for this one
        //probably not that serious
        //connection.setTransactionIsolation(?????);
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
        int out = -1;
		
        try {
            //statement = connection.createStatement();
            String query = "select Store_seq.NEXTVAL from dual";
            stat = connection.createStatement();
            rs = stat.executeQuery(query);

            if (rs.next()) {
                int storeSeq = rs.getInt(1);

                query = "insert into STORE values(?, ?, ?, ?, ?, ?)";
                //not sure how to deal with the store id
                insertStatement = connection.prepareStatement(query);
                insertStatement.setInt(1, storeSeq);
                insertStatement.setString(2, name);
                insertStatement.setString(3, address);
                insertStatement.setString(4, storeType);
                insertStatement.setDouble(5, gpsLong);
                insertStatement.setDouble(6, gpsLat);
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
		finally {
			try {
                if (rs != null) rs.close();
				if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
		}
		


        return out;
    }

    public int addCoffee(String name, String description, int intensity, double price, double rewardPoints, double redeemPoints) {
        int out = -1;
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
        try {
            String query = "select Coffee_seq.NEXTVAL from dual";
            stat = connection.createStatement();
            rs = stat.executeQuery(query);

            if (rs.next()) {
                int cofSeq = rs.getInt(1);

                query = "insert into Coffee values (?, ?, ?, ?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(query);
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
            //Ex.printStackTrace();'
			out = -1;
        }
		finally {
			try {
                if (rs != null) rs.close();
				if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
		}


        return out;
    }

    public int offerCoffee(int storeId, int coffeeId) {
		int out = -1;
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
		
        try {
            String query = "insert into OfferCoffee values(?, ?)";

            insertStatement = connection.prepareStatement(query);
            insertStatement.setInt(1, storeId);
            insertStatement.setInt(2, coffeeId);
            if(insertStatement.executeUpdate() > 0)
                out = 1;
            else
                out = -1;
        }
        catch (Exception Ex) {
            System.out.println("Error Adding Offer Coffee: " + Ex.toString());
            out = -1;
        }
		finally {
			try {
                //if (rs != null) rs.close();
				//if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
		}
		return out;
    }

    public int addPromotion(String name, Date startDate, Date endDate) {
		int out = -1;
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
        try {
            String query = "SELECT Promotion_seq.NEXTVAL from dual";
            insertStatement = connection.prepareStatement(query);
            rs = insertStatement.executeQuery();

            if(rs.next()){
                int promoseq = rs.getInt(1);
                query = "insert into Promotion values(?, ?, ?, ?)";

                insertStatement = connection.prepareStatement(query);
                insertStatement.setInt(1, promoseq);
                insertStatement.setString(2, name);
                insertStatement.setDate(3, startDate);
                insertStatement.setDate(4, endDate);

                if(insertStatement.executeUpdate() > 0)
                    out = promoseq;
                else
                    out = -1;
            }
            else
                out = -1;
        }
        catch (Exception Ex) {
            System.out.println("Error adding Promotion: " + Ex.toString());
            out = -1;
        }
		finally {
			try {
                if (rs != null) rs.close();
				if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
		}
		
		return out;
    }

    public int promoteFor(int promotionId, int coffeeId) {
		int out = -1;
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
        try {
            String query = "insert into PromoteFor values(?, ?)";
            insertStatement = connection.prepareStatement(query);
            insertStatement.setInt(1, promotionId);
            insertStatement.setInt(2, coffeeId);
            if(insertStatement.executeUpdate() > 0)
                out = 1;
            else
                out = -1;
        }
        catch (Exception Ex) {
            System.out.println("Error adding promoteFor: " + Ex.toString());
            out = -1;
        }
		finally {
			try {
                if (rs != null) rs.close();
				if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
		}
		
		return out;
    }

    public int hasPromotion(int storeId, int promotionId) {
		int out = -1;
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
        try {
            String query = "insert into HasPromotion values(?, ?)";
            insertStatement = connection.prepareStatement(query);
            insertStatement.setInt(1, storeId);
            insertStatement.setInt(2, promotionId);
            if(insertStatement.executeUpdate() > 0)
                out = 1;
            else
                out = -1;
        }
        catch (Exception Ex) {
            System.out.println("Error adding hasPromotion: " + Ex.toString());
            out = -1;
        }
		finally {
			try {
                if (rs != null) rs.close();
				if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
		}
		return out;
    }

    public int addMemberLevel(String name, double boosterFactor) {
		int out = -1;
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
        try {
            String query = "SELECT MemberLevel_seq.NEXTVAL from dual";
            insertStatement = connection.prepareStatement(query);
            rs = insertStatement.executeQuery();

            if(rs.next()){
                int memberseq = rs.getInt(1);
				rs.close();
				insertStatement.close();
				
                query = "insert into MemberLevel values(?, ?, ?)";

                insertStatement = connection.prepareStatement(query);
                insertStatement.setInt(1, memberseq);
                insertStatement.setString(2, name);
                insertStatement.setFloat(3, (float) boosterFactor);

                if(insertStatement.executeUpdate() > 0)
                    out = memberseq;
                else
                    out = -1;
				insertStatement.close();
            }
            else
                out = -1;
        }
        catch (Exception Ex) {
            System.out.println("MemberLevel Machine Error: " + Ex.toString());
            out = -1;
        }
		finally {
			try {
                if (rs != null) rs.close();
				if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
		}
		return out;
    }

    public int addCustomer(String firstName, String lastName, String email, int memberLevelId, double totalPoints) {
		int out = -1;
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
        try {
            String query = "SELECT Customer_seq.NEXTVAL from dual";
            insertStatement = connection.prepareStatement(query);
            rs = insertStatement.executeQuery();

            if(rs.next()){
                int customerseq = rs.getInt(1);
				insertStatement.close();
				rs.close();
                query = "insert into Customer values(?, ?, ?, ?, ?, ?)";

                insertStatement = connection.prepareStatement(query);
                insertStatement.setInt(1, customerseq);
                insertStatement.setString(2, firstName);
                insertStatement.setString(3, lastName);
                insertStatement.setString(4, email);
                insertStatement.setInt(5, memberLevelId);
                insertStatement.setFloat(6, (float) totalPoints);

                if(insertStatement.executeUpdate() > 0)
                    out = customerseq;
                else
                    out = -1;
				insertStatement.close();
            }
            else
                out = -1;
        }
        catch (Exception Ex) {
            System.out.println("Machine Error: " + Ex.toString());
            out = -1;
        }
		finally {
			try {
                if (rs != null) rs.close();
				if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
		}
		return out;
		
    }

    public int addPurchase(int customerId, int storeId, Date purchaseTime, List<Integer> coffeeIds, List<Integer> purchaseQuantities, List<Integer> redeemQuantities) {
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
		int out = -1;
        mainBlock: try {

            connection.setAutoCommit(false);

            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            //this probably should be changed, idk to what^^^

            String query = "select Purchase_seq.nextval from dual";
            stat = connection.createStatement();
            rs = stat.executeQuery(query);
            if (rs.next()){
                int purchSeq = rs.getInt(1);
				rs.close();
                stat.close();
                query = "insert into Purchase values (?, ?, ?, ?)";
                insertStatement = connection.prepareStatement(query);
                insertStatement.setInt(1, purchSeq);
                insertStatement.setInt(2, customerId);
                insertStatement.setInt(3, storeId);
                insertStatement.setDate(4, convertSqltoJavaDate(purchaseTime));
                if (!(insertStatement.executeUpdate() > 0)) {
                    System.out.println("Failed to update Purchase");
                    connection.rollback();
					insertStatement.close();
                    out = -1;
					break mainBlock;
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
						insertStatement.close();
                        out = -1;
						break mainBlock;
                    }
                }
                insertStatement.close();
                connection.commit();
                out = purchSeq;
            }
            else {
                System.out.println("Failed to generate purchase id");
                connection.rollback();
                out = -1;
            }

        }
        catch (Exception Ex) {
            System.out.println("Machine Failure: " + Ex.toString());
            out = -1;
			
        }
		finally {
			try {
				connection.rollback();
                if (rs != null) rs.close();
				if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
		}
	
		return out;
    }

    public List<Integer> getCoffees() {
        List<Integer> coffees = new ArrayList<Integer>();
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
        try {
            query = "select coffee_id from coffee";

            stat = connection.createStatement();
            rs = stat.executeQuery(query);
            int counter=1;

            while(rs.next()) {
                coffees.add(rs.getInt(1));
                counter ++;
            }
            rs.close();
        }
        catch(Exception Ex)
        {
            System.out.println("Machine Error: " + Ex.toString());
			coffees = new ArrayList<Integer>();
        }
        finally {
			try {
				connection.setAutoCommit(true);
                if (rs != null) rs.close();
				if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
			
		}
        return coffees;
    }

    public List<Integer> getCoffeesByKeywords(String keyword1, String keyword2) {
  	List<Integer> coffees = new ArrayList<Integer>();
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
        try {
			keyword1 = "%" + keyword1 + "%";
			keyword2 = "%" + keyword2 + "%";
            query = "select coffee_id, name from coffee where name like ? and name like ?";
			
            insertStatement = connection.prepareStatement(query);
			insertStatement.setString(1, keyword1); 
			insertStatement.setString(2, keyword2);
            rs = insertStatement.executeQuery();
            int counter=1;
            String name = "";

            while(rs.next()) {
                name = rs.getString(2);
                coffees.add(rs.getInt(1));
                counter ++;
            }
            rs.close();
        }
        catch(Exception Ex)
        {
            System.out.println("Machine Error: " + Ex.toString());
			coffees = new ArrayList<Integer>();
        }
        finally {
			try {
                if (rs != null) rs.close();
				if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
		}
        return coffees;
    }

    public double getPointsByCustomerId(int customerId) {
        double total_points = -1;
        String c_id = String.valueOf(customerId);
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
        try {
            query = "SELECT TOTAL_POINTS FROM CUSTOMER WHERE CUSTOMER_ID = ?";
            insertStatement = connection.prepareStatement(query);
			insertStatement.setInt(1, customerId);
            rs = insertStatement.executeQuery();
            int counter=1;
            while(rs.next()) {
                total_points = rs.getLong(1);
                counter ++;
            }
			
            rs.close();
        }
        catch(Exception Ex)
        {
            System.out.println("Machine Error: " + Ex.toString());
        }
        finally{
            try {
                if (rs != null) rs.close();
				if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
        }

        return total_points;
    }

    public List<Integer> getTopKStoresInPastXMonth(int k, int x) {
		ArrayList<Integer> out = new ArrayList<Integer>();
        int days = x * 30;
        String query = "SELECT CURRENT_DATE FROM dual";
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
        try {
            insertStatement = connection.prepareStatement(query);
            rs = insertStatement.executeQuery();

            if (rs.next()) {
                java.util.Date curdate = rs.getDate(1);
				rs.close();
				insertStatement.close();
                Calendar cal = new GregorianCalendar();
                cal.setTime(curdate);
                cal.add(Calendar.DAY_OF_MONTH, days*-1);
                java.util.Date past = cal.getTime();

                query = "SELECT Store_Id FROM ( " + 
							"SELECT Store_Id, SUM(Price*Purchase_Quantity) AS PURCHS " +
							"FROM (Coffee NATURAL JOIN BuyCoffee) NATURAL JOIN ( " +
								"SELECT * " + 
								"FROM Purchase " +
								"WHERE Purchase_Time BETWEEN ? AND CURRENT_DATE) " + 
							"GROUP BY Store_Id " +
							"ORDER BY PURCHS) " + 
						"FETCH FIRST ? ROWS WITH TIES";

                insertStatement = connection.prepareStatement(query);
                insertStatement.setDate(1, (convertSqltoJavaDate(past)));
                insertStatement.setInt(2, k);

                rs = insertStatement.executeQuery();
                
                if(rs.next()){
                    List<Integer> stores = new ArrayList<Integer>();
                    do {
                        stores.add(rs.getInt(1));
                    } while (rs.next());
                    insertStatement.close();
                    rs.close();
                    return stores;
                }
                else {
                    insertStatement.close();
                    rs.close();
                    return new ArrayList<Integer>();
                }

            } else {
                insertStatement.close();
                rs.close();
                return new ArrayList<Integer>();
            }
        } catch (SQLException e) {
            System.out.println("Machine Error: " + e.toString());
            //return new ArrayList<Integer>();
        }
		finally {
			try {
                if (rs != null) rs.close();
				if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
		}
		return out;
    }

    public List<Integer> getTopKCustomersInPastXMonth(int k, int x) {
        int days = x * 30;
        String query = "SELECT CURRENT_DATE FROM dual";
		PreparedStatement insertStatement = null;
		Statement stat = null; 
		ResultSet rs = null;
        try {
            insertStatement = connection.prepareStatement(query);
            rs = insertStatement.executeQuery();

            if (rs.next()) {
                java.util.Date curdate = rs.getDate(1);
				rs.close();
				insertStatement.close();
                Calendar cal = new GregorianCalendar();
                cal.setTime(curdate);
                cal.add(Calendar.DAY_OF_MONTH, days*-1);
                java.util.Date past = cal.getTime();

                query = "SELECT Customer_Id FROM ( " + 
							"SELECT Customer_Id, SUM(Price*Purchase_Quantity) AS PURCHS " +
							"FROM (Coffee NATURAL JOIN BuyCoffee) NATURAL JOIN ( " +
								"SELECT * " + 
								"FROM Purchase " +
								"WHERE Purchase_Time BETWEEN ? AND CURRENT_DATE) " + 
							"GROUP BY Customer_Id " +
							"ORDER BY PURCHS) " + 
						"FETCH FIRST ? ROWS WITH TIES";

                insertStatement = connection.prepareStatement(query);
                insertStatement.setDate(1, (convertSqltoJavaDate(past)));
                insertStatement.setInt(2, k);

                rs = insertStatement.executeQuery();

                if(rs.next()){
                    List<Integer> stores = new ArrayList<Integer>();
                    do {
                        stores.add(rs.getInt(1));
                    } while (rs.next());
                    insertStatement.close();
                    rs.close();
                    return stores;
                }
                else {
                    insertStatement.close();
                    rs.close();
                    return new ArrayList<Integer>();
                }

            } else {
                rs.close();
                insertStatement.close();
                return new ArrayList<Integer>();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            //return new ArrayList<Integer>();
        }
		finally {
			try {
                if (rs != null) rs.close();
				if (stat != null) stat.close();
				if (insertStatement != null) insertStatement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
		}
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
		Date date1 = new Date(0);
		long mil = 96400000;
		Date date2 = new Date(mil);
		//System.out.println(date1 + " " + date2);
        int promo = test.addPromotion("TestPromo", date1, date2);
        System.out.println("Promotion: " + promo);

        System.out.println("PromoteFor success: " + test.promoteFor(promo, cof));

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
		 //should fail because the customer does not have enough redeem points
        System.out.println("Add purchase (should fail): " + test.addPurchase(cust, store, new Date(1), cofIds, purchs, redeems));
       
		System.out.println("Add purchase success: " + test.addPurchase(cust, store, new Date(1), cofIds, redeems, purchs));

        System.out.println("CID 19 TOTAL POINTS:" + test.getPointsByCustomerId(1));

        System.out.println("GET COFFEE: COFEE_ID");
        List<Integer> c = test.getCoffees();
        for(Integer i : c){
            System.out.println(i);
        }
	    /* should return same as above */
        System.out.println("GET COFFEE KEYWORD COFEE_ID");
        List<Integer> n = test.getCoffeesByKeywords("test", "Coffee");
        for(Integer i : n){
            System.out.println(i);
        }
	    /* should return empty list */
        System.out.println("GET COFFEE KEYWORD COFEE_ID");
        List<Integer> d = test.getCoffeesByKeywords("test", "fancy");
        for(Integer i : d){
            System.out.println(i);
        }

        List<Integer> topCustomers = test.getTopKCustomersInPastXMonth(5, 5);
        List<Integer> topStores = test.getTopKStoresInPastXMonth(2, 2);
        for(int i = 0; i < topCustomers.size(); i++){
            System.out.println(topCustomers.get(i));
        }
        for(int i = 0; i < topStores.size(); i++){
            System.out.println(topStores.get(i));
        }
    }

}
