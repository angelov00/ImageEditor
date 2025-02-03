package com.imageeditor.algorithm.filters;

import com.imageeditor.algorithm.core.ImageAlgorithm;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InvertColorsFilter implements ImageAlgorithm {
    @Override
    public BufferedImage apply(BufferedImage image) {

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {

                Color currColor = new Color(image.getRGB(x, y), true);
                Color invertedColor = new Color(255 - currColor.getRed(), 255 - currColor.getGreen(), 255 - currColor.getBlue());
                result.setRGB(x, y, invertedColor.getRGB());
            }
        }

        return result;
    }

    @Override
    public String getFilterName() {
        return "Invert colors";
    }
}

