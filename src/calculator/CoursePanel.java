package calculator;

import java.awt.Color;
import java.awt.Component;
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
	protected JPanel coursePanel, navigationPanel;
	protected Semester semester;

	public CoursePanel(SystemController controller, Semester semester) {
		super(controller);
		this.semester = semester;
		addComponentsToPane();
		System.out.println(this.getClass());
	}

	private void addComponentsToPane() {

		// Layout.
		coursePanel = new JPanel();
		coursePanel.setLayout(new BoxLayout(coursePanel, BoxLayout.PAGE_AXIS));

		// Semester label.
		JLabel semesterLbl = new JLabel("Semester: " + semester.getSchoolName()
				+ " " + semester.getSemesterName() + "\n");
		semesterLbl.setForeground(Color.blue);
		coursePanel.add(semesterLbl);
		// Instructions label.
		JLabel semestersInstructionLbl = new JLabel("Choose a class:");
		coursePanel.add(semestersInstructionLbl);
		// Buttons to represent courses.
		for (String key : semester.getCourses().keySet()) {
			coursePanel.add(createButton(key));
		}

		// Navigation label/separator.
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel,
				BoxLayout.PAGE_AXIS));
		navigationPanel.add(new JLabel("Navigation"));
		// Add course button.
		navigationPanel.add(createButton("addCourse", "Add new..."));
		// Back button.
		navigationPanel.add(createButton("back", "Back"));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(coursePanel);
		add(navigationPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("back")) {
			controller.rootFrame.showPanel("semesterPanel", this);
		} else if (action.equals("addCourse")) {
			controller.rootFrame.addPanel(new CourseDialog(controller),
					"courseDialog");
			controller.rootFrame.showPanel("courseDialog", this);
			controller.rootFrame.setSize(300, 500);
		} else {
			// Set active course.
			controller.activeCourse = semester.getCourses().get(action);
			controller.rootFrame.addPanel(new CourseInfoPanel(controller,
					semester.getCourses().get(action)), "courseInfoPanel");
			controller.rootFrame.showPanel("courseInfoPanel", this);
			controller.rootFrame.setSize(500, 500);

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Component component = e.getComponent();
		// If right click and a semester button.
		if (SwingUtilities.isRightMouseButton(e) && component.getName() != null) {
			int response = JOptionPane.showConfirmDialog(null,
					"Are you sure you wish to remove this course?", "Confirm",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				semester.getCourses().remove(component.getName());
				controller.saveUserList();
				coursePanel.remove(component);
				coursePanel.revalidate();
				coursePanel.repaint();
			} else if (response == JOptionPane.CLOSED_OPTION) {
				return;
			}
		} else {
			return;
		}
	}
}
