/* Bui, John
 * Howard, Jonah
 * Lai, Henry
 * Taylor, Lisa
 * TCSS 342 - Data Structures
 * Professor Donald Chinn
 * Homework 6B
 * March 8, 2016
 */

package model;

/**
 * Exception class for circular dependency.
 */
public class CircularDependencyException extends Exception {
    public CircularDependencyException() {
    }

    public CircularDependencyException(String message) {
        super(message);
    }
}
