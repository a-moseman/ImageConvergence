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

    public static Mutation randomImage(BufferedImage original, BufferedImage image, double distance, int mutationAmount, int maxMutationSize) {
        int m, x, y, size;

        ColorModel cm = image.getColorModel();
        boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
        WritableRaster raster = image.copyData(null);

        BufferedImage out = new BufferedImage(cm, raster, isAlphaPremultiplied, null);
        Graphics g = out.getGraphics();
        double newDistance = distance;
        for (m = 0; m < mutationAmount; m++) {
            size = RANDOM.nextInt(maxMutationSize) + 1;
            x = RANDOM.nextInt(image.getWidth()) - (size >> 1);
            y = RANDOM.nextInt(image.getHeight()) - (size >> 1);
            g.setColor(randomColor());
            //g.fillRect(x, y, size, size);
            g.fillOval(x, y, size, size);
            for (int dx = -size; dx <= size; dx++) {
                for (int dy = -size; dy <= size; dy++) {
                    int x0 = x + dx;
                    int y0 = y + dy;
                    if (x0 < 0 || y0 < 0 || x0 >= original.getWidth() || y0 >= original.getHeight()) {
                        continue;
                    }
                    newDistance -= Distance.colorDistance(new Color(original.getRGB(x0, y0)), new Color(image.getRGB(x0, y0))) / (original.getWidth() * original.getHeight());
                    newDistance += Distance.colorDistance(new Color(original.getRGB(x0, y0)), new Color(out.getRGB(x0, y0))) / (original.getWidth() * original.getHeight());
                }
            }
        }
        return new Mutation(out, newDistance);
    }
}
