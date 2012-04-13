package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class CourseFrame extends JFrame implements ActionListener {

	private SystemController controller;
	private JFrame previousFrame;
	private JButton doneBtn, backBtn;
	private JPanel panel;
	private JLabel courseNameLbl;
	private JTextField courseNameField;

	public CourseFrame(SystemController controller, JFrame previousFrame) {
		// Initialization.
		setSize(300, 300);
		setTitle("New Course");
		this.controller = controller;
		this.previousFrame = previousFrame;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Course name.
		courseNameLbl = new JLabel("Course name: ");
		courseNameField = new JTextField(10); // int = entry spaces.
		courseNameField.addActionListener(this);
	
		// Done button.
		doneBtn = new JButton("Done");
		doneBtn.setActionCommand("done");
		doneBtn.addActionListener(this);
	
		// Back button.
		backBtn = new JButton("Back");
		backBtn.setActionCommand("back");
		backBtn.addActionListener(this);
		
		 // Layout.
		 panel = new JPanel(new GridLayout(2,2));
		 panel.add(courseNameLbl);
		 panel.add(courseNameField);
		 panel.add(backBtn);
		 panel.add(doneBtn);
		 add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("back")) {
			setVisible(false);
			previousFrame.setVisible(true);
		}
		if (action.equals("done")) {
			// Check that a valid course name was entered.
			String courseName = courseNameField.getText();
			if (courseName != null) {
				// NEED TO CHECK THIS DOESN'T MAKE buttons with the exact same
				// name....
				JButton newCourseBtn = new JButton(courseName);
				newCourseBtn.setActionCommand(courseName);
				newCourseBtn.addActionListener(this);
				previousFrame.add(newCourseBtn);
				// Might need to panel.revalidate();
			}
			setVisible(false);
			previousFrame.setVisible(true);
		}

	}
}
