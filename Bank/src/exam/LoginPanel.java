package exam;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

public class LoginPanel extends JPanel {
    public LoginPanel(MainFrame mainFrame) {
        setBackground(new Color(128, 0, 128));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("===BBanK===");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        JLabel idLabel = new JLabel("ID");
        idLabel.setForeground(new Color(245,245,220));
        JTextField idField = new JTextField(15);
        idField.setMaximumSize(new Dimension(200, 30));

        JLabel pwLabel = new JLabel("PW");
        pwLabel.setForeground(new Color(245,245,220));
        JPasswordField pwField = new JPasswordField(15); 
        pwField.setMaximumSize(new Dimension(200, 30));

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        ActionListener logActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText();
                String password = new String(pwField.getPassword());

                if (authenticateUser(id, password)) {
                    mainFrame.showCard("menu");
                } else {
                    JOptionPane.showMessageDialog(mainFrame, "login failed");
                }
            }

            private boolean authenticateUser(String id, String password) {
                return authenticateFromCSV(id, password) || authenticateFromTXT(id, password);
            }

            private boolean authenticateFromCSV(String id, String password) {
                try (BufferedReader br = new BufferedReader(new FileReader("user.csv"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] userDetails = line.split(",");
                        if (userDetails.length == 2 && userDetails[0].equals(id) && userDetails[1].equals(password)) {
                            return true;
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return false;
            }

            private boolean authenticateFromTXT(String id, String password) {
                try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] userDetails = line.split(",");
                        if (userDetails.length == 2 && userDetails[0].equals(id) && userDetails[1].equals(password)) {
                            return true;
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                return false;
            }
        };

        loginButton.addActionListener(logActionListener);
        idField.addActionListener(logActionListener);
        pwField.addActionListener(logActionListener);

        JLabel signupLabel = new JLabel("join");
        signupLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupLabel.setForeground(new Color(245,245,220));
        signupLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signupLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                mainFrame.showCard("signup");
            }
        });

        JLabel exitJLabel = new JLabel("Exit");
        exitJLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitJLabel.setForeground(new Color(245,245,220));
        exitJLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent event) {
                System.exit(0);
            }
        });

        add(Box.createVerticalGlue());
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(idLabel);
        add(idField);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(pwLabel);
        add(pwField);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(loginButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(signupLabel);
        add(Box.createVerticalGlue());
    }
}
