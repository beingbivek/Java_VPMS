package vpms.view;

import javax.swing.*;

public class StaffManagementView extends javax.swing.JFrame {

    // Constructor
    public StaffManagementView() {
        initComponents();

        String[] columnNames = {"ID", "Name", "Status", "Role", "Contact"};
        Object[][] data = {
            {"001", "Ram Thapa", "Active", "Staff", "9841******"},
            {"002", "Shyam Kaji", "On Leave", "Staff", "9810******"},
            {"101", "Ralph", "Active", "Admin", "9800******"}
        };

        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
    }

    // Initialize GUI components
    private void initComponents() {
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jPanel3 = new JPanel();
        jPanel4 = new JPanel();
        jPanel7 = new JPanel();

        jLabel1 = new JLabel("Staff Management");
        jLabel2 = new JLabel("Dashboard");
        jLabel3 = new JLabel("Parking Records");
        jLabel4 = new JLabel("Staff Management");
        jLabel5 = new JLabel("Slot Management");
        jLabel6 = new JLabel("Reports and Logs");
        jLabel7 = new JLabel("Settings");
        jLabel8 = new JLabel("Logout");
        jLabel9 = new JLabel("Settings");
        jLabel10 = new JLabel("Logout");

        jButton1 = new JButton("Cancel");
        jButton2 = new JButton("Edit Staff");
        jButton3 = new JButton("Filter");
        jButton4 = new JButton("Delete");
        jButton5 = new JButton("Add User");
        jButton6 = new JButton("Export");

        jTextField1 = new JTextField("Search");

        jTable1 = new JTable(
            new Object[][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String[] {"ID", "Name", "Type", "Email", "Password", "Image"}
        );

        jScrollPane1 = new JScrollPane(jTable1);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Layout setup can be added here (omitted for brevity)
        pack();
    }

    // Event handlers
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        // Search logic here
    }

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {
        // Delete logic here
    }

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {
        // Add user logic here
    }

    // Main method
    public static void main(String args[]) {
       try {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
         catch (Exception ex) {
            java.util.logging.Logger.getLogger(StaffManagementView.class.getName())
                .log(java.util.logging.Level.SEVERE, null, ex);
           // Variables declaration
    private JButton jButton1, jButton2, jButton3, jButton4, jButton5, jButton6;
    private JLabel jLabel1, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9, jLabel10;
    private JPanel jPanel1, jPanel2, jPanel3, jPanel4, jPanel7;
    private JScrollPane jScrollPane1;
    private JTable jTable1;
    private JTextField jTextField1;

    // Getter
    public JTable getUserTable() {
        return jTable1;
    }
}
