package com.whittle.logit;

import java.awt.BorderLayout;
import java.awt.LayoutManager;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JSplitPane splitPane = new JSplitPane();

	public MainPanel() {
		init();
	}

	public MainPanel(LayoutManager arg0) {
		super(arg0);
		init();
	}

	public MainPanel(boolean arg0) {
		super(arg0);
		init();
	}

	public MainPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		init();
	}

	private void init() {
		setLayout(new BorderLayout());
		splitPane.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);
		
		splitPane.setRightComponent(new AddItemPanel());
	}

	public JSplitPane getSplitPane() {
		return splitPane;
	}

}
