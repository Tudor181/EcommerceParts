package com.truckcompany.example.TruckCompany.swing;

import javax.swing.*;
import javax.swing.border.LineBorder;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.truckcompany.example.TruckCompany.Domain.User;

import io.swagger.v3.oas.models.media.MediaType;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.net.http.HttpHeaders;

// import org.apache.http.HttpEntity;
// import org.apache.http.HttpResponse;
// import org.apache.http.client.methods.HttpPost;
// import org.apache.http.impl.client.CloseableHttpClient;
// import org.apache.http.impl.client.HttpClients;
// import org.apache.http.util.EntityUtils;

public class RegisterForm extends JFrame {
    public void initialize() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

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

        mainPanel.add(createFieldPanel("Email: ", emailField));

        JTextField nameField = new JTextField(20);
        nameField.setText("Enter name");
        nameField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (nameField.getText().equals("Enter name")) {
                    nameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (nameField.getText().isEmpty()) {
                    nameField.setText("Enter name");
                }
            }
        });

        mainPanel.add(createFieldPanel("Name: ", nameField));

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passwordField.setBorder(new LineBorder(Color.BLUE));
            }

            @Override
            public void focusLost(FocusEvent e) {
                passwordField.setBorder(UIManager.getBorder("TextField.border"));
            }
        });

        mainPanel.add(createFieldPanel("Password: ", passwordField));

        mainPanel.add(Box.createVerticalStrut(10));

        JButton registerButton = new JButton("Register");
        registerButton.setAlignmentX(0.5f);
        mainPanel.add(registerButton);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get user input
                String email = emailField.getText();
                String password = String.valueOf(passwordField.getPassword());
                String name = nameField.getText();

                // Create a user object
                User user = new User();
                user.setEmail(email);
                user.setPassword(password);
                user.setName(name);

                // Register the user
                registerUser(user);
            }
        });

        add(mainPanel);

        setTitle("Registration Form");
        setMinimumSize(new Dimension(300, 200));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private JPanel createFieldPanel(String label, JComponent field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel(label));
        panel.add(Box.createRigidArea(new Dimension(10, 0))); // Add horizontal space between label and field
        panel.add(field);
        return panel;
    }

    private void registerUser(User user) {
        try {
            // Create a RestTemplate
            // RestTemplate restTemplate = new RestTemplate();
            RestTemplate restTemplate = MyRestTemplate.getRestTemplate();

            String registerEndpoint = "http://localhost:8080/user/register/";

            // Create an HttpEntity with the user object and headers
            HttpEntity<User> request = new HttpEntity<>(user);

            // Make the HTTP POST request and get the response
            ResponseEntity<Void> response = restTemplate.postForEntity(registerEndpoint, request, Void.class);

            // Check the response status code and handle accordingly
            if (response.getStatusCode().is2xxSuccessful()) {
                JOptionPane.showMessageDialog(this, "Registration successful!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);

                MySwing mySwing = new MySwing();
                mySwing.initialize();
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
