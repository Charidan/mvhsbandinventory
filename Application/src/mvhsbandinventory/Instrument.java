package mvhsbandinventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * Defines the data related to an instrument stored in the flat-file database.
 *
 * @author nicholson
 * @author jonathan
 */
public class Instrument
{
    private Map properties = new HashMap();

    public static String[] attributes = { "name", "brand", "serial", "rank",
        "value", "status", "notes", "ligature", "mouthpiece", "caps", "bow", 
        "history" };
    
    public static List attributeList = Arrays.asList(attributes);
    public static int attributesLength = attributes.length;

    /**
     * Constructor for an instrument without any properties.
     */
    public Instrument () {}

    /**
     * Constructor for an instrument contructed from the data in an array.  The
     * values in the array must be arranged in an order corresponding to the
     * static Instruments.attributes array.
     *
     * @param properties
     * @throws Exception
     */
    public Instrument (Object[] properties) throws Exception {
        for (int i = 0; i < attributesLength; i++) {
            set(attributes[i], properties[i]);
        }
    }

    /**
     * An accessor that sets the property named by the attribute argument to
     * the value passed in via the value argument.  The attribute must be
     * contained in the Instruments.attributes array.  If it is not, an
     * exception will be thrown.
     *
     * @param attribute
     * @param value
     * @throws Exception
     */
    public void set (String attribute, Object value) throws Exception
    {
        // Prevent people from storing arbitrary values in the instrument
        if (!attributeList.contains(value))
        {
            throw new Exception("Illegal attribute name.");
        }
        
        properties.put(attribute, value);
    }

    /**
     * An accessor that retrieves the value of the property named by the
     * attribute argument.
     *
     * @param attribute
     * @return value
     */
    public Object get (String attribute)
    {
        return properties.get(attribute);
    }

    /**
     * Formats the data in the HashMap into a comma-separated list of values,
     * perfect for exporting into a CSV file.
     *
     * @return serialized representation of this instrument's properties.
     */
    @Override
    public String toString()
    {
		int cols = attributes.length;
        String[][] table = new String[size][];
        
        // Convert our data into a useful two-dimensional array of the values in
        // the objects; this will represent the data in a spreadsheet set
        for (var col = 0; col < cols; col++)
        {
			int title = attributes[col];
			Object data = (Object) properties.get(title);
			
			if (data instanceof ArrayList) 
			{
				
			}
			else
			{
				
			}
		}
		
		// Convert our two-dimensional array into a CSV file
		String csv = "";
		
		// TODO: write code to print out the two-dimensional array with newlines
		// and commas separating the rows and columns, respectively
		
		return csv;
    }
}
