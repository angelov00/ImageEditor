package com.imageeditor.algorithm.adjustments;

import com.imageeditor.algorithm.core.ParametrizedImageAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;

public class ContrastAdjustment implements ParametrizedImageAlgorithm {

    private float contrastValue = 1.0f;

    @Override
    public BufferedImage apply(BufferedImage image) {
        RescaleOp rescaleOp = new RescaleOp(contrastValue, 128 * (1 - contrastValue), null);
        return rescaleOp.filter(image, null);
    }

    @Override
    public String getFilterName() {
        return "Contrast Adjustment";
    }

    @Override
    public void showParameterDialog(Component parentComponent) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        slider.setMajorTickSpacing(50);
        slider.setMinorTickSpacing(10);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        int result = JOptionPane.showConfirmDialog(parentComponent, slider, "Adjust Contrast", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            contrastValue = slider.getValue() / 100.0f;
        }
    }
}