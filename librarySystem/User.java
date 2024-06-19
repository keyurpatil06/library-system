package librarySystem;

public class User extends UserAccount {
    private int balance;
    private int fine;
    static Book b = new Book();

    public User() {
        this.balance = 0;
        this.fine = 0;
    }

    public void deposit(int amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: $" + amount);
            System.out.println("Account Balance: $" + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void payFine() {
        accumulateFine();
        if (b.checkDueDates() && balance >= fine) {
            balance -= fine;
            System.out.println("Fine paid: $" + fine);
        } else {
            System.out.println("No book is past due date");
        }
    }

    public void accumulateFine() {
        if (b.checkDueDates()) {
            for (int i = 1; i <= b.getOverDueCount(); i++) {
                fine += 50;
            }
            System.out.println("Fine accumulated: $" + fine);
        }
    }

    public void displayAccountInfo() {
        System.out.println("Account Balance: $" + balance);
        System.out.println("Fine: $" + fine);
    }
}