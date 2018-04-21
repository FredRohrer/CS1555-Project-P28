//BCDriver.java
import java.sql.Date;
import java.util.*;
import java.sql.*;

public class BCDriver {
	 public static void main (String [] args) {

		 BoutiqueCoffee test = new BoutiqueCoffee();

		 int store = test.addStore("Test", "TestAdd", "TestType", 1, 2);
		 System.out.println("ADD STORE: " + store);

		 int cof = test.addCoffee("testCoffee", "good I guess", 1, 10, 10, 10);
		 System.out.println("ADD COFFEE: " + cof);

		 System.out.println("OFFER COFFEE: " + test.offerCoffee(store, cof));
		 Date date1 = new Date(0);
		 long mil = 96400000;
		 Date date2 = new Date(mil);
		 //System.out.println(date1 + " " + date2);
		 int promo = test.addPromotion("TestPromo", date1, date2);
		 System.out.println("ADD PROMOTION: " + promo);

		 System.out.println("PROMOTE COFFEE: " + test.promoteFor(promo, cof));

		 System.out.println("HAS PROMOTION: " + test.hasPromotion(store, promo));

		 int memLevel = test.addMemberLevel("TestLevel", 2);
		 System.out.println("ADD MEMBER LEVEL: " + memLevel);
		 
		 int cust = test.addCustomer("TestFName", "TestLName", "TestMail", memLevel, 0);
		 System.out.println("ADD CUSTOMER:" + cust);

		 ArrayList<Integer> cofIds = new ArrayList<Integer>();
		 cofIds.add(cof);
		 ArrayList<Integer> redeems = new ArrayList<Integer>();
		 redeems.add(0);
		 ArrayList<Integer> purchs = new ArrayList<Integer>();
		 purchs.add(1);
		 //should fail because the customer does not have enough redeem points
		 //System.out.println("Add purchase (should fail): " + test.addPurchase(cust, store, new Date(1), cofIds, purchs, redeems));
		 
		 
		 System.out.println("ADD PURCHASE: " + test.addPurchase(cust, store, Date.valueOf("2018-04-20"), cofIds, purchs, redeems));

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
		 System.out.println("GET TOP 5 CUSTOMERS IN PAST 5 MONTHS:");
		 for(int i = 0; i < topCustomers.size(); i++){
			 System.out.println(topCustomers.get(i));
		 }
		 System.out.println("GET TOP 2 STORES IN PAST 2 MONTHS:");
		 for(int i = 0; i < topStores.size(); i++){
			 System.out.println(topStores.get(i));
		 }
	}
}
