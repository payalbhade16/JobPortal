import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDashboard extends JFrame {
    private JTextArea usersArea, jobsArea;
    private JTextField deleteUserIdField, deleteJobIdField;
    private JButton deleteUserButton, deleteJobButton, refreshButton;

    public AdminDashboard() {
        setTitle("Admin Dashboard");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        usersArea = new JTextArea();
        jobsArea = new JTextArea();
        usersArea.setEditable(false);
        jobsArea.setEditable(false);

        add(new JScrollPane(usersArea), BorderLayout.WEST);
        add(new JScrollPane(jobsArea), BorderLayout.CENTER);

        panel.add(new JLabel("Enter User ID to Delete:"));
        deleteUserIdField = new JTextField();
        panel.add(deleteUserIdField);

        deleteUserButton = new JButton("Delete User");
        panel.add(deleteUserButton);

        panel.add(new JLabel("Enter Job ID to Delete:"));
        deleteJobIdField = new JTextField();
        panel.add(deleteJobIdField);

        deleteJobButton = new JButton("Delete Job");
        panel.add(deleteJobButton);

        refreshButton = new JButton("Refresh Data");
        add(refreshButton, BorderLayout.SOUTH);
        add(panel, BorderLayout.NORTH);

        deleteUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });

        deleteJobButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteJob();
            }
        });

        refreshButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadUsers();
                loadJobs();
            }
        });

        loadUsers();
        loadJobs();
        setVisible(true);
    }

    private void loadUsers() {
        usersArea.setText("Users:\n---------------------\n");
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM users";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                usersArea.append("ID: " + rs.getInt("id") + " | Name: " + rs.getString("name") +
                        " | Email: " + rs.getString("email") + " | Role: " + rs.getString("role") + "\n");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void loadJobs() {
        jobsArea.setText("Jobs:\n---------------------\n");
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

    private void deleteUser() {
        int userId = Integer.parseInt(deleteUserIdField.getText());

        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM users WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, userId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "User Deleted Successfully!");
                loadUsers();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteJob() {
        int jobId = Integer.parseInt(deleteJobIdField.getText());

        try (Connection conn = DBConnection.getConnection()) {
            String query = "DELETE FROM jobs WHERE id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, jobId);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                JOptionPane.showMessageDialog(this, "Job Deleted Successfully!");
                loadJobs();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new AdminDashboard();
    }
}
