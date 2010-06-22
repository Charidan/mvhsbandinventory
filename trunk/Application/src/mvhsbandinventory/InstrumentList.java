package mvhsbandinventory;

import java.util.ArrayList;

/**
 *
 * @author jonathan
 */
public class InstrumentList
{
    private ArrayList list = new ArrayList();
    private InstrumentFileStore store;

    public InstrumentList () {
        folder = path;
    }

    public void add (Instrument instrument)
    {
        list.add(instrument);
    }

    public void delete (Instrument instrument)
    {
        list.remove(instrument);

        // TODO: Add code to commit the data to the database.
    }

    public void sort (String key)
    {

    }

    public Instrument[] selectList (String key, Object value)
    {
        Instrument[] selection = {};
        int length = list.size();

        Instrument current = null;
        int next = 0;
        
        for (int i = 0; i < length; i++) 
        {
            current = (Instrument) list.get(i);
            
            if (current.get(key) == value)
            {
                selection[next] = current;
                next++;
            }
        }
        
        return selection;
    }

    public String exportToExcel (Instrument[] instruments) {
        // Netbeans, please stop whining about my code.
        return null;
    }
}
