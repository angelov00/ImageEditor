package com.imageeditor.controller.transform;

import com.imageeditor.controller.ImageAlgorithm;

import java.awt.image.BufferedImage;

public class FlipHorizontalTransformation implements ImageAlgorithm {

    @Override
    public BufferedImage apply(BufferedImage image) {

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        int height = image.getHeight();
        int width = image.getWidth();

        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int currentColor = image.getRGB(x, y);
                result.setRGB(width - x - 1, y, currentColor);
            }
        }

        return result;
    }

    @Override
    public String getFilterName() {
        return "Flip Horizontal";
    }
}
