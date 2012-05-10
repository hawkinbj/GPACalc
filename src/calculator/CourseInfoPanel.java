package calculator;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CourseInfoPanel extends GUIPanel {

	private static final long serialVersionUID = 1488575000302467412L;
	private Course course;
	protected JPanel infoPanel, instructionPanel, gradeTypesPanel,
			navigationPanel;

	public CourseInfoPanel(SystemController controller, Course course) {
		super(controller);
		this.course = course;
		addComponentsToPane();
		System.out.println(this.getClass());
	}

	private void addComponentsToPane() {
		// Info panel.
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		// Labels.
		JLabel courseLbl = new JLabel("Course: " + course.getCourseName()
				+ "\n");
		courseLbl.setForeground(Color.blue);
		infoPanel.add(courseLbl);
		infoPanel.add(new JLabel("Credit hours: "
				+ Integer.toString(course.getCreditHours())));

		// Instruction panel.
		instructionPanel = new JPanel();
		instructionPanel.setLayout(new BoxLayout(instructionPanel,
				BoxLayout.PAGE_AXIS));
		// Instructions label.
		JLabel InstructionLbl = new JLabel("Choose a grade type to edit:");
		instructionPanel.add(InstructionLbl);

		// Grade types panel.
		gradeTypesPanel = new JPanel();
		gradeTypesPanel.setLayout(new BoxLayout(gradeTypesPanel,
				BoxLayout.PAGE_AXIS));
		for (String gradeType : course.getGrades().keySet()) {
			gradeTypesPanel.add(createButton(gradeType));
		}

		// Navigation panel.
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel,
				BoxLayout.PAGE_AXIS));
		navigationPanel.add(new JLabel("Navigation"));
		// Set final grade button
		// Edit course button - MOVE THIS TO CoursePanel right-click menu...
		navigationPanel.add(createButton("edit", "Edit..."));
		// Calculate button.
		navigationPanel.add(createButton("calculate", "Calculate"));
		// Back button.
		navigationPanel.add(createButton("back", "Back"));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(infoPanel);
		add(instructionPanel);
		add(gradeTypesPanel);
		add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("back")) {
			controller.showPanel("coursePanel");
		} else if (action.equals("calculate")) {
			controller.addPanel(new CalcPanel(controller, course), "calcPanel");
			controller.showPanel("calcPanel");
			controller.rootFrame.setSize(300, 300);
		} else if (action.equals("edit")) {
			controller.addPanel(new CourseDialog(controller), "courseDialog");
			CourseDialog previousFrame = (CourseDialog) controller.panels
					.get("courseDialog");
			previousFrame.courseNameField.setText(course.getCourseName());
			// Check the appropriate checkboxes.
			for (int i = 0; i < previousFrame.gradeCheckBoxes.size(); i++) {
				JCheckBox checkBox = previousFrame.gradeCheckBoxes.get(i);
				if (course.getGrades().get(checkBox.getName()) != null) {
					checkBox.setSelected(true);
				}
			}
			controller.showPanel("courseDialog", this);
			controller.rootFrame.setSize(300, 500);
		} else {
			controller.addPanel(new GradePanel(controller, course.getGrades()
					.get(action)), "gradePanel");
			controller.showPanel("gradePanel", this);
			controller.rootFrame.setSize(325, 300);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();
		// If right click and a semester button.
		if (SwingUtilities.isRightMouseButton(e) && component.getName() != null) {
			int response = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove this grade type?",
					"Confirm", JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				course.removeGrade(component.getName());
				controller.saveUserList();
				gradeTypesPanel.remove(component);
				gradeTypesPanel.revalidate();
				gradeTypesPanel.repaint();
			} else if (response == JOptionPane.CLOSED_OPTION) {
				return;
			}
		} else {
			return;
		}
	}
}
