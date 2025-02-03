package com.imageeditor.action.file;

import com.imageeditor.ui.component.ImagePanel;
import com.imageeditor.history.ImageCaretaker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class OpenFileAction extends AbstractAction {

    private final JFrame parent;
    private final ImagePanel imagePanel;
    private final ImageCaretaker imageCaretaker;

    public OpenFileAction(JFrame parent, ImagePanel imagePanel, ImageCaretaker imageCaretaker) {
        super("Open");
        this.parent = parent;
        this.imagePanel = imagePanel;
        this.imageCaretaker = imageCaretaker;

        putValue(ACCELERATOR_KEY, KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select an image to open");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Image Files", "png", "jpg", "jpeg", "gif", "bmp"));

        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            try {
                BufferedImage image = ImageIO.read(selectedFile);
                imagePanel.setImage(image);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(parent, "Failed to open image: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
