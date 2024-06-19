package librarySystem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CreateAccount {
    private String userID;
    private String password;

    CreateAccount(String userID, String password) {
        this.userID = userID;
        this.password = password;
    }

    CreateAccount(String userID, String password, String fileName) {
        this.userID = userID;
        this.password = password;

        String newFolder = "D:\\Misc\\OOP\\librarySystem\\users\\" + userID.hashCode();
        // String folderPath = "D:\\Misc\\OOP\\users"; // Path to your folder
        String folderPath = newFolder;

        if (!isValidUserId(fileName)) {
            File folder = new File(newFolder);
            File file = new File(folderPath, fileName);

            try {
                folder.mkdirs();
                file.createNewFile();
                writeUserInfoToFile(file);
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            System.out.println("Choose new username");
        }

    }

    public String getUserID() {
        return userID;
    }

    public String getPassword() {
        return password;
    }

    public boolean isValidUserId(String fileName) {
        String folderPath = "D:\\Misc\\OOP\\librarySystem\\users\\" + userID.hashCode();
        // String fileNameToCheck = "file-to-check.txt";

        File folder = new File(folderPath);
        File[] filesInFolder = folder.listFiles();

        if (filesInFolder != null) {
            for (File file : filesInFolder) {
                if (file.isFile() && file.getName().equals(fileName)) {
                    System.out.print("UserName already taken. ");
                    return true;
                }
            }
        }

        return false;
    }

    private void writeUserInfoToFile(File file) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(userID + ": " + password);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}