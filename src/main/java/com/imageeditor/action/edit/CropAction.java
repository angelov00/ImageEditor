package com.imageeditor.action.edit;

import com.imageeditor.history.ImageCaretaker;
import com.imageeditor.ui.component.ImagePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CropAction extends AbstractAction {

    private static final String CROP_INSTRUCTIONS = "Select the area to crop with the mouse.\nThen press the 'Crop' button again to crop.";
    private static final String CONFIRM_CROP_MESSAGE = "Do you want to crop the selected area?";

    private final ImagePanel imagePanel;
    private final ImageCaretaker caretaker;

    public CropAction(ImagePanel imagePanel, ImageCaretaker caretaker) {
        super("Crop");
        this.imagePanel = imagePanel;
        this.caretaker = caretaker;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!isImageLoaded()) {
            showNoImageError();
            return;
        }

        if (!imagePanel.isCroppingMode()) {
            startCroppingMode();
        } else {
            handleCropConfirmation();
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

    private void handleCropConfirmation() {
        int result = JOptionPane.showConfirmDialog(null, CONFIRM_CROP_MESSAGE, "Confirm Crop", JOptionPane.YES_NO_CANCEL_OPTION);
        switch (result) {
            case JOptionPane.YES_OPTION:
                imagePanel.finalizeCrop();
                break;
            case JOptionPane.NO_OPTION:
                imagePanel.resetCropSelection();
                break;
            case JOptionPane.CANCEL_OPTION:
            case JOptionPane.CLOSED_OPTION:
                imagePanel.disableCroppingMode();
                break;
        }
    }
}
