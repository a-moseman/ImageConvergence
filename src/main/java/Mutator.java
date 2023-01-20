import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Random;

public class Mutator {
    private static final Random RANDOM = new Random();

    public static Color randomColor() {
        return new Color(RANDOM.nextInt(256), RANDOM.nextInt(256), RANDOM.nextInt(256));
    }

    public static BufferedImage[] randomImages(BufferedImage image, int mutationAmount, int tests, int maxMutationSize) {
        BufferedImage[] images = new BufferedImage[tests];
        int i, m, x, y, size;

        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);

        for (i = 0; i < tests; i++) {
            images[i] = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
            Graphics g = images[i].getGraphics();
            for (m = 0; m < mutationAmount; m++) {
                size = RANDOM.nextInt(maxMutationSize) + 1;
                x = RANDOM.nextInt(image.getWidth()) - size / 2;
                y = RANDOM.nextInt(image.getHeight()) - size / 2;
                g.setColor(randomColor());
                //g.fillRect(x, y, size, size);
                g.fillOval(x, y, size, size);
            }
        }
        return images;
    }
}
