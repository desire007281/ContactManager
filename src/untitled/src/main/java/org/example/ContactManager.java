package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ContactManager {
    private JFrame frame;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private ArrayList<Contact> contacts;
    private DefaultListModel<String> listModel;
    private JList<String> contactList;

    public ContactManager() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        frame = new JFrame("Contact Manager");
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        contacts = new ArrayList<>();
        listModel = new DefaultListModel<>();
        contactList = new JList<>(listModel);

        mainPanel.setLayout(cardLayout);
        mainPanel.add(createListView(), "ListView");
        mainPanel.add(createFormView(), "FormView");
        mainPanel.add(createDetailsView(), "DetailsView");

        frame.add(mainPanel);
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private JPanel createListView() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 230, 250)); // Light lavender background
        JButton addButton = new JButton("Add New Contact");
        JButton viewButton = new JButton("View Details");

        addButton.setBackground(new Color(100, 149, 237)); // Cornflower Blue
        addButton.setForeground(Color.WHITE);
        viewButton.setBackground(new Color(60, 179, 113)); // Medium Sea Green
        viewButton.setForeground(Color.WHITE);

        addButton.addActionListener(e -> cardLayout.show(mainPanel, "FormView"));
        viewButton.addActionListener(e -> showDetailsView());

        panel.add(new JScrollPane(contactList), BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createFormView() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 239, 204)); // Light Peach background
        JPanel inputPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        inputPanel.setBackground(new Color(255, 239, 204));

        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel phonePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));

        namePanel.setBackground(new Color(255, 239, 204));
        phonePanel.setBackground(new Color(255, 239, 204));
        emailPanel.setBackground(new Color(255, 239, 204));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(new Color(34, 34, 34)); // Dark grey text
        JTextField nameField = new JTextField(8);
        namePanel.add(nameLabel);
        namePanel.add(nameField);

        JLabel phoneLabel = new JLabel("Phone:");
        phoneLabel.setForeground(new Color(34, 34, 34));
        JTextField phoneField = new JTextField(8);
        phonePanel.add(phoneLabel);
        phonePanel.add(phoneField);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(new Color(34, 34, 34));
        JTextField emailField = new JTextField(8);
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);

        inputPanel.add(namePanel);
        inputPanel.add(phonePanel);
        inputPanel.add(emailPanel);

        JButton saveButton = new JButton("Save Contact");
        JButton cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        saveButton.setBackground(new Color(100, 149, 237));
        saveButton.setForeground(Color.WHITE);
        cancelButton.setBackground(new Color(220, 20, 60)); // Crimson
        cancelButton.setForeground(Color.WHITE);

        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            if (!name.isEmpty() && !phone.isEmpty() && !email.isEmpty()) {
                contacts.add(new Contact(name, phone, email));
                listModel.addElement(name);
                cardLayout.show(mainPanel, "ListView");
            } else {
                JOptionPane.showMessageDialog(frame, "All fields must be filled.");
            }
        });

        cancelButton.addActionListener(e -> cardLayout.show(mainPanel, "ListView"));

        panel.add(inputPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createDetailsView() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(240, 248, 255)); // Alice Blue background
        JLabel detailsLabel = new JLabel("Select a contact to view details.");
        detailsLabel.setForeground(new Color(34, 34, 34)); // Dark grey text
        JButton backButton = new JButton("Back to List");

        backButton.setBackground(new Color(60, 179, 113));
        backButton.setForeground(Color.WHITE);

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "ListView"));

        panel.add(detailsLabel, BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    private void showDetailsView() {
        int selectedIndex = contactList.getSelectedIndex();
        if (selectedIndex != -1) {
            Contact contact = contacts.get(selectedIndex);
            JOptionPane.showMessageDialog(frame, "Name: " + contact.getName() + "Phone: " + contact.getPhone() + "\nEmail: " + contact.getEmail());
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a contact.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ContactManager::new);
    }
}

class Contact {
    private String name;
    private String phone;
    private String email;

    public Contact(String name, String phone, String email) {
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public String getName() { return name; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
}