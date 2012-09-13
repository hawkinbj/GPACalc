package com.hawkinbj.gpacalc.view;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;

public class RootFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = -1406044665804707164L;
	protected Container contentPane;
	private SystemController controller;
	private HashMap<String, GUIPanel> panels;
	private String previous;
	private String current;

	public RootFrame(SystemController controller) {
		this.controller = controller;
		setDefaultLookAndFeelDecorated(true);
		setTitle("GPACalc");
		setDefaultCloseOperation(3);
		setMinimumSize(new Dimension(200, 300));
		setLocation(600, 200);
		this.contentPane = getContentPane();
		this.contentPane.setLayout(new CardLayout());
		this.panels = new HashMap<String, GUIPanel>();
		this.previous = (this.current = null);
		addComponentsToPane();
		setVisible(true);
	}

	private void addComponentsToPane() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		JMenuItem newAction = new JMenuItem("New");
		JMenuItem logOut = new JMenuItem("Log out");
		logOut.setActionCommand("logOut");
		logOut.addActionListener(this);
		fileMenu.add(newAction);
		fileMenu.add(logOut);
	}

	protected HashMap<String, GUIPanel> getAllPanels() {
		return this.panels;
	}

	protected void clearAllPanels() {
		this.panels.clear();
	}

	protected GUIPanel getPanel(String panelName) {
		return (GUIPanel) this.panels.get(panelName);
	}

	public void addPanel(GUIPanel panelToAdd, GUIPanel panelToHide) {
		String panelToAddName = panelToAdd.getName();
		this.panels.put(panelToAddName, panelToAdd);
		showPanel(panelToAddName, panelToHide);
	}

	protected GUIPanel getPreviousPanel() {
		return (GUIPanel) this.panels.get(this.previous);
	}

	protected void showPanel(String newPanelName, GUIPanel panelToHide) {
		this.previous = this.current;
		this.current = newPanelName;

		this.contentPane.remove(panelToHide);
		this.contentPane.add((Component) this.panels.get(newPanelName),
				newPanelName);
		panelToHide.setVisible(false);
		((GUIPanel) this.panels.get(newPanelName)).repaint();
		((GUIPanel) this.panels.get(newPanelName)).setVisible(true);
		pack();
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("logOut")) {
			this.controller.logOut();
			this.panels.clear();
			this.contentPane.removeAll();
			addPanel(new WelcomePanel(this.controller), new GUIPanel(
					this.controller));
		}
	}
}
