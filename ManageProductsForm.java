import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ManageProductsForm extends javax.swing.JFrame {

    public ManageProductsForm() {
        initComponents();
        setTitle("Manage Products");
        setLocationRelativeTo(null);
        loadProducts();
    }

    private void loadProducts() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM products";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) productsTable.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("category"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("description")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load Error: " + e.getMessage());
        }
    }

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        int selectedRow = productsTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this product?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = (int) productsTable.getValueAt(selectedRow, 0);
                try {
                    Connection conn = DatabaseConnection.getConnection();
                    String sql = "DELETE FROM products WHERE id=?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setInt(1, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Product Deleted!");
                    loadProducts();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Deletion Error: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a product first!");
        }
    }                                             

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        loadProducts();
    }                                              
}
