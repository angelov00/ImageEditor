package com.imageeditor.ui.frame;

import com.imageeditor.controller.file.ExitAction;
import com.imageeditor.controller.filter.ApplyFilterAction;
import com.imageeditor.controller.filter.filters.EdgeDetectionFilter;
import com.imageeditor.controller.filter.filters.GrayscaleFilter;
import com.imageeditor.controller.filter.filters.InvertColorsFilter;
import com.imageeditor.controller.filter.filters.SepiaFilter;
import com.imageeditor.controller.history.RedoAction;
import com.imageeditor.controller.history.UndoAction;
import com.imageeditor.controller.file.OpenFileAction;
import com.imageeditor.controller.file.SaveFileAction;
import com.imageeditor.history.ImageCaretaker;
import com.imageeditor.ui.panel.ImagePanel;

import javax.swing.*;
import java.awt.*;

public class AppFrame extends JFrame {

    private static final int FRAME_WIDTH = 1280;
    private static final int FRAME_HEIGHT = 720;

    private final ImagePanel imagePanel;
    private final ImageCaretaker imageCaretaker;

    public AppFrame() {
        setTitle("Image Editor");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        imageCaretaker = new ImageCaretaker();
        imagePanel = new ImagePanel(imageCaretaker);

        add(imagePanel, BorderLayout.CENTER);
        createMenu();
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem(new OpenFileAction(this, imagePanel, imageCaretaker)));
        fileMenu.add(new JMenuItem(new SaveFileAction(this, imagePanel)));
        fileMenu.add(new JMenuItem(new ExitAction()));

        JMenu editMenu = new JMenu("Edit");
        editMenu.add(new JMenuItem(new UndoAction(imagePanel, imageCaretaker)));
        editMenu.add(new JMenuItem(new RedoAction(imagePanel, imageCaretaker)));

        JMenu filterMenu = new JMenu("Filter");
        filterMenu.add(new JMenuItem(new ApplyFilterAction(imagePanel, imageCaretaker, new GrayscaleFilter())));
        filterMenu.add(new JMenuItem(new ApplyFilterAction(imagePanel, imageCaretaker, new InvertColorsFilter())));
        filterMenu.add(new JMenuItem(new ApplyFilterAction(imagePanel, imageCaretaker, new SepiaFilter())));
        filterMenu.add(new JMenuItem(new ApplyFilterAction(imagePanel, imageCaretaker, new EdgeDetectionFilter())));

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(filterMenu);

        setJMenuBar(menuBar);
    }
}
