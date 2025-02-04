package com.imageeditor.action.history;

import com.imageeditor.history.ImageCaretaker;
import com.imageeditor.ui.component.ImagePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class UndoAction extends AbstractAction {

    private final ImagePanel imagePanel;
    private final ImageCaretaker imageCaretaker;

    public UndoAction(ImagePanel imagePanel) {
        super("Undo");
        this.imagePanel = imagePanel;
        this.imageCaretaker = imagePanel.getCaretaker();
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