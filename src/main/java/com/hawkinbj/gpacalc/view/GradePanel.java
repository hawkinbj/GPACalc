package com.hawkinbj.gpacalc.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;
import com.hawkinbj.gpacalc.model.Grade;

public class GradePanel extends GUIPanel {
	private static final long serialVersionUID = 1673864396132217214L;

	private Grade grade;

	private JPanel entryPanel;

	private JPanel navigationPanel;

	private static final String[] PERCENTAGES = { "5", "10", "15", "20", "25",
			"30", "35", "40", "45" };

	private static final String[] NUMOFGRADES = { "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "10", "11", "12", "13", "14", "15", "16" };

	private JComboBox<String> percentComboBox;

	private JComboBox<String> numOfGradesComboBox;

	private JCheckBox dropLowestBox;

	private JCheckBox extraCreditBox;

	private String gradeType;

	private JTextField earnedField;

	private JTextField possibleField;

	private GridLayout entryLayout;

	private static final int WEIGHTED_ROWS = 6;

	public GradePanel(SystemController controller, Grade grade) {
		super(controller);

		this.grade = grade;
		this.gradeType = grade.getType();

		this.addComponentsToPane();
	}

	private void addComponentsToPane() {
		entryLayout = new GridLayout(5, 2);

		entryPanel = new JPanel();
		entryPanel.setLayout(entryLayout);
		entryPanel.add(new JLabel("Cumulative points earned: "));

		earnedField = new JTextField(3);
		entryPanel.add(earnedField);

		entryPanel.add(new JLabel("Number of grades: "));

		numOfGradesComboBox = new JComboBox<String>(NUMOFGRADES);
		numOfGradesComboBox.setSelectedIndex(0);
		entryPanel.add(this.numOfGradesComboBox);

		entryPanel.add(new JLabel("Points possible for each: "));
		possibleField = new JTextField(3);
		entryPanel.add(possibleField);

		if (controller.getActiveCourse().getWeighted()) {
			entryLayout.setRows(WEIGHTED_ROWS);
			entryPanel.add(new JLabel("% of grade: "));

			this.percentComboBox = new JComboBox<String>(PERCENTAGES);
			this.percentComboBox.setSelectedIndex(0);
			entryPanel.add(this.percentComboBox);
		}

		entryPanel.add(new JLabel("Extra credit offered?"));
		extraCreditBox = new JCheckBox();
		entryPanel.add(this.extraCreditBox);

		entryPanel.add(new JLabel("Drop lowest " + gradeType + "?"));
		dropLowestBox = new JCheckBox();
		entryPanel.add(dropLowestBox);

		if (grade.getPointsPossiblePer() != 0) {
			this.earnedField.setText(Double.toString(grade.getTotalEarned()));

			this.numOfGradesComboBox.setSelectedItem(Integer.toString(grade
					.getNumOfGrades()));

			possibleField
					.setText(Integer.toString(grade.getPointsPossiblePer()));
			if (controller.getActiveCourse().getWeighted())
				this.percentComboBox.setSelectedItem(Integer.toString(grade
						.getPercentWeight()));
			if (grade.getDropLowest()) {
				dropLowestBox.setSelected(true);
			}
		}

		this.createTitledBorder(entryPanel, gradeType);

		navigationPanel = new JPanel(new GridLayout(2, 1));
		navigationPanel.add(createButton("done", "Done"));
		navigationPanel.add(createButton("cancel", "Cancel"));

		this.createTitledBorder(this.navigationPanel, "Navigation");

		this.setLayout(new BoxLayout(this, 3));
		this.add(entryPanel);
		this.add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (action.equals("cancel")) {
			controller.getRootFrame().showPanel("CourseInfoPanel", this);
		} else if (action.equals("done")) {
			String total = this.earnedField.getText();
			String pointsPer = possibleField.getText();

			if ((!total.equals("")) && (!pointsPer.equals(""))) {
				int numOfGrades = Integer
						.parseInt((String) this.numOfGradesComboBox
								.getSelectedItem());
				int pointsPossiblePer = Integer.parseInt(pointsPer);
				double totalEarned = Double.parseDouble(total);

				if ((totalEarned > pointsPossiblePer * numOfGrades)
						&& (!this.extraCreditBox.isSelected())) {
					JOptionPane
							.showMessageDialog(
									this,
									"The cumulative points earned you entered is not possible. If extra credit was offered, check the extra credit box.",
									"Error", 0);
					return;
				}

				grade.setTotalEarned(totalEarned);
				grade.setPointsPossiblePer(pointsPossiblePer);

				if (controller.getActiveCourse().getWeighted()) {
					grade.setPercentWeight(Integer
							.parseInt((String) this.percentComboBox
									.getSelectedItem()));
				}

				grade.setNumOfGrades(numOfGrades);
				grade.setDropLowest(false);

				if (dropLowestBox.isSelected()) {
					grade.setDropLowest(true);
				}

				controller.saveUserList();
				controller.getRootFrame().addPanel(
						new CourseInfoPanel(controller), this);
			} else {
				JOptionPane.showMessageDialog(this,
						"You left some values blank. Try again.", "Error", 0);
			}
		}
	}
}
