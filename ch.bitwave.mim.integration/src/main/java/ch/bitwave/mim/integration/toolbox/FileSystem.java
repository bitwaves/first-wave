package ch.bitwave.mim.integration.toolbox;

import java.io.File;

public class FileSystem {

	public static String[] getRelativeFolders(final File root, final File detail) {
		String rootPath = root.getAbsolutePath();
		String detailPath = detail.getAbsolutePath();
		if (detailPath.length() < rootPath.length()
				|| !detailPath.toLowerCase().startsWith(rootPath.toLowerCase())) {
			throw new RuntimeException(String.format("Path \"%s\" does not root in \"%s\".",
					detailPath, rootPath));
		}
		String relativePath = detailPath.substring(rootPath.length());
		if (relativePath.startsWith(File.separator)) {
			relativePath = relativePath.substring(1);
		}
		if (relativePath.isEmpty()) {
			return new String[0];
		}
		return relativePath.split("[\\\\/]");
	}
}
