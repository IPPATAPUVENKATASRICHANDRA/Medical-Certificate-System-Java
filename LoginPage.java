// package com.rohit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

public class LoginPage extends JFrame implements ActionListener {
    private JLabel userLabel, passwordLabel, imageLabel;
    private JTextField userTextField;
    private JPasswordField passwordField;
    private JButton loginButton, resetButton, createAccountButton;


    public LoginPage() {
        setTitle("Login Page");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Load the image
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("amritalogo.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image scaledImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon imageIcon = new ImageIcon(scaledImage);
        imageLabel = new JLabel(imageIcon);

        userLabel = new JLabel("Username:");
        passwordLabel = new JLabel("Password:");
        userTextField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        resetButton = new JButton("Reset");

        // Set the font size
        Font font = new Font("Arial", Font.PLAIN, 24);
        userLabel.setFont(font);
        passwordLabel.setFont(font);
        userTextField.setFont(font);
        passwordField.setFont(font);
        loginButton.setFont(font);
        resetButton.setFont(font);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;
        c.gridheight = 2;
        panel.add(imageLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.LINE_START;
        c.insets = new Insets(10, 10, 10, 10);
        panel.add(userLabel, c);

        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(userTextField, c);

        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.anchor = GridBagConstraints.LINE_START;
        panel.add(passwordLabel, c);

        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        panel.add(passwordField, c);

        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.fill = GridBagConstraints.NONE;
        panel.add(loginButton, c);

        c.gridx = 3;
        c.gridy = 2;
        panel.add(resetButton, c);



        add(panel, BorderLayout.CENTER);

        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
    }

    public static String name;



    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String user = userTextField.getText();
            String password = new String(passwordField.getPassword());
            name = user;

            // Check if the username and password are correct
            if (authenticate(user, password)) {
                JOptionPane.showMessageDialog(this, "Login successful!");
                if (user.contains("u4")) {
                    student obj = new student(LoginPage.name);
                    obj.setVisible(true);
                } else if (user.contains("doc")) {
                    Doctor obj = new Doctor(name);
                    obj.setVisible(true);
                    dispose();
                }else if(user.contains("adv")){
                    Info_Student obj = new Info_Student();
                    obj.setVisible(true);
                    dispose();
                }else if(user.contains("adm")){
                    CreateAccountFrame obj = new CreateAccountFrame();
                    obj.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid username or password.");
            }
        } else if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }
    }


    private boolean authenticate(String user, String password) {
        // Read the account information from the file
        try {
            File file = new File("accounts.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(user) && parts[1].equals(password)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }



    public static void main(String[] args) {
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }

}

