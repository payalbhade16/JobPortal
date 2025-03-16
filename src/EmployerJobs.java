import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployerJobs extends JFrame {
    private JTextArea jobsArea;

    public EmployerJobs() {
        setTitle("My Job Listings");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        jobsArea = new JTextArea();
        jobsArea.setEditable(false);
        add(new JScrollPane(jobsArea), BorderLayout.CENTER);

        loadJobs();
        setVisible(true);
    }

    private void loadJobs() {
        jobsArea.setText("Your Posted Jobs:\n---------------------\n");
        try (Connection conn = DBConnection.getConnection()) {
            String query = "SELECT * FROM jobs WHERE employer_id=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, 1); // Replace with the actual employer ID

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                jobsArea.append("ID: " + rs.getInt("id") + " | Title: " + rs.getString("title") +
                        " | Location: " + rs.getString("location") + "\n");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        new EmployerJobs();
    }
}
