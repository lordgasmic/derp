package lordgasmic.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Grep {

	private List<File> files;
	
	public Grep(){
		files = new ArrayList<File>();
	}
	
	public void find(File file, String searchTerm){
		String[] list = file.list();
		String base = file.getAbsolutePath();
		
		for (String s : list){
			File fs = new File(base + '/' + s);
			
			if (fs.isDirectory())
				find(fs, searchTerm);
			else {
				try {
					BufferedReader br = new BufferedReader(new FileReader(fs));
					
					String line;
					while ((line = br.readLine()) != null){
						if (line.contains(searchTerm)){
							files.add(fs);
							break;
						}
					}
					
					br.close();
				}
				catch (IOException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public List<File> getFiles(){
		return files;
	}
}
