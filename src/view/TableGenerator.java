package view;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import java.util.Vector;

public class TableGenerator extends JScrollPane {
    private JTable table;
    private DefaultTableModel model;
    protected int selectedRow = -1;
    private int selectedColumn = -1;
    private String[] columnNames;
    private Vector<Vector<String>> data;

    public TableGenerator(String[][] data, String[] columnNames) {
        this.columnNames = columnNames;
        this.data = convertDataToVector(data);

        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting()) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            int newSelectedRow = table.getSelectedRow();
                            int newSelectedColumn = table.getSelectedColumn();
                            if (newSelectedRow >= 0 && newSelectedRow < model.getRowCount() && newSelectedColumn >= 0 && newSelectedColumn < model.getColumnCount()) {
                                if (newSelectedRow != selectedRow) {
                                    selectedRow = newSelectedRow;
                                    selectedColumn = newSelectedColumn;
                                    System.out.println(selectedRow);
                                    int selectedRow = table.getSelectedRow();
                                    int rowCount = table.getRowCount();
                                    System.out.println("Selected row: " + selectedRow);
                                    System.out.println("Row count: " + rowCount);
                                }
                            } else {
                                selectedRow = -1;
                                selectedColumn = -1;
                            }
                        }
                    });
                }
            }
        });

        table.setDefaultEditor(Object.class, null);
        table.getTableHeader().setReorderingAllowed(false);

        setViewportView(table);
    }

    public long getIdValueFromRow() {
        if (selectedRow >= 0 && selectedRow < model.getRowCount()) {
            Object firstValue = model.getValueAt(selectedRow, 0);
            return Long.parseLong(firstValue.toString());
        }
        return 0;
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }


    public void removeSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            data.remove(selectedRow);
            model.removeRow(selectedRow);
            this.selectedRow = -1; // Resetujte indeks selektovanog reda
        }
    }

    public void refreshTableData(String[][] newData) {
        model.setRowCount(0); // Uklonite postojeće redove iz tabele
        data = convertDataToVector(newData); // Konvertujte novi set podataka u vektor
        for (Vector<String> row : data) {
            model.addRow(row.toArray(new String[0])); // Dodajte nove redove u tabelu
        }
    }

    private Vector<Vector<String>> convertDataToVector(String[][] data) {
        Vector<Vector<String>> vectorData = new Vector<>();
        for (String[] rowData : data) {
            Vector<String> rowVector = new Vector<>();
            for (String cellData : rowData) {
                rowVector.add(cellData);
            }
            vectorData.add(rowVector);
        }
        return vectorData;
    }
}