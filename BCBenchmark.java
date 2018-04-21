//BCBenchmark.java
import java.sql.Date;
import java.util.*;
import java.sql.*;

public class BCBenchmark{
	 public static void main (String [] args) {
	    BoutiqueCoffee test = new BoutiqueCoffee();
		int totalCustomers = 20;
		int numberOfTests = 100;

		String[] storeNames = {"BCoffee Mallowway", "BCoffee Highwell", "BCoffee Morwall", "BCoffee Bysummer", "BCoffee Newwolf" +
								"BCoffee Wintershore", "BCoffee Greyice", "BCoffee Lochcourt", "BCoffee Silverwitch", "BCoffee Prybourne"};

		String[] storeTypes = {"Full service", "Express", "Hybrid", "Drive Thru", "Truck", "Coffee Bar"};

		double minLat = -90.00;
		double maxLat = 90.00;
		double minLon = 0.00;
		double maxLon = 180.00;

		/* TEST addStore */
		System.out.println("----STRESS TESTING: addStore----");
		for(int i = 0; i < numberOfTests; i++){
			double lat = minLat + (double)(Math.random() * ((maxLat - minLat) + 1));
			double lon = minLon + (double)(Math.random() * ((maxLon - minLon) + 1));
			System.out.println("SID: " + test.addStore(storeNames[i % storeNames.length], "unlisted", storeTypes[i % storeTypes.length],
														lat, lon));
		}

	    /* TEST getPointsByCustomerId */
	    System.out.println("----STRESS TESTING: getPointsByCustomerId----");
	    for(int j = 0; j < totalCustomers; j++){
		    System.out.println("CID "+ String.valueOf(j) +" TOTAL POINTS:" + test.getPointsByCustomerId(j));
		}

		/* coffee names for keyword test */
		 String [] arr = {"Coffee", "fancy", "Tea", "Soda", "Latte", "Capuccino", "Milk", "Fake", "Water", "Bubble", "Green", "Expresso", "Americano","Colombian", "Arabic"};

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

		Random rand = new Random();

		/*TEST offerCoffee */
		 System.out.println("----STRESS TESTING: offerCoffee----");
		 for(int i = 0; i < numberOfTests; i++){
			int storeID = rand.nextInt(100) + 1;
			int coffeeID = rand.nextInt(100) + 1;
			System.out.println("Testing storeID " + storeID + " with coffeeID " + coffeeID);
			System.out.println("Status: " + test.offerCoffee(storeID, coffeeID));
		}

		GregorianCalendar gc = new GregorianCalendar();

		/*TEST addPromotion */
		System.out.println("----STRESS TESTING: addPromotion----");
		for(int i = 0; i < numberOfTests; i++){
			long ms = -946771200000L + (Math.abs(rand.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
			long ms2 = -946771200000L + (Math.abs(rand.nextLong()) % (70L * 365 * 24 * 60 * 60 * 1000));
			Date dateStart;
			Date dateEnd;

			if(ms > ms2){
				dateStart = new Date(ms2);
				dateEnd	= new Date(ms);
			}else{
				dateStart = new Date(ms);
				dateEnd = new Date(ms2);
			}

			System.out.println("PromoID: " + test.addPromotion("Promo" + i, dateStart, dateEnd));
		}

		/*TEST promoFor */
		 System.out.println("----STRESS TESTING: promoFor----");
		 for(int i = 0; i < numberOfTests; i++){
			 int promoID = rand.nextInt(100) + 1;
			 int coffeeID = rand.nextInt(100) + 1;
			 System.out.println("Testing promoID " + promoID + " with coffeeID " + coffeeID);
			 System.out.println("Status: " + test.promoteFor(promoID, coffeeID));
		 }

		 /*TEST hasPromotion */
		 System.out.println("----STRESS TESTING: hasPromotion----");
		 for(int i = 0; i < numberOfTests; i++){
			 int promoID = rand.nextInt(100) + 1;
			 int storeID = rand.nextInt(100) + 1;
			 System.out.println("Testing promoID " + promoID + " with storeID " + storeID);
			 System.out.println("Status: " + test.hasPromotion(promoID, storeID));
		 }

		 String memberLevel[] = {"bronze", "silver", "gold"};
		 /*TEST addMemberLevel */
		 System.out.println("----STRESS TESTING: addMemberLevel----");
		 for(int i = 0; i < numberOfTests; i++){
			 double boost = rand.nextDouble() * 4;
			 System.out.println("Status: " + test.addMemberLevel(memberLevel[i%memberLevel.length] + i, boost));
		 }

		 String names[] = {"John", "Doe", "Jane", "Donald", "Clayton", "Jack", "Jill", "Mary", "Richards", "Kershaw",
		 					"Castillo", "Luis", "Zack", "Reynaldo", "Garrett", "Jose", "Berrios", "Andrew", "Pam"};
		 /*TEST addCustomer */
		 System.out.println("----STRESS TESTING: addCustomer----");
		 for(int i = 0; i < numberOfTests; i++){
			 String first = names[rand.nextInt(names.length) % names.length];
			 String last = names[rand.nextInt(names.length) % names.length];
			 int leve = rand.nextInt(100) + 1;
			 double points = rand.nextDouble() * 100;
			 System.out.println("CID: " + test.addCustomer(first, last, "N/A", leve, points));
		 }

		 /* TEST getTopKStoresInPastXMonth */
		 System.out.println("----STRESS TESTING: getTopKStoresInPastXMonth----");
		 for(int j = 0; j < numberOfTests; j++){
		     int k = rand.nextInt(100) + 1;
		     int x = rand.nextInt(100) + 1;
		     System.out.println("Getting top " + k + " stores in the past " + x + " months");
             List<Integer> c = test.getTopKCustomersInPastXMonth(k, x);
             System.out.println("\t\tSTORE_ID");
             for(Integer i : c){
                 System.out.println("\t\t"+i);
             }
         }

         /* TEST getTopKCustomersInPastXMonth */
         System.out.println("----STRESS TESTING: getTopKCustomersInPastXMonth----");
         for(int j = 0; j < numberOfTests; j++){
             int k = rand.nextInt(100) + 1;
             int x = rand.nextInt(100) + 1;
             System.out.println("Getting top " + k + " customers in the past " + x + " months");
             List<Integer> c = test.getTopKCustomersInPastXMonth(k, x);
             System.out.println("\t\tCUSTOMER_ID");
             for(Integer i : c){
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
