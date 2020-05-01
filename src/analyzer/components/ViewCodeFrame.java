package analyzer.components;

import java.awt.Dimension;
import java.awt.Window;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFrame;

import analyzer.Analyzer;
import analyzer.utiil.CodeFile;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;

public class ViewCodeFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextPane codeTF;
	private CodeFile file;

	public ViewCodeFrame(CodeFile file, Window parent) {
		setSize(800, 500);
		this.file = file;
		setTitle(Analyzer.TITLE + " - " + file.getName());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(getSize());
		setMinimumSize(new Dimension(100, 100));
		setLocationRelativeTo(parent);

		JLabel lblNewLabel = new JLabel(file.getAbsolutePath().trim());
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);

		JScrollPane scrollPane = new JScrollPane();

		codeTF = new JTextPane();
		codeTF.setFont(new Font("Courier New", Font.PLAIN, 12));
		codeTF.setEditable(false);

		scrollPane.setViewportView(codeTF);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(10)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(scrollPane,
												GroupLayout.DEFAULT_SIZE, 586,
												Short.MAX_VALUE)
										.addComponent(lblNewLabel,
												GroupLayout.DEFAULT_SIZE, 586,
												Short.MAX_VALUE)).addGap(10)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(11)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
								313, Short.MAX_VALUE).addGap(11)
						.addComponent(lblNewLabel).addGap(13)));
		getContentPane().setLayout(groupLayout);

		readFile();
		pack();

		setVisible(true);
		requestFocus();
	}

	private void readFile() {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			String text = "";
			while ((line = br.readLine()) != null) {
				text += line + "\n";
			}
			codeTF.setText(text.trim());
		} catch (Exception e) {
			e.printStackTrace();
			codeTF.setText("Error while loading the file.\n\n"
					+ e.getClass().getName() + "\n" + e.getMessage());
		}
	}
}
