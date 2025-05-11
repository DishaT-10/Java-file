import java.util.*;

// Custom exception class
class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

// Abstract BankAccount class
abstract class BankAccount {
    protected String accountNumber;
    protected String accountHolder;
    protected double balance;
    protected List<String> miniStatement;

    public BankAccount(String accountNumber, String accountHolder, double initialBalance) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = initialBalance;
        this.miniStatement = new ArrayList<>();
        miniStatement.add("Account opened with ₹" + initialBalance);
    }

    public void deposit(double amount) {
        balance += amount;
        miniStatement.add("Deposited ₹" + amount);
    }

    public void withdraw(double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException("Insufficient balance!");
        }
        balance -= amount;
        miniStatement.add("Withdrew ₹" + amount);
    }

    public double getBalance() {
        return balance;
    }

    public void printMiniStatement() {
        System.out.println("\nMini Statement for " + accountHolder + ":");
        for (String entry : miniStatement) {
            System.out.println(entry);
        }
    }
}

// Savings Account class
class SavingsAccount extends BankAccount {
    public SavingsAccount(String accountNumber, String accountHolder, double initialBalance) {
        super(accountNumber, accountHolder, initialBalance);
    }
}

// Current Account class
class CurrentAccount extends BankAccount {
    public CurrentAccount(String accountNumber, String accountHolder, double initialBalance) {
        super(accountNumber, accountHolder, initialBalance);
    }
}

// Main class
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        BankAccount account = null;

        System.out.println("Choose Account Type:");
        System.out.println("1. Savings");
        System.out.println("2. Current");
        System.out.print("Enter your choice: ");
        int type = sc.nextInt();
        sc.nextLine(); // consume newline

        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();

        System.out.print("Enter Initial Deposit: ₹");
        double initialDeposit = sc.nextDouble();

        if (type == 1) {
            account = new SavingsAccount(accNo, name, initialDeposit);
        } else if (type == 2) {
            account = new CurrentAccount(accNo, name, initialDeposit);
        } else {
            System.out.println("Invalid account type selected.");
            return;
        }

        boolean run = true;

        while (run) {
            System.out.println("\n------ MENU ------");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Mini Statement");
            System.out.println("5. Exit");
            System.out.print("Choose an option: ");
            int option = sc.nextInt();

            switch (option) {
                case 1:
                    System.out.print("Enter deposit amount: ₹");
                    double dep = sc.nextDouble();
                    account.deposit(dep);
                    System.out.println("Amount deposited.");
                    break;

                case 2:
                    System.out.print("Enter withdrawal amount: ₹");
                    double wd = sc.nextDouble();
                    try {
                        account.withdraw(wd);
                        System.out.println("Amount withdrawn.");
                    } catch (InsufficientFundsException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("Current Balance: ₹" + account.getBalance());
                    break;

                case 4:
                    account.printMiniStatement();
                    break;

                case 5:
                    run = false;
                    System.out.println("Thank you for using the banking system.");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }
        }

        sc.close();
    }
}
