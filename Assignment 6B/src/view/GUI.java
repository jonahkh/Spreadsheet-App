package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.Spreadsheet;

public class GUI {
	private final JFrame myFrame;
	private final Spreadsheet mySpreadsheet;

	public GUI() {
		myFrame = new JFrame();
		mySpreadsheet = new Spreadsheet();
		myFrame.add(new JScrollPane(mySpreadsheet.getTable()), BorderLayout.CENTER);
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