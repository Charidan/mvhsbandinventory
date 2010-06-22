package mvhsbandinventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nicholson
 * @author jonathan
 */
public class Instrument
{
    private Map properties = new HashMap();

    public static String[] attributes = { "name", "brand", "serial", "rank",
        "value", "status", "notes", "history", "ligature", "mouthpiece", 
        "caps", "bow" };
    public static List attributeList = Arrays.asList(attributes);
    public static int attributesLength = attributes.length;

    public Instrument(String name, String brand, String serial, int rank,
            int value, String status, String notes, String history,
            String ligature, String mouthpiece, String cap, String bow)
    {
        // TODO: implement this; for now, this method of instantiation is
        // not going to be implemented
    }

    public Instrument (Object[] properties) throws Exception {
        for (int i = 0; i < attributesLength; i++) {
            set(attributes[i], properties[i]);
        }
    }

    public void set (String attribute, Object value) throws Exception
    {
        // Prevent people from storing arbitrary values in the instrument
        if (!attributeList.contains(value))
        {
            throw new Exception("Illegal attribute name.");
        }
        
        properties.put(attribute, value);
    }

    public Object get (String attribute)
    {
        return properties.get(attribute);
    }

    @Override
    public String toString()
    {
        String buffer = "";
        Object current = null;

        for (int i = 0; i < attributesLength; i++)
        {
            current = properties.get(attributes[i]);
            buffer += (i < attributesLength - 1) ? current + "," : current;
        }

        return buffer;
    }
}
