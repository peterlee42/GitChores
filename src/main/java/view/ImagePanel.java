package view;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
    ImagePanel(String imagePath, int width, int height) {
        final BufferedImage logoImage;
        try {
            logoImage = ImageIO.read(new File(imagePath));
        } catch (IOException ioException) {
            throw new RuntimeException(ioException);
        }
        final Image scaledImage = logoImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        final JLabel picLabel = new JLabel(new ImageIcon(scaledImage));
        this.add(picLabel);
        this.setPreferredSize(new java.awt.Dimension(width, height));
    }
}
