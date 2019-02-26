
package recursos;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *
 * @author JJCV
 */
public class Resize {
    
    public static BufferedImage resize(BufferedImage img) {
        int width = 150;
        int height = 100;
        Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();
        return resized;
    }
    
}
