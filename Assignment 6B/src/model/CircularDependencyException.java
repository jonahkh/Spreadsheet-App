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
	
    /** A generated serial version UID. */
	private static final long serialVersionUID = 1754962508847115076L;

	/**
	 * Initialize a new CircularDependencyException.
	 */
	public CircularDependencyException() {
    }

	/**
	 * Initializes a new CircularDependencyException.
	 * 
	 * @param message the message to be displayed when this exception occurs.
	 */
    public CircularDependencyException(final String message) {
        super(message);
    }
}
