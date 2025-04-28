import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class AddBuyerForm extends javax.swing.JFrame {

    public AddBuyerForm() {
        initComponents();
        setTitle("Add Buyer");
        setLocationRelativeTo(null);
    }

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            JOptionPane.showMessageDialog(this, "Invalid Email Format!");
            return;
        }

        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO buyers (name, email, phone, address) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3, phone);
            pst.setString(4, address);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Buyer Added Successfully!");
            clearFields();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        addressField.setText("");
    }
}
