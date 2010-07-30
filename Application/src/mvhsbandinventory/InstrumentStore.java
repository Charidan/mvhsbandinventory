package mvhsbandinventory;

/**
 *
 * @author jonathan
 */
public abstract class InstrumentStore
{

    public InstrumentStoreListener storeListener;

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
    public abstract Instrument[] load () throws Exception;

    /**
     * Adds a new listener that listens for changes in content in the
     * InstrumentStore to ensure that the UI is updated in real-time
     * @param l - the InstrumentStoreListener that will handle events fired
     * from the instrument store
     */
    public synchronized void addListener (InstrumentStoreListener l)
    {
         storeListener = InstrumentStoreEventMulticaster.add(storeListener, l);
    }

    public synchronized void removeListener (InstrumentStoreListener l)
    {
         storeListener = InstrumentStoreEventMulticaster.remove(storeListener, l);
    }
}
