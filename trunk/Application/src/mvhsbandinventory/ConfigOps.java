/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mvhsbandinventory;

import au.com.bytecode.opencsv.CSVParser;
import au.com.bytecode.opencsv.CSVWriter;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.contentobjects.jnotify.JNotifyListener;

/**
 *
 * @author Charidan
 */
class ConfigOps
{
    public static final String DEF_RENT_PRICE = "defRentPrice";

    private File directory;
    private JNotifyListener listener;
    private int listenerID;

    public ConfigOps(String path)
    {
        directory = new File(path);
    }

    public ArrayList<String> read()
    {
        BufferedReader reader = null;
        String line = "";
        ArrayList<String> lines = new ArrayList<String>();

        try
        {
            reader = new BufferedReader(new FileReader(Main.configFile));

            // Load all of the file into our buffer string
            while((line = reader.readLine())!=null)
            {
                if(!line.equals("")&&line!=null)
                {
                    lines.add(line);
                }
            }
        } catch(FileNotFoundException ex) {}
        catch(IOException ex)
        {
            System.out.println("File is locked.");
        } finally
        {
            // Make sure that the file reader is closed down properly
            try
            {
                reader.close();
            } catch(IOException ex)
            {
            } catch(NullPointerException ex)
            {
            }
        }

        return lines;
    }

    public String getDefaultRentalPrice() throws FileNotFoundException
    {
        CSVParser parser = new CSVParser();
        ArrayList<String> rows = read();
        String value = "ERR";

        try
        {
            for(String row : rows)
            {
                System.out.println("Row: "+row);
                String[] cells = parser.parseLine(row);
                if(cells[0].equals(DEF_RENT_PRICE))
                {
                    value = cells[1];
                    break;
                }
            }
        }
        catch (Exception e) {}
        finally
        {
            return value;
        }
    }

    public void setDefaultRentalPrice(String value) throws FileNotFoundException
    {
        CSVWriter writer = null;
        try
        {
            writer = new CSVWriter(new FileWriter(Main.configDir.getAbsolutePath()+"/config.csv"));
            List<String[]> configProps = new ArrayList<String[]>();
            configProps.add(new String[]{"defRentPrice", value});
            writer.writeAll(configProps);
            System.out.println("written");
            writer.close();
        } catch(IOException ex) {}
        finally
        {
            try
            {
                writer.close();
            } catch(IOException ex) {}
        }
    }
}
