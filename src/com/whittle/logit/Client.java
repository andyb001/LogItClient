package com.whittle.logit;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Client extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public Client() {
		this.setSize(1200, 800);
		this.setLocation(100, 100);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setJMenuBar(getLogitJMenuBar());
		Cache.getInstance().setMainPanel(new MainPanel());
		this.getContentPane().add(Cache.getInstance().getMainPanel());
		this.setVisible(true);	
	}
	
	private JMenuBar getLogitJMenuBar() {
		JMenuActionListener jMenuActionListener = new JMenuActionListener();
		JMenuBar jMenuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
		
		JMenuItem newMenuItem = new JMenuItem(JMenuActionListener.NEW);
		newMenuItem.addActionListener(jMenuActionListener);
		newMenuItem.setActionCommand(JMenuActionListener.NEW);
		fileMenu.add(newMenuItem);
		
		JMenuItem exMenuItem = new JMenuItem(JMenuActionListener.EXIT);
		exMenuItem.addActionListener(jMenuActionListener);
		exMenuItem.setActionCommand(JMenuActionListener.EXIT);
		fileMenu.add(exMenuItem);
		
		
		
		jMenuBar.add(fileMenu);
		return jMenuBar;
	}

	public static void main(String[] args) {
		new Client();
	}
}
