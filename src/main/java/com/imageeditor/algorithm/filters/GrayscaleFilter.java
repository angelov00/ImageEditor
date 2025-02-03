package com.imageeditor.algorithm.filters;

import com.imageeditor.algorithm.core.ImageAlgorithm;

import java.awt.*;
import java.awt.image.BufferedImage;

public class GrayscaleFilter implements ImageAlgorithm {

    private static final double RED_WEIGHT = 0.299;
    private static final double GREEN_WEIGHT = 0.587;
    private static final double BLUE_WEIGHT = 0.114;

    @Override
    public BufferedImage apply(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {

                Color color = new Color(image.getRGB(x, y), true);

                int gray = (int) (color.getRed() * RED_WEIGHT +
                        color.getGreen() * GREEN_WEIGHT +
                        color.getBlue() * BLUE_WEIGHT);

                Color grayColor = new Color(gray, gray, gray, color.getAlpha());
                result.setRGB(x, y, grayColor.getRGB());
            }
        }

        return result;
    }

    @Override
    public String getFilterName() {
        return "Grayscale";
    }
}
