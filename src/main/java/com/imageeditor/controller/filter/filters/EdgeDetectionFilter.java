package com.imageeditor.controller.filter.filters;

import com.imageeditor.controller.ImageAlgorithm;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EdgeDetectionFilter implements ImageAlgorithm {

    private static final int[][] SOBEL_X = {
            {-1, 0, 1},
            {-2, 0, 2},
            {-1, 0, 1}
    };

    private static final int[][] SOBEL_Y = {
            {-1, -2, -1},
            { 0,  0,  0},
            { 1,  2,  1}
    };

    private final GrayscaleFilter grayscaleFilter;

    public EdgeDetectionFilter() {
        this.grayscaleFilter = new GrayscaleFilter();
    }

    @Override
    public BufferedImage apply(BufferedImage image) {

        BufferedImage grayImage = grayscaleFilter.apply(image);

        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());

        for (int x = 1; x < image.getWidth() - 1; x++) {
            for (int y = 1; y < image.getHeight() - 1; y++) {

                int gx = 0;
                int gy = 0;

                for (int ky = -1; ky <= 1; ky++) {
                    for (int kx = -1; kx <= 1; kx++) {
                        Color neighborColor = new Color(grayImage.getRGB(x + kx, y + ky));
                        int intensity = neighborColor.getRed();
                        gx += intensity * SOBEL_X[ky + 1][kx + 1];
                        gy += intensity * SOBEL_Y[ky + 1][kx + 1];
                    }
                }

                int edgeStrength = (int) Math.sqrt(gx * gx + gy * gy);
                edgeStrength = Math.min(255, edgeStrength);
                Color edgeColor = new Color(edgeStrength, edgeStrength, edgeStrength);
                result.setRGB(x, y, edgeColor.getRGB());
            }

        }

        return result;
    }

    @Override
    public String getFilterName() {
        return "Edge detection";
    }
}
