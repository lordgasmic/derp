import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Parse {

	public static void main(String... args) throws IOException {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblDirectory = new JLabel("Directory");
		JLabel lblURL = new JLabel("URL");
		JTextField txtDirectory = new JTextField();
		JTextField txtURL = new JTextField();
		
		frame.getContentPane().add(lblDirectory, BorderLayout.CENTER);
		frame.setSize(6000, 6000);
		frame.pack();
		frame.setVisible(true);
	}
	
	private void getLinks() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("sample.htm"));
		final String fileThumb = "a class=\"fileThumb\"";
		
		String line;
		while ((line = br.readLine()) != null) {
			int index = 0;
			while ((index = line.indexOf(fileThumb, index + 1)) != -1) {
				int hrefInd = line.indexOf("href=", index);
				int hrefsub = hrefInd + 6;
				String href = line.substring(hrefsub, line.indexOf("\"", hrefsub));
				System.out.println(href);
			}
		}
		br.close();
	}
}
