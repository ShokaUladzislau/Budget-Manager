package budget;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BudgetManager {
    private static List<String> types = new ArrayList<>();
    private static final String CHOSE_THE_TYPE = "Choose the type of purchase\n" +
            "1) Food\n" +
            "2) Clothes\n" +
            "3) Entertainment\n" +
            "4) Other\n" +
            "5) Back\n";

    private static final String CHOSE_THE_TYPE_FOR_OUTPUT = "Choose the type of purchases\n" +
            "1) Food\n" +
            "2) Clothes\n" +
            "3) Entertainment\n" +
            "4) Other\n" +
            "5) All\n" +
            "6) Back\n";

    public static void addPurchaseFromConsole(Budget budget) {
        while (true) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(CHOSE_THE_TYPE);
            int userInput = Integer.parseInt(scanner.nextLine());
            System.out.println();
            if (userInput == 5) {
                break;
            } else {
                System.out.println();
                System.out.println("Enter purchase name:");
                String name = scanner.nextLine();
                System.out.println("Enter its price:");
                double price = Double.parseDouble(scanner.nextLine());
                budget.addPurchase(name, getType(userInput), price);
                System.out.println("Purchase was added!");
                System.out.println();
            }
        }
    }

    public static void addType(String type) {
        types.add(type);
    }

    public static String getType(int i) {
        return types.get(i - 1);
    }

    public static List<String> getTypes() {
        return types;
    }

    public static String printTypes() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < types.size(); i++) {
            String currentString = String.format("%d) %s\n", i + 1, types.get(i));
            stringBuilder.append(currentString);
        }
        return String.valueOf(stringBuilder);
    }

    public static int typesLength() {
        return types.size();
    }

    public static void outputListOfPurchases(Budget budget) {
        Scanner scanner = new Scanner(System.in);
        if (budget.getPurchases().isEmpty()) {
            System.out.println();
            System.out.println("Purchase list is empty!");
            System.out.println();
            return;
        }
        while (true) {
            System.out.println();
            System.out.println(CHOSE_THE_TYPE_FOR_OUTPUT);
            System.out.println();
            int userInput = Integer.parseInt(scanner.nextLine());
            if (userInput == 6) {
                break;
            } else if (userInput ==  5) {
                double total = 0.0;
                System.out.println();
                System.out.println("All:");
                for (Purchase p: budget.getPurchases()) {
                    System.out.println(p);
                    total += p.getPrice();
                }
                if (total == 0.00) {
                    System.out.println("Purchase list is empty!");
                } else {
                    System.out.printf("Total sum: $%.2f", total);
                }
                System.out.println();
            } else {
                System.out.println();
                String type = types.get(userInput - 1);
                System.out.println(type + ":");
                double total = 0.00;
                for (Purchase p: budget.getPurchases()) {
                    if (type.equals(p.getCategory())) {
                        System.out.println(p);
                        total += p.getPrice();
                    }
                }
                if (total == 0.00) {
                    System.out.println("Purchase list is empty!");
                    System.out.println();
                } else {
                    System.out.printf("Total sum: $%.2f", total);
                    System.out.println();
                }
            }
        }
    }

    public static void saveToFile(Budget budget, String fileName) {
        File file = new File(fileName);
        try {
            boolean createdNew = file.createNewFile();
            writeToNewFile(budget, file);
            System.out.println("Purchases were saved!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToNewFile(Budget budget, File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            writer.println("Balance:" + budget.getBalance());
            for (Purchase p: budget.getPurchases()) {
                writer.printf("Purchase:%s:%s:%.2f%n", p.getName(), p.getCategory(), p.getPrice());
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void printBalance(Budget budget) {
        System.out.printf("Balance: $%.2f", budget.getBalance());
        System.out.println();
    }

    public static void loadFromFile(Budget budget, String fileName) {
        File file = new File(fileName);
        String[] currentPurchase;
        try (Scanner scanner = new Scanner(file)) {
            String[] balanceString = scanner.nextLine().split(":");
            double balance = Double.parseDouble(balanceString[1]);
            budget.setBalance(balance);
            while (scanner.hasNext()) {
                currentPurchase = scanner.nextLine().split(":");
                budget.addPurchaseFromFile(currentPurchase[1],
                        currentPurchase[2],
                        Double.parseDouble(currentPurchase[3]));
            }
            System.out.println();
            System.out.println("Purchases were loaded!");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}