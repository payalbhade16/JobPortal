import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JobSeekerDashboard extends JFrame {
    private JTextArea jobsArea;
    private JTextField jobIdField;
    private JButton applyButton, refreshButton;

    public JobSeekerDashboard() {
        setTitle("Job Seeker Dashboard");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        jobsArea = new JTextArea();
        jobsArea.setEditable(false);
        add(new JScrollPane(jobsArea), BorderLayout.CENTER);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        panel.add(new JLabel("Enter Job ID to Apply:"));
        jobIdField = new JTextField();
        panel.add(jobIdField);

        applyButton = new JButton("Apply");
        refreshButton = new JButton("Refresh Jobs");

        panel.add(applyButton);
        panel.add(refreshButton);

        add(panel, BorderLayout.SOUTH);

        applyButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                applyForJob();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadJobs();
            }
        });

        loadJobs();
        setVisible(true);
    }

    private void loadJobs() {
        jobsArea.setText("Available Jobs:\n---------------------\n");
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM jobs";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                jobsArea.append("ID: " + rs.getInt("id") + " | Title: " + rs.getString("title") +
                        " | Company: " + rs.getString("company") + " | Location: " + rs.getString("location") + "\n");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void applyForJob() {
        int jobId = Integer.parseInt(jobIdField.getText());

        try (Connection conn = DBConnection.getConnection()) {
            String query = "INSERT INTO applications (job_id, seeker_id) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, jobId);
            ps.setInt(2, 2); // Replace with actual job seeker ID

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Job Application Submitted!");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new JobSeekerDashboard();
    }
}
