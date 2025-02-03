package com.imageeditor.controller.view;

import com.imageeditor.ui.panel.ImagePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ZoomInAction extends AbstractAction {

    private final ImagePanel imagePanel;

    public ZoomInAction(ImagePanel imagePanel) {
        super("Zoom In");
        this.imagePanel = imagePanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.imagePanel.zoomIn();
    }
}
