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

	private JComboBox percentComboBox;
	private JComboBox numOfGradesComboBox;
	private JCheckBox dropLowestBox;
	private JCheckBox extraCreditBox;
	private String gradeType;
	private JTextField earnedField;
	private JTextField possibleField;

	public GradePanel(SystemController controller, Grade grade) {
		super(controller);
		this.grade = grade;
		this.gradeType = grade.getType();
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.entryPanel = new JPanel();
		GridLayout entryLayout = new GridLayout(5, 2);
		this.entryPanel.setLayout(entryLayout);
		createTitledBorder(this.entryPanel, this.gradeType);

		this.entryPanel.add(new JLabel("Cumulative points earned: "));
		this.earnedField = new JTextField(3);
		this.entryPanel.add(this.earnedField);

		this.entryPanel.add(new JLabel("Number of grades: "));
		this.numOfGradesComboBox = new JComboBox(NUMOFGRADES);
		this.numOfGradesComboBox.setSelectedIndex(0);
		this.entryPanel.add(this.numOfGradesComboBox);

		this.entryPanel.add(new JLabel("Points possible for each: "));
		this.possibleField = new JTextField(3);
		this.entryPanel.add(this.possibleField);

		if (this.controller.getActiveCourse().getWeighted()) {
			entryLayout.setRows(6);
			this.entryPanel.add(new JLabel("% of grade: "));
			this.percentComboBox = new JComboBox(PERCENTAGES);
			this.percentComboBox.setSelectedIndex(0);
			this.entryPanel.add(this.percentComboBox);
		}

		this.entryPanel.add(new JLabel("Extra credit offered?"));
		this.extraCreditBox = new JCheckBox();
		this.entryPanel.add(this.extraCreditBox);

		this.entryPanel.add(new JLabel("Drop lowest " + this.gradeType + "?"));
		this.dropLowestBox = new JCheckBox();
		this.entryPanel.add(this.dropLowestBox);

		if (this.grade.getPointsPossiblePer() != 0) {
			this.earnedField.setText(Double.toString(this.grade
					.getTotalEarned()));
			this.numOfGradesComboBox.setSelectedItem(Integer
					.toString(this.grade.getNumOfGrades()));
			this.possibleField.setText(Integer.toString(this.grade
					.getPointsPossiblePer()));
			if (this.controller.getActiveCourse().getWeighted())
				this.percentComboBox.setSelectedItem(Integer
						.toString(this.grade.getPercentWeight()));
			if (this.grade.getDropLowest()) {
				this.dropLowestBox.setSelected(true);
			}

		}

		navigationPanel = new JPanel(new GridLayout(2, 1));
		navigationPanel.add(createButton("done", "Done"));
		navigationPanel.add(createButton("cancel", "Cancel"));

		this.createTitledBorder(this.navigationPanel, "Navigation");

		setLayout(new BoxLayout(this, 3));
		add(entryPanel);
		add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			this.controller.getRootFrame().showPanel("CourseInfoPanel", this);
		} else if (action.equals("done")) {
			String total = this.earnedField.getText();
			String pointsPer = this.possibleField.getText();

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
				this.grade.setTotalEarned(totalEarned);
				this.grade.setPointsPossiblePer(pointsPossiblePer);

				if (this.controller.getActiveCourse().getWeighted()) {
					this.grade.setPercentWeight(Integer
							.parseInt((String) this.percentComboBox
									.getSelectedItem()));
				}
				this.grade.setNumOfGrades(numOfGrades);
				this.grade.setDropLowest(false);
				if (this.dropLowestBox.isSelected())
					this.grade.setDropLowest(true);
				this.controller.saveUserList();
				this.controller.getRootFrame().addPanel(
						new CourseInfoPanel(this.controller), this);
			} else {
				JOptionPane.showMessageDialog(this,
						"You left some values blank. Try again.", "Error", 0);
			}
		}
	}
}
