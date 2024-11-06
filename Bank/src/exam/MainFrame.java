package exam;

import java.awt.CardLayout;

import javax.swing.*;

public class MainFrame extends JFrame{
	private CardLayout cardLayout;
	private JPanel cardPanel;
	
	public MainFrame() {
		setTitle("===BBanK===");
		cardLayout = new CardLayout();
		cardPanel = new JPanel(cardLayout);
		
		cardPanel.add(new MenuPanel(this),"menu");
		cardPanel.add(new SignupPanel(this),"signup");
		cardPanel.add(new AccountCheckPanel(this),"accountChek");
		cardPanel.add(new DepositPanel(this),"deposit");
		cardPanel.add(new WithdrawPanel(this),"withdraw");
		
		add(cardPanel);
		setSize(400, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void showCard(String name) {
		cardLayout.show(cardPanel, name);
	}
	
	public static void main(String[] args) {
		new MainFrame();
	}
}
