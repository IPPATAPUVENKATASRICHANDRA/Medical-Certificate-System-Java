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

public class Info_Student extends JFrame {
    private JTextField searchTextField;
    private DefaultTableModel tableModel;

    public Info_Student() {
        setTitle("Student Search");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 1000);
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

        tableModel = new DefaultTableModel(new Object[]{"Name", "Department", "Roll Number", "Class Advisor", "Remarks", "Date", "Attending Doctor", "Leave Required (Days)"}, 0);
        JTable resultTable = new JTable(tableModel);
        resultTable.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultTable);

        resultPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to main panel
        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(resultPanel, BorderLayout.CENTER);

        add(mainPanel);

        loadDataFromFile(); // Load data from file when the application loads
    }

    private void loadDataFromFile() {
        List<StudentRecord> studentRecords = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\rohit\\OneDrive\\Documents\\IdeaProjects\\untitled\\src\\com\\rohit\\MCS.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Doctor name:")) {
                    String studentName = "";
                    String department = "";
                    String rollNumber = "";
                    String classAdvisor = "";
                    String remarks = "";
                    String date = "";
                    String attendingDoctor = line.substring(line.indexOf(":")+1);
                    int leaveRequired = 0;
                    while ((line = br.readLine()) != null) {
                        if (line.startsWith("Department:")) {
                            department = line.substring(line.indexOf(":") + 1).trim();
                        } else if (line.startsWith("Roll Number:")) {
                            rollNumber = line.substring(line.indexOf(":") + 1).trim();
                        } else if (line.startsWith("Class Advisor:")) {
                            classAdvisor = line.substring(line.indexOf(":") + 1).trim();
                        } else if (line.startsWith("Remarks:")) {
                            remarks = line.substring(line.indexOf(":") + 1).trim();
                        } else if (line.startsWith("Date:")) {
                            date = line.substring(line.indexOf(":") + 1).trim();
                        } else if (line.startsWith("Doctor name: ")) {
                            attendingDoctor = line.substring(line.indexOf(":") + 1).trim();
                        } else if (line.startsWith("Leave Required:")) {
                            leaveRequired = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                        } else if (line.startsWith("Name:")) {
                            // Add the current student record to the list
                            studentRecords.add(new StudentRecord(studentName, department, rollNumber, classAdvisor, remarks, date, attendingDoctor, leaveRequired));

                            // Start processing the next student record
                            studentName = line.substring(line.indexOf(":") + 1).trim();
                            department = "";
                            rollNumber = "";
                            classAdvisor = "";
                            remarks = "";
                            date = "";
                            attendingDoctor = "";
                            leaveRequired = 0;
                        }
                    }
                    // Add the last student record to the list
                    studentRecords.add(new StudentRecord(studentName, department, rollNumber, classAdvisor, remarks, date, attendingDoctor, leaveRequired));
                }
            }

            if (!studentRecords.isEmpty()) {
                tableModel.setRowCount(0);
                for (StudentRecord record : studentRecords) {
                    Vector<String> row = new Vector<>();
                    row.add(record.getStudentName());
                    row.add(record.getDepartment());
                    row.add(record.getRollNumber());
                    row.add(record.getClassAdvisor());
                    row.add(record.getRemarks());
                    row.add(record.getDate());
                    row.add(record.getAttendingDoctor());
                    row.add(String.valueOf(record.getLeaveRequired()));
                    tableModel.addRow(row);
                }
            } else {
                JOptionPane.showMessageDialog(Info_Student.this, "No student records found.", "Student Search", JOptionPane.INFORMATION_MESSAGE);
            }

            System.out.println("Data loaded successfully.");
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(Info_Student.this, "Error occurred while reading the file.", "Student Search", JOptionPane.ERROR_MESSAGE);
        }
    }


    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nameToSearch = searchTextField.getText();

            int nameCount = 0;
            List<StudentRecord> studentRecords = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\rohit\\OneDrive\\Documents\\IdeaProjects\\untitled\\src\\com\\rohit\\MCS.txt"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("Name:")) {
                        String studentName = line.substring(line.indexOf(":") + 1).trim();
                        if (studentName.equalsIgnoreCase(nameToSearch)) {
                            nameCount++;
                            String department = "";
                            String rollNumber = "";
                            String classAdvisor = "";
                            String remarks = "";
                            String date = "";
                            String attendingDoctor = "";
                            int leaveRequired = 0;
                            while ((line = br.readLine()) != null && !line.startsWith("NAME:")) {
                                if (line.startsWith("Department:")) {
                                    department = line.substring(line.indexOf(":") + 1).trim();
                                } else if (line.startsWith("Roll Number:")) {
                                    rollNumber = line.substring(line.indexOf(":") + 1).trim();
                                } else if (line.startsWith("Class Advisor:")) {
                                    classAdvisor = line.substring(line.indexOf(":") + 1).trim();
                                } else if (line.startsWith("Remarks:")) {
                                    remarks = line.substring(line.indexOf(":") + 1).trim();
                                } else if (line.startsWith("Date:")) {
                                    date = line.substring(line.indexOf(":") + 1).trim();
                                } else if (line.startsWith("Attending Doctor:")) {
                                    attendingDoctor = line.substring(line.indexOf(":") + 1).trim();
                                } else if (line.startsWith("Leave Required:")) {
                                    leaveRequired = Integer.parseInt(line.substring(line.indexOf(":") + 1).trim());
                                }
                            }
                            studentRecords.add(new StudentRecord(studentName, department, rollNumber, classAdvisor, remarks, date, attendingDoctor, leaveRequired));
                        }
                    }
                }

                if (nameCount > 0) {
                    tableModel.setRowCount(0);
                    for (StudentRecord record : studentRecords) {
                        Vector<String> row = new Vector<>();
                        row.add(record.getStudentName());
                        row.add(record.getDepartment());
                        row.add(record.getRollNumber());
                        row.add(record.getClassAdvisor());
                        row.add(record.getRemarks());
                        row.add(record.getDate());
                        row.add(record.getAttendingDoctor());
                        row.add(String.valueOf(record.getLeaveRequired()));
                        tableModel.addRow(row);
                    }
                } else {
                    tableModel.setRowCount(0);
                    JOptionPane.showMessageDialog(Info_Student.this, "Name not found.", "Student Search", JOptionPane.INFORMATION_MESSAGE);
                }

                System.out.println("Search completed successfully.");
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(Info_Student.this, "Error occurred while reading the file.", "Student Search", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private class StudentRecord {
        private String studentName;
        private String department;
        private String rollNumber;
        private String classAdvisor;
        private String remarks;
        private String date;
        private String attendingDoctor;
        private int leaveRequired;

        public StudentRecord(String studentName, String department, String rollNumber, String classAdvisor, String remarks, String date, String attendingDoctor, int leaveRequired) {
            this.studentName = studentName;
            this.department = department;
            this.rollNumber = rollNumber;
            this.classAdvisor = classAdvisor;
            this.remarks = remarks;
            this.date = date;
            this.attendingDoctor = attendingDoctor;
            this.leaveRequired = leaveRequired;
        }

        public String getStudentName() {
            return studentName;
        }

        public String getDepartment() {
            return department;
        }

        public String getRollNumber() {
            return rollNumber;
        }

        public String getClassAdvisor() {
            return classAdvisor;
        }

        public String getRemarks() {
            return remarks;
        }

        public String getDate() {
            return date;
        }

        public String getAttendingDoctor() {
            return attendingDoctor;
        }

        public int getLeaveRequired() {
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
                new Info_Student().setVisible(true);
            }
        });
    }
}

