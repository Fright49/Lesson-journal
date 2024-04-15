import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

public class OcenyUcznia {
    private static String login;

    public static void GUI(String[] args) {
        JFrame frame = new JFrame("Oceny ucznia");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginPanel.add(new JLabel("Zalogowany jako: " + Login.getLogin()));
        topPanel.add(loginPanel, BorderLayout.NORTH);

        JButton showButton = new JButton("Pokaż oceny");
        showButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File("test");
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    StringBuilder sb = new StringBuilder();
                    double sum = 0.0;
                    int count = 0;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(":");
                        if (parts[0].equals(login)) {
                            double grade = Double.parseDouble(parts[1]);
                            sum += grade;
                            count++;
                            sb.append(grade).append("\n");
                        }
                    }
                    reader.close();
                    if (count == 0) {
                        JOptionPane.showMessageDialog(frame, "Brak ocen dla tego użytkownika.");
                    } else {
                        double average = sum / count;
                        String message = String.format("Twoje oceny:\n%s\nŚrednia ocen: %.2f", sb.toString(), average);
                        JOptionPane.showMessageDialog(frame, message, "Oceny ucznia "+login, JOptionPane.PLAIN_MESSAGE);
                    }

                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(frame, "Nie znaleziono pliku z ocenami!");
                    ex.printStackTrace();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Błąd odczytu pliku z ocenami!");
                    ex.printStackTrace();
                }
            }
        });

        JButton logoutButton = new JButton("Wyloguj");
        logoutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                Main.main(null);
            }
        });

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        buttonPanel.add(showButton);
        buttonPanel.add(logoutButton);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.getContentPane().add(topPanel);

        // Pobranie loginu z klasy Login
        login = Login.getLogin();

        // Wyświetlenie okna
        frame.setVisible(true);
    }
}

