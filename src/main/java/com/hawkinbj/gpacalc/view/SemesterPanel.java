package com.hawkinbj.gpacalc.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;
import com.hawkinbj.gpacalc.model.Semester;
import com.hawkinbj.gpacalc.model.Transcript;

public class SemesterPanel extends GUIPanel {
	private static final long serialVersionUID = -3839021242565662981L;
	private String schoolName;
	protected JPanel infoPanel;
	protected JPanel semestersPanel;
	protected JPanel navigationPanel;

	public SemesterPanel(SystemController controller) {
		super(controller);
		this.schoolName = controller.getActiveSchool().getName();
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.infoPanel = new JPanel();
		this.infoPanel.setLayout(new BoxLayout(this.infoPanel, 3));

		createTitledBorder(this.infoPanel, this.schoolName);

		double gpa = this.controller.calcTranscriptGPA();
		JLabel gpaLbl = new JLabel();

		if (Double.isNaN(gpa))
			gpaLbl.setText("GPA: N/A");
		else {
			gpaLbl.setText("GPA: "
					+ String.format("%.2f",
							new Object[] { Double.valueOf(gpa) }));
		}
		this.infoPanel.add(gpaLbl);

		this.semestersPanel = new JPanel();
		this.semestersPanel.setLayout(new BoxLayout(this.semestersPanel, 3));

		createTitledBorder(this.semestersPanel, "Select Semester");

		Object[] sortedSemesters = this.controller.getActiveTranscript()
				.getSemesters().keySet().toArray();

		if (sortedSemesters.length == 0) {
			JLabel instructionLbl = new JLabel(
					"Add a new semester to get started.");
			instructionLbl.setForeground(Color.blue);
			this.semestersPanel.add(instructionLbl);
		} else {
			Arrays.sort(sortedSemesters, Collections.reverseOrder());

			for (Object semester : sortedSemesters) {
				this.semestersPanel.add(createButton((String) semester));
			}
		}

		this.navigationPanel = new JPanel();
		this.navigationPanel.setLayout(new BoxLayout(this.navigationPanel, 3));

		createTitledBorder(this.navigationPanel, "Navigation");

		this.navigationPanel
				.add(createButton("newSemesterPanel", "Add new..."));
		this.navigationPanel.add(createButton("newPlanPanel", "Plan of study"));
		this.navigationPanel.add(createButton("back", "Back"));

		setLayout(new BoxLayout(this, 3));
		add(this.infoPanel);
		add(this.semestersPanel);
		add(this.navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("back")) {
			this.controller.setActiveTranscript(null);
			this.controller.getRootFrame().showPanel("MainMenuPanel", this);
		} else if (action.equals("newSemesterPanel")) {
			this.controller.getRootFrame().addPanel(
					new SemesterDialog(this.controller), this);
		} else if (action.equals("newPlanPanel")) {
			this.controller.getRootFrame().addPanel(
					new PlanPanel(this.controller), this);
		} else {
			this.controller.setActiveSemester(((Semester) this.controller
					.getActiveTranscript().getSemesters().get(action)));
			this.controller.getRootFrame().addPanel(
					new CoursePanel(this.controller), this);
		}
	}

	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();

		if ((SwingUtilities.isRightMouseButton(e))
				&& (component.getName() != null)) {
			int response = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove this semester?",
					"Confirm", 0, 3);
			if (response == 0) {
				((Transcript) this.controller.getActiveUser().getTranscripts()
						.get(this.schoolName)).removeSemester(component
						.getName());
				this.controller.saveUserList();

				this.controller.getRootFrame().addPanel(
						new SemesterPanel(this.controller), this);
			}
		}
	}
}
