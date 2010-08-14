package mvhsbandinventory;

/**
 *
 * @author jonathan
 */
public interface InstrumentStoreListener
{

    public void instrumentAdded(InstrumentStoreEvent event);
    public void instrumentRemoved(InstrumentStoreEvent event);
    public void instrumentModified(InstrumentStoreEvent event);
    
    public void instrumentLockAdded(InstrumentStoreEvent event);
    public void instrumentLockRemoved(InstrumentStoreEvent event);
    public void instrumentLockModified(InstrumentStoreEvent event);

}
