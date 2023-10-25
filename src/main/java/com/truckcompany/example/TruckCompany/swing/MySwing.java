package com.truckcompany.example.TruckCompany.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.truckcompany.example.TruckCompany.Driver;
import com.truckcompany.example.TruckCompany.Truck;
import com.truckcompany.example.TruckCompany.Requests.ChangeDriverNameRequest;
import com.truckcompany.example.TruckCompany.Requests.NewDriverRequest;

public class MySwing extends JFrame {
    private final Font mainFont = new Font("Arial Bold", Font.BOLD, 18);
    JTextField tfFirstName;

    public void initialize() {

        JLabel lbSelectOptionsBelow = new JLabel("Select from the options below");
        lbSelectOptionsBelow.setFont(mainFont);
        lbSelectOptionsBelow.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel oneTruck = new JLabel();
        oneTruck.setFont(mainFont);

        oneTruck.setText("");
        oneTruck.setHorizontalAlignment(SwingConstants.LEFT);
        tfFirstName = new JTextField();
        tfFirstName.setFont(mainFont);

        // JLabel lbWecome = new JLabel();
        // lbWecome.setFont(mainFont);

        // center part
        JButton showAllTrucks = new JButton("Show All Trucks");
        showAllTrucks.setFont(mainFont);
        showAllTrucks.setPreferredSize(new Dimension(200, 20));

        showAllTrucks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // String firstName = tfFirstName.getText();
                // String lastName = tfLastName.getText();
                // lbWecome.setText("Hello " + firstName);

                // remove();
                // Optional<Driver> driver = driverRepository.findById(new
                // ObjectId("652412cfdab64f43f65c5fb9"));
                // if (driver.isPresent()) {
                // oneTruck.setText(driver.get().getName());
                // }
                // controller.getTruckById("652412cfdab64f43f65c5fb9");

                // !!! for one truck!!!

                // RestTemplate restTemplate = new RestTemplate();
                // ResponseEntity<String> response = restTemplate.getForEntity(
                // "http://localhost:8080/api/v1.0/trucks/65240838dab64f43f65c5f8c",
                // String.class);

                // String responseBody = response.getBody();
                // System.out.println("aici" + responseBody);

                // ObjectMapper objectMapper = new ObjectMapper();
                // try {
                // Truck truck = objectMapper.readValue(responseBody, Truck.class);

                // System.out
                // .println("Received Truck: " + truck.getManufacturer() + " " +
                // truck.getNrOfRegistration());
                // oneTruck.setText(truck.getManufacturer());
                // JFrame allTruckFrame = new JFrame(truck.getManufacturer());
                // allTruckFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                // allTruckFrame.setSize(400, 300);
                // allTruckFrame.setVisible(true);
                // } catch (Exception ex) {
                // ex.printStackTrace();

                // }

                try {

                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<Truck[]> response = restTemplate.getForEntity(
                            "http://localhost:8080/api/v1.0/trucks",
                            Truck[].class);
                    if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                        Truck[] trucks = response.getBody();

                        JFrame allTruckFrame = new JFrame("All Trucks");
                        allTruckFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        allTruckFrame.setLayout(new BorderLayout());

                        String[] columnNames = { "Manufacturer", "Registration Number", "Year", "Drivers" };
                        Object[][] data = new Object[trucks.length][4];

                        for (int i = 0; i < trucks.length; i++) {
                            data[i][0] = trucks[i].getManufacturer();
                            data[i][1] = trucks[i].getNrOfRegistration();
                            data[i][2] = trucks[i].getManufactureYear();

                            StringBuffer driverNames = new StringBuffer();
                            for (Driver driver : trucks[i].getDriverIds()) {
                                driverNames.append(driver.getName()).append(", ");
                            }
                            data[i][3] = driverNames.toString();
                        }

                        JTable table = new JTable(data, columnNames);
                        JScrollPane scrollPane = new JScrollPane(table);

                        allTruckFrame.add(scrollPane, BorderLayout.CENTER);
                        allTruckFrame.setSize(600, 400);
                        allTruckFrame.setVisible(true);

                    } else {
                        oneTruck.setText(
                                "There was an error retreiving the data, status code: " + response.getStatusCode());
                    }
                } catch (Exception ex) {
                    oneTruck.setText(
                            "There was an error retreiving the data");
                }

            }
        });
        JButton showAllDrivers = new JButton("Show All Drivers");
        showAllDrivers.setFont(mainFont);
        showAllDrivers.setPreferredSize(new Dimension(200, 20));

        showAllDrivers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<Driver[]> response = restTemplate.getForEntity(
                            "http://localhost:8080/api/v1.0/drivers/GetAllDrivers",
                            Driver[].class);
                    if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                        Driver[] drivers = response.getBody();

                        JFrame allTruckFrame = new JFrame("All Drivers");
                        allTruckFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        allTruckFrame.setLayout(new BorderLayout());

                        String[] columnNames = { "Name", "Age" };
                        Object[][] data = new Object[drivers.length][2];

                        for (int i = 0; i < drivers.length; i++) {
                            data[i][0] = drivers[i].getName();
                            data[i][1] = drivers[i].getAge();
                        }

                        JTable table = new JTable(data, columnNames);
                        JScrollPane scrollPane = new JScrollPane(table);

                        allTruckFrame.add(scrollPane, BorderLayout.CENTER);
                        allTruckFrame.setSize(600, 400);
                        allTruckFrame.setVisible(true);

                    } else {
                        oneTruck.setText(
                                "There was an error retreiving the data, status code: " + response.getStatusCode());
                    }
                } catch (Exception ex) {
                    oneTruck.setText(
                            "There was an error retreiving the data");
                }

            }
        });
        JButton createNewDriver = new JButton("Create new Driver");
        createNewDriver.setFont(mainFont);
        createNewDriver.setPreferredSize(new Dimension(200, 20));

        createNewDriver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame createDriverFrame = new JFrame("Create New Driver");
                createDriverFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                JPanel createDriverPanel = new JPanel(new GridLayout(4, 2, 5, 5));

                JLabel nameLabel = new JLabel("Name:");
                JTextField nameTextField = new JTextField();

                JLabel ageLabel = new JLabel("Age:");
                JTextField ageTextField = new JTextField();

                JLabel truckIdLabel = new JLabel("Select a truck");

                JComboBox<Truck> truckIdComboBox = new JComboBox<>();

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<Truck[]> responseT = restTemplate.getForEntity(
                        "http://localhost:8080/api/v1.0/trucks",
                        Truck[].class);
                if (responseT.getStatusCode() == HttpStatusCode.valueOf(200)) {
                    Truck[] trucks = responseT.getBody();

                    // truckIdComboBox = new JComboBox<>(trucks);
                    for (Truck truck : trucks) {

                        // truckIdComboBox.addItem(truck.getId() + " " + truck.getManufacturer());
                        truckIdComboBox.addItem(truck);
                    }

                } else {
                    JOptionPane.showMessageDialog(null, "Failed to load trucks.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                JButton createButton = new JButton("Create");
                createButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        String driverName = nameTextField.getText();
                        int driverAge = Integer.parseInt(ageTextField.getText());
                        Truck selectedTruck = (Truck) truckIdComboBox.getSelectedItem();
                        if (driverAge < 18) {
                            JOptionPane.showMessageDialog(null, "Invalid age", "Info",
                                    JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }

                        String requestUrl = "http://localhost:8080/api/v1.0/drivers/new/";

                        NewDriverRequest newDriver = new NewDriverRequest(driverName, driverAge, selectedTruck.getId());

                        try {
                            ResponseEntity<Driver> response = restTemplate.postForEntity(requestUrl, newDriver,
                                    Driver.class);

                            if (response.getStatusCode().is2xxSuccessful()) {

                                System.out.println("Driver created successfully: " + response.getBody());
                                JOptionPane.showMessageDialog(null, "Driver created succesfully", "Success",
                                        JOptionPane.INFORMATION_MESSAGE);

                                createDriverFrame.dispose();
                            } else {

                                JOptionPane.showMessageDialog(null, "Failed to create driver", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                                System.err.println(
                                        "Error creating driver: " + response.getStatusCode() + " - "
                                                + response.getBody());
                            }
                        } catch (Exception exception) {
                            JOptionPane.showMessageDialog(null,
                                    "Failed to create driver" + "Status code" + exception.getMessage(), "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                        createDriverFrame.dispose();
                    }
                });

                createDriverPanel.add(nameLabel);
                createDriverPanel.add(nameTextField);
                createDriverPanel.add(ageLabel);
                createDriverPanel.add(ageTextField);
                createDriverPanel.add(truckIdLabel);
                createDriverPanel.add(truckIdComboBox);

                createDriverPanel.add(createButton);

                createDriverFrame.add(createDriverPanel);
                createDriverFrame.pack();
                createDriverFrame.setVisible(true);
            }
        });

        JButton changeDriverNameButton = new JButton("Change Driver Name");
        changeDriverNameButton.setFont(mainFont);
        changeDriverNameButton.setPreferredSize(new Dimension(200, 20));

        changeDriverNameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame changeDriverNameFrame = new JFrame("Change Driver Name");
                changeDriverNameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                changeDriverNameFrame.setSize(400, 200);
                changeDriverNameFrame.setLayout(new BorderLayout());

                JPanel changeNamePanel = new JPanel();
                changeNamePanel.setLayout(new FlowLayout());

                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<Driver[]> response = restTemplate.getForEntity(
                        "http://localhost:8080/api/v1.0/drivers/GetAllDrivers",
                        Driver[].class);
                if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                    Driver[] drivers = response.getBody();

                    JComboBox<Driver> driverComboBox = new JComboBox<>(drivers);
                    changeNamePanel.add(driverComboBox);

                    JTextField newNameField = new JTextField(20);
                    changeNamePanel.add(newNameField);

                    JCheckBox confirmCheckBox = new JCheckBox("Confirm Name Change");
                    changeNamePanel.add(confirmCheckBox);

                    JButton changeNameButton = new JButton("Change Name");
                    changeNameButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent event) {
                            if (confirmCheckBox.isSelected()) {
                                Driver selectedDriver = (Driver) driverComboBox.getSelectedItem();
                                String newName = newNameField.getText();

                                ChangeDriverNameRequest nameUpdateRequest = new ChangeDriverNameRequest(newName,
                                        selectedDriver.getId());

                                try {
                                    restTemplate.put(
                                            "http://localhost:8080/api/v1.0/drivers/changeName/",
                                            nameUpdateRequest);

                                    JOptionPane.showMessageDialog(null, "Driver's name changed successfully.",
                                            "Success", JOptionPane.INFORMATION_MESSAGE);

                                    changeDriverNameFrame.dispose();

                                } catch (HttpStatusCodeException e) {
                                    JOptionPane.showMessageDialog(null, "HTTP Error: " + e.getRawStatusCode(), "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                } catch (RestClientException e) {
                                    JOptionPane.showMessageDialog(null, "REST Client Error: " + e.getMessage(), "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            } else {
                                JOptionPane.showMessageDialog(null, "Please confirm the name change.", "Error",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    });
                    changeNamePanel.add(changeNameButton);

                    changeDriverNameFrame.add(changeNamePanel, BorderLayout.CENTER);
                    changeDriverNameFrame.setVisible(true);
                }
            }
        });

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 1, 5, 5));
        formPanel.add(lbSelectOptionsBelow);
        // formPanel.add(tfFirstName);
        formPanel.add(oneTruck);
        formPanel.setOpaque(false);

        JPanel buttonsPanel = new JPanel();
        // buttonsPanel.setLayout(new GridLayout(1, 2, 5, 5));
        BoxLayout flowLayout = new BoxLayout(buttonsPanel,
                BoxLayout.Y_AXIS);
        buttonsPanel.setLayout(flowLayout);
        buttonsPanel.add(showAllTrucks);
        buttonsPanel.add(showAllDrivers);
        buttonsPanel.add(createNewDriver);
        buttonsPanel.add(changeDriverNameButton);
        buttonsPanel.setOpaque(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout()); // divide in north west center etc...
        mainPanel.setBackground(new Color(128, 128, 255));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        // mainPanel.add(lbWecome, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);

        add(mainPanel);

        setTitle("Home frame");
        setSize(500, 600);
        setMinimumSize(new Dimension(300, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        // mainPanel.add(lbFirstNime, BorderLayout.NORTH);

    }

}
