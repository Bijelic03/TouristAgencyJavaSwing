package view;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

public class TableGenerator extends JScrollPane {
    private JTable table;
    private DefaultTableModel model;
    protected int selectedRow = -1;
    private int selectedColumn = -1;

    public TableGenerator(String[][] data, String[] columnNames) {
        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    selectedRow = table.getSelectedRow();
                    selectedColumn = table.getSelectedColumn();
                }
            }
        });

        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setReorderingAllowed(false);

        setViewportView(table);
    }
    public void updateTableData(String[] rowData) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.addRow(rowData);
    }
    
 
    public void removeSelectedRow() {
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
        }
    }
}