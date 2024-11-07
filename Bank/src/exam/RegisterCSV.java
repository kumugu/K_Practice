package exam;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.nio.file.Files;


public class RegisterCSV {
    public static void registerUser(String username, String password, String accountNumber) throws IOException {
        String filePath = "user.csv";
        boolean fileExits = Files.exists(Paths.get(filePath));

        try (FileWriter writer = new FileWriter(filePath, true)) {
            if (!fileExits) {
                writer.append("username, password, account_number, balance\n");
            }
            writer.append(username)
                  .append(',')
                  .append(password)
                  .append(',')
                  .append(accountNumber)
                  .append(",0.0\n");
        }
    }

    public static void main(String[] args) throws IOException {
        registerUser("user1", "password1", "1234567890");
    }
}
