package mvhsbandinventory;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author jonathan
 */
public class InstrumentFileStore extends InstrumentStore 
{
	private File directory;
	
	public static String horizontalSeparator = ",";
	public static String verticalSeparator = System.getProperty("line.separator");
	
	/**
	 * Constructs a new InstrumentFileStore object for storing Instrument 
	 * information on the disk.
	 * @param path
	 * @throws Error
	 */
	public void InstrumentFileStore (String path) throws Error
	{
		directory = new File(path);
		
		if (!directory.exists()) 
		{
			throw new Error("File store path does not exist.");
		}
		
		if (!directory.isDirectory()) 
		{
			throw new Error("File story path must be a directory; the current" +
							" path is not a directory");
		}
	}
	
	/**
	 * Generates a File object that should reference the specified instrument
	 * in the data store.
	 * @param instrument
	 * @return file that should reference the specified instrument
	 */
	private File getFile (Instrument instrument)
	{
		String name = (String) instrument.get("name");
		String brand = (String) instrument.get("brand");
		String serial = (String) instrument.get("serial");
		
		// Generate the CSV file name
		String fileName = name + "-" + brand + "-" + serial + ".csv";
		
		return new File(directory, fileName);
	}
	
	/**
	 * Serializes the data contained in the instrument into a CSV string that
	 * can be written to a file.  The data in the instrument will be written
	 * such that there will be one attribute of the instrument per column, with 
	 * the first row of each column containing the key name.  The second row 
	 * will contain the key value.  If the key value is an ArrayList (rather 
	 * than a string, the array list will be written to the string, one item per
	 * row, starting at the second row.
	 * 
	 * The attributes parameter allows you to choose a subset of the attributes
	 * specified as valid in the Instrument.attributes static array.
	 * @param instrument
	 * @param attributes
	 * @return csv-serialized instrument string
	 */ 
	public static String serialize (Instrument instrument, String[] attributes)
	{
		int height = 0;
		int width = attributes.length;
		
		// Determine if there (a) are any ArrayLists in the Instrument and (b)
		// what the size of the largest ArrayList in the Instrument is
		for (String key : attributes) 
		{
			Object value = instrument.get(key);
			
			if (value instanceof ArrayList) 
			{
				ArrayList<String> list = (ArrayList<String>) value;
				height = (list.size() > height) ? list.size() : height;
			}
		}
		
		// Generate a two-dimensional array to be turned into our CSV file
		String[][] table = new String[width][height];
		
		// Insert our data into the two-dimensional array so that we have a 
		// table that we will generate a CSV file out of
		for (int c = 0; c < width; c++) 
		{
			String key = attributes[c];
			Object value = instrument.get(key);
			
			table[c][0] = key;
			
			if (value instanceof ArrayList) 
			{
				ArrayList<String> list = (ArrayList<String>) value;
				int length = list.size();
				for (int r = 1; r <= length; r++) {
					table[c][r] = list.get(r - 1);
				}
			} 
			else 
			{
				table[c][1] = (String) value;
			}
		}
		
		// Serialize this table into CSV format so that we can later write the 
		// data to file
		String buffer = "";
		
		for (int r = 0; r < height; r++)
		{
			String rowBuffer = "";
			
			for (int c = 0; c < width; c++)
			{
				String cell = table[r][c];
				rowBuffer += (c != width - 1) ? 
					cell + horizontalSeparator : cell + verticalSeparator;
			}
		}
		
		return buffer;
	}
	
	/**
	 * A convenience version of the InstrumentFileStore.serialize function that
	 * exports every valid property specified in the Instrument.attributes 
	 * static array.
	 * @param instrument
	 * @return csv-serialized instrument string
	 */
	public static String serialize (Instrument instrument)
	{
		return serialize(instrument, Instrument.attributes);
	}
	
	/**
	 * Parse the data serialized in CSV format (presumably with the 
	 * InstrumentFileStore.serialize method) into an Instrument object.  See the 
	 * documentation for the InstrumentFileStore.serialize method for more 
	 * details on the serialization format for the CSV file.
	 * @param csv-serialized instrument
	 * @return instrument object
	 */ 
	public static Instrument unserialize (String csv)
	{
		// Create a two-dimensional array that will hold our CSV data
		String[][] table = null;
		
		// Start by splitting the data into separate rows
		ArrayList<Object> rows = Arrays.asList(csv.split(verticalSeparator));
		
		// Now, we're going to further split the rows into individual cells and
		// determine the width of the table specified in the serialized data
		int height = rows.length;
		int width = 0;
		
		for (int r = 0; r < height; r++)
		{
			String row = rows.get(r);
			String[] cells = row.split(horizontalSeparator);
			
			if (width == 0) 
			{
				width = cells.length;
				table = new String[width][height];
			}
			
			for (int c = 0; c < width; c++) 
			{
				table[c][r] = cells[c];
			}
		}
		
		// Create an Instrument object and fill in the data from the two-
		// dimensional array
		Instrument instrument = new Instrument();
		
		for (int c = 0; c < width; c++)
		{
			String attribute = table[c][0];
			
			// Infer the data type from the arrangement of data in the cells
			if (table[c][2] !== "")
			{
				// There is content in the third row, meaning that there is an 
				// array of items; we need to extract an arraylist of items
				ArrayList<String> value = new ArrayList<String>();
				for (int r = 1; r < height; r++) {
					value.add(table[c][r]);
				}
				instrument.set(attribute, value);
			}
			else
			{
				// There's only one value for the field--there is just a string 
				// for this field
				String value = table[c][1];
				instrument.set(attribute, value);
			}
		}
		
		return instrument;
	}
	
	/**
	 * Determines whether the specified instrument is already stored in the 
	 * data store.
	 * @param instrument
	 * @return a boolean value determining whether the instrument is in the 
	 * data store; true indicates that the instrument is in the store and false
	 * indicates that it doesn't exist in the data store
	 */
	public boolean exists (Instrument instrument)
	{
		return getFile(instrument).exists();
	}
	
	/**
     * Adds a new instrument to the store; serializes all of the data and
     * writes the data to the disk.
     * @param instrument
     */
	public void add (Instrument instrument)
	{
		// With other store types, we might actually need to do something 
		// different to create a new instrument item; for here, however, we 
		// don't need anything extra
		update(instrument);
	}
	
	public void update (Instrument instrument)
	{
		// Serialize the file
		String csv = serialize(instrument);
		
		// Write the serialized instrument to the appropriate file on disk
		File file = getFile(instrument);
		Writer pointer = new BufferedWriter(new FileWriter());
		pointer.write(csv);
		pointer.close();
	}
	
	public void delete (Instrument instrument)
	{
		
	}
	
	public Instrument[] load ()
	{
		return null;
	}
}
