package analyzer.utiil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CodeFile extends File {

	private static final long serialVersionUID = 1L;
	private int codeLines;
	private int commentsLine;
	private int javadocLine;
	private int lineCount;

	public CodeFile(String arg0) throws IOException {
		super(arg0);
		scan();
	}

	public CodeFile(File f) throws IOException {
		super(f.getAbsolutePath());
		scan();
	}

	/**
	 * 
	 * @throws IOException
	 */
	private void scan() throws IOException {
		if (exists() && isFile() && getAbsolutePath().endsWith(".java")) {

			try (BufferedReader br = new BufferedReader(new FileReader(this))) {
				String line;
				while ((line = br.readLine()) != null) {
					lineCount++;
					line = line.trim();

					if (!line.equals("")) {
						if (line.startsWith("//")) {
							commentsLine++;
							// System.out.println("Comment: " + line);
							continue;
						}
						if (line.startsWith("/*") || line.startsWith("*")) {
							javadocLine++;
							// System.out.println("Doc: " + line);
							continue;
						}
						// System.out.println("Code: " + line);
						codeLines++;
					}
				}
			}

		} else {
			throw new IllegalStateException();
		}
	}

	@Override
	public int compareTo(File f) {
		return getName().compareTo(f.getName());
	}

	@Override
	public String toString() {
		return getName();
	}

	public int getCodeLines() {
		return codeLines;
	}

	public int getCommentsLines() {
		return commentsLine;
	}

	public int getLineCount() {
		return lineCount;
	}

	public int getJavadocLines() {
		return javadocLine;
	}
}
