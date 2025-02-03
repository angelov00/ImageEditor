package com.imageeditor.algorithm.adjustments;

import com.imageeditor.algorithm.core.ParametrizedImageAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class BrightnessAdjustment implements ParametrizedImageAlgorithm {

    private float adjustmentValue = 0.0f;

    @Override
    public void showParameterDialog(Component parentComponent) {

        JSlider slider = new JSlider(JSlider.HORIZONTAL, -100, 100, (int) adjustmentValue);
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel("Adjust brightness:"), BorderLayout.NORTH);
        panel.add(slider, BorderLayout.CENTER);

        int result = JOptionPane.showConfirmDialog(parentComponent, panel, "Brightness",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            adjustmentValue = slider.getValue();
        }
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage result = new BufferedImage(width, height, image.getType());

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int alpha = (rgb >> 24) & 0xFF;
                int red   = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue  = rgb & 0xFF;

                int newRed   = clamp((int)(red + adjustmentValue));
                int newGreen = clamp((int)(green + adjustmentValue));
                int newBlue  = clamp((int)(blue + adjustmentValue));

                int newRgb = (alpha << 24) | (newRed << 16) | (newGreen << 8) | newBlue;
                result.setRGB(x, y, newRgb);
            }
        }
        return result;
    }

    private int clamp(int value) {
        return Math.max(0, Math.min(255, value));
    }


    @Override
    public String getFilterName() {
        return "Brightness Adjustment";
    }
}
