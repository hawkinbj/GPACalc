package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class FinalGradePanel extends GUIPanel {

	private static final long serialVersionUID = -438674586864467004L;
	private JComboBox letterGradeComboBox;

	public FinalGradePanel(SystemController controller) {
		super(controller);
		addComponentsToPane();

	}

	private void addComponentsToPane() {
		// Set grade panel.
		JPanel setGradePanel = new JPanel(new GridLayout(2, 2));
		createTitledBorder(setGradePanel,
				controller.activeCourse.getCourseName());
		setGradePanel.add(new JLabel("Set final letter grade: "));

		// Convert GradingScale's gradingScaleMap to array of choices.
		Object[] letterGrades = controller.activeSchool.getGradingScale()
				.getGradingScaleMap().keySet().toArray();
		Arrays.sort(letterGrades);
		letterGradeComboBox = new JComboBox(letterGrades);
		setGradePanel.add(letterGradeComboBox);
		letterGradeComboBox.setSelectedItem(controller.activeCourse
				.getFinalGrade());

		// Button to clear selection (and a label).
		setGradePanel.add(new JLabel("Clear final grade: "));
		setGradePanel.add(createButton("clearFinalGrade", "Clear"));

		// Navigation panel.
		JPanel navPanel = new JPanel(new GridLayout(2, 1));
		createTitledBorder(navPanel, "Navigation");
		// Done button.
		navPanel.add(createButton("done", "Done"));
		// Cancel button.
		navPanel.add(createButton("cancel", "Cancel"));

		// Layout.
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(setGradePanel);
		add(navPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("clearFinalGrade"))
			letterGradeComboBox.setSelectedIndex(-1);
		else if (action.equals("done")) {
			controller.activeCourse.setFinalGrade((String) letterGradeComboBox
					.getSelectedItem());
			controller.saveUserList();
			controller.rootFrame
					.addPanel(new CourseInfoPanel(controller), this);
		} else if (action.equals("cancel")) {
			controller.rootFrame.showPanel("CourseInfoPanel", this);
		}
	}
}
