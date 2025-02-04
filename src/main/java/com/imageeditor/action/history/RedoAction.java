package com.imageeditor.action.history;

import com.imageeditor.history.ImageCaretaker;
import com.imageeditor.ui.component.ImagePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RedoAction extends AbstractAction {

    private final ImagePanel imagePanel;
    private final ImageCaretaker imageCaretaker;

    public RedoAction(ImagePanel imagePanel) {
        super("Redo");
        this.imagePanel = imagePanel;
        this.imageCaretaker = imagePanel.getCaretaker();
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