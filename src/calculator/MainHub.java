package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MainHub extends JFrame implements ActionListener {

	private SystemController controller;
	private JFrame previousFrame;
	protected JPanel panel;

	public MainHub(SystemController controller) {

		setSize(300, 300);
		setTitle("Main Menu");
		this.controller = controller;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Menu.
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Define and add two drop down menu to the menubar.
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		// Create and add menu item to drop down menus.
		JMenuItem newAction = new JMenuItem("New");
		JMenuItem openAction = new JMenuItem("Open");
		fileMenu.add(newAction);
		fileMenu.add(openAction);

		// Logout button.

		// New course button.
		JButton newCourseBtn = new JButton("New course...");
		newCourseBtn.setActionCommand("newCourse");
		newCourseBtn.addActionListener(this);

		// Layout.
		panel = new JPanel(new GridLayout(3, 1));
		panel.add(newCourseBtn);
		add(panel);
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
