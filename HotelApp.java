import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HotelApp extends JFrame {
    private static final String BACKGROUND_PATH = "C:\\Users\\msi\\Desktop\\Nouveau dossier (4)\\hotel.gif";

    public HotelApp() {
        setTitle("Hotel Review App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(790, 550);
        setLayout(new BorderLayout());
        setIconImage(new ImageIcon("C:\\Users\\msi\\Desktop\\Nouveau dossier (4)\\logo.jpg").getImage());

        // Background Image
        ImageIcon introImage = new ImageIcon(BACKGROUND_PATH);
        JLabel introLabel = new JLabel(introImage);
        add(introLabel, BorderLayout.CENTER);

        // Welcome Message
        JLabel welcomeLabel = new JLabel("Welcome to Hotel Review App");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.BLUE);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton userButton = createStyledButton("User");
        JButton adminButton = createStyledButton("Admin");

        buttonPanel.add(userButton);
        buttonPanel.add(adminButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Event Listeners
        userButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UserForm();
            }
        });

        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AdminLoginForm();
            }
        });

        // Center the window on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setBackground(new Color(59, 89, 182));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

 
}
