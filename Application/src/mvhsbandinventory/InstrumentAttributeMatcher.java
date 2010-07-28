/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mvhsbandinventory;

/**
 *
 * @author jonathan
 */
public class InstrumentAttributeMatcher
{

    private String key;
    private String value;
    private boolean contains;

    public InstrumentAttributeMatcher (String key, String value, boolean contains)
    {
        this.key = key;
        this.value = value;
        this.contains = contains;
    }
}
