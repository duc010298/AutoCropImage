package processcropimage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ProcessCropImage {

    public static void main(String[] args) {
        String img = args[0];
        String status = args[1];
        String dir = "D:/Anhsieuam/" + img;
        try {
            BufferedImage originalImgage = ImageIO.read(new File(dir));
            int width = originalImgage.getWidth();
            if (width == 569 || width == 360) {
                return;
            }
            BufferedImage subImgage;
            if (status.equals("true")) {
                subImgage = originalImgage.getSubimage(91, 63, 569, 380);
            } else {
                subImgage = originalImgage.getSubimage(198, 159, 360, 314);
            }

            File outputfile = new File(dir);
            ImageIO.write(subImgage, "bmp", outputfile);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
