package exam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class SignupPanel extends JPanel {
	private JTextField idField, nameField, accountField, contactField;
	private JPasswordField pwField;

	public SignupPanel(MainFrame mainFrame) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(245, 245, 220));  // 배경색 설정

		JLabel titleLabel = new JLabel("=회원가입=");
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		titleLabel.setFont(new Font("돋움", Font.BOLD, 24));  // 글꼴과 크기 설정

		// 라벨과 입력 필드 패널 생성
		idField = new JTextField(15);
		JPanel idPanel = createLabeledField("아  이  디", idField);
		idPanel.setBackground(new Color(245,245,220));

		pwField = new JPasswordField(15);
		JPanel pwPanel = createLabeledField("비밀번호", pwField);
		pwPanel.setBackground(new Color(245,245,220));

		nameField = new JTextField(15);
		JPanel namePanel = createLabeledField("이        름", nameField);
		namePanel.setBackground(new Color(245,245,220));

		accountField = new JTextField(15);
		JPanel accountPanel = createLabeledField("계좌번호", accountField);
		accountPanel.setBackground(new Color(245,245,220));

		contactField = new JTextField(15);
		JPanel contactPanel = createLabeledField("연  락  처", contactField);
		contactPanel.setBackground(new Color(245,245,220));

		// 가입 버튼
		JButton signupButton = new JButton("가입");
		signupButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		signupButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = idField.getText();
				String password = new String(pwField.getPassword());
				String name = nameField.getText();
				String account = accountField.getText();
				String contact = contactField.getText();

				try (FileWriter writer = new FileWriter("users.txt", true)) {
					writer.write(id + "," + password + "," + name + "," + account + "," + contact + "\n");
					JOptionPane.showMessageDialog(mainFrame, "회원가입 성공");
					mainFrame.showCard("login");
				} catch (IOException ex) {
					JOptionPane.showMessageDialog(mainFrame, "회원가입 실패");
				}
			}
		});

		// 돌아가기 버튼
		JButton backButton = new JButton("돌아가기");
		backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				mainFrame.showCard("login");
			}
		});

		// 레이아웃 설정
		add(Box.createVerticalGlue());
		add(titleLabel);
		add(Box.createRigidArea(new Dimension(0, 20)));

		add(idPanel);
		add(pwPanel);
		add(namePanel);
		add(accountPanel);
		add(contactPanel);

		add(Box.createRigidArea(new Dimension(0, 20)));
		add(signupButton);
		add(Box.createRigidArea(new Dimension(0, 10)));
		add(backButton);
		add(Box.createVerticalGlue());
	}

	// 라벨과 입력 필드를 수평으로 정렬하는 패널 생성 메서드
	private JPanel createLabeledField(String labelText, JTextField textField) {
		JLabel label = new JLabel(labelText);

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.insets = new Insets(5, 5, 5, 5);  // 패딩 설정
		gbc.gridx = 0;
		gbc.gridy = 0;
		panel.add(label, gbc);

		gbc.gridx = 1;
		panel.add(textField, gbc);

		panel.setMaximumSize(new Dimension(300, 30));
		panel.setAlignmentX(Component.CENTER_ALIGNMENT);

		return panel;
	}
}
