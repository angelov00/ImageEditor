package com.imageeditor.action.view;

import com.imageeditor.ui.component.ImagePanel;

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
