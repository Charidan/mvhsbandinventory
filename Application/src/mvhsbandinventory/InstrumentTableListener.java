package mvhsbandinventory;

import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author jonathan
 */
public class InstrumentTableListener implements ListSelectionListener
{
    JTable table;
    Display display;

    public InstrumentTableListener (JTable table, Display display)
    {
        this.table = table;
        this.display = display;
    }

    public void valueChanged(ListSelectionEvent e)
    {
        if (e.getSource() == table.getSelectionModel()
                && table.getRowSelectionAllowed())
        {
            display.displayInstrument();
        }
    }

}
