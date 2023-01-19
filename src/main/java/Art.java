import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.Random;

public class Art {
    private static final Random RANDOM = new Random();
    private BufferedImage original;
    private BufferedImage image;

    public Art(BufferedImage original) {
        this.original = original;
        this.image = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    private Color randomColor() {
        return new Color(RANDOM.nextInt(256), RANDOM.nextInt(256), RANDOM.nextInt(256), 255);
    }

    private BufferedImage[] randomImages(int mutationAmount, int tests, int maxMutationSize) {
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
                x = RANDOM.nextInt(original.getWidth()) - size / 2;
                y = RANDOM.nextInt(original.getHeight()) - size / 2;
                g.setColor(randomColor());
                //g.fillRect(x, y, size, size);
                g.fillOval(x, y, size, size);
            }
        }
        return images;
    }

    public double update(int tests, int mutationAmount, int maxMutationSize) {
        // generate random mutations of current
        BufferedImage[] images = randomImages(mutationAmount, tests, maxMutationSize);
        // find distance
        double[] distances = new double[tests];
        int i;
        int best = 0;
        for (i = 0; i < tests; i++) {
            distances[i] = imageDistance(images[i]);
            if (distances[i] < distances[best]) {
                best = i;
            }
        }

        if (distances[best] > imageDistance(image)) { // if best if worst than current, redo
            return update(tests, mutationAmount, maxMutationSize);
        }

        System.out.println(distances[best]);
        image = images[best];
        return distances[best];
    }

    private double imageDistance(BufferedImage image) {
        double d = 0;
        int x, y;
        for (x = 0; x < original.getWidth(); x++) {
            for (y = 0; y < original.getHeight(); y++) {
                Color a = new Color(original.getRGB(x, y));
                Color b = new Color(image.getRGB(x, y));
                d += colorDistance(a, b);
            }
        }
        return d / (original.getWidth() * original.getHeight());
    }

    private double colorDistance(Color a, Color b) {
        return Math.sqrt(Math.pow(a.getRed() - b.getRed(), 2) + Math.pow(a.getGreen() - b.getGreen(), 2) + Math.pow(a.getBlue() - b.getBlue(), 2));
    }

    public BufferedImage get() {
        return image;
    }
}
