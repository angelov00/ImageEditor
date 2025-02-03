package com.imageeditor.history;

import com.imageeditor.ui.component.ImagePanel;

import java.util.Deque;
import java.util.LinkedList;

public class ImageCaretaker {

    private final Deque<ImageMemento> undoStack;
    private final Deque<ImageMemento> redoStack;
    private static final int MAX_HISTORY_SIZE = 64;

    public ImageCaretaker() {
        undoStack = new LinkedList<>();
        redoStack = new LinkedList<>();
    }

    public void saveState(ImagePanel imagePanel) {
        if (undoStack.size() >= MAX_HISTORY_SIZE) {
            undoStack.removeLast();
        }

        undoStack.push(imagePanel.createMemento());
        redoStack.clear();
    }

    public void undo(ImagePanel imagePanel) {
        if (!undoStack.isEmpty()) {
            ImageMemento memento = undoStack.pop();
            redoStack.push(imagePanel.createMemento());
            imagePanel.restoreFromMemento(memento);
        }
    }

    public void redo(ImagePanel imagePanel) {
        if (!redoStack.isEmpty()) {
            ImageMemento memento = redoStack.pop();
            undoStack.push(imagePanel.createMemento());
            imagePanel.restoreFromMemento(memento);
        }
    }

    public boolean canUndo() {
        return !this.undoStack.isEmpty();
    }

    public boolean canRedo() {
        return !this.redoStack.isEmpty();
    }

    public void clearHistory() {
        this.undoStack.clear();
        this.redoStack.clear();
    }
}
