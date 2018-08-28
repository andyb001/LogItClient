package com.whittle.logit;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JMenuActionListener implements ActionListener {

	public static final String EXIT = "Exit";
	public static final String NEW = "New";

	public JMenuActionListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		switch (actionEvent.getActionCommand()) {
		case EXIT:
			System.exit(0);
		case NEW:
			Cache c = Cache.getInstance();
			Component comp = c.getMainPanel().getSplitPane().getRightComponent();
			c.getMainPanel().getSplitPane().remove(comp);
			c.getMainPanel().getSplitPane().setRightComponent(new AddItemPanel());
		default:
			break;
		}
	}

}
