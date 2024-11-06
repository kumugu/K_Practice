package exam;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class SignupPanel extends JPanel{

	public SignupPanel(MainFrame mainFrame) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JLabel label = new JLabel("===회원가입===");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		JButton backButton = new JButton("돌아가기");
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.showCard("menu");
			}
		});
		
		add(Box.createHorizontalGlue());
		add(label);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(backButton);
		add(Box.createHorizontalGlue());
		
	}
}
