package com.imageeditor.ui.component;

import com.imageeditor.history.ImageCaretaker;
import com.imageeditor.history.ImageMemento;
import com.imageeditor.utils.CropUtil;
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

    private boolean croppingMode = false;
    private Point cropStart;
    private Rectangle selectionRectangle;

    public ImagePanel(ImageCaretaker imageCaretaker) {
        this.imageCaretaker = imageCaretaker;
        this.zoomManager = new ImageZoomManager();

        addMouseWheelListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);

        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    cropSelection();
                    disableCroppingMode();
                }
            }
        });
    }

    private void cropSelection() {
        if (image == null || selectionRectangle == null) return;

        Point imgStart = transformToImageCoordinates(new Point(selectionRectangle.x, selectionRectangle.y));
        Point imgEnd = transformToImageCoordinates(new Point(selectionRectangle.x + selectionRectangle.width, selectionRectangle.y + selectionRectangle.height));

        int cropX = Math.max(0, imgStart.x);
        int cropY = Math.max(0, imgStart.y);
        int cropWidth = Math.min(image.getWidth() - cropX, imgEnd.x - imgStart.x);
        int cropHeight = Math.min(image.getHeight() - cropY, imgEnd.y - imgStart.y);

        if (cropWidth <= 0 || cropHeight <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid crop selection.", "Crop Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BufferedImage croppedImage = image.getSubimage(cropX, cropY, cropWidth, cropHeight);
        setImage(croppedImage);
        selectionRectangle = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (image == null) {
            drawNoImageMessage(g);
        } else {
            drawScaledImage(g);
            if (croppingMode && selectionRectangle != null) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLUE);
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(selectionRectangle);
            }
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
        if (this.image != null) {
            saveState();
        }
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

    public void enableCroppingMode() {
        if (image == null) {
            JOptionPane.showMessageDialog(this, "No image loaded.", "Crop Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        croppingMode = true;
        selectionRectangle = null;
        setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
        repaint();
    }

    public void disableCroppingMode() {
        System.out.println("in disable");
        croppingMode = false;
        selectionRectangle = null;
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        repaint();
    }

    public boolean isCroppingMode() {
        return croppingMode;
    }

    public void resetCropSelection() {
        selectionRectangle = null;
        repaint();
    }

    public void finalizeCrop() {
        if (selectionRectangle != null && selectionRectangle.width > 0 && selectionRectangle.height > 0) {
            cropSelectedArea();
        } else {
            JOptionPane.showMessageDialog(null, "No valid selection made.", "Crop Error", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println("DISABLE TO BE CALLED!");
        disableCroppingMode();
    }

    public void cropSelectedArea() {

        System.out.println("in cropSelectedArea");

        if (image == null || selectionRectangle == null) {
            JOptionPane.showMessageDialog(this, "No image or valid selection.", "Crop Error", JOptionPane.ERROR_MESSAGE);
            disableCroppingMode(); // Добавяме това
            return;
        }

        saveState();

        Point start = transformToImageCoordinates(new Point(selectionRectangle.x, selectionRectangle.y));
        Point end = transformToImageCoordinates(new Point(selectionRectangle.x + selectionRectangle.width, selectionRectangle.y + selectionRectangle.height));

        int x = Math.max(0, start.x);
        int y = Math.max(0, start.y);
        int width = Math.min(image.getWidth() - x, end.x - start.x);
        int height = Math.min(image.getHeight() - y, end.y - start.y);

        if (width <= 0 || height <= 0) {
            JOptionPane.showMessageDialog(this, "Invalid crop selection.", "Crop Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Rectangle cropRect = new Rectangle(x, y, width, height);
        BufferedImage cropped = CropUtil.cropImage(image, cropRect);

        setImage(cropped);
        disableCroppingMode();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (croppingMode) {
            cropStart = e.getPoint();
            selectionRectangle = new Rectangle(cropStart);
        } else {
            dragStart = e.getPoint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (!croppingMode) {
            dragStart = null;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (croppingMode) {
            Point current = e.getPoint();
            int x = Math.min(cropStart.x, current.x);
            int y = Math.min(cropStart.y, current.y);
            int width = Math.abs(cropStart.x - current.x);
            int height = Math.abs(cropStart.y - current.y);
            selectionRectangle = new Rectangle(x, y, width, height);
            repaint();
        } else if (dragStart != null && zoomManager.getZoomFactor() > 1.0) {
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

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mouseMoved(MouseEvent e) {}

    public BufferedImage getImage() {
        return this.image;
    }


    private Point transformToImageCoordinates(Point screenPoint) {
        if (image == null) return screenPoint;

        double zoom = zoomManager.getZoomFactor();
        BufferedImage scaledImage = zoomManager.scaleImage(image);

        int imageX = (int) ((screenPoint.x - (getWidth() - scaledImage.getWidth()) / 2 + viewPosition.x) / zoom);
        int imageY = (int) ((screenPoint.y - (getHeight() - scaledImage.getHeight()) / 2 + viewPosition.y) / zoom);

        return new Point(imageX, imageY);
    }


}
