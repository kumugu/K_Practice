package exam;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class MenuPanel extends JPanel{

	public MenuPanel(MainFrame mainFrame) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JButton signupButton = new JButton("회원가입");
		signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		signupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.showCard("signup");
			}
		});
		
		JButton accountCheckButton = new JButton("계좌조회");
		accountCheckButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		accountCheckButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.showCard("accountCheck");
			}
		});
		
		JButton depositButton = new JButton("입   금");
		depositButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		depositButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.showCard("deposit");
			}
		});
		
		JButton withdrawButton = new JButton("출   금");
		withdrawButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		withdrawButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.showCard("witdraw");
			}
		});
		JButton exitButton = new JButton("종   료");
		exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		exitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		add(Box.createHorizontalGlue());
		add(signupButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(accountCheckButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(depositButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(withdrawButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(exitButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(Box.createHorizontalGlue());
	}
}
