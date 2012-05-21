package calculator;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GradePanel extends GUIPanel {

	private static final long serialVersionUID = 1673864396132217214L;
	private Grade grade;
	private JPanel entryPanel, navigationPanel;
	// Add customization later.
	private static final String[] PERCENTAGES = { "5", "10", "15", "20", "25",
			"30", "35", "40", "45" };
	private static final String[] NUMOFGRADES = { "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "10", "11", "12", "13", "14", "15", "16" };
	private JComboBox percentComboBox, numOfGradesComboBox;
	private JCheckBox dropLowestBox, extraCreditBox;
	private String gradeType;
	private JTextField earnedField, possibleField;

	public GradePanel(SystemController controller, Grade grade) {
		super(controller);
		this.grade = grade;
		this.gradeType = grade.getType();
		addComponentsToPane();
	}

	private void addComponentsToPane() {

		// // Instructions.
		// instructionPanel = new JPanel(new GridLayout(2, 1));

		// Entry panel.
		entryPanel = new JPanel();
		GridLayout entryLayout = new GridLayout(5, 2);
		entryPanel.setLayout(entryLayout);
		createTitledBorder(entryPanel, gradeType);

		// Points earned.
		entryPanel.add(new JLabel("Cumulative points earned: "));
		earnedField = new JTextField(3);
		entryPanel.add(earnedField);

		// Number of grades.
		entryPanel.add(new JLabel("Number of grades: "));
		numOfGradesComboBox = new JComboBox(NUMOFGRADES);
		numOfGradesComboBox.setSelectedIndex(0);
		entryPanel.add(numOfGradesComboBox);

		// Points possible for each.
		entryPanel.add(new JLabel("Points possible for each: "));
		possibleField = new JTextField(3);
		entryPanel.add(possibleField);

		// Percentage of grade - only show if Course.getWeighted() == true.
		if (controller.activeCourse.getWeighted()) {
			entryLayout.setRows(6);
			entryPanel.add(new JLabel("% of grade: "));
			percentComboBox = new JComboBox(PERCENTAGES);
			percentComboBox.setSelectedIndex(0);
			entryPanel.add(percentComboBox);
		}

		// Extra credit allowed?
		entryPanel.add(new JLabel("Extra credit offered?"));
		extraCreditBox = new JCheckBox();
		entryPanel.add(extraCreditBox);
		// Drop lowest? (checkbox).
		entryPanel.add(new JLabel("Drop lowest " + gradeType + "?"));
		dropLowestBox = new JCheckBox();
		entryPanel.add(dropLowestBox);

		// Load existing data (if any).
		if (grade.getPointsPossiblePer() != 0) {
			earnedField.setText(Double.toString(grade.getTotalEarned()));
			numOfGradesComboBox.setSelectedItem(Integer.toString(grade
					.getNumOfGrades()));
			possibleField
					.setText(Integer.toString(grade.getPointsPossiblePer()));
			if (controller.activeCourse.getWeighted())
				percentComboBox.setSelectedItem(Integer.toString(grade
						.getPercentWeight()));
			if (grade.getDropLowest()) {
				dropLowestBox.setSelected(true);
			}
		}

		// Navigation label/separator.
		navigationPanel = new JPanel(new GridLayout(2, 1));
		createTitledBorder(navigationPanel, "Navigation");
		// Done button.
		navigationPanel.add(createButton("done", "Done"));
		// Cancel button.
		navigationPanel.add(createButton("cancel", "Cancel"));

		// Layout.
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(entryPanel);
		add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			controller.rootFrame.showPanel("CourseInfoPanel", this);
		} else if (action.equals("done")) {

			String total = earnedField.getText();
			String pointsPer = possibleField.getText();

			if (!total.equals("") && !pointsPer.equals("")) {
				int numOfGrades = Integer.parseInt((String) numOfGradesComboBox
						.getSelectedItem());
				int pointsPossiblePer = Integer.parseInt(pointsPer);
				double totalEarned = Double.parseDouble(total);
				if ((totalEarned > (pointsPossiblePer * numOfGrades))
						&& !extraCreditBox.isSelected()) {
					JOptionPane
							.showMessageDialog(
									this,
									"The cumulative points earned you entered is not possible. If extra credit was offered, check the extra credit box.",
									"Error", JOptionPane.ERROR_MESSAGE);
					return; // might need to break
				}
				grade.setTotalEarned(totalEarned);
				grade.setPointsPossiblePer(pointsPossiblePer);
				// If course is weighted, get the selected weight.
				if (controller.activeCourse.getWeighted()) {
					// Save the percentage.
					grade.setPercentWeight(Integer
							.parseInt((String) percentComboBox
									.getSelectedItem()));
				}
				grade.setNumOfGrades(numOfGrades);
				grade.setDropLowest(false);
				if (dropLowestBox.isSelected())
					grade.setDropLowest(true);
				controller.saveUserList();
				controller.rootFrame.addPanel(new CourseInfoPanel(controller),
						this);
			} else {
				JOptionPane.showMessageDialog(this,
						"You left some values blank. Try again.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
