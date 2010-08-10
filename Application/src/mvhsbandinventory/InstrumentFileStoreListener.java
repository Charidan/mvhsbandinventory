package mvhsbandinventory;

import net.contentobjects.jnotify.JNotifyListener;

/**
 *
 * @author jonathan
 */
public class InstrumentFileStoreListener implements JNotifyListener
{

    private InstrumentFileStore store;

    InstrumentFileStoreListener (InstrumentFileStore store)
    {
        this.store = store;
    }

    public void fileCreated (int wd, String root, String name)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fileDeleted (int wd, String root, String name)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fileModified (int wd, String root, String name)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void fileRenamed (int wd, String root, String oldName, String newName)
    {
        throw new UnsupportedOperationException("Not supported; users aren't " +
                "allowed to change the name, brand, or serial of instruments.");
    }

}
