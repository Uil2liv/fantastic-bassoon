package app.ui.adview;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Preview extends JLabel {
    int width;
    int height;
    int margin;

    public Preview(String path, int width, int height, int margin) {
        super();
        this.width = width;
        this.height = height;
        this.margin = margin;

        // Set a Compound Border to act as a margin
        Border border = this.getBorder();
        Border marginBorder = new EmptyBorder(this.margin, this.margin, this.margin, this.margin);
        this.setBorder(new CompoundBorder(border, marginBorder));

        this.setImage(path);
    }

    public Preview(String path) {
        this(path, 750, -1, 5);
    }

    public void setImage(String path) {
        File imgFile = new File(path);
        try {
            // Set a scaled version of the Picture as the label Icon
            BufferedImage buffImg = ImageIO.read(imgFile);
            this.setIcon(new ImageIcon(buffImg.getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
