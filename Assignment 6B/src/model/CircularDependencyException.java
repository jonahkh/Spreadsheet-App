package model;

/**
 * Exception class for circular dependency.
 *
 * @author Lisa Taylor
 * @version 3 March 2016
 */
public class CircularDependencyException extends Exception {
    public CircularDependencyException() {
    }

    public CircularDependencyException(String message) {
        super(message);
    }
}
