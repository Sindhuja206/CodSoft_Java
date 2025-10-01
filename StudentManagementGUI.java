import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

class Student implements Serializable {
    private String name;
    private String rollNumber;
    private String grade;
    private int age;

    public Student(String name, String rollNumber, String grade, int age) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.age = age;
    }

    public String getName() { return name; }
    public String getRollNumber() { return rollNumber; }
    public String getGrade() { return grade; }
    public int getAge() { return age; }

    public void setName(String name) { this.name = name; }
    public void setGrade(String grade) { this.grade = grade; }
    public void setAge(int age) { this.age = age; }

    @Override
    public String toString() {
        return "Roll No: " + rollNumber + ", Name: " + name + ", Age: " + age + ", Grade: " + grade;
    }
}

class StudentManagementSystem {
    private List<Student> students;
    private final String FILE_NAME = "students.dat";

    public StudentManagementSystem() {
        students = loadStudents();
    }

    public void addStudent(Student s) {
        students.add(s);
        saveStudents();
    }

    public boolean removeStudent(String rollNumber) {
        Student toRemove = null;
        for (Student s : students) {
            if (s.getRollNumber().equalsIgnoreCase(rollNumber)) {
                toRemove = s;
                break;
            }
        }
        if (toRemove != null) {
            students.remove(toRemove);
            saveStudents();
            return true;
        }
        return false;
    }

    public Student searchStudent(String rollNumber) {
        for (Student s : students) {
            if (s.getRollNumber().equalsIgnoreCase(rollNumber)) {
                return s;
            }
        }
        return null;
    }

    public List<Student> getAllStudents() {
        return students;
    }

    private void saveStudents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(students);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving students: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private List<Student> loadStudents() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Student>) ois.readObject();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error loading students: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}

public class StudentManagementGUI extends JFrame {
    private StudentManagementSystem sms;
    private JTable table;
    private DefaultTableModel tableModel;

    private JTextField tfRoll, tfName, tfAge, tfGrade;

    public StudentManagementGUI() {
        sms = new StudentManagementSystem();
        setTitle("Student Management System");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(5, 3, 10, 10));
        tfRoll = new JTextField();
        tfName = new JTextField();
        tfAge = new JTextField();
        tfGrade = new JTextField();

        formPanel.add(new JLabel("Roll Number:"));
        formPanel.add(tfRoll);
        formPanel.add(new JLabel()); 

        formPanel.add(new JLabel("Name:"));
        formPanel.add(tfName);
        formPanel.add(new JLabel());

        formPanel.add(new JLabel("Age:"));
        formPanel.add(tfAge);
        formPanel.add(new JLabel());

        formPanel.add(new JLabel("Grade:"));
        formPanel.add(tfGrade);
        formPanel.add(new JLabel());

        JButton btnAdd = new JButton("Add Student");
        JButton btnRemove = new JButton("Remove Student");
        JButton btnEdit = new JButton("Edit Selected");
        formPanel.add(btnAdd);
        formPanel.add(btnRemove);
        formPanel.add(btnEdit);

        add(formPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Roll No", "Name", "Age", "Grade"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        JTextField tfSearch = new JTextField(10);
        JButton btnSearch = new JButton("Search");
        JButton btnDisplayAll = new JButton("Display All");
        bottomPanel.add(new JLabel("Search Roll No:"));
        bottomPanel.add(tfSearch);
        bottomPanel.add(btnSearch);
        bottomPanel.add(btnDisplayAll);
        add(bottomPanel, BorderLayout.SOUTH);

        loadTable();

        btnAdd.addActionListener(e -> {
            String roll = tfRoll.getText().trim();
            String name = tfName.getText().trim();
            String ageStr = tfAge.getText().trim();
            String grade = tfGrade.getText().trim();

            if (roll.isEmpty() || name.isEmpty() || ageStr.isEmpty() || grade.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            int age;
            try { age = Integer.parseInt(ageStr); } 
            catch (NumberFormatException ex) { 
                JOptionPane.showMessageDialog(this, "Invalid age!");
                return; 
            }

            sms.addStudent(new Student(name, roll, grade, age));
            JOptionPane.showMessageDialog(this, "Student added successfully!");
            clearFields();
            loadTable();
        });

        btnRemove.addActionListener(e -> {
            String roll = tfRoll.getText().trim();
            if (roll.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Roll Number to remove!");
                return;
            }
            boolean removed = sms.removeStudent(roll);
            if (removed) {
                JOptionPane.showMessageDialog(this, "Student removed successfully!");
                clearFields();
                loadTable();
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
            }
        });

        btnEdit.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Select a student from the table to edit!");
                return;
            }

            String currentRoll = tableModel.getValueAt(selectedRow, 0).toString();
            Student s = sms.searchStudent(currentRoll);

            if (s != null) {
                tfRoll.setText(s.getRollNumber());
                tfRoll.setEditable(false);
                tfName.setText(s.getName());
                tfAge.setText(String.valueOf(s.getAge()));
                tfGrade.setText(s.getGrade());

                JButton btnSaveChanges = new JButton("Save Changes");
                formPanel.add(btnSaveChanges);
                formPanel.revalidate();
                formPanel.repaint();

                btnSaveChanges.addActionListener(ev -> {
                    String name = tfName.getText().trim();
                    String ageStr = tfAge.getText().trim();
                    String grade = tfGrade.getText().trim();

                    if (name.isEmpty() || ageStr.isEmpty() || grade.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "All fields are required!");
                        return;
                    }

                    int age;
                    try { age = Integer.parseInt(ageStr); } 
                    catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid age!");
                        return;
                    }

                    s.setName(name);
                    s.setAge(age);
                    s.setGrade(grade);
                    sms.removeStudent(currentRoll);
                    sms.addStudent(s);

                    JOptionPane.showMessageDialog(this, "Student updated successfully!");
                    clearFields();
                    loadTable();

                    formPanel.remove(btnSaveChanges);
                    formPanel.revalidate();
                    formPanel.repaint();
                    tfRoll.setEditable(true);
                });
            }
        });

        btnSearch.addActionListener(e -> {
            String roll = tfSearch.getText().trim();
            if (roll.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter Roll Number to search!");
                return;
            }
            Student s = sms.searchStudent(roll);
            if (s != null) {
                JOptionPane.showMessageDialog(this, "Found: " + s.getName() + ", Age: " + s.getAge() + ", Grade: " + s.getGrade());
            } else {
                JOptionPane.showMessageDialog(this, "Student not found!");
            }
        });
        btnDisplayAll.addActionListener(e -> loadTable());
        setVisible(true);
    }
    private void loadTable() {
        tableModel.setRowCount(0);
        for (Student s : sms.getAllStudents()) {
            tableModel.addRow(new Object[]{s.getRollNumber(), s.getName(), s.getAge(), s.getGrade()});
        }
    }
    private void clearFields() {
        tfRoll.setText("");
        tfRoll.setEditable(true);
        tfName.setText("");
        tfAge.setText("");
        tfGrade.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentManagementGUI());
    }
}
