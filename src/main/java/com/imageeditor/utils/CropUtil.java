package com.imageeditor.utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CropUtil {

    public static BufferedImage cropImage(BufferedImage src, Rectangle cropArea) {
        int x = Math.max(0, cropArea.x);
        int y = Math.max(0, cropArea.y);
        int width = Math.min(cropArea.width, src.getWidth() - x);
        int height = Math.min(cropArea.height, src.getHeight() - y);

        return src.getSubimage(x, y, width, height);
    }
}
