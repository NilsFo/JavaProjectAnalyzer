package analyzer.components.panels;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ProjectPathPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField pathTF;
	private JButton goBT;

	public ProjectPathPanel() {
		setBorder(new TitledBorder(null, "Project source directory",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));

		pathTF = new JTextField();
		pathTF.setColumns(10);
		pathTF.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				onTyped();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				onTyped();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				onTyped();
			}
		});

		JButton button = new JButton("...");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				choose();
			}
		});

		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEADING);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(pathTF, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(button)
					.addContainerGap())
				.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(pathTF, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 31, Short.MAX_VALUE)
					.addContainerGap())
		);

		goBT = new JButton("Scan project");
		panel.add(goBT);
		setLayout(groupLayout);

		onTyped();
	}

	private void choose() {
		JFileChooser c = new JFileChooser();
		c.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		c.setCurrentDirectory(new File(getPath()));
		c.setMultiSelectionEnabled(false);
		int i = c.showOpenDialog(this);
		if (i == JFileChooser.APPROVE_OPTION) {
			setPath(c.getSelectedFile().getPath());
		}
	}

	private void onTyped() {
		File f = new File(getPath());
		goBT.setEnabled(f.exists() && f.isDirectory());
	}

	public void setActionListener(ActionListener l) {
		goBT.addActionListener(l);
	}

	public String getPath() {
		return pathTF.getText();
	}

	public void setPath(String path) {
		pathTF.setText(path);
	}

	public void setPath(File f) {
		setPath(f.getAbsolutePath());
	}

	public File getFile() {
		return new File(getPath());
	}
}
