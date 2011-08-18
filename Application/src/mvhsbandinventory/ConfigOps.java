/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mvhsbandinventory;

import au.com.bytecode.opencsv.CSVParser;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.contentobjects.jnotify.JNotifyListener;

/**
 *
 * @author Charidan
 */
class ConfigOps
{

    private File directory;
    private JNotifyListener listener;
    private int listenerID;

    public ConfigOps(String path)
    {
        directory = new File(path);
    }

    public String getDefaultRentalPrice() throws FileNotFoundException
    {
        CSVParser parser = new CSVParser();
        String value = "75";

        try
        {
            String[] cells = parser.parseLine("DefRentPrice");
            value = cells[1];
        }
        catch (Exception e) {}
        finally
        {
            return value;
        }
    }

    public void setDefaultRentalPrice(String value) throws FileNotFoundException
    {
        CSVParser parser = new CSVParser();

        try
        {
            String[] cells = parser.parseLine("DefRentPrice");
            cells[1] = value;
        }
        catch (Exception e) {}
    }
}
