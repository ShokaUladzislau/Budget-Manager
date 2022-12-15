package budget;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final String fileName = "purchases.txt";
        final String CHOSE_YOUR_ACTION = "Choose your action:\n" +
                "1) Add income\n" +
                "2) Add purchase\n" +
                "3) Show list of purchases\n" +
                "4) Balance\n" +
                "5) Save\n" +
                "6) Load\n" +
                "7) Analyze (Sort)\n" +
                "0) Exit\n";
        Scanner scanner = new Scanner(System.in);
        Budget budget = new Budget();
        BudgetManager.addType("Food");
        BudgetManager.addType("Clothes");
        BudgetManager.addType("Entertainment");
        BudgetManager.addType("Other");
        int userInput;

        while (true) {
            System.out.println(CHOSE_YOUR_ACTION);
            userInput = Integer.parseInt(scanner.nextLine());
            if (userInput == 0) {
                System.out.println();
                System.out.println("Bye!");
                break;
            }
            switch (userInput) {
                case 1:
                    System.out.println();
                    budget.addIncome(scanner);
                    break;
                case 2:
                    System.out.println();
                    BudgetManager.addPurchaseFromConsole(budget);
                    break;
                case 3:
                    System.out.println();
                    BudgetManager.outputListOfPurchases(budget);
                    break;
                case 4:
                    System.out.println();
                    BudgetManager.printBalance(budget);
                    break;
                case 5:
                    System.out.println();
                    BudgetManager.saveToFile(budget, fileName);
                    break;
                case 6:
                    System.out.println();
                    BudgetManager.loadFromFile(budget, fileName);
                    break;
                case 7:
                    System.out.println();
                    Analyzer.startAnalysis(budget);
                    break;
                default:
                    break;
            }
            System.out.println();
        }
    }
}