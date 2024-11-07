package exam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoginCSV {
    public static boolean loginUser(String username, String password) throws IOException {
        String filePath = "user.csv";

        try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(username) && values[1].equals(password)) {
                    System.out.println("로그인 성공!");
                    return true;
                }
            }
        }
        System.out.println("로그인 실패!");
        return false;
    }

    public static void main(String[] args) throws IOException {
        loginUser("user1", "password1");
    }
}
