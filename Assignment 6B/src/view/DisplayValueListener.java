package view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import model.Spreadsheet;

public class DisplayValueListener extends AbstractAction {
	
	/** The current Spreadsheet. */
	private final Spreadsheet mySpreadsheet;
	
	/**
	 * Initializes a new display formula action listener.
	 * 
	 * @param theSpreadsheet the current spreadsheet
	 */
	public DisplayValueListener(final Spreadsheet theSpreadsheet) {
		mySpreadsheet = theSpreadsheet;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

}
