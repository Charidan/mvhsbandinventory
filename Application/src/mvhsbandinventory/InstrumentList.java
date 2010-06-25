package mvhsbandinventory;

import java.util.ArrayList;

public enum SortType {
	ASCENDING, DESCENDING
}

/**
 * 
 * @author jonathan
 */
public class InstrumentList
{
    private ArrayList list = new ArrayList();
    private InstrumentStore store;

    public InstrumentList (InstrumentStore model) 
    {
		// Store our pointer to the store for later use
        store = model;
        
		// Load all of the items from the datastore and put them into our 
		// private ArrayList
		Instrument[] instruments = store.load();
		for (Instrument instrument : instruments)
		{
			list.add(instrument);
		}
    }

    public void add (Instrument instrument)
    {
        // Add the item to our local memory cache and to our data store
        list.add(instrument);
        store.add(instrument);
    }

    public void delete (Instrument instrument)
    {
		// Delete the item from our local memory cache and to our data store
        list.remove(instrument);
        store.delete(instrument);
    }

	
    public void sort (String key)
    {

    }
	
	/**
	 * Returns an array of all of the Instrument objects that have the specified 
	 * value (set by the value argument) set for the the key argument specified.
	 * @param key
	 * @param value
	 * @return instrument array subset
	 */
    public Instrument[] selectList (String key, Object value)
    {
		// This is a cache of the length of the list of all of the Instrument
		// objects that we're dealing with so that we can loop through them; we 
		// determine this here so that we don't have to recalculate this for 
		// every iteration of the loop
        int length = list.size();
        
        // These are an array of the items that we're selecting and a variable 
        // to mark the index of the next item to be inserted into the array
        Instrument[] selection = {};
        int next = 0;
        
        // Iterate through all of the items in our memcache of the data store
        for (int i = 0; i < length; i++) 
        {
			// Grab the item from the memcache for this index
            Instrument current = (Instrument) list.get(i);
            
            // If this item's value for the specified key matches the value 
            // argument, add it to our selection array
            if (current.get(key) == value)
            {
                selection[next] = current;
                next++;
            }
        }
        
        return selection;
	}
	
	
    public String exportToExcel (Instrument[] instruments) {
        // Netbeans, please stop whining about my code.
        return null;
    }
}
