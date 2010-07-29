package mvhsbandinventory;

import java.awt.AWTEvent;

/**
 *
 * @author jonathan
 */
public class InstrumentStoreEvent extends AWTEvent {
    public static final int INSTRUMENT_FIRST = AWTEvent.RESERVED_ID_MAX + 1;
    public static final int INSTRUMENT_ADDED = INSTRUMENT_FIRST;
    public static final int INSTRUMENT_MODIFIED = INSTRUMENT_FIRST + 1;
    public static final int INSTRUMENT_DELETED = INSTRUMENT_FIRST + 2;
    public static final int INSTRUMENT_LAST = INSTRUMENT_FIRST + 3;

    public InstrumentStoreEvent (Instrument source, int id) {
        super(source, id);
    }

}
