import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class AddProductForm extends javax.swing.JFrame {

    public AddProductForm() {
        initComponents();
        setTitle("Add Product");
        setLocationRelativeTo(null);
    }

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {                                           
        String name = nameField.getText();
        String category = categoryField.getText();
        String priceText = priceField.getText();
        String quantityText = quantityField.getText();
        String description = descriptionField.getText();

        if (name.isEmpty() || category.isEmpty() || priceText.isEmpty() || quantityText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try {
            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);

            Connection conn = DatabaseConnection.getConnection();
            String sql = "INSERT INTO products (name, category, price, quantity, description) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, category);
            pst.setDouble(3, price);
            pst.setInt(4, quantity);
            pst.setString(5, description);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Product Added Successfully!");
            clearFields();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter valid numbers for Price and Quantity!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }
    }

    private void clearFields() {
        nameField.setText("");
        categoryField.setText("");
        priceField.setText("");
        quantityField.setText("");
        descriptionField.setText("");
    }
}
