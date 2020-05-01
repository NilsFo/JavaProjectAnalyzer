package analyzer.components.models;

import javax.swing.table.DefaultTableModel;

public class FileTableModel extends DefaultTableModel {

	private static final long serialVersionUID = -6097890087998733231L;

	public FileTableModel(Object[] arg0, int arg1) {
		super(arg0, arg1);
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

}
