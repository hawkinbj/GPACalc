package com.hawkinbj.gpacalc.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;

public class MainMenuPanel extends GUIPanel {
	private static final long serialVersionUID = 997821906993439522L;
	protected JPanel instructionPanel;
	protected JPanel schoolsPanel;
	protected JPanel navigationPanel;

	public MainMenuPanel(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.schoolsPanel = new JPanel();
		this.schoolsPanel.setLayout(new BoxLayout(this.schoolsPanel, 3));
		createTitledBorder(this.schoolsPanel, "Select School");

		if (this.controller.getActiveUser().getTranscripts().size() == 0) {
			JLabel instructionLbl = new JLabel(
					"Add a new school to get started.");
			instructionLbl.setForeground(Color.blue);
			this.schoolsPanel.add(instructionLbl);
		} else {
			Iterator<String> localIterator = this.controller.getActiveUser()
					.getTranscripts().keySet().iterator();

			while (localIterator.hasNext()) {
				String schoolName = (String) localIterator.next();
				this.schoolsPanel.add(createButton(schoolName));
			}

		}

		this.navigationPanel = new JPanel();
		this.navigationPanel.setLayout(new BoxLayout(this.navigationPanel, 3));
		createTitledBorder(this.navigationPanel, "Navigation");

		this.navigationPanel.add(createButton("selectSchool", "Add new..."));

		setLayout(new BoxLayout(this, 3));

		add(this.schoolsPanel);
		add(this.navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("selectSchool")) {
			this.controller.getRootFrame().addPanel(
					new SchoolPanel(this.controller), this);
		} else {
			this.controller.setActiveTranscript(this.controller.getActiveUser()
					.getTranscript(action));
			this.controller.setActiveSchool(this.controller.getActiveUser()
					.getTranscript(action).getSchool());
			this.controller.getRootFrame().addPanel(
					new SemesterPanel(this.controller), this);
		}
	}

	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();
		System.out.println(component.getName());

		if ((SwingUtilities.isRightMouseButton(e))
				&& (component.getName() != null)) {
			int response = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove this school?", "Confirm",
					0, 3);
			if (response == 0) {
				this.controller.getActiveUser().removeTranscript(
						component.getName());
				this.controller.saveUserList();
				this.controller.getRootFrame().addPanel(
						new MainMenuPanel(this.controller), this);
			} else if (response != -1)
				;
		} else
			;
	}
}