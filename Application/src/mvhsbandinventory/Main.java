/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mvhsbandinventory;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.io.File;

/**
 *
 * @author nicholson
 */
public class Main
{

    public static JFrame window;
    public static Display panel;
    public static InstrumentFileStore store;
    public static InstrumentList list;
    public static String[] paths = 
        {
            "/home/jonathan/csvtest",
            "C:/csvTest",
            "/users/chazgwennap/documents/csvtest"
        };

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            // TODO: Move this file path information into some sort of configuration
            // file that isn't covered by the version control system
            int length = paths.length;
            String path = null;

            // Try to find a directory path that actually is valid
            for (int i = 0; i < length; i++)
            {
                String pathString = paths[i];
                File pathFile = new File(pathString);
                
                if (pathFile.exists())
                {
                    path = pathString;
                }
            }

            store = new InstrumentFileStore(path);
            list = new InstrumentList(store);

            window = new JFrame();
            panel = new Display(list);

            window.add(panel);
            window.setTitle("MVHS - Band Inventory");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setMinimumSize(new Dimension(930, 575));
            window.setVisible(true);
            panel.repaint();
        } 
        catch (Exception e)
        {
            // Display an error message to the user telling them that the app
            // was unable to be loaded, probably because the path to the data
            // store was incorrect
            JOptionPane.showMessageDialog
            (
                window,
                "Unable to load application.  This typically occurs " +
                "because the file path to the datastore containing the " +
                "instrument data is not in the array of search paths.",
                "Unable to load application.",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}