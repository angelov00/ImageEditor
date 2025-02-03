package com.imageeditor.controller.view;

import com.imageeditor.ui.panel.ImagePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ZoomOutAction extends AbstractAction {

    private final ImagePanel imagePanel;

    public ZoomOutAction(ImagePanel imagePanel) {
        super("Zoom Out");
        this.imagePanel = imagePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.imagePanel.zoomOut();
    }
}
