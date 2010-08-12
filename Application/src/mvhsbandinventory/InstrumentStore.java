package mvhsbandinventory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonathan
 */
public abstract class InstrumentStore
{
    List<InstrumentStoreListener> listeners = new ArrayList<InstrumentStoreListener>();

    /**
     * Adds a new instrument to the store; serializes all of the data and
     * writes the data to the disk.
     * @param instrument
     */
    public abstract void add (Instrument instrument);

    /**
     * Updates an existing instrument in the store; serializes all of the data
     * in the instrument parameter and writes the data to the disk.
     * @param instrument
     */
    public abstract void update (Instrument instrument);

    /**
     * Deletes the instrument from the store permenantly.
     * @param instrument
     */
    public abstract void delete (Instrument instrument) throws Exception;

	/**
	 * Determines whether the specified instrument is already stored in the 
	 * data store.
	 * @param instrument
	 * @return a boolean value determining whether the instrument is in the 
	 * data store; true indicates that the instrument is in the store and false
	 * indicates that it doesn't exist in the data store
	 */
	public abstract boolean exists (Instrument instrument);

    /**
     * Loads all of the instruments from the store.
     * @return an array of all of the parsed instruments in the store
     */
    public abstract List<Instrument> load () throws Exception;

    public synchronized void addListener (InstrumentStoreListener listener)
    {
        listeners.add(listener);
    }

    public synchronized void removeListener (InstrumentStoreListener listener)
    {
        listeners.remove(listener);
    }

    public synchronized void fireEvent (int type, Instrument instrument)
    {
        for (InstrumentStoreListener listener : listeners)
        {
            InstrumentStoreEvent event = new InstrumentStoreEvent(this, type,
                    instrument);
            
            switch (type)
            {
                case InstrumentStoreEvent.ADDED:
                    listener.instrumentAdded(event);
                    break;
                case InstrumentStoreEvent.MODIFIED:
                    listener.instrumentModified(event);
                    break;
                case InstrumentStoreEvent.REMOVED:
                    listener.instrumentRemoved(event);
                    break;
            }
        }
    }
}
