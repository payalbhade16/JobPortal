import javax.swing.*;
import java.awt.*;

public class JobPortal extends JFrame {
    public JobPortal() {
        setTitle("Job Portal");
        setSize(600, 400); // Increased height for better spacing
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header Panel with Title
        JPanel headerPanel = new JPanel();
        JLabel titleLabel = new JLabel("Job Portal", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);

        // Buttons Panel (Above Image)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");
        JButton adminButton = new JButton("Admin Dashboard");
        JButton employerButton = new JButton("Employer Dashboard");
        JButton jobSeekerButton = new JButton("Job Seeker Dashboard");

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(adminButton);
        buttonPanel.add(employerButton);
        buttonPanel.add(jobSeekerButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Image Panel (Below Buttons) with Resized Image
        ImageIcon icon = new ImageIcon("jobportal.jpg"); // Add actual image path
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(500, 250, Image.SCALE_SMOOTH); // Resize image
        ImageIcon resizedIcon = new ImageIcon(resizedImg);

        JLabel imageLabel = new JLabel(resizedIcon);
        JPanel imagePanel = new JPanel();
        imagePanel.add(imageLabel);
        add(imagePanel, BorderLayout.SOUTH);

        // Action Listeners
        loginButton.addActionListener(e -> new Login().setVisible(true));
        registerButton.addActionListener(e -> new Register().setVisible(true));
        adminButton.addActionListener(e -> new AdminDashboard().setVisible(true));
        employerButton.addActionListener(e -> new EmployerDashboard().setVisible(true));
        jobSeekerButton.addActionListener(e -> new JobSeekerDashboard().setVisible(true));

        setVisible(true);
    }

    public static void main(String[] args) {
        new JobPortal();
    }
}
