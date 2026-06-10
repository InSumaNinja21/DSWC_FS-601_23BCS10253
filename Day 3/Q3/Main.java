package Day3.Q3;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
class Order {
    private int orderId;
    private String traderName;
    private double price;
    private int quantity;

    public Order(int orderId, String traderName,double price, int quantity) {
        this.orderId = orderId;
        this.traderName = traderName;
        this.price = price;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getTraderName() {
        return traderName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Order ID: " + orderId +
               ", Trader: " + traderName +
               ", Price: $" + price +
               ", Quantity: " + quantity;
    }
}

// Exchange Manager
class ExchangeManager {
    private ConcurrentHashMap<String,CopyOnWriteArrayList<Order>> orderBook;

    public ExchangeManager() {
        orderBook = new ConcurrentHashMap<>();
    }

    // Thread-safe order placement
    public void placeOrder(String ticker, Order order) {
        orderBook.computeIfAbsent(ticker,k -> new CopyOnWriteArrayList<>()).add(order);
        System.out.println("Order added for " + ticker);
    }

    public void displayOrders(String ticker) {
        CopyOnWriteArrayList<Order> orders =orderBook.get(ticker);
        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders found for "+ ticker);
            return;
        }

        System.out.println("\nOrder Book for "+ ticker + ":");
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
public class TradeXOrderMatchingEngine {
    public static void main(String[] args) {
        ExchangeManager exchange =new ExchangeManager();
        Order o1 =new Order(11,"A",7500.50,2);
        Order o2 =new Order(10,"B",5200.50,2);
        Order o3 =new Order(12,"C",750.50,2);
        Order o4 =new Order(13,"D",7800.50,2);
        exchange.placeOrder("BTC", o1);
        exchange.placeOrder("BTC", o2);
        exchange.placeOrder("ETH", o3);
        exchange.placeOrder("BTC", o4);
        exchange.displayOrders("BTC");
        exchange.displayOrders("ETH");
    }
}
