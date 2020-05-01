package analyzer.utiil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class ProjectData implements Serializable {

	private static final long serialVersionUID = 1L;

	private int commentLines;
	private int javadocLines;
	private int codeLines;
	private int lineCount;
	private int packages;
	private int emptyPackages;
	private int referencedJars;
	private String name;
	private ArrayList<CodeFile> foundFiles;

	public ProjectData() {
		foundFiles = new ArrayList<CodeFile>();
	}

	public void readFile(File file) throws IOException {
		CodeFile f = new CodeFile(file);
		foundFiles.add(f);
		// System.out.println("Searching code in " + file.getAbsolutePath());

		commentLines += f.getCommentsLines();
		javadocLines += f.getJavadocLines();
		codeLines += f.getCodeLines();
		lineCount+=f.getLineCount();
	}

	public void registerPackage(File f) {
		packages++;
		if (f.listFiles().length == 0)
			emptyPackages++;
	}

	public int getCommentLines() {
		return commentLines;
	}

	public int getJavadocLines() {
		return javadocLines;
	}

	public int getCodeLines() {
		return codeLines;
	}

	public int getFilecount() {
		return foundFiles.size();
	}

	public ArrayList<CodeFile> getFiles() {
		return foundFiles;
	}

	public int getLines() {
		return lineCount;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}

	public int getPackages() {
		return packages;
	}

	public int getEmptyPackages() {
		return emptyPackages;
	}

	public void setName(String name) {
		this.name=name;
	}

	public int getReferencedJars() {
		return referencedJars;
	}
	
	public int modJars(){
		referencedJars++;
		return referencedJars;
	}
}
