import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class Test {
	enum t {
		One{
			public void test() {
				System.out.println("out");
			}
		};
	}
	
	private static void deleteFile(File f) {
		File[] files = f.listFiles();

		for (File file : files) {
			if (file.getName().equals(".svn")) {
				try {
					FileUtils.deleteDirectory(file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (file.isDirectory()) {
				deleteFile(file);
			}
		}
	}

	public static void main(String[] args) {
	}
}
