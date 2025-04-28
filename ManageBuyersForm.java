import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ManageBuyersForm extends javax.swing.JFrame {

    public ManageBuyersForm() {
        initComponents();
        setTitle("Manage Buyers");
        setLocationRelativeTo(null);
        loadBuyers();
    }

    private void loadBuyers() {
        try {
            Connection conn = DatabaseConnection.getConnection();
            String sql = "SELECT * FROM buyers";
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = (DefaultTableModel) buyersTable.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("address")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Load Error: " + e.getMessage());
        }
    }

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {                                              
        int selectedRow = buyersTable.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this buyer?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                int id = (int) buyersTable.getValueAt(selectedRow, 0);
                try {
                    Connection conn = DatabaseConnection.getConnection();
                    String sql = "DELETE FROM buyers WHERE id=?";
                    PreparedStatement pst = conn.prepareStatement(sql);
                    pst.setInt(1, id);
                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Buyer Deleted!");
                    loadBuyers();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, "Deletion Error: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a buyer first!");
        }
    }                                             

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {                                               
        loadBuyers();
    }                                              
}
