package com.imageeditor.algorithm.adjustments;

import com.imageeditor.algorithm.core.ParametrizedImageAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

public class SharpnessAdjustment implements ParametrizedImageAlgorithm {
    private float sharpnessValue = 0.0f;

    @Override
    public BufferedImage apply(BufferedImage image) {
        float[] sharpenMatrix = {
                0.0f, -sharpnessValue, 0.0f,
                -sharpnessValue, 1 + 4 * sharpnessValue, -sharpnessValue,
                0.0f, -sharpnessValue, 0.0f
        };
        Kernel kernel = new Kernel(3, 3, sharpenMatrix);
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return op.filter(image, null);
    }

    @Override
    public String getFilterName() {
        return "Sharpness Adjustment";
    }

    @Override
    public void showParameterDialog(Component parentComponent) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 100, 0);
        slider.setMajorTickSpacing(25);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        int result = JOptionPane.showConfirmDialog(parentComponent, slider, "Adjust Sharpness", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            sharpnessValue = slider.getValue() / 100.0f;
        }
    }
}