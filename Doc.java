// package com.rohit;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Doc extends JFrame {
    private JTextField searchTextField;
    private DefaultTableModel tableModel;
    private JLabel countLabel;

    public Doc() {
        setTitle("Student Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create the GUI
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BorderLayout());
        searchPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel searchLabel = new JLabel("Enter the name to search:");
        searchLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        searchTextField = new JTextField(20);
        searchTextField.setFont(new Font("Arial", Font.PLAIN, 14));
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        searchButton.addActionListener(new SearchButtonListener());

        searchPanel.add(searchLabel, BorderLayout.NORTH);
        searchPanel.add(searchTextField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // Result Panel
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BorderLayout());
        resultPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        tableModel = new DefaultTableModel(new Object[]{"Name", "REMARKS", "Attending Doctor", "Leave Required"}, 0);
        JTable resultTable = new JTable(tableModel);
        resultTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultTable);

        resultPanel.add(scrollPane, BorderLayout.CENTER);

        // Count Panel
        JPanel countPanel = new JPanel();
        countPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        countLabel = new JLabel();
        countLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        countPanel.add(countLabel);

        // Add panels to main panel
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel, BorderLayout.CENTER);
        mainPanel.add(countPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nameToSearch = searchTextField.getText();

            int nameCount = 0;
            int leaveCount = 0;
            List<StudentRecord> studentRecords = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\rohit\\OneDrive\\Documents\\IdeaProjects\\untitled\\src\\com\\rohit\\MCS.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("Name:")) {
                        String studentName = line.substring(line.indexOf(":") + 1).trim();
                        if (studentName.equalsIgnoreCase(nameToSearch)) {
                            nameCount++;
                            String REMARKS = "";
                            String attendingDoctor = "";
                            boolean leaveRequired = false;
                            while ((line = br.readLine()) != null && !line.startsWith("NAME:")) {
                                if (line.startsWith("REMARKS:")) {
                                    REMARKS = line.substring(line.indexOf(":") + 1).trim();
                                } else if (line.startsWith("Attending Doctor:")) {
                                    attendingDoctor = line.substring(line.indexOf(":") + 1).trim();
                                } else if (line.startsWith("Leave Required:")) {
                                    leaveRequired = Boolean.parseBoolean(line.substring(line.indexOf(":") + 1).trim());
                                }
                            }
                            studentRecords.add(new StudentRecord(studentName, REMARKS, attendingDoctor, leaveRequired));
                            if (leaveRequired) {
                                leaveCount++;
                            }
                        }
                    }
                }

                if (nameCount > 0) {
                    tableModel.setRowCount(0);
                    for (StudentRecord record : studentRecords) {
                        Vector<String> row = new Vector<>();
                        row.add(record.getStudentName());
                        row.add(record.getREMARKS());
                        row.add(record.getAttendingDoctor());
                        row.add(String.valueOf(record.isLeaveRequired()));
                        tableModel.addRow(row);
                    }
                    countLabel.setText("Number of students requiring leave: " + leaveCount);
                } else {
                    tableModel.setRowCount(0);
                    countLabel.setText("");
                    JOptionPane.showMessageDialog(Doc.this, "Name not found.", "Student Search", JOptionPane.INFORMATION_MESSAGE);
                }

                System.out.println("Search completed successfully.");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Doc.this, "Error occurred while reading the file.", "Student Search", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class StudentRecord {
        private String studentName;
        private String REMARKS;
        private String attendingDoctor;
        private boolean leaveRequired;

        public StudentRecord(String studentName, String REMARKS, String attendingDoctor, boolean leaveRequired) {
            this.studentName = studentName;
            this.REMARKS = REMARKS;
            this.attendingDoctor = attendingDoctor;
            this.leaveRequired = leaveRequired;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getREMARKS() {
            return REMARKS;
        }

        public String getAttendingDoctor() {
            return attendingDoctor;
        }

        public boolean isLeaveRequired() {
            return leaveRequired;
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Doc().setVisible(true);
            }
        });
    }
}
