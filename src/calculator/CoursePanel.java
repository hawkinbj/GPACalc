package calculator;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class CoursePanel extends GUIPanel implements ActionListener {

	private static final long serialVersionUID = -6768153191699813450L;
	protected JPanel infoPanel, coursePanel, navigationPanel;

	public CoursePanel(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {

		// Information panel.
		infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
		createTitledBorder(infoPanel, controller.activeSemester.getSchoolName()
				+ " " + controller.activeSemester);
		// Total credit hours attempted this semester.
		JLabel creditHoursLbl = new JLabel("Credit hours: "
				+ Integer.toString(controller.activeSemester
						.getTotalHoursAttempted()));
		infoPanel.add(creditHoursLbl);
		// Semester GPA.
		double gpa = controller.calcSemseterGPA();
		JLabel gpaLbl = new JLabel();
		if (gpa == -1 || Double.isNaN(gpa))
			gpaLbl.setText("Semester GPA: N/A");
		else
			gpaLbl.setText("Semester GPA: " + String.format("%.2f", gpa));
		infoPanel.add(gpaLbl);

		// Course panel.
		coursePanel = new JPanel();
		coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.PAGE_AXIS));
		createTitledBorder(coursePanel, "Select Course");
		if (controller.activeSemester.getCourses().size() == 0) {
			JLabel instructionLbl = new JLabel(
					"Add a new course to get started.");
			instructionLbl.setForeground(Color.blue);
			coursePanel.add(instructionLbl);
		} else {
			// Buttons to represent courses.
			for (String key : controller.activeSemester.getCourses().keySet()) {
				coursePanel.add(createButton(key));
			}
		}

		// Navigation label/separator.
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel,
				BoxLayout.PAGE_AXIS));
		createTitledBorder(navigationPanel, "Navigation");
		// Add course button.
		navigationPanel.add(createButton("addCourse", "Add new..."));
		// Back button.
		navigationPanel.add(createButton("back", "Back"));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(infoPanel);
		add(coursePanel);
		add(navigationPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("back")) {
			controller.activeSemester = null;
			controller.rootFrame.addPanel(new SemesterPanel(controller), this);
		} else if (action.equals("addCourse")) {
			controller.rootFrame.addPanel(new CourseDialog(controller), this);
		} else {
			// Set active course.
			controller.activeCourse = controller.activeSemester.getCourses()
					.get(action);
			controller.rootFrame
					.addPanel(new CourseInfoPanel(controller), this);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();
		// If right click and a semester button.
		if (SwingUtilities.isRightMouseButton(e) && component.getName() != null) {
			int response = JOptionPane.showConfirmDialog(this,
					"Are you sure you wish to remove this course?", "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				controller.activeSemester.getCourses().remove(
						component.getName());
				controller.saveUserList();
				controller.rootFrame
						.addPanel(new CoursePanel(controller), this);
			} else if (response == JOptionPane.CLOSED_OPTION) {
				return;
			}
		} else {
			return;
		}
	}
}
