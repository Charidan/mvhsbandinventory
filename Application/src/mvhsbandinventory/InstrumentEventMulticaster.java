package mvhsbandinventory;

import java.awt.AWTEventMulticaster;
import java.util.EventListener;

/**
 *
 * @author jonathan
 */
public class InstrumentEventMulticaster extends AWTEventMulticaster
                                        implements InstrumentStoreListener
{
    
    protected InstrumentEventMulticaster (EventListener a, EventListener b) 
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
