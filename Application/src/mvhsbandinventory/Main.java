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
    public static final String path = "C:/InstrumentInventory/data";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        try
        {
            File dir = new File(path);
            if(!dir.exists()) dir.mkdirs();
            else if(!dir.isDirectory()) throw new Error("Target folder "+path+" is a file instead of a directory.");
            store = new InstrumentFileStore(path);
            list = new InstrumentList(store);

            window = new JFrame();
            panel = new Display(list);

            window.add(panel);
            window.setTitle("MVHS - Instrument Inventory");
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setMinimumSize(new Dimension(810, 450));
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
                e.getMessage(),
                "Unable to load application.",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}