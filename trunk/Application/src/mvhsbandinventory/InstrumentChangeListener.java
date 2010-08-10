package mvhsbandinventory;

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
        list.addLocal(event.instrument);
        System.out.println("added " + event.instrument);
    }

    public void instrumentRemoved (InstrumentStoreEvent event)
    {
        list.deleteLocal(event.instrument);
    }

    public void instrumentModified (InstrumentStoreEvent event)
    {
        
    }

}
