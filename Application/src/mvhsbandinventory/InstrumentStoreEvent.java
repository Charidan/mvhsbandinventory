package mvhsbandinventory;

import java.awt.AWTEvent;

/**
 *
 * @author jonathan
 */
public class InstrumentStoreEvent extends AWTEvent {

    public InstrumentStoreEvent (Instrument source, int id) {
        super(source, id);
    }

}
