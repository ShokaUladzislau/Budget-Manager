package budget;

import java.util.*;

public class Budget {
    private double balance = 0.00;
    private double total = 0.00;
    private List<Purchase> purchases = new ArrayList<>();

    public void addIncome(Scanner scanner) {
        System.out.println("Enter income:");
        String userInput = scanner.nextLine();
        balance += Double.parseDouble(userInput);
        System.out.println("Income was added!");
        System.out.println();
    }

    public void addPurchase(String name, String category, double price) {
        purchases.add(new Purchase(name, category, price));
        balance -= price;
        total += price;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<Purchase> getPurchases() {
        return new ArrayList<>(purchases);
    }

    public void addPurchaseFromFile(String name, String category, double price) {
        purchases.add(new Purchase(name, category, price));
    }
}