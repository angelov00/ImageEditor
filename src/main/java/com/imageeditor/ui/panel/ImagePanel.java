package com.imageeditor.ui.panel;

import com.imageeditor.history.ImageCaretaker;
import com.imageeditor.history.ImageMemento;
import com.imageeditor.utils.ImageZoomManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener {

    private BufferedImage image;
    private final ImageZoomManager zoomManager;
    private final ImageCaretaker imageCaretaker;

    private Point dragStart;
    private Point viewPosition = new Point(0, 0);
    private Point zoomCenter = new Point(0, 0);

    public ImagePanel(ImageCaretaker imageCaretaker) {
        this.imageCaretaker = imageCaretaker;
        this.zoomManager = new ImageZoomManager();

        addMouseWheelListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null) {
            drawNoImageMessage(g);
        } else {
            drawScaledImage(g);
        }
    }

    private void drawNoImageMessage(Graphics g) {
        final String message = "No image selected";
        FontMetrics fontMetrics = g.getFontMetrics();
        int textWidth = fontMetrics.stringWidth(message);
        int textHeight = fontMetrics.getHeight();
        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() - textHeight) / 2 + fontMetrics.getAscent();
        g.drawString(message, x, y);
    }

    private void drawScaledImage(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        BufferedImage scaledImage = zoomManager.scaleImage(image);
        int x = (getWidth() - scaledImage.getWidth()) / 2 - viewPosition.x;
        int y = (getHeight() - scaledImage.getHeight()) / 2 - viewPosition.y;

        g2d.drawImage(scaledImage, x, y, this);
    }

    public void setImage(BufferedImage image) {
        this.image = image;
        resetView();
        updatePanelSize();
        repaint();
    }

    private void resetView() {
        viewPosition.setLocation(0, 0);
        zoomCenter.setLocation(getWidth() / 2, getHeight() / 2);
    }

    private void updatePanelSize() {
        if (image != null) {
            setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
            revalidate();
        }
    }

    public void zoomIn() {
        zoomManager.zoomIn();
        repaint();
    }

    public void zoomOut() {
        zoomManager.zoomOut();
        repaint();
    }

    private void updateImage(int wheelRotation) {
        if (image != null) {
            double oldZoom = zoomManager.getZoomFactor();
            boolean zoomChanged = zoomManager.zoom(wheelRotation);

            if (zoomChanged) {
                updateViewPosition(oldZoom);
            }

            repaint();
        }
    }

    private void updateViewPosition(double oldZoom) {
        double newZoom = zoomManager.getZoomFactor();
        double zoomRatio = newZoom / oldZoom;

        int newX = (int) ((zoomCenter.x - viewPosition.x) * (1 - zoomRatio) + viewPosition.x);
        int newY = (int) ((zoomCenter.y - viewPosition.y) * (1 - zoomRatio) + viewPosition.y);

        viewPosition.setLocation(newX, newY);
        constrainViewPosition();
    }

    private void constrainViewPosition() {
        if (image != null) {
            BufferedImage scaledImage = zoomManager.scaleImage(image);
            int maxX = Math.max(0, (scaledImage.getWidth() - getWidth()) / 2);
            int maxY = Math.max(0, (scaledImage.getHeight() - getHeight()) / 2);

            viewPosition.x = Math.max(-maxX, Math.min(maxX, viewPosition.x));
            viewPosition.y = Math.max(-maxY, Math.min(maxY, viewPosition.y));
        }
    }

    public ImageMemento createMemento() {
        return new ImageMemento(image);
    }

    public void restoreFromMemento(ImageMemento memento) {
        this.image = memento.getImage();
        repaint();
    }

    private void saveState() {
        if (imageCaretaker != null) {
            imageCaretaker.saveState(this);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dragStart = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        dragStart = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragStart != null && zoomManager.getZoomFactor() > 1.0) {
            Point current = e.getPoint();
            int dx = dragStart.x - current.x;
            int dy = dragStart.y - current.y;
            viewPosition.translate(dx, dy);
            dragStart = current;
            constrainViewPosition();
            repaint();
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (image != null) {
            zoomCenter = e.getPoint();
            updateImage(e.getWheelRotation());
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
    @Override
    public void mouseMoved(MouseEvent e) {}

    public BufferedImage getImage() {
        return this.image;
    }
}
