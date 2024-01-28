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

public class PracownicyApp extends JFrame {
    private JTextField imieField, nazwiskoField, pensjaField, stanowiskoField;
    private JTextField dataUrodzeniaField, numerIDField, rodzajUmowyField, dataZawarciaUmowyField, okresWypowiedzeniaField;
    private DefaultListModel<String> pracownicyListModel;

    public PracownicyApp() {
        setTitle("Aplikacja Pracownicy");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

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

        add(panel);
        setVisible(true);
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

