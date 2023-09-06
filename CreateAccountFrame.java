// package com.rohit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;

public class CreateAccountFrame extends JFrame {

    public CreateAccountFrame() {
        setTitle("Create Account");
        setSize(700, 350);
        setLocationRelativeTo(null);

        JPanel createAccountPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel createAccountLabel = new JLabel("Enter your username and password to create a new account:");
        JTextField createAccountUserTextField = new JTextField();
        JPasswordField createAccountPasswordField = new JPasswordField();
        JButton createAccountButton = new JButton("Create Account");

        // Set the font size
        Font font = new Font("Arial", Font.PLAIN, 18);
        createAccountLabel.setFont(font);
        createAccountUserTextField.setFont(font);
        createAccountPasswordField.setFont(font);
        createAccountButton.setFont(font);

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(10, 10, 10, 10);
        createAccountPanel.add(createAccountLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        createAccountPanel.add(new JLabel("Username:"), c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        createAccountPanel.add(createAccountUserTextField, c);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        createAccountPanel.add(new JLabel("Password:"), c);

        c.gridx = 1;
        c.gridy = 2;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        createAccountPanel.add(createAccountPasswordField, c);

        c.gridx = 1;
        c.gridy = 3;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        createAccountPanel.add(createAccountButton, c);

        createAccountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user = createAccountUserTextField.getText();
                String password = new String(createAccountPasswordField.getPassword());

                // Save the account information to a file
                try {
                    FileWriter writer = new FileWriter("accounts.txt", true);
                    writer.write(user + "," + password + '\n');
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                JOptionPane.showMessageDialog(CreateAccountFrame.this, "Account created successfully!");
                dispose();
            }
        });

        add(createAccountPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CreateAccountFrame();
        });
    }
}
