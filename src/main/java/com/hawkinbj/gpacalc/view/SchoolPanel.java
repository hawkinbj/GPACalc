package com.hawkinbj.gpacalc.view;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;
import com.hawkinbj.gpacalc.model.School;
import com.hawkinbj.gpacalc.model.Transcript;

public class SchoolPanel extends GUIPanel {
	private static final long serialVersionUID = 1029559901433692231L;
	private JPanel schoolsPanel;
	private JPanel navigationPanel;

	public SchoolPanel(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.schoolsPanel = new JPanel();
		this.schoolsPanel.setLayout(new BoxLayout(this.schoolsPanel, 3));
		createTitledBorder(this.schoolsPanel, "Select School");

		for (School school : this.controller.getSchools().values()) {
			this.schoolsPanel.add(createButton(school.getName()));
		}

		this.navigationPanel = new JPanel();
		this.navigationPanel.setLayout(new BoxLayout(this.navigationPanel, 3));
		createTitledBorder(this.navigationPanel, "Navigation");

		this.navigationPanel.add(createButton("newSchoolPanel", "Add new..."));

		this.navigationPanel.add(createButton("cancel", "Cancel"));

		setLayout(new BoxLayout(this, 3));
		add(this.schoolsPanel);
		add(this.navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			this.controller.getRootFrame().showPanel("MainMenuPanel", this);
		} else if (action.equals("newSchoolPanel")) {
			this.controller.getRootFrame().addPanel(
					new SchoolDialog(this.controller), this);
		} else if (this.controller.getActiveUser().addTranscript(
				action,
				new Transcript((School) this.controller.getSchools()
						.get(action)))) {
			this.controller.saveUserList();
			this.controller.getRootFrame().addPanel(
					new MainMenuPanel(this.controller), this);
		} else {
			JOptionPane.showMessageDialog(this, "That school already exists.",
					"Error", 0);
		}
	}
}
