package com.imageeditor.algorithm.core;

import com.imageeditor.ui.component.ImagePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class ApplyImageAlgorithmAction extends AbstractAction {

    private final ImagePanel imagePanel;
    private final ImageAlgorithm imageAlgorithm;

    public ApplyImageAlgorithmAction(ImagePanel imagePanel,  ImageAlgorithm imageAlgorithm) {
        super(imageAlgorithm.getFilterName());
        this.imagePanel = imagePanel;
        this.imageAlgorithm = imageAlgorithm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image = imagePanel.getImage();
        if (image == null) {
            JOptionPane.showMessageDialog(null, "No image loaded.");
            return;
        }

        if (imageAlgorithm instanceof ParametrizedImageAlgorithm) {
            ((ParametrizedImageAlgorithm) imageAlgorithm).showParameterDialog(imagePanel);
        }

        this.imagePanel.saveState();
        BufferedImage filteredImage = imageAlgorithm.apply(image);
        imagePanel.setImage(filteredImage);
    }
}
