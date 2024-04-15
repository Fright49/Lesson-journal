import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class UczniowieGUI {

	private static JComboBox<Student> ListaStudentow;
    private static JTextField gradeField;

    private static Student[] loadStudentsFromFile() throws IOException {
        ArrayList<Student> studentsList = new ArrayList<Student>();
        File file = new File("Loginy");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(",");
            String firstName = parts[0];
            String lastName = parts[1];
            String login = parts[2];
            if (login.contains("U")) {
                studentsList.add(new Student(firstName));
            }
        }
        scanner.close();
        Student[] studentsArray = new Student[studentsList.size()];
        studentsList.toArray(studentsArray);
        return studentsArray;
    }


    
    
    private static class ShowGradesButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Student student = (Student) ListaStudentow.getSelectedItem();
            String studentName = student.getFirstName();
            String gradesStr = "";
            double sum = 0.0;
            int count = 0;
            try {
                File file = new File("test");
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (line.startsWith(studentName)) {
                        String[] parts = line.substring(line.indexOf(":") + 2).split(",");
                        for (String gradeStr : parts) {
                            int grade = Integer.parseInt(gradeStr.trim());
                            gradesStr += grade + "\n";
                            sum += grade;
                            count++;
                        }
                    }
                }
                scanner.close();
                if (gradesStr.equals("")) {
                    JOptionPane.showMessageDialog(null, "Nie znaleziono ocen dla studenta " + studentName);
                } else {
                    double avg = sum / count;
                    JOptionPane.showMessageDialog(null, "Oceny dla studenta " + studentName + ":\n" + gradesStr + "Średnia: " + avg);
                }
            } catch (IOException ex) {
                System.out.println("Błąd odczytu pliku");
            }
        }
    }
    
    


    public static void GUI(String[] args) throws IOException {
    	
    	

        Student[] students = loadStudentsFromFile();
        JFrame frame = new JFrame("Dodawanie ocen");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

      
        
        
        JPanel topPanel = new JPanel(new FlowLayout());
        JLabel studentLabel = new JLabel("Wybierz studenta:");
        ListaStudentow = new JComboBox<>(students);
        topPanel.add(new JLabel("Zalogowany jako: " + Login.getLogin()+ "   |   "));
        topPanel.add(studentLabel);
        topPanel.add(ListaStudentow);
        frame.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new FlowLayout());
        JLabel gradeLabel = new JLabel("Ocena:");
        gradeField = new JTextField(10);
        centerPanel.add(gradeLabel);
        centerPanel.add(gradeField);
        frame.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel1 = new JPanel(new FlowLayout());
        JButton showGradesButton = new JButton("Pokaż oceny");
        showGradesButton.addActionListener(new ShowGradesButtonListener());
        bottomPanel1.add(showGradesButton);
        frame.add(bottomPanel1, BorderLayout.EAST);
        JPanel bottomPanel = new JPanel(new FlowLayout());

        
        JButton removeButton = new JButton("Usuń ocenę");
        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Student student = (Student) ListaStudentow.getSelectedItem();
                String studentName = student.getFirstName();
                String gradeToRemove = gradeField.getText().trim();

                try {
                    File inputFile = new File("test");
                    File tempFile = new File("test.tmp");

                    Scanner scanner = new Scanner(inputFile);
                    PrintWriter writer = new PrintWriter(new FileWriter(tempFile));

                    boolean foundGrade = false;
                    boolean foundStudent = false;
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        if (line.startsWith(studentName)) {
                            foundStudent = true;
                            String[] parts = line.substring(line.indexOf(":") + 2).split(",");
                            ArrayList<Integer> gradesList = new ArrayList<Integer>();
                            for (String gradeStr : parts) {
                                int grade = Integer.parseInt(gradeStr.trim());
                                if (Integer.toString(grade).equals(gradeToRemove)) {
                                    foundGrade = true;
                                } else {
                                    gradesList.add(grade);
                                }
                            }
                            if (foundGrade) {
                                if (gradesList.isEmpty()) {
                                    // Remove the whole line if there are no grades left for the student
                                    JOptionPane.showMessageDialog(frame, "Usunięto ocenę " + gradeToRemove + " dla studenta " + studentName);
                                } else {
                                    writer.println(studentName + ": " + gradesList.toString().replaceAll("[\\[\\]]", ""));
                                }
                            } else {
                                writer.println(line);
                            }
                        } else {
                            writer.println(line);
                        }
                    }

                    writer.close();
                    scanner.close();
                    inputFile.delete();
                    tempFile.renameTo(inputFile);

                    if (!foundStudent) {
                        JOptionPane.showMessageDialog(frame, "Nie znaleziono studenta " + studentName);
                    } else if (!foundGrade) {
                        JOptionPane.showMessageDialog(frame, "Nie znaleziono oceny " + gradeToRemove + " dla studenta " + studentName);
                    }

                } catch (IOException ex) {
                    System.out.println("Błąd operacji na pliku");
                }

                gradeField.setText("");
            }
        });
        bottomPanel.add(removeButton);





        
        JButton addButton = new JButton("Dodaj ocenę");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Student student = (Student) ListaStudentow.getSelectedItem();
                String[] grades = gradeField.getText().split(",");
                Oceny studentGrades = new Oceny(student);
                for (String grade : grades) {
                    try {
                        int parsedGrade = Integer.parseInt(grade.trim());
                        if (parsedGrade < 1 || parsedGrade > 6) {
                            JOptionPane.showMessageDialog(frame, "Wpisz ocene z zakres od 1 do 6. Wpisales ocene : " + grade);
                            return;
                        }
                        studentGrades.addGrade(parsedGrade);
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Możesz wpisać tylko liczbe! Wpisales ocene  : " + grade);
                        return;
                    }
                }
                String gradesStr = studentGrades.getGrades().toString().replaceAll("[\\[\\]]", "");
                String studentName = student.getFirstName();
                String gradeRecord = studentName + ": " + gradesStr + "\n";

                JOptionPane.showMessageDialog(frame, "Dodano ocenę " + gradeField.getText() + " dla studenta " + studentName);
                gradeField.setText("");

                try {
                    FileWriter myWriter = new FileWriter("test", true);
                    myWriter.write(gradeRecord);
                    myWriter.close();
                    System.out.println("Zapisano do pliku");

                } catch (IOException ex) {
                    System.out.println("Błąd zapisu do pliku");
                }
            }
        });


        bottomPanel.add(addButton);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        
        JButton logoutButton = new JButton("Wyloguj");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Main.main(null);
            }
        });
        bottomPanel.add(logoutButton);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                try {
                	System.out.println("Sortowanie ocen...");
                    SortFile.scan(args); // Dodaj wywołanie funkcji scan()
                } catch (IOException ex) {
                    System.out.println("Błąd sortowania pliku");
                }
            }
        });

        frame.setVisible(true);
    }}


