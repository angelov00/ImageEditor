package com.imageeditor.controller.filter;

import com.imageeditor.controller.ImageAlgorithm;
import com.imageeditor.history.ImageCaretaker;
import com.imageeditor.ui.panel.ImagePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

public class ApplyFilterAction extends AbstractAction {

    private final ImagePanel imagePanel;
    private final ImageCaretaker caretaker;
    private final ImageAlgorithm imageAlgorithm;

    public ApplyFilterAction(ImagePanel imagePanel, ImageCaretaker caretaker, ImageAlgorithm imageAlgorithm) {
        super(imageAlgorithm.getFilterName());
        this.imagePanel = imagePanel;
        this.caretaker = caretaker;
        this.imageAlgorithm = imageAlgorithm;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        BufferedImage image = imagePanel.getImage();
        if (image == null) {
            JOptionPane.showMessageDialog(null, "No image loaded.");
            return;
        }

        caretaker.saveState(imagePanel);
        BufferedImage filteredImage = imageAlgorithm.apply(image);
        imagePanel.setImage(filteredImage);
    }
}
