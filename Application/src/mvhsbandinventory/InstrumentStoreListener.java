package mvhsbandinventory;

import java.util.EventListener;

/**
 *
 * @author jonathan
 */
public interface InstrumentStoreListener extends EventListener {
    public abstract void instrumentAdded(InstrumentStoreEvent e);
    public abstract void instrumentModified(InstrumentStoreEvent e);
    public abstract void instrumentDeleted(InstrumentStoreEvent e);
}
