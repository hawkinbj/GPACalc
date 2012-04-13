package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainHub extends JFrame implements ActionListener {

	private SystemController controller;
	private JFrame previousFrame;
	private JButton newCourseBtn;

	// private JPanel panel;

	public MainHub(SystemController controller) {

		setSize(300, 300);
		setTitle("Main Menu");
		this.controller = controller;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Logout button.

		// New course button.
		newCourseBtn = new JButton("New course...");
		newCourseBtn.setActionCommand("newCourse");
		newCourseBtn.addActionListener(this);
		add(newCourseBtn);

		// Layout.
		// panel = new JPanel(new GridLayout(3, 1));
		// panel.add(newCourseBtn);
		// add(panel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("newCourse")) {
			new CourseFrame(controller, this).setVisible(true);
			this.setVisible(false);
		}

	}
}
