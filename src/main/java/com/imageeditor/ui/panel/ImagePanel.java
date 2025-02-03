package com.imageeditor.ui.panel;

import com.imageeditor.history.ImageCaretaker;
import com.imageeditor.history.ImageMemento;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {

    private BufferedImage image;
    private double zoomFactor = 1.0;

    private final ImageCaretaker imageCaretaker;

    public ImagePanel(ImageCaretaker imageCaretaker) {
        this.imageCaretaker = imageCaretaker;
    }

    public BufferedImage getImage() {
        return this.image;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(image == null) {
            final String message = "No image selected";
            FontMetrics fontMetrics = g.getFontMetrics();
            int textWidth = fontMetrics.stringWidth(message);
            int textHeight = fontMetrics.getHeight();
            int x = (this.getWidth() - textWidth) / 2;
            int y = (this.getHeight() - textHeight) / 2 + fontMetrics.getAscent();
            g.drawString(message, x, y);
        } else {
            g.drawImage(image, 0, 0, null);
        }
    }

    public ImageMemento createMemento() {
        return new ImageMemento(image);
    }

    public void restoreFromMemento(ImageMemento memento) {
        this.image = memento.getImage();
        repaint();
    }

    private void saveState() {
        if (imageCaretaker != null) {
            imageCaretaker.saveState(this);
        }
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        repaint();
    }

}
