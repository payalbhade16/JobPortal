import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployerDashboard extends JFrame {
    private JTextField titleField, companyField, locationField;
    private JTextArea descriptionArea;
    private JButton postJobButton, viewJobsButton;

    public EmployerDashboard() {
        setTitle("Employer Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 2));

        add(new JLabel("Job Title:"));
        titleField = new JTextField();
        add(titleField);

        add(new JLabel("Company:"));
        companyField = new JTextField();
        add(companyField);

        add(new JLabel("Location:"));
        locationField = new JTextField();
        add(locationField);

        add(new JLabel("Description:"));
        descriptionArea = new JTextArea();
        add(new JScrollPane(descriptionArea));

        postJobButton = new JButton("Post Job");
        viewJobsButton = new JButton("View My Jobs");

        add(postJobButton);
        add(viewJobsButton);

        postJobButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                postJob();
            }
        });

        viewJobsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new EmployerJobs().setVisible(true);
            }
        });

        setVisible(true);
    }

    private void postJob() {
        String title = titleField.getText();
        String company = companyField.getText();
        String location = locationField.getText();
        String description = descriptionArea.getText();

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO jobs (title, company, location, description, employer_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, title);
            ps.setString(2, company);
            ps.setString(3, location);
            ps.setString(4, description);
            ps.setInt(5, 1); // Replace with actual employer ID

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Job Posted Successfully!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new EmployerDashboard();
    }
}
