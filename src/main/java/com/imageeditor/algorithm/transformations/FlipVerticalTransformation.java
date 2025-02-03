package com.imageeditor.algorithm.transformations;

import com.imageeditor.algorithm.core.ImageAlgorithm;

import java.awt.image.BufferedImage;

public class FlipVerticalTransformation implements ImageAlgorithm {

    @Override
    public BufferedImage apply(BufferedImage image) {

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        int height = image.getHeight();
        int width = image.getWidth();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int currentColor = image.getRGB(x, y);
                result.setRGB(x, height - y - 1, currentColor);
            }
        }

        return result;
    }

    @Override
    public String getFilterName() {
        return "Flip Vertical";
    }
}
