package mvhsbandinventory;

import net.contentobjects.jnotify.JNotifyListener;

/**
 *
 * @author jonathan
 */
public class InstrumentFileStoreListener implements JNotifyListener
{

    private InstrumentFileStore store;

    InstrumentFileStoreListener(InstrumentFileStore store)
    {
        this.store = store;
    }

    public void fileCreated(int i, String string, String string1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fileDeleted(int i, String string, String string1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fileModified(int i, String string, String string1)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fileRenamed(int i, String string, String string1, String string2)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
