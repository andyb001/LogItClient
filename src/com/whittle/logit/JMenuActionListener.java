package com.whittle.logit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JMenuActionListener implements ActionListener {

	public static final String EXIT = "Exit";

	public JMenuActionListener() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void actionPerformed(ActionEvent actionEvent) {
		switch (actionEvent.getActionCommand()) {
		case EXIT:
			System.exit(0);

		default:
			break;
		}
	}

}
