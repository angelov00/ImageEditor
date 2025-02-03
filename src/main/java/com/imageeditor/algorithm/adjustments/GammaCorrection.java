package com.imageeditor.algorithm.adjustments;

import com.imageeditor.algorithm.core.ParametrizedImageAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GammaCorrection implements ParametrizedImageAlgorithm {
    private double gammaValue = 1.0;

    @Override
    public BufferedImage apply(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                int r = (int) (Math.pow((double) ((rgb >> 16) & 0xff) / 255, gammaValue) * 255);
                int g = (int) (Math.pow((double) ((rgb >> 8) & 0xff) / 255, gammaValue) * 255);
                int b = (int) (Math.pow((double) (rgb & 0xff) / 255, gammaValue) * 255);
                result.setRGB(x, y, (rgb & 0xff000000) | (r << 16) | (g << 8) | b);
            }
        }
        return result;
    }

    @Override
    public String getFilterName() {
        return "Gamma Correction";
    }

    @Override
    public void showParameterDialog(Component parentComponent) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 1, 300, 100);
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        int result = JOptionPane.showConfirmDialog(parentComponent, slider, "Adjust Gamma", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            gammaValue = slider.getValue() / 100.0;
        }
    }
}