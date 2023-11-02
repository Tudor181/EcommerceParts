package com.truckcompany.example.TruckCompany.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.truckcompany.example.TruckCompany.Domain.Category;
import com.truckcompany.example.TruckCompany.Domain.Driver;
import com.truckcompany.example.TruckCompany.Domain.Truck;
import com.truckcompany.example.TruckCompany.Domain.TruckPartInventory;
import com.truckcompany.example.TruckCompany.Requests.AddToCartRequest;
import com.truckcompany.example.TruckCompany.Requests.ChangeDriverNameRequest;
import com.truckcompany.example.TruckCompany.Requests.NewDriverRequest;

public class MySwing extends JFrame {
    private final Font mainFont = new Font("Arial Bold", Font.BOLD, 18);
    private DefaultListModel<Truck> truckListModel = new DefaultListModel<>();
    private JList<Truck> truckList = new JList<>(truckListModel);
    private ArrayList<TruckPartInventory> basket = new ArrayList<>();
    private JList<TruckPartInventory> cartList = new JList<>();

    JTextField tfFirstName;

    public void initialize() {

        JLabel lbSelectOptionsBelow = new JLabel("Select from the options below");
        lbSelectOptionsBelow.setFont(mainFont);
        lbSelectOptionsBelow.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel error = new JLabel();
        error.setFont(mainFont);

        error.setText("");
        error.setHorizontalAlignment(SwingConstants.LEFT);
        tfFirstName = new JTextField();
        tfFirstName.setFont(mainFont);

        JPanel truckListPanel = new JPanel(new BorderLayout());

        // Create a title label
        JLabel titleLabel = new JLabel("Truck List");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));

        JScrollPane truckScrollPane = new JScrollPane(truckList);
        int screenHeight = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
        int scrollHeight = (int) (0.6 * (screenHeight - 120));
        truckList.addListSelectionListener(new TruckSelectionListener());

        truckListPanel.add(titleLabel, BorderLayout.NORTH);

        truckListPanel.add(truckScrollPane, BorderLayout.CENTER);
        // truckList.setVisible(false);
        // truckScrollPane.setVisible(false);

        // JLabel lbWecome = new JLabel();
        // lbWecome.setFont(mainFont);

        // center part
        JButton showAllTrucks = new JButton("Show All Trucks");
        showAllTrucks.setFont(mainFont);
        showAllTrucks.setPreferredSize(new Dimension(200, 20));

        showAllTrucks.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    RestTemplate restTemplate = new RestTemplate();
                    ResponseEntity<Truck[]> response = restTemplate.getForEntity(
                            "http://localhost:8080/api/v1.0/trucks",
                            Truck[].class);
                    if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                        Truck[] trucks = response.getBody();

                        truckListModel.clear();
                        for (Truck truck : trucks) {
                            truckListModel.addElement(truck);
                        }

                        truckList.setCellRenderer(new TruckListCellRenderer());
                        truckScrollPane.setPreferredSize(new Dimension(400, scrollHeight));
                        truckList.setFixedCellHeight(40); // Adjust the row height as needed
                        truckList.setOpaque(false);
                        truckScrollPane.setOpaque(false);

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
                        // allTruckFrame.setVisible(true);

                    } else {
                        error.setText(
                                "There was an error retreiving the data, status code: " + response.getStatusCode());
                    }
                } catch (Exception ex) {
                    error.setText(
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

                        JFrame allDriversFrame = new JFrame("All Drivers");
                        allDriversFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        allDriversFrame.setLayout(new BorderLayout());

                        String[] columnNames = { "Name", "Age" };
                        Object[][] data = new Object[drivers.length][2];

                        for (int i = 0; i < drivers.length; i++) {
                            data[i][0] = drivers[i].getName();
                            data[i][1] = drivers[i].getAge();
                        }

                        JTable table = new JTable(data, columnNames);
                        JScrollPane scrollPane = new JScrollPane(table);

                        allDriversFrame.add(scrollPane, BorderLayout.CENTER);
                        allDriversFrame.setSize(600, 400);
                        allDriversFrame.setVisible(true);

                    } else {
                        error.setText(
                                "There was an error retreiving the data, status code: " + response.getStatusCode());
                    }
                } catch (Exception ex) {
                    error.setText(
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

                    for (Truck truck : trucks) {

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

        // Create "Show Cart" button
        JButton showCartButton = new JButton("Show Cart");
        showCartButton.setFont(mainFont);
        showCartButton.setPreferredSize(new Dimension(200, 20));
        DefaultListModel<TruckPartInventory> cartListModel = new DefaultListModel<>();
        cartList = new JList<>(cartListModel);

        showCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showCartFrame(cartListModel);
            }
        });

        // Add "Show Cart" button to your buttonsPanel

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(3, 1, 5, 5));
        formPanel.add(lbSelectOptionsBelow);

        formPanel.setOpaque(false);

        JPanel buttonsPanel = new JPanel();

        BoxLayout flowLayout = new BoxLayout(buttonsPanel,
                BoxLayout.Y_AXIS);
        buttonsPanel.setLayout(flowLayout);
        buttonsPanel.add(showAllTrucks);
        buttonsPanel.add(showAllDrivers);
        buttonsPanel.add(createNewDriver);
        buttonsPanel.add(changeDriverNameButton);
        buttonsPanel.add(showCartButton);
        buttonsPanel.add(error);

        buttonsPanel.add(truckListPanel);
        buttonsPanel.setOpaque(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(128, 128, 255));
        mainPanel.add(formPanel, BorderLayout.NORTH);

        mainPanel.add(buttonsPanel, BorderLayout.CENTER);

        add(mainPanel);

        setTitle("Home frame");
        setSize(500, 600);
        setMinimumSize(new Dimension(300, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);

    }

    private class TruckSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                Truck selectedTruck = truckList.getSelectedValue();

                if (selectedTruck != null) {
                    openTruckInfoDialog(selectedTruck);
                }
            }
        }
    }

    private void showCartFrame(DefaultListModel<TruckPartInventory> cartListModel) {
        JFrame cartFrame = new JFrame("Shopping Cart");
        cartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cartFrame.setSize(400, 400);

        JPanel cartPanel = new JPanel(new BorderLayout());

        JLabel cartLabel = new JLabel("Your Shopping Cart");
        cartLabel.setFont(mainFont);
        cartLabel.setHorizontalAlignment(SwingConstants.CENTER);

        cartPanel.add(cartLabel, BorderLayout.NORTH);

        JScrollPane cartScrollPane = new JScrollPane(cartList);
        cartPanel.add(cartScrollPane, BorderLayout.CENTER);

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setFont(mainFont);
        checkoutButton.setPreferredSize(new Dimension(200, 20));

        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        cartPanel.add(checkoutButton, BorderLayout.SOUTH);

        cartFrame.add(cartPanel);
        cartFrame.setVisible(true);
    }

    private void openTruckInfoDialog(Truck truck) {
        JDialog dialog = new JDialog(this, "Truck Information", true);
        dialog.setLayout(new BorderLayout());
        int dialogX = this.getX() + this.getWidth(); // X position: Right edge of the main frame
        int dialogY = this.getY(); // Y position: Top edge of the main frame

        dialog.setLocation(dialogX, dialogY);
        JPanel infoPanel = new JPanel(new GridLayout(5, 2));

        addInfoRow(infoPanel, "Manufacturer:", truck.getManufacturer());
        addInfoRow(infoPanel, "Registration Number:", truck.getNrOfRegistration());
        addInfoRow(infoPanel, "Year:", Integer.toString(truck.getManufactureYear()));

        StringBuilder driversText = new StringBuilder();
        for (Driver driver : truck.getDriverIds()) {
            driversText.append(driver.getName()).append(", ");
        }

        addInfoRow(infoPanel, "Drivers:", driversText.toString());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JButton searchPartsButton = new JButton("Search Parts for this Truck");
        searchPartsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openPartsSearchFrame(truck, dialog);
                dialog.dispose();
            }
        });

        buttonPanel.add(searchPartsButton);

        infoPanel.add(new JPanel());
        infoPanel.add(buttonPanel);

        dialog.add(infoPanel, BorderLayout.CENTER);

        dialog.setSize(400, 200);
        dialog.setVisible(true);
    }

    private void addInfoRow(JPanel panel, String label, String value) {
        panel.add(new JLabel(label));
        panel.add(new JLabel(value));
    }

    private Category[] getCategories() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<Category[]> response = restTemplate.getForEntity(
                    "http://localhost:8080/categories",
                    Category[].class);
            if (response.getStatusCode() == HttpStatusCode.valueOf(200)) {
                Category[] categories = response.getBody();
                return categories;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;

    }

    private void openPartsSearchFrame(Truck truck, JDialog dialog) {
        JFrame partsSearchFrame = new JFrame("Search Parts for Truck selected");
        partsSearchFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        partsSearchFrame.setSize(650, 400);
        partsSearchFrame.setResizable(false);
        int partsDialogX = dialog.getX(); // X position: Same as Truck Information dialog
        int partsDialogY = dialog.getY() + dialog.getHeight();
        partsSearchFrame.setLocation(partsDialogX, partsDialogY);

        JPanel filterPanel = new JPanel(new FlowLayout());

        JLabel filterLabel = new JLabel("Select a Category:");

        Category[] categories = getCategories();

        if (categories != null) {

            JComboBox<Category> categoryComboBox = new JComboBox<>();
            for (Category category : categories) {
                categoryComboBox.addItem(category);
            }

            filterPanel.add(filterLabel);
            filterPanel.add(categoryComboBox);

            categoryComboBox.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Category selectedCategory = (Category) categoryComboBox.getSelectedItem();
                    updatePartsTable(truck, selectedCategory, partsSearchFrame);
                }
            });

            JPanel searchPanel = new JPanel(new BorderLayout());
            searchPanel.add(filterPanel, BorderLayout.NORTH);

            partsSearchFrame.add(searchPanel);
            partsSearchFrame.setVisible(true);
            updatePartsTable(truck, categoryComboBox.getItemAt(0), partsSearchFrame);
        } else {
            JOptionPane.showMessageDialog(null, "There was an unexpected error getting the categories", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private TruckPartInventory[] parts;

    private TruckPartInventory[] getPartsForTruckAndCategory(Truck truck, Category category) {
        RestTemplate restTemplate = MyRestTemplate.getRestTemplate();
        try {
            // ResponseEntity<Driver[]> response = restTemplate.getForEntity(
            // "http://localhost:8080/api/v1.0/drivers/GetAllDrivers",
            // Driver[].class);
            // "http://localhost:8080/truckpartinventory/category/" + category.getId() +
            // "/truck/" + truck.getId(),
            ResponseEntity<TruckPartInventory[]> response2 = restTemplate.getForEntity(
                    "http://localhost:8080/truckpartinventory/category/" + category.getId()
                            + "/truck/" + truck.getId(),
                    TruckPartInventory[].class);

            if (response2.getStatusCode() == HttpStatusCode.valueOf(200)) {
                TruckPartInventory[] parts = response2.getBody();
                if (parts != null && parts.length > 0) {
                    return parts;
                } else
                    return null;

            }

        } catch (Exception e) {
            return null;
        }
        return null;

    }

    private void updatePartsTable(Truck truck, Category category, JFrame partsSearchFrame) {

        parts = getPartsForTruckAndCategory(truck, category);

        if (parts != null) {
            JFrame partsFilteredFrame = new JFrame("Parts for this Truck and category selected");
            partsFilteredFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            partsFilteredFrame.setSize(600, 400);

            Point partsSearchFrameLocation = partsSearchFrame.getLocationOnScreen();
            int partsFilteredFrameX = partsSearchFrameLocation.x;
            int partsFilteredFrameY = partsSearchFrameLocation.y + partsSearchFrame.getHeight();
            partsFilteredFrame.setLocation(partsFilteredFrameX, partsFilteredFrameY);

            partsFilteredFrame.addWindowFocusListener(new WindowAdapter() {
                @Override
                public void windowLostFocus(WindowEvent e) {
                    partsFilteredFrame.dispose();
                }
            });

            Object[][] t = new Object[parts.length][3];

            for (int i = 0; i < parts.length; i++) {
                t[i][0] = parts[i].getName();
                t[i][1] = parts[i].getAmount();
                t[i][2] = "Add to Basket";
            }
            DefaultTableModel dm = new DefaultTableModel();
            String[] columnNames = { "Part Name", "Amount in stock", "Action" };
            dm.setDataVector(t, columnNames);
            JTable partsTable = new JTable(dm);

            partsTable.getColumn("Action").setCellRenderer(new ButtonRenderer());
            partsTable.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

            partsFilteredFrame.add(partsTable);
            partsFilteredFrame.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "There was an unexpected error getting the parts", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addToBasket(int rowIndex) {
        if (rowIndex >= 0 && rowIndex < parts.length) {
            TruckPartInventory selectedPart = parts[rowIndex];
            // basket.add(selectedPart);

            RestTemplate restTemplate = MyRestTemplate.getRestTemplate();

            AddToCartRequest addToCartRequest = new AddToCartRequest("6543c379f8e0cb3f68a16f20", selectedPart.getId());

            String addToCartUrl = "http://localhost:8080/user/AddToCart/" + "6543c379f8e0cb3f68a16f20" + '/'
                    + selectedPart.getId(); // trebuie userID
            System.out.println("sa vedem" + addToCartUrl);
            try {
                ResponseEntity<Boolean> response = restTemplate.postForEntity(addToCartUrl, null, Boolean.class);

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() == true) {
                    // Item added to the cart successfully
                    basket.add(selectedPart);
                    JOptionPane.showMessageDialog(this, "Added '" + selectedPart.getName() + "' to the basket.");
                } else if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
                    JOptionPane.showMessageDialog(this, "Failed to add item to the cart. Bad request.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else if (response.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                    JOptionPane.showMessageDialog(this, "Failed to add item to the cart. Internal server error.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add item to the cart.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (HttpStatusCodeException e) {
                JOptionPane.showMessageDialog(this, "HTTP Error: " + e.getRawStatusCode(), "Error",
                        JOptionPane.ERROR_MESSAGE);
                System.out.println("EXCEP{TION" + e);
            } catch (RestClientException e) {
                JOptionPane.showMessageDialog(this, "REST Client Error: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            // System.out.println("se adauga in cos" + selectedPart);
            // JOptionPane.showMessageDialog(this, "Added '" + selectedPart.getName() + "'
            // to the basket.");
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {

        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            if (isSelected) {
                setForeground(table.getSelectionForeground());
                setBackground(table.getSelectionBackground());
            } else {
                setForeground(table.getForeground());
                setBackground(UIManager.getColor("Button.background"));
            }
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;

        private String label;

        private boolean isPushed;
        private int clickedRow;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            if (isSelected) {
                button.setForeground(table.getSelectionForeground());
                button.setBackground(table.getSelectionBackground());
            } else {
                button.setForeground(table.getForeground());
                button.setBackground(table.getBackground());
            }
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            clickedRow = row;
            return button;
        }

        public Object getCellEditorValue() {
            if (isPushed) {
                // JOptionPane.showMessageDialog(button, label + ": Ouch!");
                addToBasket(clickedRow);
            }
            isPushed = false;
            return new String(label);
        }

        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

}
