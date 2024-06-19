package librarySystem;

import java.util.Scanner;

public class UserAccount {
    public void welcome() {
        cls();
        System.out.println("-----------------------------");
        System.out.println("| Library Management System |");
        System.out.println("-----------------------------");
    }

    // function to clear console (formatting purposes).
    public void cls() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        int choice;
        String choiceStr;

        welcome();
        System.out.println("\n---------------------");
        System.out.println("|       Menu        |");
        System.out.println("---------------------");
        System.out.println("| 1. Create Account |");
        System.out.println("| 2. Login          |");
        System.out.println("| 3. Log out        |");
        System.out.println("---------------------");
        System.out.print("Enter choice: ");
        choiceStr = sc.nextLine();
        choice = Integer.parseInt(choiceStr);

        switch (choice) {
            case 1:
                boolean isUniqueUsername = false;

                while (!isUniqueUsername) {
                    String userID;
                    String password;

                    // sc.nextLine();
                    System.out.print("userID: ");
                    userID = sc.nextLine();
                    System.out.print("Password: ");
                    password = sc.nextLine();

                    CreateAccount c = new CreateAccount(userID, password);
                    if (c.isValidUserId(userID.hashCode() + ".txt")) {
                        System.out.println("Choose a new username.");
                    } else {
                        String fileName = Integer.toString(userID.hashCode()) + ".txt";
                        c = new CreateAccount(userID, password, fileName);
                        isUniqueUsername = true;
                        System.out.println("Account Created!");
                    }
                }

                break;

            case 2:
                boolean isValidPassword = false;
                Authentication a = new Authentication();

                while (!isValidPassword) {
                    a.getCredentials(sc);

                    if (a.check()) {
                        isValidPassword = true;

                        boolean exitUserMenu = false;
                        String book;
                        User u = new User();

                        while (!exitUserMenu) {
                            System.out.println("\n-----------------------------");
                            System.out.println("|           Menu            |");
                            System.out.println("-----------------------------");
                            System.out.println("| 1. Deposit                |");
                            System.out.println("| 2. Issue Book             |");
                            System.out.println("| 3. Return Book            |");
                            System.out.println("| 4. Pay Fine               |");
                            System.out.println("| 5. Display Account        |");
                            System.out.println("| 6. Display issued books   |");
                            System.out.println("| 7. Log out                |");
                            System.out.println("-----------------------------");

                            System.out.print("Enter choice: ");
                            int userChoice = Integer.parseInt(sc.nextLine());
                            Book b = new Book();
                            welcome();

                            switch (userChoice) {
                                case 1:
                                    int amount;
                                    System.out.print("Enter amount: ");
                                    amount = Integer.parseInt(sc.nextLine());
                                    u.deposit(amount);
                                    break;

                                case 2:
                                    b.printBooks();
                                    System.out.print("\nEnter book name to issue from the available titles: ");
                                    book = sc.nextLine();
                                    b.issueBook(book, a.getUserID());
                                    break;

                                case 3:
                                    b.displayIssuedBooks();
                                    System.out.print("\nEnter book name to return: ");
                                    book = sc.nextLine();
                                    b.returnBook(book, a.getUserID());
                                    break;

                                case 4:
                                    u.payFine();
                                    break;

                                case 5:
                                    u.displayAccountInfo();
                                    break;

                                case 6:
                                    b.displayIssuedBooks();
                                    break;

                                case 7:
                                    System.out.println("Logged out!");
                                    exitUserMenu = true;
                                    break;

                                default:
                                    System.out.println("Invalid choice");
                                    break;
                            }
                        }
                    }
                }
                break;

            case 3:
                System.out.println("Logged out!");
                System.exit(0);
                break;

            default:
                System.out.println("Invalid choice");
                break;
        }

        sc.close();
    }
}