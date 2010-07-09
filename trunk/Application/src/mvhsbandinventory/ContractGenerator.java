/*
 * Copyright (c) 2009  Mel Nicholson.
 * All Rights Reserved.
 */

package mvhsbandinventory;

import java.awt.Dimension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.io.RandomAccess;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author nicholson
 */
public class ContractGenerator
{
    public static PDFont hbo = PDType1Font.HELVETICA_BOLD_OBLIQUE;
    public static PDFont hb = PDType1Font.HELVETICA_BOLD;
    public static PDFont h = PDType1Font.HELVETICA;

    public ContractGenerator()
    {
        
    }
    
    public void generateContract() //Will take Intrument i later
    {
        try
        {
            PDDocument document = new PDDocument();
            PDPage blankPage = new PDPage();
            document.addPage(blankPage);
            
            PDPageContentStream s = new PDPageContentStream(document, blankPage);
            s.beginText();
            s.setFont(hbo, 16);
            s.moveTextPositionByAmount(200, 700);
            s.drawString("MOUNTAIN VIEW HIGH SCHOOL");
            s.setFont(hb, 18);
            s.moveTextPositionByAmount(-30, -32);
            s.drawString("BOND FOR MUSICAL INSTRUMENT");
            s.setLineWidth(1);
            s.drawLine(170, 668, 475, 668);
            s.moveTextPositionByAmount(-70, -68);
            s.setFont(hb, 12);
            s.drawString("Instrument:");
            s.moveTextPositionByAmount(0, -16);
            s.setFont(hb, 12);
            s.drawString("Make / Model:");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("Serial #:");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("Replacement Value:");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("Mouthpiece:");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("Bow:");
            s.setFont(h, 12);
            s.moveTextPositionByAmount(0, -20);
            s.drawString("We, the undersigned, verify that the above information is correct, and agree to accept");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("the responsibility for the care and maintenance of the above instrument and ");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("accessories. We realize that in the case of theft, loss, or damage, it is our responsibility ");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("to repair or replace this equipment. We further realize the loan of this musical ");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("instrument is a privelege for the purpose of practicing at home and participationg in ");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("class or school performances. Under no circumstances may this property be loaned to ");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("others or used for non Mountain View High School related activities without the ");
            s.moveTextPositionByAmount(0, -16);
            s.drawString("instructor's consent.");



            s.endText();
            s.close();
            
            try
            {
                document.save("C:/csvTest/hello.pdf");
            } catch (COSVisitorException ex)
            {
                System.out.println("Oops v2" +ex);
            }
            document.close();

        } catch (IOException ex)
        {
            System.out.println("FILE IS OPEN SILLY " +ex);
        }
    }

}
