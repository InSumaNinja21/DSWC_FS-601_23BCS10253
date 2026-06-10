import java.util.ArrayList;
import java.util.List;

enum Status {
    COMPLETED,
    PENDING,
    CANCELLED
}

enum Category {
    ELECTRONICS,
    CLOTHING,
    GROCERY,
    BOOKS
}

class Transaction {
    private int transactionId;
    private Status status;
    private Category category;
    private double amount;

    public Transaction(int transactionId, Status status, Category category, double amount) {
        this.transactionId = transactionId;
        this.status = status;
        this.category = category;
        this.amount = amount;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public Status getStatus() {
        return status;
    }

    public Category getCategory() {
        return category;
    }

    public double getAmount() {
        return amount;
    }
}

class SalesAnalyzer {

    public double calculateElectronicsRevenue(List<Transaction> transactions) {
        return transactions.stream().filter(t -> t.getStatus() == Status.COMPLETED && t.getCategory() == Category.ELECTRONICS).mapToDouble(Transaction::getAmount).sum();
    }
}

public class Main {
    public static void main(String[] args) {

        List<Transaction> transactions = new ArrayList<>();

        transactions.add(new Transaction(101, Status.COMPLETED, Category.ELECTRONICS, 25000));
        transactions.add(new Transaction(102, Status.PENDING, Category.ELECTRONICS, 15000));
        transactions.add(new Transaction(103, Status.COMPLETED, Category.CLOTHING, 5000));
        transactions.add(new Transaction(104, Status.COMPLETED, Category.ELECTRONICS, 30000));
        transactions.add(new Transaction(105, Status.CANCELLED, Category.ELECTRONICS, 10000));

        SalesAnalyzer analyzer = new SalesAnalyzer();

        double revenue = analyzer.calculateElectronicsRevenue(transactions);

        System.out.println("Total Electronics Revenue = $" + revenue);
    }
}
