package com.ZUNr1.ui.validation;

import javafx.scene.Node;

public class ValidationResult {
    private final boolean valid;
    private final String message;
    private final Node focusNode;

    private ValidationResult(boolean valid, String message, Node focusNode) {
        this.valid = valid;
        this.message = message;
        this.focusNode = focusNode;
    }

    public static ValidationResult success() {
        return new ValidationResult(true, null, null);
    }

    public static ValidationResult failure(String message, Node focusNode) {
        return new ValidationResult(false, message, focusNode);
    }

    // Getter方法
    public boolean isValid() { return valid; }
    public String getMessage() { return message; }
    public Node getFocusNode() { return focusNode; }
}