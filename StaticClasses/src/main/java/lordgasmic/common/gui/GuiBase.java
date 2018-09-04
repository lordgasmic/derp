package lordgasmic.common.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public abstract class GuiBase implements ActionListener,IGuiBase{
	
	protected JFrame frame;

	protected JMenuBar menuBar;
	
	private Map<JMenuItem, GuiPanel> guis;

	public GuiBase(){
		frame = new JFrame("Lordgasmic Recipe");
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		menuBar = new JMenuBar();
		
		guis = new HashMap<JMenuItem, GuiPanel>();

		initMenus();
		initMenuItems();
		
		initGuis();

		frame.setJMenuBar(menuBar);
	}

	public void display(){
		//Display the window.
		frame.setVisible(true);
	}
	
	protected void display(GuiPanel g){
		frame.getContentPane().removeAll();
		frame.repaint();
		
		g.load();
		frame.getContentPane().add(g);
		frame.getContentPane().validate();
	}

	public void actionPerformed(ActionEvent e) {
		for (JMenuItem menuItem : guis.keySet()){
			if (e.getSource() == menuItem)
				display(guis.get(menuItem));
		}
	}
	
	public void setGuis(Map<JMenuItem, GuiPanel> guis) {
		this.guis = guis;
	}
	
	public void addGui(JMenuItem menuItem, GuiPanel guiPanel) {
		guis.put(menuItem, guiPanel);
	}
}
