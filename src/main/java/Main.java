import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {
    public static void main(String[] args) throws Exception {
        // TODO: include image in project
        String path = "C:\\Users\\drewm\\OneDrive\\Desktop\\Pictures\\diceSmall.png";
        File file = new File(path);
        BufferedImage inputImage;
        inputImage = ImageIO.read(file);
        Art art = new Art(inputImage);
        int width = inputImage.getWidth();
        int height = inputImage.getHeight();
        JFrame frame = new JFrame();
        frame.setSize(width, height);
        frame.setUndecorated(true);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent keyEvent) {

            }

            @Override
            public void keyPressed(KeyEvent keyEvent) {
                System.exit(0);
            }

            @Override
            public void keyReleased(KeyEvent keyEvent) {

            }
        });
        frame.add(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(art.get(), 0, 0, this);
            }
        });
        int maxMutationSize = 1000;
        frame.setVisible(true);
        while (maxMutationSize > 2) {
            maxMutationSize = (int) art.update(1, maxMutationSize);
            frame.repaint();
        }
    }
}
