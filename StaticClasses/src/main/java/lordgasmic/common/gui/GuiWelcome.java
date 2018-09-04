package lordgasmic.common.gui;

import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JLabel;

public class GuiWelcome extends GuiPanel{

	private static final long serialVersionUID = 1L;

	public void load(){
        //Add the ubiquitous "Hello World" label.
		c.fill = GridBagConstraints.HORIZONTAL;
        JLabel label = new JLabel("Hello World");
        c.gridx = 0;
        c.gridy = 0;
        this.add(label, c);
        
        
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 1;
        this.add(new JButton("test"), c);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
