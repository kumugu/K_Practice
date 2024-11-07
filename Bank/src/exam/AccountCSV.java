package exam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class AccountCSV {
    public static void viewAccount(String username) throws IOException {
        String filePath = "user.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals(username)) {
                    System.out.println("계좌번호 : " + values[2]);
                    System.out.println("잔고 : " + values[3]);
                    return;
                }
            }
        }
        System.out.println("사용자를 찾을 수 없습니다.");
    }
    public static void main(String[] args) throws IOException {
        viewAccount("user1");
    }
}
