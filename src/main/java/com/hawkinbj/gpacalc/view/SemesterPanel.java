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
	private JLabel overallGPALbl;
	private JLabel majorGPALbl;

	public SemesterPanel(SystemController controller) {
		super(controller);
		schoolName = controller.getActiveSchool().getName();
		this.addComponentsToPane();
	}

	private void addComponentsToPane() {
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(this.infoPanel, 3));

		this.createTitledBorder(this.infoPanel, this.schoolName);

		overallGPALbl = this.formatGPADisplay("Overall GPA: ",
				controller.calcTranscriptGPA());

		majorGPALbl = this.formatGPADisplay("Major GPA: ",
				controller.calcMajorGPA());

		infoPanel.add(overallGPALbl);
		infoPanel.add(majorGPALbl);

		semestersPanel = new JPanel();
		semestersPanel.setLayout(new BoxLayout(semestersPanel, 3));

		this.createTitledBorder(semestersPanel, "Select Semester");

		Object[] sortedSemesters = controller.getActiveTranscript()
				.getSemesters().keySet().toArray();

		if (sortedSemesters.length == 0) {
			JLabel instructionLbl = new JLabel(
					"Add a new semester to get started.");
			instructionLbl.setForeground(Color.blue);
			semestersPanel.add(instructionLbl);
		} else {
			Arrays.sort(sortedSemesters, Collections.reverseOrder());

			for (Object semester : sortedSemesters) {
				semestersPanel.add(createButton((String) semester));
			}
		}

		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel, 3));

		createTitledBorder(navigationPanel, "Navigation");

		navigationPanel.add(createButton("newSemesterPanel", "Add new..."));
		navigationPanel.add(createButton("newPlanPanel", "Plan of study"));
		navigationPanel.add(createButton("back", "Back"));

		setLayout(new BoxLayout(this, 3));
		add(infoPanel);
		add(semestersPanel);
		add(navigationPanel);
	}

	private JLabel formatGPADisplay(String baseText, double GPA) {
		JLabel gpaLabel = new JLabel();

		if (Double.isNaN(GPA))
			gpaLabel.setText(baseText + "N/A");
		else {
			gpaLabel.setText(baseText
					+ String.format("%.2f",
							new Object[] { Double.valueOf(GPA) }));
		}

		return gpaLabel;
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (action.equals("back")) {
			controller.setActiveTranscript(null);
			controller.getRootFrame().showPanel("MainMenuPanel", this);
		} else if (action.equals("newSemesterPanel")) {
			controller.getRootFrame().addPanel(new SemesterDialog(controller),
					this);
		} else if (action.equals("newPlanPanel")) {
			controller.getRootFrame().addPanel(new PlanPanel(controller), this);
		} else {
			controller.setActiveSemester(((Semester) controller
					.getActiveTranscript().getSemesters().get(action)));
			controller.getRootFrame().addPanel(new CoursePanel(controller),
					this);
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
				((Transcript) controller.getActiveUser().getTranscripts()
						.get(schoolName)).removeSemester(component.getName());
				controller.saveUserList();

				controller.getRootFrame().addPanel(
						new SemesterPanel(controller), this);
			}
		}
	}
}
