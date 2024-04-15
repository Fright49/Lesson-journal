import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;


public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Logowanie do NET");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        Login login1= new Login();
        JTextField loginField = new JTextField(20);
        JPanel loginPanel = new JPanel(new FlowLayout());
        loginPanel.add(new JLabel("Login: "));
        loginPanel.add(loginField);

        JPasswordField passwordField = new JPasswordField(20);
        JPanel passwordPanel = new JPanel(new FlowLayout());
        passwordPanel.add(new JLabel("Hasło: "));
        passwordPanel.add(passwordField);

        // Tworzenie panelu, który zawiera pola tekstowe
        JPanel fieldsPanel = new JPanel(new GridLayout(2, 1));
        fieldsPanel.add(loginPanel);
        fieldsPanel.add(passwordPanel);

        // Tworzenie menedżera układu GridBagLayout
        GridBagLayout layout = new GridBagLayout();
        JPanel centerPanel = new JPanel(layout);

        // Tworzenie obiektu GridBagConstraints, który pozwoli na wyśrodkowanie panelu
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.CENTER;

        // Dodanie panelu z polami tekstowymi do centralnej części panelu
        centerPanel.add(fieldsPanel, gbc);

        // Dodanie panelu do kontenera
        frame.getContentPane().add(centerPanel);




        // Tworzenie przycisku "Zaloguj" i dodanie go do okna
        JButton loginButton = new JButton("Zaloguj");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String login = loginField.getText();
                login1.setLogin(loginField.getText());
                String password = new String(passwordField.getPassword());

                // Odczyt loginów i haseł z pliku
                try {
                    File file = new File("Loginy");
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line;
                    boolean authenticated = false; // Inicjalizacja zmiennej authenticated wartością false
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts[0].equals(login) && parts[1].equals(password)) {
                            authenticated = true;
                            break;
                        }
                    }
                    reader.close();
                    if (authenticated) {
                        String[] parts = line.split(",");

                    	 if (parts[2].equals("U")) {
                    	        JOptionPane.showMessageDialog(frame, "Witaj " + login + " !");
                    	        frame.getContentPane().removeAll(); // Usunięcie elementów z okna
                    	        frame.setVisible(false);
                    	        OcenyUcznia.GUI(args);
                    	        
                    	    } else {
                    	        JOptionPane.showMessageDialog(frame, "Witaj " + login + " !");
                    	        frame.getContentPane().removeAll(); // Usunięcie elementów z okna
                    	        frame.setVisible(false);
                    	        UczniowieGUI.GUI(args);
                    	    }

                       

                      

                        
                    } else {
                        JOptionPane.showMessageDialog(frame, "Błędny login lub hasło!");
                    }
                } catch (FileNotFoundException ex) {
                    JOptionPane.showMessageDialog(frame, "Nie znaleziono pliku z loginami i hasłami!");
                    ex.printStackTrace();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Błąd odczytu pliku z loginami i hasłami!");
                    ex.printStackTrace();
                }
            }

        });
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(loginButton);
        frame.getContentPane().add(buttonPanel, BorderLayout.SOUTH);
        
     // Tworzenie obiektu GridBagConstraints, który pozwoli na wyśrodkowanie panelu z polami tekstowymi
        GridBagConstraints gbcFields = new GridBagConstraints();
        gbcFields.gridx = 0;
        gbcFields.gridy = 0;
        gbcFields.weightx = 1.0;
        gbcFields.weighty = 1.0;
        gbcFields.fill = GridBagConstraints.CENTER;

        // Dodanie panelu z polami tekstowymi do centralnej części panelu
        centerPanel.add(fieldsPanel, gbcFields);

        // Tworzenie obiektu GridBagConstraints, który pozwoli na wyśrodkowanie przycisku "Zaloguj"
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 1;
        gbcButton.weightx = 1.0;
        gbcButton.weighty = 1.0;
        gbcButton.fill = GridBagConstraints.CENTER;

        // Dodanie panelu z przyciskiem "Zaloguj" do centralnej części panelu
        centerPanel.add(buttonPanel, gbcButton);

        // Dodanie centralnej części panelu do kontenera
        frame.getContentPane().add(centerPanel, BorderLayout.CENTER);

     // Tworzenie przycisku "Zarejestruj"
        JButton registerButton = new JButton("Zarejestruj się");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Tworzenie okna dialogowego
                JDialog dialog = new JDialog(frame, "Rejestracja", true);
                dialog.setSize(400, 300);
                dialog.setLayout(new GridLayout(4, 1));

                // Tworzenie pola tekstowego na login
                JPanel loginPanel = new JPanel(new FlowLayout());
                JTextField loginField = new JTextField(20);
                loginPanel.add(new JLabel("Login: "));
                loginPanel.add(loginField);

                // Tworzenie pola tekstowego na hasło
                JPanel passwordPanel = new JPanel(new FlowLayout());
                JPasswordField passwordField = new JPasswordField(20);
                passwordPanel.add(new JLabel("Hasło: "));
                passwordPanel.add(passwordField);

                // Tworzenie pola tekstowego na typ użytkownika
                JPanel typePanel = new JPanel(new FlowLayout());
                JTextField typeField = new JTextField(2);
                typePanel.add(new JLabel("Typ użytkownika (N/U): "));
                typePanel.add(typeField);

                // Tworzenie przycisku "Zapisz" i dodanie akcji
                JPanel savePanel = new JPanel(new FlowLayout());
                JButton saveButton = new JButton("Zapisz");
                saveButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        String login = loginField.getText();
                        String password = new String(passwordField.getPassword());
                        String type = typeField.getText();
                        String line = login + "," + password + "," + type;

                        try {
                            // Otwarcie pliku w trybie append
                            FileWriter fw = new FileWriter("Loginy", true);
                            fw.write(line + "\n"); 
                            fw.close();
                            JOptionPane.showMessageDialog(dialog, "Zarejestrowano użytkownika!");
                            dialog.dispose(); // Zamknięcie okna dialogowego
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(dialog, "Błąd zapisu do pliku!");
                            ex.printStackTrace();
                        }
                    }
                });
                savePanel.add(saveButton);

                // Dodanie komponentów do okna dialogowego
                dialog.add(loginPanel);
                dialog.add(passwordPanel);
                dialog.add(typePanel);
                dialog.add(savePanel);

                dialog.setVisible(true);
            }
        });
     // Tworzenie panelu przycisków
   
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        // Dodawanie panelu przycisków do górnego panelu
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(buttonPanel, BorderLayout.CENTER);

        // Dodawanie górnego panelu do ramki
        frame.getContentPane().add(topPanel, BorderLayout.SOUTH);


        frame.setVisible(true);
    }
}


