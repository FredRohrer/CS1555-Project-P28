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
        List<Integer> coffees = new ArrayList<Integer>();

        try {
            query = "select coffee_id from coffee";

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
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
        }
        finally{
            try {
                if (statement!=null) statement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
        }
        return coffees;
    }

    public List<Integer> getCoffeesByKeywords(String keyword1, String keyword2) {
        List<Integer> coffees = new ArrayList<Integer>();

        try {
            query = "select coffee_id, name from coffee";

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            int counter=1;
            String name = "";

            while(rs.next()) {
                name = rs.getString(2);
                if(name.contains(keyword1) && name.contains(keyword2)){
                    coffees.add(rs.getInt(1));
                }
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
                if (statement!=null) statement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
        }
        return coffees;
    }

    public double getPointsByCustomerId(int customerId) {
        double total_points = -1;
        String c_id = String.valueOf(customerId);
        try {
            query = "SELECT TOTAL_POINTS FROM CUSTOMER WHERE CUSTOMER_ID = "+c_id;
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
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
                if (statement!=null) statement.close();
            } catch (SQLException e) {
                System.out.println("Cannot close Statement. Machine error: "+e.toString());
            }
        }

        return total_points;
    }

    public List<Integer> getTopKStoresInPastXMonth(int k, int x) {
        int days = x * 30;
        String query = "SELECT CURRENT_DATE FROM dual";

        try {
            PreparedStatement insertStatement = connection.prepareStatement(query);
            ResultSet rs = insertStatement.executeQuery();

            if (rs.next()) {
                java.util.Date curdate = rs.getDate(1);
                Calendar cal = new GregorianCalendar();
                cal.setTime(curdate);
                cal.add(Calendar.DAY_OF_MONTH, days*-1);
                java.util.Date past = cal.getTime();

                query = "SELECT Store_Id FROM (" +
                        "SELECT Store_Id, SUM(Price*Purchase_Quantity) FROM (" +
                        "SELECT * FROM Coffee NATURAL JOIN (" +
                        "SELECT * FROM BuyCoffee NATURAL JOIN (" +
                        "SELECT * FROM Purchase WHERE Purchase_Time BETWEEN ? AND CURRENT_DATE)))" +
                        "GROUP BY Store_ID)" +
                        "ORDER BY Store_ID DESC " +
                        "FETCH FIRST ? ROWS WITH TIES";

                insertStatement = connection.prepareStatement(query);
                insertStatement.setDate(1, (convertSqltoJavaDate(past)));
                insertStatement.setInt(2, k);

                rs = insertStatement.executeQuery();
                
                if(rs.next()){
                    List<Integer> stores = new ArrayList<Integer>();
                    while (rs.next()){
                        stores.add(rs.getInt(1));
                    }
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
            e.printStackTrace();
            return new ArrayList<Integer>();
        }
    }

    public List<Integer> getTopKCustomersInPastXMonth(int k, int x) {
        int days = x * 30;
        String query = "SELECT CURRENT_DATE FROM dual";

        try {
            PreparedStatement insertStatement = connection.prepareStatement(query);
            ResultSet rs = insertStatement.executeQuery();

            if (rs.next()) {
                java.util.Date curdate = rs.getDate(1);
                Calendar cal = new GregorianCalendar();
                cal.setTime(curdate);
                cal.add(Calendar.DAY_OF_MONTH, days*-1);
                java.util.Date past = cal.getTime();

                query = "SELECT Customer_Id FROM (" +
                        "SELECT Customer_Id, SUM(Price*Purchase_Quantity) FROM (" +
                        "SELECT * FROM Coffee NATURAL JOIN (" +
                        "SELECT * FROM BuyCoffee NATURAL JOIN (" +
                        "SELECT * FROM Purchase WHERE Purchase_Time BETWEEN ? AND CURRENT_DATE)))" +
                        "GROUP BY Customer_Id)" +
                        "ORDER BY Customer_Id DESC " +
                        "FETCH FIRST ? ROWS WITH TIES";

                insertStatement = connection.prepareStatement(query);
                insertStatement.setDate(1, (convertSqltoJavaDate(past)));
                insertStatement.setInt(2, k);

                rs = insertStatement.executeQuery();

                if(rs.next()){
                    List<Integer> stores = new ArrayList<Integer>();
                    while (rs.next()){
                        stores.add(rs.getInt(1));
                    }
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
            return new ArrayList<Integer>();
        }
    }

    //Use this for tests
    public static void main (String [] args) {
        BoutiqueCoffee test = new BoutiqueCoffee();

        int store = test.addStore("Test", "TestAdd", "TestType", 1, 2);
        System.out.println("Store ID: " + store);

        int cof = test.addCoffee("testCoffee", "good I guess", 1, 10, 10, 10);
        System.out.println("Coffee ID: " + cof);

        System.out.println("offerCoffee success:" + test.offerCoffee(store, cof));

        //int promo = test.addPromotion("TestPromo", new Date(100000), new Date());
        //System.out.println("Promotion: " + promo);

        System.out.println("PromoteFor success: " + test.promoteFor(store, cof));

        //System.out.println("hasPromotion success: " + test.hasPromotion(store, promo));

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
        //System.out.println(test.addPurchase(cust, store, new Date(), cofIds, purchs, redeems));
        //should fail because the customer does not have enough redeem points

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
