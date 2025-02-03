package com.imageeditor.algorithm.filters;

import com.imageeditor.algorithm.core.ImageAlgorithm;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SepiaFilter implements ImageAlgorithm {

    private static final double NEW_RED_RED = 0.393;
    private static final double NEW_RED_GREEN = 0.769;
    private static final double NEW_RED_BLUE = 0.189;

    private static final double NEW_GREEN_RED = 0.349;
    private static final double NEW_GREEN_GREEN = 0.686;
    private static final double NEW_GREEN_BLUE = 0.168;

    private static final double NEW_BLUE_RED = 0.272;
    private static final double NEW_BLUE_GREEN = 0.534;
    private static final double NEW_BLUE_BLUE = 0.131;

    @Override
    public BufferedImage apply(BufferedImage image) {

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {

                Color currentColor = new Color(image.getRGB(x, y));

                int newRed = (int) (NEW_RED_RED * currentColor.getRed() +
                        NEW_RED_GREEN * currentColor.getGreen() +
                        NEW_RED_BLUE * currentColor.getBlue());

                int newGreen = (int) (NEW_GREEN_RED * currentColor.getRed() +
                        NEW_GREEN_GREEN * currentColor.getGreen() +
                        NEW_GREEN_BLUE * currentColor.getBlue());

                int newBlue = (int) (NEW_BLUE_RED * currentColor.getRed() +
                        NEW_BLUE_GREEN * currentColor.getGreen() +
                        NEW_BLUE_BLUE * currentColor.getBlue());

                newRed = Math.min(255, Math.max(0, newRed));
                newGreen = Math.min(255, Math.max(0, newGreen));
                newBlue = Math.min(255, Math.max(0, newBlue));


                Color newColor = new Color(newRed, newGreen, newBlue);
                result.setRGB(x, y, newColor.getRGB());

            }
        }

        return result;
    }

    @Override
    public String getFilterName() {
        return "Sepia";
    }
}
