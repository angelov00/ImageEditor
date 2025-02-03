package com.imageeditor.utils;

import java.awt.image.BufferedImage;

public class ImageZoomManager {

    private double zoomFactor = 1.0;
    private static final double ZOOM_STEP = 0.1;
    private static final double MAX_ZOOM = 2.0;
    private static final double MIN_ZOOM = 0.5;

      public double getZoomFactor() {
        return zoomFactor;
    }

    public void zoomIn() {
        if (Double.compare(zoomFactor, MAX_ZOOM) < 0) {
            zoomFactor = Math.min(zoomFactor + ZOOM_STEP, MAX_ZOOM);
        }
    }

    public void zoomOut() {
        if (Double.compare(zoomFactor, MIN_ZOOM) > 0) {
            zoomFactor = Math.max(zoomFactor - ZOOM_STEP, MIN_ZOOM);
        }
    }

    public BufferedImage scaleImage(BufferedImage image) {
        if (Double.compare(zoomFactor, 1.0) == 0) {
            return image;
        }
        int newWidth = (int) Math.round(image.getWidth() * zoomFactor);
        int newHeight = (int) Math.round(image.getHeight() * zoomFactor);
        BufferedImage scaledImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        scaledImage.getGraphics().drawImage(image, 0, 0, newWidth, newHeight, null);
        return scaledImage;
    }

    public boolean zoom(int wheelRotation) {
        double oldZoom = zoomFactor;
        if (wheelRotation < 0) {
            zoomIn();
        } else {
            zoomOut();
        }
        return oldZoom != zoomFactor;
    }
}
