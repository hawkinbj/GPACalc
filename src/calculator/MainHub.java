package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class MainHub extends JFrame implements ActionListener {

	private SystemController controller;
	private JFrame previousFrame;
	private JTextField schoolNameField;
	private JPanel mainMenuPanel, schoolsPanel;

	public MainHub(SystemController controller) {

		setSize(300, 300);
		this.controller = controller;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainMenu();
	}

	private void mainMenu() {

		setTitle("Main Menu");

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

		// Add school button.
		JButton addSchoolBtn = new JButton("Add School...");
		addSchoolBtn.setActionCommand("addSchool");
		addSchoolBtn.addActionListener(this);

		// Layout.
		mainMenuPanel = new JPanel(new GridLayout(2, 1));
		mainMenuPanel.add(addSchoolBtn);
		add(mainMenuPanel);
	}

	private void selectSchool() {

		// Schools panel.
		schoolsPanel = new JPanel(new GridLayout(controller.schools.size() + 3,
				1));
		// Instructions label.
		JLabel schoolsInstructionLbl = new JLabel("Choose a school:");
		schoolsPanel.add(schoolsInstructionLbl);
		// Buttons to represent schools.
		for (int i = 0; i < controller.schools.size(); i++) {
			String schoolName = controller.schools.get(i).getName();
			JButton schoolBtn = new JButton(schoolName); // Might need to
															// uniquely name....
			schoolBtn.setActionCommand(schoolName);
			schoolBtn.addActionListener(this);
			schoolsPanel.add(schoolBtn);
		}

		// Navigation label/separator.
		JLabel schoolsNavLbl = new JLabel("Navigation");
		schoolsPanel.add(schoolsNavLbl);

		// Cancel button.
		JButton cancelBtn = new JButton("Cancel");
		cancelBtn.setActionCommand("cancelSelectSchool");
		cancelBtn.addActionListener(this);

		schoolsPanel.add(cancelBtn);

		add(schoolsPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("addSchool")) {
			mainMenuPanel.setVisible(false);
			selectSchool();
		}
		if (action.equals("doneAddSchool")) {
			String newSchoolName = schoolNameField.getText();
			// Add new transcript to active user's profile.
			controller.activeUser.addTranscript(new Transcript(newSchoolName));
			JButton schoolBtn = new JButton(newSchoolName);
			schoolBtn.setName(newSchoolName);
			schoolBtn.setActionCommand("school");
			mainMenuPanel.add(schoolBtn);
		}

		if (action.equals("courses")) {
			new CourseFrame(controller, this).setVisible(true);
			this.setVisible(false);
		}
		if (action.equals("cancelSelectSchool")) {
			schoolsPanel.setVisible(false);
			mainMenuPanel.setVisible(true);
		}

	}
}
