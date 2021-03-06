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
        if (list.isUnique(event.instrument)) {
            list.addLocal(event.instrument);
            System.out.println("[Realtime] Added: " + event.instrument);
        }
    }

    public void instrumentRemoved (InstrumentStoreEvent event)
    {
        String name = event.instrument.get("Instrument");
        String brand = event.instrument.get("Brand");
        String serial = event.instrument.get("Serial");

        try
        {
            Instrument existing = list.get(name, brand, serial);
            list.deleteLocal(existing);
            System.out.println("[Realtime] Deleted: " + event.instrument);

        }
        catch (Exception ex) {}
    }

    public void instrumentModified (InstrumentStoreEvent event)
    {
        System.out.println("[Realtime] Modified: " + event.instrument);

        if (list.isUnique(event.instrument))
        {
            instrumentAdded(event);
            return;
        }

        list.updateLocal(event.instrument);
        Main.panel.displayInstrument();
    }

}
