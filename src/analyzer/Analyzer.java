package analyzer;

import java.awt.Window;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import analyzer.components.AnalyzerFrame;

public class Analyzer {

	public static final String DATE = "15.06.2015";
	public static final String DEV = "Nils Förster";
	public static final String VERSION = "0.2 Indev";
	public static final String TITLE = "Java Project analyzer";

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {

				try {
					UIManager.setLookAndFeel(UIManager
							.getSystemLookAndFeelClassName());
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				new AnalyzerFrame();
			}
		});
	}

	public static void showInfo(Window parent) {
		JOptionPane.showMessageDialog(parent, TITLE + " " + VERSION + "\nBy "
				+ DEV + " - " + DATE, "Information",
				JOptionPane.INFORMATION_MESSAGE);
	}
}
