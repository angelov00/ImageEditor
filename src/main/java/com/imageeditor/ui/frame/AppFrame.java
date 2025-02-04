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

    public AppFrame() {
        setDarkTheme();

        setTitle("Image Editor - Dark Mode");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageCaretaker imageCaretaker = new ImageCaretaker();
        imagePanel = new ImagePanel(imageCaretaker);
        imagePanel.setBackground(new Color(30, 30, 30));

        add(imagePanel, BorderLayout.CENTER);
        createMenu();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void setDarkTheme() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }

            Color darkBackground = new Color(40, 40, 40);
            Color darkerBackground = new Color(30, 30, 30);
            Color textColor = new Color(200, 200, 200);
            Color selectionColor = new Color(85, 170, 255);

            UIManager.put("control", darkBackground);
            UIManager.put("info", darkBackground);
            UIManager.put("nimbusBase", darkerBackground);
            UIManager.put("nimbusAlertYellow", new Color(255, 220, 35));
            UIManager.put("nimbusDisabledText", new Color(100, 100, 100));
            UIManager.put("nimbusFocus", selectionColor);
            UIManager.put("nimbusGreen", new Color(35, 190, 35));
            UIManager.put("nimbusInfoBlue", selectionColor);
            UIManager.put("nimbusLightBackground", darkerBackground);
            UIManager.put("nimbusOrange", new Color(255, 140, 0));
            UIManager.put("nimbusRed", new Color(255, 80, 80));
            UIManager.put("nimbusSelectedText", Color.WHITE);
            UIManager.put("nimbusSelectionBackground", selectionColor);
            UIManager.put("text", textColor);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void createMenu() {
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(25, 25, 25));
        menuBar.setForeground(Color.WHITE);

        Font menuFont = new Font("Arial", Font.BOLD, 12);

        JMenu fileMenu = createDarkMenu("File", menuFont);
        fileMenu.add(createMenuItem(new OpenFileAction(this, imagePanel), menuFont));
        fileMenu.add(createMenuItem(new SaveFileAction(this, imagePanel), menuFont));
        fileMenu.add(createMenuItem(new ExitAction(), menuFont));
        menuBar.add(fileMenu);

        JMenu editMenu = createDarkMenu("Edit", menuFont);
        editMenu.add(createMenuItem(new UndoAction(imagePanel), menuFont));
        editMenu.add(createMenuItem(new RedoAction(imagePanel), menuFont));
        menuBar.add(editMenu);

        JMenu filterMenu = createDarkMenu("Filter", menuFont);
        filterMenu.add(createMenuItem(new ApplyImageAlgorithmAction(imagePanel, new SepiaFilter()), menuFont));
        filterMenu.add(createMenuItem(new ApplyImageAlgorithmAction(imagePanel, new GrayscaleFilter()), menuFont));
        filterMenu.add(createMenuItem(new ApplyImageAlgorithmAction(imagePanel, new InvertColorsFilter()), menuFont));
        filterMenu.add(createMenuItem(new ApplyImageAlgorithmAction(imagePanel, new EdgeDetectionFilter()), menuFont));
        menuBar.add(filterMenu);

        JMenu adjustmentsMenu = createDarkMenu("Adjustments", menuFont);
        adjustmentsMenu.add(createMenuItem(new ApplyImageAlgorithmAction(imagePanel, new BrightnessAdjustment()), menuFont));
        adjustmentsMenu.add(createMenuItem(new ApplyImageAlgorithmAction(imagePanel, new ContrastAdjustment()), menuFont));
        adjustmentsMenu.add(createMenuItem(new ApplyImageAlgorithmAction(imagePanel, new GammaCorrection()), menuFont));
        adjustmentsMenu.add(createMenuItem(new ApplyImageAlgorithmAction(imagePanel, new SaturationAdjustment()), menuFont));
        adjustmentsMenu.add(createMenuItem(new ApplyImageAlgorithmAction(imagePanel, new SharpnessAdjustment()), menuFont));
        menuBar.add(adjustmentsMenu);

        JMenu transformMenu = createDarkMenu("Transform", menuFont);
        transformMenu.add(createMenuItem(new ApplyImageAlgorithmAction(imagePanel, new FlipVerticalTransformation()), menuFont));
        transformMenu.add(createMenuItem(new ApplyImageAlgorithmAction(imagePanel, new FlipHorizontalTransformation()), menuFont));
        transformMenu.add(createMenuItem(new ApplyImageAlgorithmAction(imagePanel, new RotateTransformation(90)), menuFont));
        transformMenu.add(createMenuItem(new ApplyImageAlgorithmAction(imagePanel, new RotateTransformation(-90)), menuFont));
        transformMenu.add(createMenuItem(new CropAction(imagePanel), menuFont));
        menuBar.add(transformMenu);

        JMenu viewMenu = createDarkMenu("View", menuFont);
        viewMenu.add(createMenuItem(new ZoomInAction(imagePanel), menuFont));
        viewMenu.add(createMenuItem(new ZoomOutAction(imagePanel), menuFont));
        menuBar.add(viewMenu);

        setJMenuBar(menuBar);
    }

    private JMenu createDarkMenu(String name, Font font) {
        JMenu menu = new JMenu(name);
        menu.setForeground(Color.WHITE);
        menu.setFont(font);
        menu.setMargin(new Insets(6, 4, 6, 4));
        return menu;
    }

    private JMenuItem createMenuItem(Action action, Font font) {
        JMenuItem menuItem = new JMenuItem(action);
        menuItem.setFont(font);
        menuItem.setMargin(new Insets(5, 5, 5, 5));
        menuItem.setForeground(Color.WHITE);
        return menuItem;
    }
}
