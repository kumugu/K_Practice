package exam;

import java.awt.Component;

import javax.swing.*;

public class AccountCheckPanel extends JPanel{

	public AccountCheckPanel(MainFrame mainFrame) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel label = new JLabel("===계좌조회===");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);		
		JButton backButton = new JButton("돌아가기");
		
		
		add(label);
	}
}
