import java.awt.*;
import java.awt.image.BufferedImage;

public class Distance {
    public static double colorDistance(Color a, Color b) {
        return Math.sqrt(Math.pow(a.getRed() - b.getRed(), 2) + Math.pow(a.getGreen() - b.getGreen(), 2) + Math.pow(a.getBlue() - b.getBlue(), 2));
    }

    public static double imageDistance(BufferedImage original, BufferedImage image) {
        double d = 0;
        int x, y;
        // TODO: potential to get distance without looping through pixels?
        // idea 1: generate value when generating image, value used for distance calculation
        // idea 2: calculate distance when mutating based on mutation
        // idea 3: multithreading
        // idea 4; GPU programming, i.e. shaders (OpenCL, etc.)
        Color a, b;
        for (x = 0; x < original.getWidth(); x++) {
            for (y = 0; y < original.getHeight(); y++) {
                // TODO: optimize math for color distance
                a = new Color(original.getRGB(x, y));
                b = new Color(image.getRGB(x, y));
                d += colorDistance(a, b);
            }
        }
        return d / (original.getWidth() * original.getHeight());
    }
}
