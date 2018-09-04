package lordgasmic.common.util;
import java.io.File;
import java.io.IOException;


public class MoveToRoot {

	File root = new File( "." );
	
	public static void main(String[] args) throws IOException {

		MoveToRoot m = new MoveToRoot();
		m.list(m.root);
	}

	private void list(File path){
		String[] files = path.list();

		for (String f : files) {
			File fs = new File(path, f);
			
			if (fs.isDirectory()) {
				list(fs);
			}
			else {
				fs.renameTo(new File(root, fs.getName()));
			}
		}
	}
}
