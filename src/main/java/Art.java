import java.awt.image.BufferedImage;

public class Art {
    private BufferedImage original;
    private BufferedImage image;
    private double distance;

    public Art(BufferedImage original) {
        this.original = original;
        this.image = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.distance = Distance.imageDistance(original, image);
    }

    public double update(int mutationAmount, int maxMutationSize) {
        // generate random mutations of current
        Mutation mutation = Mutator.randomImage(original, image, distance, mutationAmount, maxMutationSize);
        if (distance <= mutation.DISTANCE) { // if best if worst than current, redo
            return update(mutationAmount, maxMutationSize);
        }
        distance = mutation.DISTANCE;
        image = mutation.IMAGE;
        System.out.println(distance);
        return distance;
    }

    public BufferedImage get() {
        return image;
    }
}
