package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * A JLabel that displays an image from a given file path, scaled to the
 * specified width and height.
 */
class ImageLabel extends JLabel {
    ImageLabel(String imagePath, int width, int height) {
        super(createIcon(imagePath, width, height));
    }

    private static ImageIcon createIcon(String imagePath, int width, int height) {
        final BufferedImage logoImage;
        try {
            logoImage = ImageIO.read(new File(imagePath));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
        final Image scaledImage = logoImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
}
