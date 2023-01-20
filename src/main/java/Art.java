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

    public double update(int tests, int mutationAmount, int maxMutationSize) {
        // generate random mutations of current
        BufferedImage[] images = Mutator.randomImages(image, mutationAmount, tests, maxMutationSize);
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

        if (distances[best] >= imageDistance(image)) { // if best if worst than current, redo
            return update(tests, mutationAmount, maxMutationSize);
        }

        System.out.println(distances[best]);
        image = images[best];
        return distances[best];
    }

    private double imageDistance(BufferedImage image) {
        double d = 0;
        int x, y;
        // TODO: potential to get distance without looping through pixels?
        // idea 1: generate value when generating image, value used for distance calculation
        // idea 2: calculate distance when mutating based on mutation
        // idea 3: multithreading
        // idea 4; GPU programming, i.e. shaders (OpenCL, etc.)
        for (x = 0; x < original.getWidth(); x++) {
            for (y = 0; y < original.getHeight(); y++) {
                // TODO: optimize math for color distance
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
