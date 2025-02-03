package com.imageeditor.controller;

import java.awt.image.BufferedImage;

public interface ImageAlgorithm {
    BufferedImage apply(BufferedImage image);
    String getFilterName();
}
