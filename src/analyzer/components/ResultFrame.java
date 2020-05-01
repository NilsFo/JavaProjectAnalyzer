package analyzer.components;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import analyzer.Analyzer;
import analyzer.components.models.FileTableModel;
import analyzer.utiil.CodeFile;
import analyzer.utiil.ProjectData;
import javax.swing.JButton;

public class ResultFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private Window parent;
	private ResultFrame frame;
	private ProjectData data;
	private JLabel nameLB;
	private JLabel codeLB;
	private JLabel documentationLB;
	private JTextField filterTF;
	private JLabel commentLB;
	private JLabel sizeLB;
	private JButton viewBT;

	public ResultFrame(ProjectData data, Window parent) {
		setSize(475, 555);
		setTitle(Analyzer.TITLE);
		this.parent = parent;
		this.data = data;
		frame = this;
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Found files",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0,
				0, 0)));

		JLabel lblCount = new JLabel("Count: " + data.getFilecount());

		JPanel panel_10 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_10.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						Alignment.TRAILING,
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.TRAILING)
												.addComponent(
														panel_10,
														Alignment.LEADING,
														GroupLayout.DEFAULT_SIZE,
														197, Short.MAX_VALUE)
												.addComponent(
														scrollPane,
														GroupLayout.DEFAULT_SIZE,
														197, Short.MAX_VALUE)
												.addComponent(
														lblCount,
														GroupLayout.DEFAULT_SIZE,
														197, Short.MAX_VALUE))
								.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(
				Alignment.TRAILING).addGroup(
				gl_panel.createSequentialGroup()
						.addContainerGap()
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE,
								235, Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_10, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(lblCount, GroupLayout.PREFERRED_SIZE, 19,
								GroupLayout.PREFERRED_SIZE)));

		JLabel lblFilter = new JLabel("Filter: ");
		panel_10.add(lblFilter);

		filterTF = new JTextField();
		filterTF.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				fillTable();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				fillTable();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				fillTable();
			}
		});
		panel_10.add(filterTF);
		filterTF.setColumns(10);

		table = new JTable();
		scrollPane.setViewportView(table);
		table.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(ListSelectionEvent e) {
						System.out.println("table changed");
						update();
					}
				});
		panel.setLayout(gl_panel);

		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), "File info",
				TitledBorder.LEADING, TitledBorder.TOP, null,
				new Color(0, 0, 0)));

		JPanel panel_4 = new JPanel();

		viewBT = new JButton("View Code");
		viewBT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewCode();
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_panel_1
										.createParallelGroup(Alignment.LEADING)
										.addComponent(panel_4,
												GroupLayout.DEFAULT_SIZE, 171,
												Short.MAX_VALUE)
										.addComponent(viewBT))
						.addContainerGap()));
		gl_panel_1.setVerticalGroup(gl_panel_1.createParallelGroup(
				Alignment.LEADING).addGroup(
				Alignment.TRAILING,
				gl_panel_1
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 256,
								Short.MAX_VALUE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(viewBT).addContainerGap()));
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));

		nameLB = new JLabel("Name: -");
		panel_4.add(nameLB);

		codeLB = new JLabel("Code lines: -");
		panel_4.add(codeLB);

		documentationLB = new JLabel("Javadoc lines: -");
		panel_4.add(documentationLB);

		commentLB = new JLabel("Comment lines: -");
		panel_4.add(commentLB);

		sizeLB = new JLabel("Lines: -");
		panel_4.add(sizeLB);
		panel_1.setLayout(gl_panel_1);
		// panel_4.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "Project info",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		JLabel lblName = new JLabel("Name: " + data.getName());
		lblName.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel panel_3 = new JPanel();
		// panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2
				.setHorizontalGroup(gl_panel_2
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_panel_2
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_panel_2
														.createParallelGroup(
																Alignment.TRAILING)
														.addComponent(
																panel_3,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																410,
																Short.MAX_VALUE)
														.addComponent(
																lblName,
																Alignment.LEADING,
																GroupLayout.DEFAULT_SIZE,
																410,
																Short.MAX_VALUE))
										.addContainerGap()));
		gl_panel_2.setVerticalGroup(gl_panel_2.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_panel_2
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblName)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 80,
								GroupLayout.PREFERRED_SIZE)
						.addContainerGap(31, Short.MAX_VALUE)));
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.X_AXIS));

		JPanel panel_5 = new JPanel();
		panel_3.add(panel_5);
		panel_5.setLayout(new BoxLayout(panel_5, BoxLayout.Y_AXIS));

		JLabel lblNils = new JLabel("Code lines: " + data.getCodeLines() + " ["
				+ (data.getCodeLines() * 100 / data.getLines()) + "%]");
		panel_5.add(lblNils);

		JLabel lblCommentLines = new JLabel("Comment lines: "
				+ data.getCommentLines() + " ["
				+ (data.getCommentLines() * 100 / data.getLines()) + "%]");
		panel_5.add(lblCommentLines);

		JLabel lblJavadocLines = new JLabel("Javadoc lines: "
				+ data.getJavadocLines() + " ["
				+ (data.getJavadocLines() * 100 / data.getLines()) + "%]");
		panel_5.add(lblJavadocLines);

		JLabel lblAllLines = new JLabel("Lines: " + data.getLines());
		panel_5.add(lblAllLines);

		JPanel panel_8 = new JPanel();
		panel_3.add(panel_8);

		JPanel panel_6 = new JPanel();
		panel_3.add(panel_6);
		panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));

		JLabel lblClasses = new JLabel("Classes: " + data.getFilecount());
		panel_6.add(lblClasses);

		JLabel lblPackages = new JLabel("Packages: " + data.getPackages());
		panel_6.add(lblPackages);

		JLabel lblEmptyPackages = new JLabel("Empty packages: "
				+ data.getEmptyPackages());
		panel_6.add(lblEmptyPackages);

		JLabel lblReferencedJars = new JLabel("Referenced jars: "
				+ data.getReferencedJars());
		panel_6.add(lblReferencedJars);

		JPanel panel_9 = new JPanel();
		panel_3.add(panel_9);

		JPanel panel_7 = new JPanel();
		panel_3.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.Y_AXIS));

		JLabel lblTest = new JLabel("Ø code-lines per class: "
				+ data.getCodeLines() / data.getFilecount());
		panel_7.add(lblTest);

		JLabel lblNewLabel = new JLabel("Ø comment-lines per class: "
				+ data.getCommentLines() / data.getFilecount());
		panel_7.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Ø javadoc-lines per class: "
				+ data.getJavadocLines() / data.getFilecount());
		panel_7.add(lblNewLabel_1);
		panel_2.setLayout(gl_panel_2);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
							.addGap(10)
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(7))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(11)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
					.addGap(2))
		);
		getContentPane().setLayout(groupLayout);
		setLocationRelativeTo(parent);
		setPreferredSize(getSize());
		setMinimumSize(getSize());

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);

		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				close();
			}
		});
		mnFile.add(mntmClose);

		JMenuItem mntmNewMenuItem = new JMenuItem("Exit");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnFile.add(mntmNewMenuItem);

		JMenu mnNewMenu = new JMenu("?");
		menuBar.add(mnNewMenu);

		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Analyzer.showInfo(frame);
			}
		});
		mnNewMenu.add(mntmAbout);

		update();
		fillTable();
		pack();

		setVisible(true);
	}
	
	private void viewCode(){
		CodeFile f = (CodeFile) table.getValueAt(table.getSelectedRow(), 0);
		new ViewCodeFrame(f,this);
	}

	private void update() {
		int i = table.getSelectedRow();
		if (i != -1) {
			CodeFile f = (CodeFile) table.getValueAt(i, 0);
			nameLB.setText("Name: " + f.getName());
			codeLB.setText("Code lines: " + f.getCodeLines());
			documentationLB.setText("Javadoc lines: " + f.getJavadocLines());
			commentLB.setText("Comment lines: " + f.getCommentsLines());
			sizeLB.setText("Lines: " + f.getLineCount());
		}
		viewBT.setEnabled(!(i == -1));
	}

	private void fillTable() {
		String filter = filterTF.getText().trim().toLowerCase();
		ArrayList<CodeFile> l = new ArrayList<CodeFile>();

		if (filter.equals("")) {
			// System.out.println("No filter. everything is ok");
			l = new ArrayList<CodeFile>(data.getFiles());
		} else {
			for (int i = 0; i < data.getFilecount(); i++) {
				CodeFile f = data.getFiles().get(i);
				String s = f.getName().trim().toLowerCase();
				// System.out.println("Go compare: " + filter + " <> " + s);
				if (s.contains(filter)) {
					l.add(f);
				}
			}
		}

		Collections.sort(l);
		table.setModel(new FileTableModel(new String[] { "Listed files" }, l
				.size()));

		for (int i = 0; i < l.size(); i++) {
			table.setValueAt(l.get(i), i, 0);
		}
		update();
	}

	private void close() {
		dispose();
		parent.requestFocus();
	}
}
