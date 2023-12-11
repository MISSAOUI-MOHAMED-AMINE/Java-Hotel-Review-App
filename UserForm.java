import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class UserForm extends JFrame {
    private JSlider securitySlider; // Renamed from ratingSlider to securitySlider
    private JSlider comfortSlider; // Added a comfortSlider
    private JSlider repasSlider; // Added a repasSlider
    private JSlider animationSlider; 
    private JSlider propreteSlider; // Added a propreteSlider
    private JSlider serviceSlider; 
    private JTextArea commentTextArea;

    public UserForm() {
        setTitle("User Review Form");
        setSize(450, 550);
        setLayout(new GridLayout(9, 2));
        setIconImage(new ImageIcon("C:\\Users\\msi\\Desktop\\Nouveau dossier (4)\\logo.jpg").getImage());
        JLabel logLabel = new JLabel("Numéro de Chambre :");
        add(logLabel);

        JTextField chambreField = new JTextField(); 
        add(chambreField);

        JLabel securityLabel = new JLabel("Sécurité :");
        add(securityLabel);
        securitySlider = new JSlider(1, 5, 1);
        securitySlider.setMajorTickSpacing(1);
        securitySlider.setPaintTicks(true);
        securitySlider.setPaintLabels(true);
        add(securitySlider);

        JLabel comfortLabel = new JLabel("Comfort :");
        add(comfortLabel);

        comfortSlider = new JSlider(1, 5, 1); 
        comfortSlider.setMajorTickSpacing(1);
        comfortSlider.setPaintTicks(true);
        comfortSlider.setPaintLabels(true);
        add(comfortSlider);

        JLabel repasLabel = new JLabel("Repas :");
        add(repasLabel);

        repasSlider = new JSlider(1, 5, 1);
        repasSlider.setMajorTickSpacing(1);
        repasSlider.setPaintTicks(true);
        repasSlider.setPaintLabels(true);
        add(repasSlider);

        JLabel animationLabel = new JLabel("Animation :");
        add(animationLabel);

        animationSlider = new JSlider(1, 5, 1);
        animationSlider.setMajorTickSpacing(1);
        animationSlider.setPaintTicks(true);
        animationSlider.setPaintLabels(true);
        add(animationSlider);

        JLabel propreteLabel = new JLabel("Propreté :");
        add(propreteLabel);

        propreteSlider = new JSlider(1, 5, 1);
        propreteSlider.setMajorTickSpacing(1);
        propreteSlider.setPaintTicks(true);
        propreteSlider.setPaintLabels(true);
        add(propreteSlider);

        JLabel serviceLabel = new JLabel("Service :");
        add(serviceLabel);

        serviceSlider = new JSlider(1, 5, 1);
        serviceSlider.setMajorTickSpacing(1);
        serviceSlider.setPaintTicks(true);
        serviceSlider.setPaintLabels(true);
        add(serviceSlider);

        JLabel commentLabel = new JLabel("Comments:");
        add(commentLabel);

        commentTextArea = new JTextArea();
        JScrollPane commentScrollPane = new JScrollPane(commentTextArea);
        add(commentScrollPane);

        JButton submitButton = new JButton("Submit");
        add(submitButton);

        JButton cancelButton = new JButton("Cancel");
        add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });


        submitButton.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            ConnectBD take = new ConnectBD();
            int chambre = Integer.parseInt(chambreField.getText());
            if(take.checkID(chambre)){
                JOptionPane.showMessageDialog(UserForm.this, "This chambre number is already exists", "Input Error", JOptionPane.ERROR_MESSAGE);
        } else {
            int securityRating = securitySlider.getValue();
            int comfortRating = comfortSlider.getValue();
            int repasRating = repasSlider.getValue();
            int animationRating = animationSlider.getValue();
            int propreteRating = propreteSlider.getValue();
            int serviceRating = serviceSlider.getValue();
            String comment = commentTextArea.getText();
            float rating= (float) (securityRating+comfortRating+animationRating+repasRating+propreteRating+serviceRating)/6;
            take.save(chambre, securityRating, comfortRating, propreteRating, animationRating, repasRating, serviceRating,rating, comment);

            JOptionPane.showMessageDialog(UserForm.this, "Data Saved", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(UserForm.this, "Please enter a valid chambre number", "Input Error", JOptionPane.ERROR_MESSAGE);
        } 
        
    }
});

        setLocationRelativeTo(null);  // Center the frame on the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
      new UserForm();  
    }
}