package com.bertolozi.Exceptions;

public class SetLookAndFeelException extends Exception {
    public SetLookAndFeelException() {
        super("The application failed to set the look and feel. It probably may be an error within Java Swing. " +
                "Please try again.");
    }
}
