package librarySystem;

import java.io.File;
import java.util.Scanner;

public class Authentication {
    private String userID;
    private String password;
    private String expectedPassword;

    public void getCredentials(Scanner sc) {
        System.out.print("userID: ");
        userID = sc.nextLine();
        System.out.print("Password: ");
        password = sc.nextLine();
    }

    public boolean check() {
        String folderPath = "D:\\Misc\\projects\\OOP\\librarySystem\\users\\" + userID.hashCode(); // Path to folder
        File ptr = new File(folderPath, userID.hashCode() + ".txt");

        try (Scanner reader = new Scanner(ptr)) {
            // f.createNewFile();
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(":");
                // System.out.println(Arrays.toString(parts));

                if (parts.length == 2) {
                    // String userID = c.getUserID().trim();
                    expectedPassword = parts[1].trim();

                    if (password.equals(expectedPassword)) {
                        System.out.println("Correct Password\n");
                        return true;
                    } else {
                        System.out.println("Incorrect Password\n");
                    }
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("No such account exists.\n");
        }

        return false;
    }

    public String getUserID() {
        return userID;
    }
}