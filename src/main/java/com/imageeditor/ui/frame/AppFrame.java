package com.imageeditor.ui.frame;

import com.imageeditor.algorithm.adjustments.*;
import com.imageeditor.action.edit.CropAction;
import com.imageeditor.action.file.ExitAction;
import com.imageeditor.algorithm.core.ApplyImageAlgorithmAction;
import com.imageeditor.algorithm.filters.EdgeDetectionFilter;
import com.imageeditor.algorithm.filters.GrayscaleFilter;
import com.imageeditor.algorithm.filters.InvertColorsFilter;
import com.imageeditor.algorithm.filters.SepiaFilter;
import com.imageeditor.action.history.RedoAction;
import com.imageeditor.action.history.UndoAction;
import com.imageeditor.action.file.OpenFileAction;
import com.imageeditor.action.file.SaveFileAction;
import com.imageeditor.algorithm.transformations.FlipHorizontalTransformation;
import com.imageeditor.algorithm.transformations.FlipVerticalTransformation;
import com.imageeditor.algorithm.transformations.RotateTransformation;
import com.imageeditor.action.view.ZoomInAction;
import com.imageeditor.action.view.ZoomOutAction;
import com.imageeditor.history.ImageCaretaker;
import com.imageeditor.ui.component.ImagePanel;

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
        filterMenu.add(new JMenuItem(new ApplyImageAlgorithmAction(imagePanel, imageCaretaker, new GrayscaleFilter())));
        filterMenu.add(new JMenuItem(new ApplyImageAlgorithmAction(imagePanel, imageCaretaker, new InvertColorsFilter())));
        filterMenu.add(new JMenuItem(new ApplyImageAlgorithmAction(imagePanel, imageCaretaker, new SepiaFilter())));
        filterMenu.add(new JMenuItem(new ApplyImageAlgorithmAction(imagePanel, imageCaretaker, new EdgeDetectionFilter())));

        JMenu adjustmentsMenu = new JMenu("Adjustments");
        adjustmentsMenu.add(new JMenuItem(new ApplyImageAlgorithmAction(imagePanel, imageCaretaker, new BrightnessAdjustment())));
        adjustmentsMenu.add(new JMenuItem(new ApplyImageAlgorithmAction(imagePanel, imageCaretaker, new ContrastAdjustment())));
        adjustmentsMenu.add(new JMenuItem(new ApplyImageAlgorithmAction(imagePanel, imageCaretaker, new GammaCorrection())));
        adjustmentsMenu.add(new JMenuItem(new ApplyImageAlgorithmAction(imagePanel, imageCaretaker, new SaturationAdjustment())));
        adjustmentsMenu.add(new JMenuItem(new ApplyImageAlgorithmAction(imagePanel, imageCaretaker, new SharpnessAdjustment())));

        JMenu transformMenu = new JMenu("Transform");
        transformMenu.add(new JMenuItem(new ApplyImageAlgorithmAction(imagePanel, imageCaretaker, new FlipVerticalTransformation())));
        transformMenu.add(new JMenuItem(new ApplyImageAlgorithmAction(imagePanel, imageCaretaker, new FlipHorizontalTransformation())));
        transformMenu.add(new JMenuItem(new ApplyImageAlgorithmAction(imagePanel, imageCaretaker, new RotateTransformation(90))));
        transformMenu.add(new JMenuItem(new ApplyImageAlgorithmAction(imagePanel, imageCaretaker, new RotateTransformation(-90))));
        transformMenu.add(new JMenuItem(new CropAction(imagePanel, imageCaretaker)));

        JMenu viewMenu = new JMenu("View");
        viewMenu.add(new JMenuItem(new ZoomInAction(imagePanel)));
        viewMenu.add(new JMenuItem(new ZoomOutAction(imagePanel)));

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(filterMenu);
        menuBar.add(adjustmentsMenu);
        menuBar.add(transformMenu);
        menuBar.add(viewMenu);

        setJMenuBar(menuBar);
    }
}
