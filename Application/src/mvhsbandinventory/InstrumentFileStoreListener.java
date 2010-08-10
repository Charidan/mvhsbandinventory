package mvhsbandinventory;

import java.io.File;
import net.contentobjects.jnotify.JNotifyListener;

/**
 *
 * @author jonathan
 */
public class InstrumentFileStoreListener implements JNotifyListener
{

    private InstrumentFileStore store;

    public InstrumentFileStoreListener (InstrumentFileStore store)
    {
        this.store = store;
    }

    public void fileCreated (int wd, String root, String name)
    {
        sendEvent(InstrumentStoreEvent.ADDED, root, name);
    }

    public void fileDeleted (int wd, String root, String name)
    {
        sendEvent(InstrumentStoreEvent.REMOVED, root, name);
    }

    public void fileModified (int wd, String root, String name)
    {
        sendEvent(InstrumentStoreEvent.MODIFIED, root, name);
    }

    public void fileRenamed (int wd, String root, String oldName, String newName)
    {
        throw new UnsupportedOperationException("Not supported; users aren't " +
                "allowed to change the name, brand, or serial of instruments.");
    }

    private void sendEvent (int type, String root, String name) {
        File changed = new File(root + File.separator + name);
        System.err.println(changed);

        Instrument instrument = store.read(changed);
        store.fireEvent(type, instrument);
    }

}
