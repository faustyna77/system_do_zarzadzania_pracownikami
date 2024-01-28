import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
public class PracownicyApp extends JFrame {
    private JTextField imieField, nazwiskoField, pensjaField, stanowiskoField;
    private JTextField nazwaFirmyField, adresFirmyField, telefonFirmyField;

    private DefaultListModel<String> pracownicyListModel;
    private JTextField dataUrodzeniaField, numerIDField, rodzajUmowyField, dataZawarciaUmowyField, okresWypowiedzeniaField;


    public PracownicyApp() {
        setTitle("Aplikacja Pracownicy");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Dodajemy przyciski do wyświetlania listy pracowników i logowania

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));

        JLabel imieLabel = new JLabel("Imię:");
        panel.add(imieLabel);
        imieField = new JTextField();
        panel.add(imieField);

        JLabel nazwiskoLabel = new JLabel("Nazwisko:");
        panel.add(nazwiskoLabel);
        nazwiskoField = new JTextField();
        panel.add(nazwiskoField);

        JLabel pensjaLabel = new JLabel("Pensja:");
        panel.add(pensjaLabel);
        pensjaField = new JTextField();
        panel.add(pensjaField);

        JLabel stanowiskoLabel = new JLabel("Stanowisko:");
        panel.add(stanowiskoLabel);
        stanowiskoField = new JTextField();
        panel.add(stanowiskoField);

        JLabel dataUrodzeniaLabel = new JLabel("Data urodzenia (RRRR-MM-DD):");
        panel.add(dataUrodzeniaLabel);
        dataUrodzeniaField = new JTextField();
        panel.add(dataUrodzeniaField);

        JLabel numerIDLabel = new JLabel("Numer ID:");
        panel.add(numerIDLabel);
        numerIDField = new JTextField();
        panel.add(numerIDField);

        JLabel rodzajUmowyLabel = new JLabel("Rodzaj umowy:");
        panel.add(rodzajUmowyLabel);
        rodzajUmowyField = new JTextField();
        panel.add(rodzajUmowyField);

        JLabel dataZawarciaUmowyLabel = new JLabel("Data zawarcia umowy (RRRR-MM-DD):");
        panel.add(dataZawarciaUmowyLabel);
        dataZawarciaUmowyField = new JTextField();
        panel.add(dataZawarciaUmowyField);

        JLabel okresWypowiedzeniaLabel = new JLabel("Okres wypowiedzenia:");
        panel.add(okresWypowiedzeniaLabel);
        okresWypowiedzeniaField = new JTextField();
        panel.add(okresWypowiedzeniaField);

        JButton dodajButton = new JButton("Dodaj");
        dodajButton.addActionListener(new DodajButtonListener());
        panel.add(dodajButton);
        JButton usunButton = new JButton("Usuń");
        usunButton.addActionListener(new UsunButtonListener());
        panel.add(usunButton);

        JButton wyczyscButton = new JButton("Wyczyść listę pracowników");
        wyczyscButton.addActionListener(new WyczyscButtonListener());
        panel.add(wyczyscButton);

        JButton modyfikujButton = new JButton("Modyfikuj");
        modyfikujButton.addActionListener(new ModyfikujButtonListener());
        panel.add(modyfikujButton);

        pracownicyListModel = new DefaultListModel<>();
        JList<String> pracownicyList = new JList<>(pracownicyListModel);
        JScrollPane scrollPane = new JScrollPane(pracownicyList);
        panel.add(scrollPane);

        JButton zapiszButton = new JButton("Zapisz do pliku");
        zapiszButton.addActionListener(new ZapiszButtonListener());
        panel.add(zapiszButton);
        JButton wyswietlButton = new JButton("Wyświetl listę pracowników");
        wyswietlButton.addActionListener(new WyswietlButtonListener());
        panel.add(wyswietlButton);

        JButton zalogujButton = new JButton("Zaloguj się");
        zalogujButton.addActionListener(new ZalogujButtonListener());
        panel.add(zalogujButton);

        JButton wprowadzButton = new JButton("Wprowadź dane firmy");
        wprowadzButton.addActionListener(new WprowadzDaneFirmyButtonListener());
        panel.add(wprowadzButton);



        add(panel);
        setVisible(true);
    }

    private class WprowadzDaneFirmyButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            WprowadzDaneFirmyWindow dialog = new WprowadzDaneFirmyWindow(PracownicyApp.this);
            dialog.setVisible(true);
        }
    }

    private class WprowadzDaneFirmyWindow extends JDialog {
        private JTextField nazwaFirmyField, adresFirmyField, telefonFirmyField;
        private JButton okButton, cancelButton;

        public WprowadzDaneFirmyWindow(Frame parent) {
            super(parent, "Wprowadź dane firmy", true);
            setSize(300, 200);
            setLocationRelativeTo(parent);

            JPanel panel = new JPanel(new GridLayout(4, 2));

            JLabel nazwaFirmyLabel = new JLabel("Nazwa firmy:");
            panel.add(nazwaFirmyLabel);
            nazwaFirmyField = new JTextField();
            panel.add(nazwaFirmyField);

            JLabel adresFirmyLabel = new JLabel("Adres firmy:");
            panel.add(adresFirmyLabel);
            adresFirmyField = new JTextField();
            panel.add(adresFirmyField);

            JLabel telefonFirmyLabel = new JLabel("Telefon firmy:");
            panel.add(telefonFirmyLabel);
            telefonFirmyField = new JTextField();
            panel.add(telefonFirmyField);

            okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose(); // Zamknij okno dialogowe po naciśnięciu przycisku "OK"
                }
            });
            panel.add(okButton);

            cancelButton = new JButton("Anuluj");
            cancelButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose(); // Zamknij okno dialogowe po naciśnięciu przycisku "Anuluj"
                }
            });
            panel.add(cancelButton);

            add(panel);
        }

        public String getNazwaFirmy() {
            return nazwaFirmyField.getText();
        }

        public String getAdresFirmy() {
            return adresFirmyField.getText();
        }

        public String getTelefonFirmy() {
            return telefonFirmyField.getText();
        }
    }




    private class DodajButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String imie = imieField.getText();
            String nazwisko = nazwiskoField.getText();
            String pensja = pensjaField.getText();
            String stanowisko = stanowiskoField.getText();
            String dataUrodzenia = dataUrodzeniaField.getText();
            String numerID = numerIDField.getText();
            String rodzajUmowy = rodzajUmowyField.getText();
            String dataZawarciaUmowy = dataZawarciaUmowyField.getText();
            String okresWypowiedzenia = okresWypowiedzeniaField.getText();

            String pracownik = imie + "," + nazwisko + "," + pensja + "," + stanowisko + "," +
                    dataUrodzenia + "," + numerID + "," + rodzajUmowy + "," + dataZawarciaUmowy + "," + okresWypowiedzenia;
            pracownicyListModel.addElement(pracownik);

            // Clear fields after adding employee
            clearFields();
        }
    }

    private class CSVViewer extends JFrame {
        private JTextArea textArea;

        public CSVViewer() {
            setTitle("Wyswietlanie pliku CSV");
            setSize(600, 400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);

            textArea = new JTextArea();
            JScrollPane scrollPane = new JScrollPane(textArea);
            add(scrollPane);

            // Wczytaj dane z pliku CSV i wyświetl w JTextArea
            readCSVFile("pracownicy.csv");
        }

        private void readCSVFile(String filename) {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = br.readLine()) != null) {
                    textArea.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private class WyczyscButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(null, "Czy na pewno chcesz wyczyścić listę pracowników?", "Potwierdzenie", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                pracownicyListModel.clear(); // Wyczyść listę pracowników

                // Wyczyść zawartość pliku CSV
                try {
                    PrintWriter writer = new PrintWriter(new FileWriter("pracownicy.csv"));
                    writer.close();
                    JOptionPane.showMessageDialog(null, "Lista pracowników została wyczyszczona.");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Błąd podczas czyszczenia listy pracowników.");
                    ex.printStackTrace();
                }
            }
        }
    }
    private class ZalogujButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String username = "admin"; // Przykładowe dane logowania
            String password = "admin123"; // Przykładowe hasło

            String enteredUsername = getUsernameFromLoginDialog();
            String enteredPassword = getPasswordFromLoginDialog();

            if (enteredUsername.equals(username) && enteredPassword.equals(password)) {
                // Udziel dostępu, np. otwórz okno aplikacji
                JOptionPane.showMessageDialog(null, "Zalogowano pomyślnie!");
            } else {
                JOptionPane.showMessageDialog(null, "Nieprawidłowe dane logowania!");
            }
        }

        private String getUsernameFromLoginDialog() {
            // Zwróć nazwę użytkownika wprowadzoną przez użytkownika
            return JOptionPane.showInputDialog("Podaj nazwę użytkownika:");
        }

        private String getPasswordFromLoginDialog() {
            // Zwróć hasło wprowadzone przez użytkownika
            return JOptionPane.showInputDialog("Podaj hasło:");
        }
    }

    // Klasa do logowania
    private class LoginWindow extends JFrame {
        private JTextField usernameField;
        private JPasswordField passwordField;

        public LoginWindow() {
            // Implementacja okna logowania
        }
    }

    // Klasa do utworzenia nowej listy pracowników
    private class WyswietlButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            CSVViewer csvViewer = new CSVViewer();
            csvViewer.setVisible(true);
        }
    }


    private class NewEmployeeListWindow extends JFrame {
        private JTextField imieField, nazwiskoField, pensjaField, stanowiskoField;

        public NewEmployeeListWindow() {
            // Implementacja okna tworzenia nowej listy pracowników
        }
    }

    private class UsunButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = pracownicyListModel.getSize() - 1;
            if (selectedIndex != -1) {
                pracownicyListModel.remove(selectedIndex);
                clearFields();
            }
        }
    }

    private class ModyfikujButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int selectedIndex = pracownicyListModel.getSize() - 1;
            if (selectedIndex != -1) {
                String imie = imieField.getText();
                String nazwisko = nazwiskoField.getText();
                String pensja = pensjaField.getText();
                String stanowisko = stanowiskoField.getText();
                String dataUrodzenia = dataUrodzeniaField.getText();
                String numerID = numerIDField.getText();
                String rodzajUmowy = rodzajUmowyField.getText();
                String dataZawarciaUmowy = dataZawarciaUmowyField.getText();
                String okresWypowiedzenia = okresWypowiedzeniaField.getText();

                String pracownik = imie + "," + nazwisko + "," + pensja + "," + stanowisko + "," +
                        dataUrodzenia + "," + numerID + "," + rodzajUmowy + "," + dataZawarciaUmowy + "," + okresWypowiedzenia;
                pracownicyListModel.setElementAt(pracownik, selectedIndex);

                // Clear fields after modifying employee
                clearFields();
            }
        }
    }

    private class ZapiszButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                PrintWriter writer = new PrintWriter(new FileWriter("pracownicy.csv"));
                for (int i = 0; i < pracownicyListModel.size(); i++) {
                    writer.println(pracownicyListModel.get(i));
                }
                writer.close();
                JOptionPane.showMessageDialog(null, "Dane zostały zapisane do pliku pracownicy.csv");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Błąd podczas zapisu do pliku.");
                ex.printStackTrace();
            }
        }
    }

    private void clearFields() {
        imieField.setText("");
        nazwiskoField.setText("");
        pensjaField.setText("");
        stanowiskoField.setText("");
        dataUrodzeniaField.setText("");
        numerIDField.setText("");
        rodzajUmowyField.setText("");
        dataZawarciaUmowyField.setText("");
        okresWypowiedzeniaField.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PracownicyApp();
            }
        });
    }
}

