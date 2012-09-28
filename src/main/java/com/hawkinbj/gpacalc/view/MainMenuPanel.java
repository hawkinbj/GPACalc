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
		this.addComponentsToPane();
	}

	private void addComponentsToPane() {
		schoolsPanel = new JPanel();
		schoolsPanel.setLayout(new BoxLayout(schoolsPanel, 3));

		this.createTitledBorder(schoolsPanel, "Select School");

		if (controller.getActiveUser().getTranscripts().size() == 0) {
			JLabel instructionLbl = new JLabel(
					"Add a new school to get started.");
			instructionLbl.setForeground(Color.blue);
			schoolsPanel.add(instructionLbl);
		} else {
			Iterator<String> localIterator = controller.getActiveUser()
					.getTranscripts().keySet().iterator();

			while (localIterator.hasNext()) {
				String schoolName = (String) localIterator.next();
				schoolsPanel.add(createButton(schoolName));
			}

		}

		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel, 3));
		navigationPanel.add(createButton("selectSchool", "Add new..."));

		this.createTitledBorder(navigationPanel, "Navigation");

		this.setLayout(new BoxLayout(this, 3));
		this.add(schoolsPanel);
		this.add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (action.equals("selectSchool")) {
			controller.getRootFrame().addPanel(new SchoolPanel(controller),
					this);
		} else {
			controller.setActiveTranscript(controller.getActiveUser()
					.getTranscript(action));

			controller.setActiveSchool(controller.getActiveUser()
					.getTranscript(action).getSchool());

			controller.getRootFrame().addPanel(new SemesterPanel(controller),
					this);
		}
	}

	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();

		if ((SwingUtilities.isRightMouseButton(e))
				&& (component.getName() != null)) {
			int response = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove this school?", "Confirm",
					0, 3);

			if (response == 0) {
				controller.getActiveUser()
						.removeTranscript(component.getName());
				controller.saveUserList();
				controller.getRootFrame().addPanel(
						new MainMenuPanel(controller), this);
			}
		}
	}
}