import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class GUIInterface {
    private MemberManager manager;
    private JFrame frame;
    private DefaultTableModel tableModel;

    public GUIInterface(MemberManager manager) {
        this.manager = manager;
    }

    public void start() {
        try { manager.load(); } catch (IOException e) { /* ignore */ }
        SwingUtilities.invokeLater(() -> createAndShow());
    }

    private void createAndShow() {
        frame = new JFrame("MMS - GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 450);

        tableModel = new DefaultTableModel(new Object[]{"ID","Name","Phone","Achieved","Fee"},0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        JTable table = new JTable(tableModel);
        refreshTable();

        JPanel top = new JPanel();
        JTextField tfId = new JTextField(6);
        JTextField tfName = new JTextField(10);
        JTextField tfPhone = new JTextField(8);
        JTextField tfFee = new JTextField(6);
        JCheckBox cbAchieved = new JCheckBox("Achieved");
        JButton btnAdd = new JButton("Add");
        JButton btnDelete = new JButton("Delete");
        JButton btnSearch = new JButton("Search Name");
        JButton btnSortName = new JButton("Sort by Name");
        JButton btnSortId = new JButton("Sort by ID");
        JButton btnSave = new JButton("Save");

        top.add(new JLabel("ID:")); top.add(tfId);
        top.add(new JLabel("Name:")); top.add(tfName);
        top.add(new JLabel("Phone:")); top.add(tfPhone);
        top.add(new JLabel("Fee:")); top.add(tfFee);
        top.add(cbAchieved);
        top.add(btnAdd); top.add(btnDelete);
        top.add(btnSearch); top.add(btnSortName); top.add(btnSortId); top.add(btnSave);

        frame.getContentPane().add(new JScrollPane(table), BorderLayout.CENTER);
        frame.getContentPane().add(top, BorderLayout.NORTH);

        btnAdd.addActionListener(e -> {
            String id = tfId.getText().trim();
            if (id.isEmpty()) { JOptionPane.showMessageDialog(frame, "ID required"); return; }
            if (manager.findById(id) != null) { JOptionPane.showMessageDialog(frame, "ID exists"); return; }
            try {
                double fee = Double.parseDouble(tfFee.getText().trim());
                manager.addMember(new Member(id, tfName.getText(), tfPhone.getText(), cbAchieved.isSelected(), fee));
                refreshTable();
            } catch (NumberFormatException ex) { JOptionPane.showMessageDialog(frame, "Invalid fee"); }
        });

        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) { JOptionPane.showMessageDialog(frame, "Select a row"); return; }
            String id = (String) tableModel.getValueAt(row, 0);
            manager.removeMemberById(id);
            refreshTable();
        });

        btnSearch.addActionListener(e -> {
            String q = JOptionPane.showInputDialog(frame, "Name query:");
            if (q == null) return;
            List<Member> res = Searcher.searchByName(manager.getMembers(), q);
            if (res.isEmpty()) JOptionPane.showMessageDialog(frame, "No results");
            else {
                StringBuilder sb = new StringBuilder();
                for (Member m : res) sb.append(m.getId()).append(" - ").append(m.getName()).append("\\n");
                JOptionPane.showMessageDialog(frame, sb.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnSortName.addActionListener(e -> { Sorter.quickSortByName(manager.getMembers()); refreshTable(); });
        btnSortId.addActionListener(e -> { Sorter.sortById(manager.getMembers()); refreshTable(); });
        btnSave.addActionListener(e -> { try { manager.save(); JOptionPane.showMessageDialog(frame, "Saved"); } catch (IOException ex) { JOptionPane.showMessageDialog(frame, "Save failed: " + ex.getMessage()); } });

        frame.setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Member m : manager.getMembers()) tableModel.addRow(new Object[]{m.getId(), m.getName(), m.getPhone(), m.isAchievedGoal(), m.getMonthlyFee()});
    }
}
