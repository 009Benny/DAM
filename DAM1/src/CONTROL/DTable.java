package CONTROL;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class DTable extends JTable {

    @Override
    public Component prepareRenderer(TableCellRenderer renderer, int rowIndex, int columnIndex) {
        Component component = super.prepareRenderer(renderer, rowIndex, columnIndex);
        component.setBackground(Color.WHITE);
        component.setForeground(Color.BLACK);
        if ((String.class.equals(this.getColumnClass(columnIndex)))&&(getValueAt(rowIndex, columnIndex)!=null)) {
            String val = String.valueOf(getValueAt(rowIndex, columnIndex).toString());
            System.out.println(val);
            if (val.contentEquals("P")) {
                component.setBackground(Color.BLUE);
                component.setForeground(Color.BLACK);
            }
            if (val.contentEquals("NP")) {
                component.setBackground(Color.RED);
                component.setForeground(Color.BLACK);
            }
            if (val.contentEquals("ENT")) {
                component.setBackground(Color.GREEN);
                component.setForeground(Color.BLACK);
            }
        }
        return component;
    }

}
