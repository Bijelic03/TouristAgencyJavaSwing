package view;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

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
                    int newSelectedRow = table.getSelectedRow();
                    if (newSelectedRow != selectedRow) {
                        selectedRow = newSelectedRow;
                        selectedColumn = table.getSelectedColumn();
                    }
                }
            }
        });

        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setReorderingAllowed(false);

        setViewportView(table);
    }
    
    public List<Object[]> getDataFromSelectedRow() {
        List<Object[]> rowDataList = new ArrayList<>();

        if (selectedRow != -1) {
            int columnCount = model.getColumnCount();
            Object[] rowData = new Object[columnCount];

            for (int i = 0; i < columnCount; i++) {
                rowData[i] = model.getValueAt(selectedRow, i);
            }

            rowDataList.add(rowData);
        }

        return rowDataList;
    }

    public int getSelectedRow() {
    	return selectedRow;
    }
    
    public void addRow(String[] rowData) {
        model.addRow(rowData);
        model.fireTableDataChanged();
        table.revalidate();
        
    }    
    public void removeSelectedRow() {
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
        }
    }
}