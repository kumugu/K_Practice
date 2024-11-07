package exam;
import java.awt.*;
import javax.swing.*;

public class TransferPanel extends JPanel {
    public TransferPanel(MainFrame mainFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("계좌이체");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JTextField accountField = new JTextField(15);
        JTextField amountField = new JTextField(15);

        JButton transferButton = new JButton("이체");
        transferButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        transferButton.addActionListener(e -> {
            // 이체 로직 구현
            String account = accountField.getText();
            String amount = amountField.getText();
            // 여기에 이체 로직을 추가하세요
            JOptionPane.showMessageDialog(mainFrame, "이체 완료!");
        });

        JButton backButton = new JButton("돌아가기");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> mainFrame.showCard("menu"));

        add(Box.createVerticalGlue());
        add(label);
        add(new JLabel("이체할 계좌번호:"));
        add(accountField);
        add(new JLabel("이체할 금액:"));
        add(amountField);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(transferButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(backButton);
        add(Box.createVerticalGlue());
    }
}
