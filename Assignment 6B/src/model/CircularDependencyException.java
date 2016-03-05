/*
 * Lisa Taylor
 * Jonah Howard
 * Henry Lai
 * John Bui
 * 
 * TCSS 342 - Spring 2016
 * Assignment 6B
 */

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
