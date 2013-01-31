package com.hawkinbj.gpacalc.view;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;
import com.hawkinbj.gpacalc.model.Grade;

public class CourseInfoPanel extends GUIPanel {

	private static final long serialVersionUID = 1488575000302467412L;

	private JPanel infoPanel;

	private JPanel gradeTypesPanel;

	private JPanel navigationPanel;

	private JLabel finalGradeLabel;

	private JLabel currentAverage;

	private Double average;

	public CourseInfoPanel(SystemController controller) {
		super(controller);
		this.addComponentsToPane();
	}

	private void addComponentsToPane() {
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, 3));
		infoPanel.add(new JLabel("Credit hours: "
				+ Integer.toString(controller.getActiveCourse()
						.getCreditHours())));

		finalGradeLabel = new JLabel("Final grade: "
				+ controller.getActiveCourse().getFinalGrade());

		infoPanel.add(this.finalGradeLabel);

		this.createTitledBorder(infoPanel, controller.getActiveCourse()
				.getCourseName());

		if (controller.getActiveCourse().getWeighted()) {
			average = Double.valueOf(controller.getActiveCourse()
					.getWeightedTotalPointsEarned());
		} else {
			average = Double.valueOf((controller.getActiveCourse()
					.getTotalPointsEarned()
					/ controller.getActiveCourse().getTotalPointsPossible()) * 100);
		}

		currentAverage = this.formatAverage("Current average: ", average);

		infoPanel.add(currentAverage);

		gradeTypesPanel = new JPanel();
		gradeTypesPanel.setLayout(new BoxLayout(gradeTypesPanel, 3));

		this.createTitledBorder(gradeTypesPanel, "Select Grade Type");

		for (String gradeType : controller.getActiveCourse().getGrades()
				.keySet()) {
			gradeTypesPanel.add(createButton(gradeType));
		}

		gradeTypesPanel.add(createButton("setFinalGrade", "Final Grade"));

		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel, 3));
		navigationPanel.add(createButton("edit", "Edit Course..."));
		navigationPanel.add(createButton("back", "Back"));

		this.createTitledBorder(navigationPanel, "Navigation");

		this.setLayout(new BoxLayout(this, 3));
		this.add(infoPanel);
		this.add(gradeTypesPanel);
		this.add(navigationPanel);
	}

	private JLabel formatAverage(String baseText, double average) {
		JLabel averageLbl = new JLabel();

		if (Double.isNaN(average))
			averageLbl.setText(baseText + "N/A");
		else {
			averageLbl.setText(baseText
					+ String.format("%.2f",
							new Object[] { Double.valueOf(average) }));
		}

		return averageLbl;
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (action.equals("back")) {
			controller.setActiveCourse(null);

			controller.getRootFrame().addPanel(new CoursePanel(controller),
					this);
		} else if (action.equals("setFinalGrade")) {
			controller.getRootFrame().addPanel(new FinalGradePanel(controller),
					this);
		} else if (action.equals("edit")) {
			controller.getRootFrame().addPanel(new CourseDialog(controller),
					this);
		} else {
			controller.getRootFrame().addPanel(
					new GradePanel(controller, (Grade) controller
							.getActiveCourse().getGrades().get(action)), this);
		}
	}

	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();

		if ((SwingUtilities.isRightMouseButton(e))
				&& (component.getName() != null && !component.getName().equals(
						"setFinalGrade"))) {

			int response = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove this grade type?",
					"Confirm", 0, 3);

			if (response == 0) {
				controller.getActiveCourse().removeGrade(component.getName());
				controller.saveUserList();
				gradeTypesPanel.remove(component);
				gradeTypesPanel.revalidate();
				gradeTypesPanel.repaint();
			}
		}
	}
}
