package mvhsbandinventory;

import java.io.File;
import net.contentobjects.jnotify.JNotifyListener;
import java.util.regex.Pattern;

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
        File changed = new File(root, name);
        Instrument instrument = store.read(changed);

        if (instrument.isValid())
        {
            store.fireEvent(InstrumentStoreEvent.ADDED, instrument);
        }
    }


    public void fileDeleted (int wd, String root, String name)
    {
        File removed = new File(root, name);

        if (removed.exists() && store.read(removed).isValid())
            return;

        Pattern splitter = Pattern.compile("_");
        Pattern typeDelimiter = Pattern.compile("\\.");

        String[] chunks = splitter.split(name);
        int chunkLen = chunks.length;
        int lastChunk = chunkLen - 1;
        Instrument remaining = new Instrument();

        for (int i = 0; i < chunkLen; i++)
        {
            String chunk = chunks[i];
            String attribute = Instrument.attributes[i];

            if (i == lastChunk)
            {
                String[] subChunks = typeDelimiter.split(chunk, 2);
                chunk = subChunks[0];
            }
            
            try
            {
                remaining.set(attribute, chunk);
            }
            catch (Exception ex) {}
        }

        store.fireEvent(InstrumentStoreEvent.REMOVED, remaining);
    }

    public void fileModified (int wd, String root, String name)
    {
        File modified = new File(root, name);
        Instrument instrument = store.read(modified);

        if (instrument.isValid())
        {
            store.fireEvent(InstrumentStoreEvent.MODIFIED, instrument);
        }
        else
        {
            fileDeleted(wd, root, name);
        }
    }

    public void fileRenamed (int wd, String root, String oldName, String newName)
    {
    }
}
