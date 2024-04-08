import javax.swing.*;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.prefs.Preferences;

/**
 * GUI class
 */
public class Application extends JFrame {
    private static final String WIDTH_KEY = "width";
    private static final String HEIGHT_KEY = "height";
    private static final String POS_X = "x";
    private static final String POS_Y = "y";
    private Container cp;
    private Preferences preferences;
    private Dialog dialog;
    private JTextArea textArea;

    public Application() {
        super();
        cp = this.getContentPane();
        cp.setLayout(new BoxLayout(cp, BoxLayout.PAGE_AXIS));
        this.setTitle("Application");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        preferences = Preferences.userNodeForPackage(Application.class);
        int width = preferences.getInt(WIDTH_KEY, 300);
        int height = preferences.getInt(HEIGHT_KEY, 400);
        int posx = preferences.getInt(POS_X, 100);
        int posy = preferences.getInt(POS_Y, 100);

        this.setSize(width, height);
        this.setLocation(posx, posy);
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                saveUserDimensions();
                System.exit(0);
            }
        });
        this.setupApp();
    }

    /**
     * parte grafica
     */
    private void setupApp() {

        /**
         * crezione e personalizzazione della textarea.
         */
        textArea = new JTextArea(10, 30);
        textArea.setEditable(true);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setViewportView(textArea);

        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(255, 255, 255));

        JMenu editMenu = new JMenu("Edit");

        /**
         * creo l item aggiungi macchina in cui creo l action event in cui creo un
         * ogetto macchina e lo
         * aggiungo al textArea.
         */
        JMenuItem listColor = new JMenuItem("Lista colori");

        

        listColor.addActionListener(e -> {

            dialog = new JDialog(this, "lista colori", true);
            dialog.setLocation(this.getX(), this.getY());

            JPanel panel = new JPanel(new GridLayout(2, 2));

            // combobox per i colori
            JComboBox<String> colore;
            String[] colori = { "White", "Black", "Red", "Blue", "Green", "Yellow", "Cyan", "Gray", "Rosa", "Orange" };
            colore = new JComboBox<>(colori);
            colore.setFont(new Font("Arial", Font.BOLD, 14));
            colore.setBackground(Color.WHITE);
            colore.setForeground(Color.BLACK);
            colore.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            panel.add(new JLabel("Colore text area:"));
            panel.add(colore);

            JButton addButton = new JButton("Aggiungi");
            addButton.addActionListener(event -> {
                String comboColorString = (String) colore.getSelectedItem();
                switch (comboColorString) {
                    case "White":
                        textArea.setBackground(Color.WHITE);
                        textArea.setForeground(Color.BLACK);
                        break;
                    case "Black":
                        textArea.setBackground(Color.BLACK);
                        textArea.setForeground(Color.white);
                        break;
                    case "Red":
                        textArea.setBackground(Color.RED);
                        textArea.setForeground(Color.BLACK);
                        break;
                    case "Blue":
                        textArea.setBackground(Color.BLUE);
                        textArea.setForeground(Color.white);
                        break;
                    case "Green":
                        textArea.setBackground(Color.GREEN);
                        textArea.setForeground(Color.BLACK);
                        break;
                    case "Yellow":
                        textArea.setBackground(Color.YELLOW);
                        textArea.setForeground(Color.BLACK);
                        break;
                    case "Cyan":
                        textArea.setBackground(Color.CYAN);
                        textArea.setForeground(Color.BLACK);
                        break;
                    case "Gray":
                        textArea.setBackground(Color.GRAY);
                        textArea.setForeground(Color.white);
                        break;
                    case "Rosa":
                        textArea.setBackground(Color.PINK);
                        textArea.setForeground(Color.BLACK);
                        break;
                    case "Orange":
                        textArea.setBackground(Color.ORANGE);
                        textArea.setForeground(Color.BLACK);
                        break;

                    default:
                        break;
                }
                dialog.dispose();
            });

            panel.add(addButton);
            dialog.add(panel);
            dialog.pack();
            dialog.setVisible(true);

        });

        /**
         * panello secondario per i bottoni
         */
        JPanel panelButton = new JPanel(new FlowLayout());

        JButton save = new JButton("salva");
        save.addActionListener(e1->{
            if (textArea.getText().isEmpty()){
                JOptionPane.showMessageDialog(null, "Non ci sono testo da salvare", "Errore",JOptionPane.ERROR_MESSAGE);
                                              
            }
            else{
                exportCSV();
            }
        });
    
  
    
        
        editMenu.add(listColor);
        menuBar.add(editMenu);
        setJMenuBar(menuBar);
        panelButton.add(save);
        cp.add(scrollPane);
        cp.add(panelButton, BorderLayout.SOUTH);
    }

    public void saveUserDimensions() {
        preferences.putInt(WIDTH_KEY, getWidth());
        preferences.putInt(HEIGHT_KEY, getHeight());
        preferences.putInt(POS_X, getX());
        preferences.putInt(POS_Y, getY());
    }

    public void startApp(boolean packElements) {
        if (packElements)
            this.pack();
        this.setVisible(true);
    }
    public void exportCSV() {
        String filePath = "C:\\Users\\giuseppe\\Downloads\\fileCSV\\text2.txt"; 
    
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
    
            
            bw.write(textArea.getText()  + "\n");           
                
           JOptionPane.showMessageDialog(this,"File CSV creato con successo!");
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Application().startApp(false);
        });
    }
}
