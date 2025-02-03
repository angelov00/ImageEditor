package com.imageeditor.controller.history;

import com.imageeditor.history.ImageCaretaker;
import com.imageeditor.ui.panel.ImagePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction {

    private final ImagePanel imagePanel;
    private final ImageCaretaker imageCaretaker;

    public UndoAction(ImagePanel imagePanel, ImageCaretaker imageCaretaker) {
        super("Undo");
        this.imagePanel = imagePanel;
        this.imageCaretaker = imageCaretaker;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (imageCaretaker.canUndo()) {
            imageCaretaker.undo(imagePanel);
            imagePanel.repaint();
        } else {
            JOptionPane.showMessageDialog(null, "Nothing to undo", "Undo", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}