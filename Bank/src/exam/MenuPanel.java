package exam;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MenuPanel extends JPanel{
	public MenuPanel(MainFrame mainFrame) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(173,216,230));
		
		JButton accountCheckButton = new JButton("계좌조회");
		JButton transferButton = new JButton("계좌이체");
		JButton depositButton = new JButton("입   금");
		JButton withdrawButton = new JButton("출   금");
		JButton exitButton = new JButton("종   료");

		configureButton(accountCheckButton, mainFrame, "accountCheck");
		configureButton(transferButton, mainFrame, "transfer");
		configureButton(depositButton, mainFrame, "deposit");
		configureButton(withdrawButton, mainFrame, "withdraw");

		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		add(Box.createVerticalGlue());
		add(accountCheckButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(transferButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(depositButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(withdrawButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(exitButton);
		add(Box.createVerticalGlue());
	}

	private void configureButton(JButton button, MainFrame mainFrame, String cardName) {
		button.setAlignmentX(Component.CENTER_ALIGNMENT);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.showCard(cardName);
			}
		});
	}
}
