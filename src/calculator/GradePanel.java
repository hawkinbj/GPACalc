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
	private JPanel instructionPanel, entryPanel, navigationPanel;
	// Add customization later.
	private static final String[] PERCENTAGES = { "N/A", "5", "10", "15", "20",
			"25", "30", "35" };
	private static final String[] NUMOFGRADES = { "1", "2", "3", "4", "5", "6",
			"7", "8", "9", "10", "11", "12", "13", "14", "15", "16" };
	private JComboBox percentComboBox, numOfGradesComboBox;
	private JCheckBox dropLowestBox;
	private String gradeType;
	private JTextField earnedField, possibleField;

	public GradePanel(SystemController controller, Grade grade) {
		super(controller);
		this.grade = grade;
		this.gradeType = grade.getType();
		addComponentsToPane();
		System.out.println(this.getClass());
	}

	private void addComponentsToPane() {
		// Instructions.
		instructionPanel = new JPanel(new GridLayout(2, 1));
		JLabel gradeTypeLbl = new JLabel("Grade Type - " + gradeType);
		gradeTypeLbl.setForeground(Color.blue);
		instructionPanel.add(gradeTypeLbl);
		instructionPanel.add(new JLabel("Enter values."));

		// Entry panel.
		entryPanel = new JPanel(new GridLayout(5, 2));
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
		// Percentage of grade.
		entryPanel.add(new JLabel("% of grade: "));
		percentComboBox = new JComboBox(PERCENTAGES);
		percentComboBox.setSelectedIndex(0);
		entryPanel.add(percentComboBox);
		// Drop lowest? (checkbox).
		entryPanel.add(new JLabel("Drop lowest " + gradeType + "?"));
		dropLowestBox = new JCheckBox();
		entryPanel.add(dropLowestBox);

		// Navigation label/separator.
		navigationPanel = new JPanel(new GridLayout(3, 1));
		// Done button.
		navigationPanel.add(createButton("done", "Done"));
		// Cancel button.
		navigationPanel.add(createButton("cancel", "Cancel"));

		// Load existing data (if any).
		if (grade.getPointsPossiblePer() != 0) {
			earnedField.setText(Double.toString(grade.getTotalEarned()));
			numOfGradesComboBox.setSelectedItem(Integer.toString(grade
					.getNumOfGrades()));
			possibleField
					.setText(Integer.toString(grade.getPointsPossiblePer()));
			percentComboBox.setSelectedItem(Integer.toString(grade
					.getPercentWeight()));
			if (grade.getDropLowest()) {
				dropLowestBox.setSelected(true);
			}
		}

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(instructionPanel);
		add(entryPanel);
		add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			controller.showPanel("courseInfoPanel", this);
		} else if (action.equals("done")) {

			String total = earnedField.getText();
			String pointsPer = possibleField.getText();

			if (!total.equals("") && !pointsPer.equals("")) {
				int numOfGrades = Integer.parseInt((String) numOfGradesComboBox
						.getSelectedItem());
				int pointsPossiblePer = Integer.parseInt(pointsPer);
				double totalEarned = Double.parseDouble(total);
				if (totalEarned > (pointsPossiblePer * numOfGrades)) {
					JOptionPane
							.showMessageDialog(
									this,
									"The cumulative points earned you entered is not possible. Try again.",
									"Error", JOptionPane.ERROR_MESSAGE);
					return; // might need to break
				}
				grade.setTotalEarned(totalEarned);
				grade.setPointsPossiblePer(pointsPossiblePer);
				// If no percentage selected default to 100.
				if (percentComboBox.getSelectedItem().equals("N/A")) {
					grade.setPercentWeight(100);
				} else {
					grade.setPercentWeight(Integer
							.parseInt((String) percentComboBox
									.getSelectedItem()));
				}
				grade.setNumOfGrades(numOfGrades);
				grade.setDropLowest(false);
				if (dropLowestBox.isSelected())
					grade.setDropLowest(true);
				controller.saveUserList();
				controller.showPanel("courseInfoPanel", this);
			} else {
				JOptionPane.showMessageDialog(this,
						"You left some values blank. Try again.", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}
