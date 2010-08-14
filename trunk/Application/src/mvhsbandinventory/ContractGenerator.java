package mvhsbandinventory;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class ContractGenerator
{
    public static PDFont hbo = PDType1Font.HELVETICA_BOLD_OBLIQUE;
    public static PDFont hb = PDType1Font.HELVETICA_BOLD;
    public static PDFont h = PDType1Font.HELVETICA;

    public ContractGenerator()
    {
        
    }
    
    public void generateContract(Instrument i)
    {
        PDDocument document = null;
        
        try
        {
            //Create new document
            document = new PDDocument();

            //Add new page to document
            PDPage page = new PDPage();
            document.addPage(page);

            //Write all text and lines to page
            PDPageContentStream s = new PDPageContentStream(document, page);
            s.beginText();
            s.setFont(hbo, 16);
            s.moveTextPositionByAmount(180, 700);
            s.drawString("MOUNTAIN VIEW HIGH SCHOOL");
            s.setFont(hb, 18);
            s.moveTextPositionByAmount(-32, -32);
            s.drawString("BOND FOR MUSICAL INSTRUMENT");
            s.setLineWidth(1);
            s.drawLine(148, 668, 455, 668);
            s.moveTextPositionByAmount(-73, -68);
            s.setFont(hb, 12);
            s.drawString("INSTRUMENT:   " +i.get("Instrument"));
            s.moveTextPositionByAmount(0, -16);
            s.setFont(hb, 12);
            s.drawString("MAKE / MODEL:   " +i.get("Brand"));
            s.moveTextPositionByAmount(0, -16);
            s.drawString("SERIAL #:   " +i.get("Serial"));
            s.moveTextPositionByAmount(0, -16);
            s.drawString("REPLACEMENT VALUE:   $" +i.get("Value"));
            s.moveTextPositionByAmount(0, -16);
            s.drawString("MOUTHPIECE:   " +i.get("Mouthpiece"));
            s.moveTextPositionByAmount(0, -16);
            s.drawString("BOW:   " +i.get("Bow"));
            s.setFont(h, 12);
            s.moveTextPositionByAmount(0, -20); //100, 500
            s.drawString("We, the undersigned, verify that the above information" +
                    " is correct, and agree to accept");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("the responsibility for the care and maintenance of the" +
                    " above instrument and ");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("accessories. We realize that in the case of theft, " +
                    "loss, or damage, it is our responsibility ");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("to repair or replace this equipment. We further " +
                    "realize the loan of this musical ");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("instrument is a privelege for the purpose of " +
                    "practicing at home and participationg in ");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("class or school performances. Under no circumstances " +
                    "may this property be loaned to ");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("others or used for non Mountain View High School " +
                    "related activities without the ");
            s.moveTextPositionByAmount(0, -16); //100, 388
            s.drawString("instructor's consent.");
            s.moveTextPositionByAmount(0, -80);
            s.drawString("The above instrument shall be returned upon completion" +
                    " of course, when student is");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("no longer required to play this instrument, or upon " +
                    "instructor's request. When the");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("instrument is returned and found to be in the same or " +
                    "better condition, this contract");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("will become null and void. (Reasonable wear and tear " +
                    "is expected)");
            s.moveTextPositionByAmount(0, -32);
            s.setFont(hb, 12);
            s.drawString("STUDENT:");
            s.moveTextPositionByAmount(0, -32);
            s.drawString("PARENT:");
            s.moveTextPositionByAmount(0, -32);
            s.drawString("INSTRUCTOR:");
            s.moveTextPositionByAmount(10, -32);
            s.setFont(hbo, 12);
            s.drawString("PLEASE NOTE ANY DENTS OR OTHER IMPERFECTIONS BEFORE " +
                    "SIGNING!");
            s.setFont(hbo, 16);
            s.moveTextPositionByAmount(12, 224);
            s.drawString("NOTE: $50 cleaning fee per year or season is required.");
            s.moveTextPositionByAmount(209, 244);
            s.setFont(hb, 12);
            s.drawString("ON LOAN TO:   " +i.get("Renter"));
            s.moveTextPositionByAmount(0, -16);
            s.drawString("FOR USE IN:   " +i.get("Period"));
            s.moveTextPositionByAmount(0, -16);
            s.drawString("NECK STRAP:   " +i.get("NeckStrap"));
            s.moveTextPositionByAmount(0, -16);
            s.drawString("LIGATURE:   " +i.get("Ligature"));
            s.moveTextPositionByAmount(0, -16);
            s.drawString("MOUTHPIECE CAP:   " +i.get("MouthpieceCap"));
            s.moveTextPositionByAmount(0, -16); 
            s.drawString("OTHER: " +i.get("Other"));
            s.moveTextPositionByAmount(25, -292);
            s.drawString("DATE LOANED:");
            s.moveTextPositionByAmount(0, -32);
            s.drawString("FEE PAID:");
            s.moveTextPositionByAmount(0, -32);
            s.drawString("DATE RETURNED:");

            //Signature lines
            s.drawLine(160,164,325,164);
            s.drawLine(130, 196, 325, 196);
            s.drawLine(140, 228, 325, 228);
            s.drawLine(440, 164, 530, 164);
            s.drawLine(395, 196, 530, 196);
            s.drawLine(425, 228, 530, 228);
            s.drawLine(75, 100, 530, 100);
            s.drawLine(75, 75, 530, 75);
            s.drawLine(75, 50, 530, 50);
            s.setFont(h, 8);
            s.moveTextPositionByAmount(-131, -8);
            s.drawString("(signature)");
            s.moveTextPositionByAmount(0, 32);
            s.drawString("(signature)");
            s.moveTextPositionByAmount(0, 32);
            s.drawString("(signature)");
            s.moveTextPositionByAmount(230, -32);
            s.drawString("(check number)");
            s.moveTextPositionByAmount(-195, 156);
            s.drawString("(Make checks payable to \"MVHS Music\")");
            s.setFont(hb, 16);
            s.moveTextPositionByAmount(100, 280);
            s.drawString("School Year:  " +i.get("SchoolYear"));

            //Closes content streamer so that it can be reopened.
            s.endText();
            s.close();
            
            // Determine the file path for saving the document in relative to 
            // the user's temp directory and then save the PDF file there
            String path = System.getProperty("java.io.tmpdir") +
                    i.get("Instrument")+"_" +i.get("Brand")+"_" +i.get("Serial") + "_contract.pdf";
            document.save(path);

            // Try to open the document in the default desktop application for
            // reading PDF files
            if (Desktop.isDesktopSupported())
            {
                Desktop desktop = Desktop.getDesktop();

                if (desktop.isSupported(Desktop.Action.OPEN))
                {
                    desktop.open(new File(path));
                }
            }
        }
        catch (org.apache.pdfbox.exceptions.COSVisitorException ex) {}
        catch (IOException ex) {}
        finally {
            try
            {
                //Allows document to be reopened later.
                document.close();
            }
            catch (IOException ex) {}
            catch (NullPointerException ex) {}
        }
    }

}
