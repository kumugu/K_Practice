package exam;

import javax.swing.*;
import java.awt.*;

public class AccountCheckPanel extends JPanel {
    public AccountCheckPanel(MainFrame mainFrame) {
		System.out.println("생성자");
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("계좌정보");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel accountInfo = new JLabel("<html>계좌번호: 123-456-789<br>잔액: 1,000,000원<br>거래내역:</html>");
        accountInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextArea transactionHistory = new JTextArea(10, 30);
        transactionHistory.setText("거래내역을 여기에 표시합니다...");
        transactionHistory.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(transactionHistory);

        JButton backButton = new JButton("돌아가기");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> mainFrame.showCard("menu"));

        add(Box.createVerticalGlue());
        add(label);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(accountInfo);
        add(scrollPane);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(backButton);
        add(Box.createVerticalGlue());
    }
}
