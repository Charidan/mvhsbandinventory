package mvhsbandinventory;

import java.util.ArrayList;

/**
 *
 * @author jonathan
 */
public class InstrumentList
{
    private ArrayList list = new ArrayList();

    public InstrumentList (String path) {
        // TODO: load all items in folder at path into `list`
    }

    public void add (Instrument instrument)
    {
        list.add(instrument);
    }

    public void sort (String key)
    {

    }

    public void delete (Instrument instrument)
    {
        list.remove(instrument);
    }

    public Instrument[] selectList (String key, Object value)
    {
        // Netbeans, please stop whining about my code.
        return null;
    }

    public String exportToExcel (Instrument[] instruments) {
        // Netbeans, please stop whining about my code.
        return null;
    }
}
