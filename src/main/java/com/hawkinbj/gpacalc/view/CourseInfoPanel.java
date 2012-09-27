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

	public CourseInfoPanel(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.infoPanel = new JPanel();
		this.infoPanel.setLayout(new BoxLayout(this.infoPanel, 3));
		createTitledBorder(this.infoPanel, this.controller.getActiveCourse()
				.getCourseName());
		this.infoPanel.add(new JLabel("Credit hours: "
				+ Integer.toString(this.controller.getActiveCourse()
						.getCreditHours())));

		this.finalGradeLabel = new JLabel("Final grade: "
				+ this.controller.getActiveCourse().getFinalGrade());
		this.infoPanel.add(this.finalGradeLabel);

		JLabel currentAvg = new JLabel();
		Double average = Double.valueOf(0.0D);

		if (this.controller.getActiveCourse().getWeighted()) {
			average = Double.valueOf(this.controller.getActiveCourse()
					.getWeightedTotalPointsEarned());
			if (Double.isNaN(average.doubleValue()))
				currentAvg.setText("Current average: N/A");
			else
				currentAvg.setText("Current average: "
						+ String.format("%.2f", new Object[] { average }));
		} else {
			average = Double.valueOf(this.controller.getActiveCourse()
					.getTotalPointsEarned()
					/ this.controller.getActiveCourse()
							.getTotalPointsPossible());
			if (Double.isNaN(average.doubleValue()))
				currentAvg.setText("Current average: N/A");
			else
				currentAvg.setText("Current average: "
						+ String.format("%.2f", new Object[] { Double
								.valueOf(this.controller.getActiveCourse()
										.getTotalPointsEarned()
										/ this.controller.getActiveCourse()
												.getTotalPointsPossible()) }));
		}
		this.infoPanel.add(currentAvg);

		this.gradeTypesPanel = new JPanel();
		this.gradeTypesPanel.setLayout(new BoxLayout(this.gradeTypesPanel, 3));
		createTitledBorder(this.gradeTypesPanel, "Select Grade Type");
		for (String gradeType : this.controller.getActiveCourse().getGrades()
				.keySet()) {
			this.gradeTypesPanel.add(createButton(gradeType));
		}

		this.gradeTypesPanel.add(createButton("setFinalGrade", "Final Grade"));

		this.navigationPanel = new JPanel();
		this.navigationPanel.setLayout(new BoxLayout(this.navigationPanel, 3));
		createTitledBorder(this.navigationPanel, "Navigation");

		this.navigationPanel.add(createButton("edit", "Edit Course..."));

		this.navigationPanel.add(createButton("back", "Back"));

		setLayout(new BoxLayout(this, 3));
		add(this.infoPanel);
		add(this.gradeTypesPanel);
		add(this.navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("back")) {
			this.controller.setActiveCourse(null);

			this.controller.getRootFrame().addPanel(
					new CoursePanel(this.controller), this);
		} else if (action.equals("setFinalGrade")) {
			this.controller.getRootFrame().addPanel(
					new FinalGradePanel(this.controller), this);
		} else if (action.equals("edit")) {
			this.controller.getRootFrame().addPanel(
					new CourseDialog(this.controller), this);
		} else {
			this.controller.getRootFrame().addPanel(
					new GradePanel(this.controller, (Grade) this.controller
							.getActiveCourse().getGrades().get(action)), this);
		}
	}

	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();

		if ((SwingUtilities.isRightMouseButton(e))
				&& (component.getName() != null)) {
			if (component.getName().equals("setFinalGrade"))
				return;
			int response = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove this grade type?",
					"Confirm", 0, 3);
			if (response == 0) {
				this.controller.getActiveCourse().removeGrade(
						component.getName());
				this.controller.saveUserList();
				this.gradeTypesPanel.remove(component);
				this.gradeTypesPanel.revalidate();
				this.gradeTypesPanel.repaint();
			}
		}
	}
}
