package calculator;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CourseInfoPanel extends GUIPanel {

	private static final long serialVersionUID = 1488575000302467412L;
	private JPanel infoPanel, instructionPanel, gradeTypesPanel,
			navigationPanel;
	private JComboBox letterGradeComboBox;
	private JLabel finalGradeLabel;

	public CourseInfoPanel(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		// Info panel.
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		createTitledBorder(infoPanel, controller.activeCourse.getCourseName());
		infoPanel.add(new JLabel("Credit hours: "
				+ Integer.toString(controller.activeCourse.getCreditHours())));
		// infoPanel.add(new JLabel("Final grade: " + course.getFinalGrade()));
		finalGradeLabel = new JLabel("Final grade: "
				+ controller.activeCourse.getFinalGrade());
		infoPanel.add(finalGradeLabel);

		// Grade types panel.
		gradeTypesPanel = new JPanel();
		gradeTypesPanel.setLayout(new BoxLayout(gradeTypesPanel,
				BoxLayout.PAGE_AXIS));
		createTitledBorder(gradeTypesPanel, "Select Grade Type");
		for (String gradeType : controller.activeCourse.getGrades().keySet()) {
			gradeTypesPanel.add(createButton(gradeType));
		}
		// Set final grade button
		gradeTypesPanel.add(createButton("setFinalGrade", "Final Grade"));

		// Navigation panel.
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel,
				BoxLayout.PAGE_AXIS));
		createTitledBorder(navigationPanel, "Navigation");
		// Edit course button - MOVE THIS TO CoursePanel right-click menu...
		navigationPanel.add(createButton("edit", "Edit Course..."));
		// Back button.
		navigationPanel.add(createButton("back", "Back"));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(infoPanel);
		add(gradeTypesPanel);
		add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("back")) {
			controller.activeCourse = null;
			// Need to create new as course may have been edited.
			controller.rootFrame.addPanel(new CoursePanel(controller), this);
		} else if (action.equals("setFinalGrade")) {
			// CONSIDER MAKING A NEW CLASS!!!!
			// Final grade panel.
			JPanel setGradePanel = new JPanel(new GridLayout(1, 2));
			setGradePanel.add(new JLabel("Set final letter grade: "));
			// Convert GradingScale's gradingScaleMap to array of choices.
			Object[] letterGrades = controller.activeSchool.getGradingScale()
					.getGradingScaleMap().keySet().toArray();
			Arrays.sort(letterGrades);
			letterGradeComboBox = new JComboBox(letterGrades);
			setGradePanel.add(letterGradeComboBox);
			int test = JOptionPane.showOptionDialog(this, setGradePanel,
					"Set Final Grade", JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);
			if (test == JOptionPane.OK_OPTION) {
				controller.activeCourse
						.setFinalGrade((String) letterGradeComboBox
								.getSelectedItem());
				finalGradeLabel.setText("Final grade: "
						+ controller.activeCourse.getFinalGrade());
				controller.saveUserList();
				validate();
				repaint();
			} else
				// user clicked cancel.
				return;
		} else if (action.equals("edit")) {
			controller.rootFrame.addPanel(new CourseDialog(controller), this);
		} else {
			controller.rootFrame.addPanel(new GradePanel(controller,
					controller.activeCourse.getGrades().get(action)), this);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();
		// If right click and a semester button.
		if (SwingUtilities.isRightMouseButton(e) && component.getName() != null) {
			if (component.getName().equals("setFinalGrade"))
				return;
			int response = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove this grade type?",
					"Confirm", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				controller.activeCourse.removeGrade(component.getName());
				controller.saveUserList();
				gradeTypesPanel.remove(component);
				gradeTypesPanel.revalidate();
				gradeTypesPanel.repaint();
			} else if (response == JOptionPane.CLOSED_OPTION) {
				return;
			}
			// } else {
			// return;
		}
	}
}
