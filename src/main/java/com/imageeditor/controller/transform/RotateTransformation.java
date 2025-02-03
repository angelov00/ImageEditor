package com.imageeditor.controller.transform;

import com.imageeditor.controller.ImageAlgorithm;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;


public class RotateTransformation implements ImageAlgorithm {

    private double angle;

    public RotateTransformation(double angle) {
        this.angle = angle;
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        if (image == null) {
            return null;
        }

        double radians = Math.toRadians(angle);
        int w = image.getWidth();
        int h = image.getHeight();

        int newWidth = (int) Math.round(w * Math.abs(Math.cos(radians)) + h * Math.abs(Math.sin(radians)));
        int newHeight = (int) Math.round(h * Math.abs(Math.cos(radians)) + w * Math.abs(Math.sin(radians)));

        BufferedImage rotatedImage = new BufferedImage(newWidth, newHeight, image.getType());
        Graphics2D g2d = rotatedImage.createGraphics();

        /*
         В геометрията афинна трансформация (или афинно преобразувание) е вид линейна трансформация,
         която запазва колинеарността и пропорциите на дължините на отсечки, но не задължително дължините и ъглите.
         (транслация, ротация, мащабиране, срязване (shear))
        */
        AffineTransform transform = new AffineTransform();
        transform.translate((newWidth - w) / 2.0, (newHeight - h) / 2.0);
        transform.rotate(radians, w / 2.0, h / 2.0);
        g2d.setTransform(transform);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();

        return rotatedImage;
    }

    @Override
    public String getFilterName() {
        return "Rotate " + angle + " degrees";
    }
}
