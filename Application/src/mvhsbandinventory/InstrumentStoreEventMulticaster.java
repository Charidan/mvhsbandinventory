package mvhsbandinventory;

import java.awt.AWTEventMulticaster;
import java.util.EventListener;

/**
 *
 * @author jonathan
 */
public class InstrumentStoreEventMulticaster extends AWTEventMulticaster
                                        implements InstrumentStoreListener
{

    static InstrumentStoreListener add(InstrumentStoreListener storeListener, InstrumentStoreListener l)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    static InstrumentStoreListener remove(InstrumentStoreListener storeListener, InstrumentStoreListener l)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
    protected InstrumentStoreEventMulticaster (EventListener a, EventListener b)
    {
        super(a, b);
    }

    public void instrumentAdded(InstrumentStoreEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void instrumentModified(InstrumentStoreEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void instrumentDeleted(InstrumentStoreEvent e)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
