package budget;

import java.util.*;

import static budget.BudgetManager.getTypes;
import static budget.BudgetManager.printTypes;

public class Analyzer {
    private static final String HOW_TO_SORT = "How do you want to sort?\n" +
            "1) Sort all purchases\n" +
            "2) Sort by type\n" +
            "3) Sort certain type\n" +
            "4) Back\n";

    public static void startAnalysis(Budget budget) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(HOW_TO_SORT);
            System.out.println();
            String userInput = scanner.nextLine();
            if ("4".equals(userInput)) {
                System.out.println();
                break;
            }
            switch (userInput) {
                case "1":
                    sortAllPurchases(budget);
                    System.out.println();
                    break;
                case "2":
                    sortByType(budget);
                    System.out.println();
                    break;
                case "3":
                    sortByCertainType(budget);
                    System.out.println();
                    break;

            }
        }
    }

    private static void sortByCertainType(Budget budget) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Choose the type of purchase\n" +
                "1) Food\n" +
                "2) Clothes\n" +
                "3) Entertainment\n" +
                "4) Other\n");
        System.out.println();
        String userInput = scanner.nextLine();
        sortByCertainType(budget, BudgetManager.getType(Integer.parseInt(userInput)));
        System.out.println();
    }

    private static void sortByCertainType(Budget budget, String type) {
        double totalSum = 0.0;
        List<Purchase> purchasesByType = new ArrayList<>();
        for (Purchase p: budget.getPurchases()) {
            if (p.getCategory().equals(type)) {
                purchasesByType.add(p);
                totalSum += p.getPrice();
            }
        }
        purchasesByType.sort(new Comparator<Purchase>() {
            @Override
            public int compare(Purchase p1, Purchase p2) {
                return (int) (p2.getPrice() * 100 - p1.getPrice() * 100);
            }
        });
        if (purchasesByType.isEmpty()) {
            System.out.println("Purchase list is empty!");
        } else {
            // System.out.println(type + ":");
            for (Purchase p: purchasesByType) {
                System.out.println(p);
            }
            System.out.println("Total sum: $" + totalSum);
        }
    }

    private static void sortByType(Budget budget) {
        Map<String, Double> typeMap = new LinkedHashMap<>();
        double totalSum = 0.0;
        for (String s: getTypes()) {
            double total = 0.0;
            for (Purchase p: budget.getPurchases()) {
                if (p.getCategory().equals(s)) {
                    total += p.getPrice();
                }
                totalSum += p.getPrice();
            }
            typeMap.put(s, total);
        }
        List<Map.Entry<String, Double>> sorted = new LinkedList<>(typeMap.entrySet());
        Collections.sort(sorted, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> stringDoubleEntry, Map.Entry<String, Double> t1) {
                return (int) (- stringDoubleEntry.getValue() * 100 + t1.getValue() * 100);
            }
        });
        for (var entry: sorted) {
            if (entry.getValue() == 0.0) {
                System.out.println(entry.getKey() + " - $0");
            } else {

                System.out.printf("%s - $%.2f\n", entry.getKey(), entry.getValue());
            }
        }
        System.out.println("Total sum: $" + totalSum);
    }

    private static void sortAllPurchases(Budget budget) {
        List<Purchase> result = budget.getPurchases();
        result.sort(new Comparator<Purchase>() {
            @Override
            public int compare(Purchase p1, Purchase p2) {
                return (int) ((p2.getPrice() - p1.getPrice()) * 100);
            }
        });
        if (result.isEmpty()) {
            System.out.println("Purchase list is empty!");
            System.out.println();
        } else {
            double total = 0.0;
            // System.out.println("All:");
            for (Purchase p: result) {
                System.out.println(p);
                total += p.getPrice();
            }
            System.out.println("Total: $" + total);
        }
    }
}