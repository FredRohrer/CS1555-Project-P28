//BCDriver.java
import java.sql.Date;
import java.util.*;
import java.sql.*;

public class BCDriver {
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
