//BCBenchmark.java
import java.sql.Date;
import java.util.*;
import java.sql.*;

public class BCBenchmark{
	 public static void main (String [] args) {
	    BoutiqueCoffee test = new BoutiqueCoffee();
	    int totalCustomers = 20;
	    int numberOfTests = 100;
	    /* coffee names for keyword test */
	    String [] arr = {"Coffee", "fancy", "Tea", "Soda", "Latte", "Capuccino", "Milk", "Fake", "Water", "Bubble", "Green", "Expresso", "Americano","Colombian", "Arabic"};

	    /* TEST getPointsByCustomerId */
	    System.out.println("----STRESS TESTING: getPointsByCustomerId----");
	    for(int j = 0; j < totalCustomers; j++){
		    System.out.println("CID "+ String.valueOf(j) +" TOTAL POINTS:" + test.getPointsByCustomerId(j));
		}
	    /* TEST getCoffees */
		for(int j = 4; j < numberOfTests; j++){
		    System.out.println("----STRESS TESTING: getCoffees----");
		   	/* add an extra coffee to the table */
		   	System.out.println("adding one more coffee: "+ arr[j%arr.length]);
		   	int cof = test.addCoffee("test"+arr[j%arr.length], "good I guess", j, 10, 10, 10);

		   	/* print coffee_id with update extra coffee on it */
		   	System.out.println("\t\tCOFEE_ID");
		    List<Integer> c = test.getCoffees();
		    for(Integer i : c){
		        System.out.println("\t\t"+i);
		    }
		}
		/* TEST getCoffeesByKeywords */
		for(int j = 0; j < numberOfTests; j++){
		    /* should return same as above */
		    System.out.println("----STRESS TESTING: getCoffeesByKeywords----");
		    System.out.println("checking for coffee: "+ arr[j%arr.length]);
		    List<Integer> n = test.getCoffeesByKeywords("test", arr[j%arr.length]);
		   	System.out.println("\t\tCOFEE_ID");
		    for(Integer i : n){
		        System.out.println("\t\t"+i);
		    }
		}

		// got ORA-01000: maximum open cursors exceeded
		// for(int j = 0; j < numberOfTests; j++){
		//     /* should always return empty list */
		//    	System.out.println("\t\tCOFEE_ID");
		//    	System.out.println("checking for NON EXISTANT coffee: ");
		//     List<Integer> d = test.getCoffeesByKeywords("test", "NOTSTORED");
		//     for(Integer i : d){
		//         System.out.println("\t\t"+i);
		//     }
		// }
	}
}
