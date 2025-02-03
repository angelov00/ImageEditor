package com.imageeditor.action.file;

import com.imageeditor.ui.component.ImagePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SaveFileAction extends AbstractAction {

    private final JFrame parent;
    private final ImagePanel imagePanel;

    public SaveFileAction(JFrame parent, ImagePanel imagePanel) {
        super("Save");
        this.parent = parent;
        this.imagePanel = imagePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (imagePanel.getImage() != null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Select a location to save the image");

            int result = fileChooser.showSaveDialog(parent);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();

                if (!filePath.endsWith(".png") && !filePath.endsWith(".jpg") && !filePath.endsWith(".jpeg")) {
                    selectedFile = new File(filePath + ".png");
                }

                try {
                    ImageIO.write(imagePanel.getImage(), "PNG", selectedFile);
                    JOptionPane.showMessageDialog(parent, "Image saved successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(parent, "Failed to save image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(parent, "No image to save", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
