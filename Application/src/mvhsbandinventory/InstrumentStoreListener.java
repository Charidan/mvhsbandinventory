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
}
