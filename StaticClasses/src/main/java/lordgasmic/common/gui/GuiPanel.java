package lordgasmic.common.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

public abstract class GuiPanel extends JPanel implements Gui,ActionListener {

	private static final long serialVersionUID = -6887247837144635217L;
	
	private List<Object> garbage;
	
	protected GridBagConstraints c;
	
	public GuiPanel(){
		garbage = new ArrayList<Object>();
		this.setLayout(new GridBagLayout());
		c = new GridBagConstraints();
	}
	
	protected void collect(Object obj){
		garbage.add(obj);
	}
}
