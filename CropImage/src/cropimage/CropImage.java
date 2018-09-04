/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cropimage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Đỗ Trung Đức
 */
public class CropImage {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String img = args[0];
        String dir = "D:/Anhsieuam/" + img;
        try {
            BufferedImage originalImgage = ImageIO.read(new File(dir));
            if (originalImgage.getWidth() == 569) {
                return;
            }
            BufferedImage subImgage = originalImgage.getSubimage(91, 63, 569, 380);

            File outputfile = new File(dir);
            ImageIO.write(subImgage, "bmp", outputfile);

            originalImgage.flush();
            subImgage.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
