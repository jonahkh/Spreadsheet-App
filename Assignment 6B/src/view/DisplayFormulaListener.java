package view;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import model.Spreadsheet;

public class DisplayFormulaListener extends AbstractAction {

	/** The current Spreadsheet. */
	private final Spreadsheet mySpreadsheet;
	
	/**
	 * Initializes a new display formula action listener.
	 * 
	 * @param theSpreadsheet the current spreadsheet
	 */
	public DisplayFormulaListener(final Spreadsheet theSpreadsheet) {
		mySpreadsheet = theSpreadsheet;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
//		for ()
		
	}

}
