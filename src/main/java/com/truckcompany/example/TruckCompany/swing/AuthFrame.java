package com.truckcompany.example.TruckCompany.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.truckcompany.example.TruckCompany.Domain.User;

import javax.swing.BoxLayout;

public class AuthFrame extends JFrame {
    public void initialize() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        // mainPanel.setAlignmentX(10f);// Set vertical layout
        mainPanel.add(Box.createVerticalStrut(10));

        JTextField emailField = new JTextField(20);
        emailField.setText("Enter email");
        emailField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (emailField.getText().equals("Enter email")) {
                    emailField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (emailField.getText().isEmpty()) {
                    emailField.setText("Enter email");
                }
            }
        });

        mainPanel.add(new JLabel("Email: "));
        mainPanel.add(emailField);
        mainPanel.add(Box.createVerticalStrut(10));
        JPasswordField passwordField = new JPasswordField(20);
        // passwordField.setText("Enter password");
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                // if (passwordField.getText().equals("Enter password")) {
                // passwordField.setText("");
                // }
                passwordField.setBorder(new LineBorder(Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent e) {
                // if (passwordField.getText().isEmpty()) {
                // passwordField.setText("Enter password");
                // }
                passwordField.setBorder(UIManager.getBorder("TextField.border")); // Reset the border to default
            }
        });
        mainPanel.add(new JLabel("Password: "));
        mainPanel.add(passwordField);

        // Create and style the "Login" button
        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(0.5f);
        loginButton.setForeground(Color.BLACK); // Set text color
        loginButton.setBackground(new Color(0, 102, 204)); // Set background color
        loginButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font
        mainPanel.add(loginButton);

        // Create and style the "Register" button
        JButton registerButton = new JButton("Register");
        registerButton.setAlignmentX(0.5f);
        registerButton.setForeground(Color.BLACK); // Set text color
        registerButton.setBackground(new Color(0, 153, 0)); // Set background color
        registerButton.setFont(new Font("Arial", Font.PLAIN, 16)); // Set font
        mainPanel.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create a modal dialog
                String question = "Are you sure you want to log in?";
                int choice = JOptionPane.showConfirmDialog(AuthFrame.this, question, "Login Confirmation",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (choice == JOptionPane.YES_OPTION) {
                    JOptionPane.showMessageDialog(null, "You have selected to log in!", "Result",
                            JOptionPane.INFORMATION_MESSAGE);
                    // loginUser(emailField.getText(), String.valueOf(passwordField.getPassword()));
                    dispose();
                    MySwing mySwing = new MySwing();
                    mySwing.initialize();
                    // Handle 'Yes' button action
                    // You can add your login logic here
                    System.out.println("User clicked 'Yes' to log in.");
                } else if (choice == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(null, "You have selected not to log in!", "Result",
                            JOptionPane.WARNING_MESSAGE);
                    System.out.println("User clicked 'No' to cancel login.");
                } else {
                    JOptionPane.showMessageDialog(null, "You have selected cancel!", "Result",
                            JOptionPane.ERROR_MESSAGE);
                    System.out.println("User clicked 'Cancel'.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Create and display the RegisterForm
                RegisterForm registerForm = new RegisterForm();
                registerForm.initialize();
                dispose(); // Close the current login form
            }
        });

        add(mainPanel);

        setTitle("LOGIN");
        setMinimumSize(new Dimension(300, 150));
        // setMaximumSize(get);
        setResizable(false);
        pack();
        setLocationRelativeTo(null); // Center the frame on the screen
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void loginUser(String name, String password) {
        try {
            // Create a RestTemplate
            // RestTemplate restTemplate = new RestTemplate();
            RestTemplate restTemplate = MyRestTemplate.getRestTemplate();

            String registerEndpoint = "http://localhost:8080/user/register/";
            User u = new User();
            // Create an HttpEntity with the user object and headers
            HttpEntity<User> request = new HttpEntity<>(u);

            // Make the HTTP POST request and get the response
            ResponseEntity<Void> response = restTemplate.postForEntity(registerEndpoint, request, Void.class);

            // Check the response status code and handle accordingly
            if (response.getStatusCode().is2xxSuccessful()) {
                JOptionPane.showMessageDialog(this, "Login successful!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                MySwing mySwing = new MySwing();
                mySwing.initialize();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Login failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
