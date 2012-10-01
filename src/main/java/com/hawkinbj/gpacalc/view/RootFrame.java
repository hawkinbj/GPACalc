/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
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

		// What is this?
		setDefaultLookAndFeelDecorated(true);

		this.setTitle("GPACalc");
		this.setDefaultCloseOperation(3);
		this.setMinimumSize(new Dimension(200, 300));
		this.setLocation(600, 200);
		this.contentPane = getContentPane();
		this.contentPane.setLayout(new CardLayout());
		panels = new HashMap<String, GUIPanel>();
		this.previous = (this.current = null);
		this.addComponentsToPane();
		this.setVisible(true);
	}

	private void addComponentsToPane() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);

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
		return panels;
	}

	protected void clearAllPanels() {
		panels.clear();
	}

	protected GUIPanel getPanel(String panelName) {
		return (GUIPanel) panels.get(panelName);
	}

	public void addPanel(GUIPanel panelToAdd, GUIPanel panelToHide) {
		String panelToAddName = panelToAdd.getName();

		panels.put(panelToAddName, panelToAdd);

		this.showPanel(panelToAddName, panelToHide);
	}

	protected GUIPanel getPreviousPanel() {
		return (GUIPanel) panels.get(this.previous);
	}

	protected void showPanel(String newPanelName, GUIPanel panelToHide) {
		previous = this.current;
		current = newPanelName;

		contentPane.remove(panelToHide);
		contentPane.add((Component) panels.get(newPanelName), newPanelName);

		panelToHide.setVisible(false);
		((GUIPanel) panels.get(newPanelName)).repaint();
		((GUIPanel) panels.get(newPanelName)).setVisible(true);
		this.pack();
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("logOut")) {
			controller.logOut();
			panels.clear();
			contentPane.removeAll();

			this.addPanel(new WelcomePanel(controller),
					new GUIPanel(controller));
		}
	}
}
