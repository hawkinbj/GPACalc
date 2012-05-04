package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class MainHub extends JFrame implements ActionListener, MouseListener {

	private SystemController controller;
	protected JPanel mainMenuPanel;
	private JMenuBar menuBar;
	private JMenu fileMenu;
	private JMenuItem newAction, openAction; // temporary.
	private JButton selectSchool;

	public MainHub(SystemController controller) {

		setSize(300, 300);
		this.controller = controller;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Main Menu");

		// Menu.
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		// Define and add two drop down menu to the menubar.
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);

		// Create and add menu item to drop down menus.
		JMenuItem newAction = new JMenuItem("New");
		JMenuItem openAction = new JMenuItem("Open");
		fileMenu.add(newAction);
		fileMenu.add(openAction);

		// Select school button.
		selectSchool = new JButton("Select School...");
		selectSchool.setActionCommand("selectSchool");
		selectSchool.addActionListener(this);

		// Layout.
		mainMenuPanel = new JPanel(new GridLayout(controller.activeUser
				.getTranscripts().size() + 2, 1));
		mainMenuPanel.add(selectSchool);
		// Load active users information (if any) and display on screen.
		for (Transcript transcript : controller.activeUser.getTranscripts()) {
			String schoolName = transcript.getSchoolName();
			JButton schoolBtn = new JButton(schoolName);
			schoolBtn.setActionCommand(schoolName);
			schoolBtn.addActionListener(this);
			mainMenuPanel.add(schoolBtn);
		}
		add(mainMenuPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("selectSchool")) {
			new SchoolFrame(controller, this).setVisible(true);
			setVisible(false);
		} else {
			for (School school : controller.schools) {
				String schoolName = school.getName();
				if (schoolName.equals(action)) {
					// go to SemesterFrame
					new SemesterFrame(controller, this, schoolName)
							.setVisible(true);
					setVisible(false);
				}
			}

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			System.out.println("Right button");
		} else {
			System.out.println("NOT A RIGHT BUTTON");
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
