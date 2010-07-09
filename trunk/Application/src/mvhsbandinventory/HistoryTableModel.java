/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mvhsbandinventory;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import mvhsbandinventory.Display;

/**
 *
 * @author nicholson
 */
public class HistoryTableModel extends AbstractTableModel
{
    private String[] columnNames = {"History Events"};
    private Display disp;

    public HistoryTableModel(Display disp)
    {
        this.disp = disp;
    }

    public int getColumnCount()
    {
        return columnNames.length;
    }

    public int getRowCount()
    {
        return disp.getTableSelected().getHistory().size();
    }

    @Override
    public String getColumnName(int col)
    {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col)
    {
        return disp.getTableSelected().getHistory().get(row);
    }

    @Override
    public Class getColumnClass(int c)
    {
        return String.class;
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return false;
    }
}
