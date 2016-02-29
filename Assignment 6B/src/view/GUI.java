package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Spreadsheet;

public class GUI {
	private final JFrame myFrame;
	private final JScrollPane myJScrollPane;
	private final Spreadsheet mySpreadsheet;

	public GUI() {
		myFrame = new JFrame();
		mySpreadsheet = new Spreadsheet();
		myJScrollPane = new JScrollPane(mySpreadsheet.getTable(), 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		myFrame.add(myJScrollPane, BorderLayout.CENTER);
		myFrame.pack();
	}
	
	
	public void run() {
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		myFrame.setVisible(true);
	}
	
	public static void main(String... args) {
		GUI gui = new GUI();
		gui.run();
	}
}
