package com.imageeditor;

import com.imageeditor.ui.frame.AppFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppFrame::new);
    }
}