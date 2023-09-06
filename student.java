// package com.rohit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class student extends JFrame {
    private DefaultTableModel tableModel;
    private String name;

    public student(String name) {
        this.name = name;
        setTitle("Student Data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create the GUI
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Result Panel
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        tableModel = new DefaultTableModel(new Object[]{"Name", "Date"}, 0);
        JTable resultTable = new JTable(tableModel);
        resultTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultTable);

        resultPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to main panel
        mainPanel.add(resultPanel, BorderLayout.CENTER);

        add(mainPanel);

        loadDataFromFile();
    }

    private void loadDataFromFile() {
        int leaveCount = 0;
        List<String> leaveDates = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\rohit\\OneDrive\\Documents\\IdeaProjects\\untitled\\src\\com\\rohit\\MCS.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Roll Number:")) {
                    String studentNumber = line.substring(line.indexOf(":") + 1).trim();
                    if (studentNumber.equalsIgnoreCase(name)) {
                        while ((line = br.readLine()) != null && !line.startsWith("Doctor name:")) {
                            if(line.startsWith("Name:")){
                                name = line.substring(line.indexOf(":")+1);
                            }
                            if (line.startsWith("Date:")) {
                                String date = line.substring(line.indexOf(":") + 1).trim();
                                leaveDates.add(date);
                                leaveCount++;
                            }
                        }
                    }
                }
            }

            if (leaveCount > 0) {
                tableModel.setRowCount(0);
                for (String date : leaveDates) {
                    Vector<String> row = new Vector<>();
                    row.add(name);
                    row.add(date);
                    tableModel.addRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No data found for the given name.", "Student Data", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error occurred while reading the file.", "Student Data", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }
}
