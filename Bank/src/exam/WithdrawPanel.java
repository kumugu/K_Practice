package exam;
import java.awt.*;
import javax.swing.*;

public class WithdrawPanel extends JPanel {
    public WithdrawPanel(MainFrame mainFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("출금");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField amountField = new JTextField(15);

        JButton withdrawButton = new JButton("출금");
        withdrawButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        withdrawButton.addActionListener(e -> {
            // 출금 로직 구현
            String amount = amountField.getText();
            // 여기에 출금 로직을 추가하세요
            JOptionPane.showMessageDialog(mainFrame, "출금 완료!");
        });

        JButton backButton = new JButton("돌아가기");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> mainFrame.showCard("menu"));

        add(Box.createVerticalGlue());
        add(label);
        add(new JLabel("출금할 금액:"));
        add(amountField);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(withdrawButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(backButton);
        add(Box.createVerticalGlue());
    }
}

