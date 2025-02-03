package com.imageeditor.utils;

import java.awt.image.BufferedImage;

public class ImageZoomManager {

    private double zoomFactor = 1.0;
    private static final double ZOOM_STEP = 0.1;
    private static final double MAX_ZOOM = 2.0;
    private static final double MIN_ZOOM = 0.5;

    /**
     * Връща текущия зум фактор.
     */
    public double getZoomFactor() {
        return zoomFactor;
    }

    /**
     * Увеличава зум фактора, ако текущата стойност е под MAX_ZOOM.
     * Използваме Double.compare за точно сравнение на double числа.
     */
    public void zoomIn() {
        if (Double.compare(zoomFactor, MAX_ZOOM) < 0) {
            // Запазваме, че зум факторът никога не надминава MAX_ZOOM
            zoomFactor = Math.min(zoomFactor + ZOOM_STEP, MAX_ZOOM);
        }
    }

    /**
     * Намалява зум фактора, ако текущата стойност е над MIN_ZOOM.
     */
    public void zoomOut() {
        if (Double.compare(zoomFactor, MIN_ZOOM) > 0) {
            // Запазваме, че зум факторът никога не пада под MIN_ZOOM
            zoomFactor = Math.max(zoomFactor - ZOOM_STEP, MIN_ZOOM);
        }
    }

    /**
     * Връща нов BufferedImage, който е мащабиран според текущия zoomFactor.
     * Ако zoomFactor е 1.0 (т.е. няма промяна), се връща оригиналното изображение.
     */
    public BufferedImage scaleImage(BufferedImage image) {
        // Ако няма промяна в зума, връщаме оригиналното изображение
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
