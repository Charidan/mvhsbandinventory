package mvhsbandinventory;

import java.util.EventObject;

/**
 *
 * @author jonathan
 */
public class InstrumentStoreEvent extends EventObject
{
    public static final int ADDED = 0;
    public static final int REMOVED = 1;
    public static final int MODIFIED = 2;

    public int type;
    public Instrument instrument;

    InstrumentStoreEvent(Object source, int type, Instrument instrument)
    {
        super(source);

        this.type = type;
        this.instrument = instrument;
    }

}
