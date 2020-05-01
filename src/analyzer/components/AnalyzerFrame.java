package analyzer.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import analyzer.Analyzer;
import analyzer.components.panels.ProjectPathPanel;
import analyzer.utiil.ProjectData;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class AnalyzerFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private ProjectPathPanel panel;
	private ProjectData data;
	private AnalyzerFrame frame;

	public AnalyzerFrame() {
		frame = this;
		setSize(478, 175);
		setTitle(Analyzer.TITLE);

		panel = new ProjectPathPanel();
		panel.setActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					prepareScan();
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(
							frame,
							"An unexpected error occured while trying to access the project-files.\n\n"
									+ ex.getClass().getName() + ":\n\""
									+ ex.getMessage() + "\"", "Error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(10)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 442,
								Short.MAX_VALUE).addGap(10)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(11)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 95,
								Short.MAX_VALUE).addGap(10)));
		getContentPane().setLayout(groupLayout);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setPreferredSize(getSize());
		setMinimumSize(getSize());

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);

		JMenuItem mntmClearPath = new JMenuItem("Clear path");
		mntmClearPath.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				panel.setPath("");
			}
		});
		mnEdit.add(mntmClearPath);

		JMenu menu = new JMenu("?");
		menuBar.add(menu);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Analyzer.showInfo(frame);
			}
		});
		menu.add(mntmAbout);
		setVisible(true);
	}

	private void prepareScan() throws IOException {
		data = new ProjectData();
		File f = panel.getFile();
		data.setName(f.getName());
		scan(f);
		new ResultFrame(data, this);
	}

	private void scan(File file) throws IOException {
		if (!file.exists()) {
			return;
		}

		if (file.isDirectory()) {
			File[] children = file.listFiles();
			data.registerPackage(file);
			for (int i = 0; i < children.length; i++) {
				// System.out.println("Subfolder found. Scaning: " +
				// children[i]);
				scan(children[i]);
			}
		} else {
			// System.out.println("Existing file found: " + file.getPath());
			if (file.getPath().endsWith(".java")) {
				data.readFile(file);
			}
			if (file.getPath().endsWith(".jar")) {
				data.modJars();
			}
		}
	}
}
