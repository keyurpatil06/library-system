package librarySystem;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Book {
    private String[] bookName = new String[3];
    private String[] bookDueDate = new String[3];
    private int booksIssued = 0;
    private int overDueCount = 0;

    public boolean checkBook(String book) {
        File ptr = new File("D:\\Misc\\projects\\OOP\\librarySystem", "BooksNameList.txt");

        try (Scanner reader = new Scanner(ptr)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] bookName = line.split("-");
                for (String name : bookName) {
                    if (name.trim().equals(book)) {
                        // System.out.println("Book is present");
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return false;
    }

    public void printBooks() {
        File ptr = new File("D:\\Misc\\projects\\OOP\\librarySystem", "BooksNameList.txt");

        try (Scanner reader = new Scanner(ptr)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] bookName = line.split("-");
                System.out.println(bookName[0]);
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void issueBook(String bookName, String userID) {
        if (booksIssued < 3 && checkBook(bookName)) {
            for (int i = 0; i < 3; i++) {
                if (this.bookName[i] == null) {
                    this.bookName[i] = bookName;

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                    Date currentDate = new Date();
                    long oneWeekInMillis = 7 * 24 * 60 * 60 * 1000; // 1 week in milliseconds
                    Date dueDate = new Date(currentDate.getTime() + oneWeekInMillis);
                    this.bookDueDate[i] = dateFormat.format(dueDate);

                    booksIssued++;
                    System.out.println("Book '" + bookName + "' has been issued.");
                    saveBookInfoToFile(bookName, this.bookDueDate[i], userID);
                    break;
                }
            }
        } else if (booksIssued == 3) {
            System.out.println("You have already issued the maximum number of books (3).");
        } else {
            System.out.println("Book is not available");
        }
    }

    public void returnBook(String bookName, String userID) {
        for (int i = 0; i < 3 && checkBook(bookName); i++) {
            if (this.bookName[i] != null && this.bookName[i].equalsIgnoreCase(bookName)) {
                this.bookName[i] = null;
                this.bookDueDate[i] = null;
                booksIssued--;

                System.out.println("Book '" + bookName + "' has been returned.");
                updateBooksNameList(bookName, userID);
                return;
            }
        }
        System.out.println("Book '" + bookName + "' was not found in your issued books.");
    }

    private void updateBooksNameList(String bookName, String userID) {
        String pathName = "D:\\Misc\\projects\\OOP\\librarySystem\\users\\" + userID.hashCode() + "\\";

        File issuedBooks = new File(pathName, "issuedBooks.txt");
        File tempIssuedBooks = new File(pathName, "TempIssuedBooks.txt");

        try {
            Scanner reader = new Scanner(issuedBooks);
            FileWriter writer = new FileWriter(tempIssuedBooks);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (!line.contains(bookName)) {
                    writer.write(line + "\n");
                }
            }

            reader.close();
            writer.close();
            issuedBooks.delete();
            tempIssuedBooks.renameTo(issuedBooks);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void saveBookInfoToFile(String bookName, String dueDate, String userID) {
        try {
            String pathName = "D:\\Misc\\projects\\OOP\\librarySystem\\users\\" + userID.hashCode()
                    + "\\issuedBooks.txt";
            FileWriter writer = new FileWriter(pathName, true);
            writer.write(bookName + " - " + dueDate + "\n");
            writer.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public boolean checkDueDates() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        Date currentDate = new Date();
        boolean isOverDue = false;

        for (int i = 0; i < booksIssued; i++) {
            try {
                Date dueDate = dateFormat.parse(bookDueDate[i]);
                if (currentDate.after(dueDate)) {
                    System.out.println("Book '" + bookName[i] + "' is past its due date.");
                    overDueCount++;
                    isOverDue = true;
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return isOverDue;
    }

    public int getOverDueCount() {
        return overDueCount;
    }

    public void displayIssuedBooks() {
        System.out.println("Issued Books:");
        for (int i = 0; i < 3; i++) {
            if (this.bookName[i] != null && this.bookDueDate[i] != null) {
                System.out.println("Book Name: " + this.bookName[i] + " | Due Date: " + this.bookDueDate[i]);
            }
        }
    }

    // public static void main(String[] args) {
    // Book b = new Book();
    // b.issueBook("Eleanor Oliphant Is Completely Fine", "1");
    // b.issueBook("Dracula", "1");
    // b.issueBook("Dune", "1");
    // b.issueBook("The Hobbit", "1");
    // b.returnBook("Dracula", "1");
    // b.returnBook("Dune", "1");
    // b.returnBook("Eleanor Oliphant Is Completely Fine", "1");
    // b.displayIssuedBooks();
    // System.out.println(b.checkDueDates());
    // }
}