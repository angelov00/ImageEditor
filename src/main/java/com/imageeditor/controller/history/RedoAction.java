package com.imageeditor.controller.history;

import com.imageeditor.history.ImageCaretaker;
import com.imageeditor.ui.panel.ImagePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractAction {

    private final ImagePanel imagePanel;
    private final ImageCaretaker imageCaretaker;

    public RedoAction(ImagePanel imagePanel, ImageCaretaker imageCaretaker) {
        super("Redo");
        this.imagePanel = imagePanel;
        this.imageCaretaker = imageCaretaker;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (imageCaretaker.canRedo()) {
            imageCaretaker.redo(imagePanel);
            imagePanel.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Nothing to redo", "Redo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}