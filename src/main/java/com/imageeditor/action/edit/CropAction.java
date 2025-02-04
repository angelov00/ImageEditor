package com.imageeditor.action.edit;

import com.imageeditor.ui.component.ImagePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CropAction extends AbstractAction {

    private static final String CROP_INSTRUCTIONS = "Select the area to crop with the mouse.\nThen press the Enter button again to crop.";

    private final ImagePanel imagePanel;

    public CropAction(ImagePanel imagePanel) {
        super("Crop");
        this.imagePanel = imagePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isImageLoaded()) {
            showNoImageError();
            return;
        }

        if (!imagePanel.isCroppingMode()) {
            startCroppingMode();
        }
    }

    private boolean isImageLoaded() {
        return imagePanel.getImage() != null;
    }

    private void showNoImageError() {
        JOptionPane.showMessageDialog(null, "No image loaded.", "Crop Error", JOptionPane.ERROR_MESSAGE);
    }

    private void startCroppingMode() {
        imagePanel.enableCroppingMode();
        JOptionPane.showMessageDialog(null, CROP_INSTRUCTIONS, "Crop Instructions", JOptionPane.INFORMATION_MESSAGE);
    }

}
