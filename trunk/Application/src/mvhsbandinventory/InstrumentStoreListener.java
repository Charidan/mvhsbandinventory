package mvhsbandinventory;

/**
 *
 * @author jonathan
 */
public abstract class InstrumentStoreListener
{

    public abstract void instrumentAdded(InstrumentStoreEvent event);
    public abstract void instrumentRemoved(InstrumentStoreEvent event);
    public abstract void instrumentModified(InstrumentStoreEvent event);

}
