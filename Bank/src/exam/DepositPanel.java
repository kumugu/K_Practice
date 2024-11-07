package exam;
import java.awt.*;
import javax.swing.*;

public class DepositPanel extends JPanel {
    public DepositPanel(MainFrame mainFrame) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel label = new JLabel("입금");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField amountField = new JTextField(15);

        JButton depositButton = new JButton("입금");
        depositButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        depositButton.addActionListener(e -> {
            // 입금 로직 구현
            String amount = amountField.getText();
            // 여기에 입금 로직을 추가하세요
            JOptionPane.showMessageDialog(mainFrame, "입금 완료!");
        });

        JButton backButton = new JButton("돌아가기");
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener(e -> mainFrame.showCard("menu"));

        add(Box.createVerticalGlue());
        add(label);
        add(new JLabel("입금할 금액:"));
        add(amountField);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(depositButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(backButton);
        add(Box.createVerticalGlue());
    }
}
