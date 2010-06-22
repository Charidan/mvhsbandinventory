package mvhsbandinventory;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nicholson
 */
public class Instrument
{
    private Map properties = new HashMap();

    public static String[] attributes = { "name", "brand", "serial", "rank", 
        "value", "status", "notes", "history", "ligature", "mouthpiece", 
        "caps", "bow" };

    public Instrument(String name, String brand, String serial, int rank,
            int value, String status, String notes, String history,
            String ligature, String mouthpiece, String cap, String bow)
    {
        
    }

    @Override
    public String toString()
    {
        String buffer = "";
        int length = attributes.length;

        Object current = null;

        for (int i = 0; i < length; i++)
        {
            current = properties.get(attributes[i]);
            buffer += (i < length - 1) ? current + "," : current;
        }

        return buffer;
    }
}
