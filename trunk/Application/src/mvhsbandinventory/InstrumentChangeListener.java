package mvhsbandinventory;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author jonathan
 */
public class InstrumentChangeListener implements InstrumentStoreListener
{
    InstrumentList list;

    InstrumentChangeListener (InstrumentList list) {
        this.list = list;
    }

    public void instrumentAdded (InstrumentStoreEvent event)
    {
        String name = event.instrument.get("Name");
        String brand = event.instrument.get("Brand");
        String serial = event.instrument.get("Serial");

        try
        {
            list.get(name, brand, serial);
        }
        catch (Exception ex)
        {
            list.addLocal(event.instrument);
            System.out.println("added " + event.instrument);
        }
    }

    public void instrumentRemoved (InstrumentStoreEvent event)
    {
        String name = event.instrument.get("Name");
        String brand = event.instrument.get("Brand");
        String serial = event.instrument.get("Serial");

        try
        {
            Instrument existing = list.get(name, brand, serial);
            list.deleteLocal(existing);
        }
        catch (Exception ex) {}
    }

    public void instrumentModified (InstrumentStoreEvent event)
    {
        
    }

}
