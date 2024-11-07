package exam;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TransactionCSV {
    public static void deposit(String username, double amount) throws IOException {
        String filePath = "users.csv";
        List<String[]> users = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(username)) {
                    double newBalance = Double.parseDouble(values[3]) + amount;
                    values[3] = String.valueOf(newBalance);
                    System.out.println(amount + "원이 입금되었습니다.");
                }
                users.add(values);
            }
        }

        try (FileWriter writer = new FileWriter(filePath)) {
            for (String[] user : users) {
                writer.append(String.join(",", user)).append("\n");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        deposit("user1", 1000);
    }
}
