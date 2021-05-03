package utils;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class convertToPDF {
    public static void main(String[] args) throws IOException, DocumentException {

        Document document = new Document(PageSize.A4,0,0,0,0);
        PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\DELL\\Downloads\\pdf12.pdf"));
        document.open();

        java.awt.Image image1 = ImageIO.read( new File(grayScale("C:\\Users\\DELL\\Pictures\\Donatello.png")));

        Image i  = Image.getInstance(image1,null,false);
        i.scaleAbsolute(PageSize.A4.getWidth(),PageSize.A4.getHeight());

        document.add(i);
        document.close();
    }

    public static String grayScale(String path) {
        BufferedImage image;
        int width;
        int height;
        try {
            File input = new File(path);
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();

            for(int i=0; i<height; i++) {

                for(int j=0; j<width; j++) {

                    Color c = new Color(image.getRGB(j, i));
                    int red = (int)(c.getRed() * 0.299);
                    int green = (int)(c.getGreen() * 0.587);
                    int blue = (int)(c.getBlue() *0.114);
                    Color newColor = new Color(red+green+blue,

                            red+green+blue,red+green+blue);

                    image.setRGB(j,i,newColor.getRGB());
                }
            }

            File ouptut = new File("C:\\Users\\DELL\\Downloads\\grayscale.jpg");
            ImageIO.write(image, "jpg", ouptut);
            return "C:\\Users\\DELL\\Downloads\\grayscale.jpg";
        } catch (Exception e) {}
        return null;
    }

}
