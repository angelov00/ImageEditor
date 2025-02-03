package com.imageeditor.history;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageMemento {

    private final BufferedImage image;

    public ImageMemento(final BufferedImage image) {
        this.image = deepCopy(image);
    }

    public BufferedImage getImage() {
        return deepCopy(image);
    }

    public BufferedImage deepCopy(final BufferedImage image) {
        if (image == null) return null;
        BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        Graphics g = copy.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return copy;
    }
}
