import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoutiqueCoffee {

  public int addStore(String name, String address, String storeType, double gpsLong, double gpsLat) {
    return -1;
  }

  public int addCoffee(String name, String description, int intensity, double price, double rewardPoints, double redeemPoints) {
    return -1;
  }

  public int offerCoffee(int storeId, int coffeeId) {
    return -1;
  }

  public int addPromotion(String name, Date startDate, Date endDate) {
    return -1;
  }

  public int promoteFor(int promotionId, int coffeeId) {
    return -1;
  }

  public int hasPromotion(int storeId, int promotionId) {
    return -1;
  }

  public int addMemberLevel(String name, double boosterFactor) {
    return -1;
  }

  public int addCustomer(String firstName, String lastName, String email, int memberLevelId, double totalPoints) {
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
}
