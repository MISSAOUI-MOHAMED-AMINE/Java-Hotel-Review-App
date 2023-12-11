import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminLoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AdminLoginForm() {
        setTitle("Admin Login");
        setSize(300, 200);
        setLayout(new GridLayout(3, 2));
        setIconImage(new ImageIcon("C:\\Users\\msi\\Desktop\\Nouveau dossier (4)\\logo.jpg").getImage());
        JLabel usernameLabel = new JLabel("Username:");
        add(usernameLabel);

        usernameField = new JTextField();
        add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        add(passwordLabel);

        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        add(loginButton);

        JButton cancelButton = new JButton("Cancel");
        add(cancelButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] password = passwordField.getPassword();

                if (authenticate(username, password)) {
                    new GraphicPage();
                } else {
                    JOptionPane.showMessageDialog(AdminLoginForm.this, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });
        setLocationRelativeTo(null);  // Center the frame on the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    private boolean authenticate(String username, char[] password) {
        return "admin".equals(username) && "admin".equals(new String(password));
    }
}

