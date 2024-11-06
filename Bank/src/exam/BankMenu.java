package exam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.beans.Customizer;

import javax.swing.*;

public class BankMenu extends JFrame{

	public BankMenu() {
		setTitle("======BBanK======");
		
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBackground(new Color(128, 0, 128));

		JButton button1 = new JButton("회원가입");
		JButton button2 = new JButton("계좌조회");
		JButton button3 = new JButton("입금");
		JButton button4 = new JButton("출금");
		JButton button5 = new JButton("종료");

		button1.setAlignmentX(Component.CENTER_ALIGNMENT);
		button2.setAlignmentX(Component.CENTER_ALIGNMENT);
		button3.setAlignmentX(Component.CENTER_ALIGNMENT);
		button4.setAlignmentX(Component.CENTER_ALIGNMENT);
		button5.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		mainPanel.add(Box.createVerticalGlue());
		mainPanel.add(button1);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(button2);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(button3);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(button4);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		mainPanel.add(button5);
		mainPanel.add(Box.createVerticalGlue());
		add(mainPanel);
		
		setBounds(300, 300, 400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new BankMenu();
	}
}
