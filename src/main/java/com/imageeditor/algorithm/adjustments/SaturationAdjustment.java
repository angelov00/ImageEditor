package com.imageeditor.algorithm.adjustments;

import com.imageeditor.algorithm.core.ParametrizedImageAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class SaturationAdjustment implements ParametrizedImageAlgorithm {
    private float saturationValue = 1.0f;

    @Override
    public BufferedImage apply(BufferedImage image) {
        BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                int rgb = image.getRGB(x, y);
                float[] hsb = Color.RGBtoHSB((rgb >> 16) & 0xff, (rgb >> 8) & 0xff, rgb & 0xff, null);
                hsb[1] = Math.min(1.0f, hsb[1] * saturationValue);
                result.setRGB(x, y, Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
            }
        }
        return result;
    }

    @Override
    public String getFilterName() {
        return "Saturation Adjustment";
    }

    @Override
    public void showParameterDialog(Component parentComponent) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        int result = JOptionPane.showConfirmDialog(parentComponent, slider, "Adjust Saturation", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            saturationValue = slider.getValue() / 100.0f;
        }
    }
}