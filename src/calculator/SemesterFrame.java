package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class SemesterFrame extends JFrame implements ActionListener {

	private SystemController controller;
	private MainHub previousFrame;
	private JTextField newSemesterField;
	private String schoolName;
	private Transcript transcript; // active transcript
	private JPanel semestersPanel, newSchoolPanel;

	// private Transcript transcript;

	public SemesterFrame(SystemController controller, MainHub previousFrame,
			String schoolName) {

		setSize(300, 300);
		this.controller = controller;
		this.previousFrame = previousFrame;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		for (Transcript transcript : controller.activeUser.getTranscripts()) {
			if (transcript.getSchoolName().equals(schoolName))
				this.transcript = transcript;
		}

		this.schoolName = schoolName;
		setTitle(schoolName);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		// Semesters panel.
		semestersPanel = new JPanel(new GridLayout(transcript.getSemesters()
				.size() + 4, 1));
		// Instructions label.
		JLabel semestersInstructionLbl = new JLabel("Choose a semester:");
		semestersPanel.add(semestersInstructionLbl);
		// Buttons to represent semesters.
		for (Semester semester : transcript.getSemesters()) {
			String semesterName = semester.getSemesterName();
			JButton semesterBtn = new JButton(semesterName); // Might need to
																// uniquely
																// name....
			semesterBtn.setActionCommand(semesterName);
			semesterBtn.addActionListener(this);
			semestersPanel.add(semesterBtn);
		}

		JButton addSemesterBtn = new JButton("Add new...");
		addSemesterBtn.setActionCommand("newSemesterPanel");
		addSemesterBtn.addActionListener(this);
		semestersPanel.add(addSemesterBtn);

		// Navigation label/separator.
		JLabel semesterNavLbl = new JLabel("Navigation");
		semestersPanel.add(semesterNavLbl);

		// Back button.
		JButton backBtn = new JButton("Back");
		backBtn.setActionCommand("back");
		backBtn.addActionListener(this);

		semestersPanel.add(backBtn);
		add(semestersPanel);
	}

	// private void newSchoolMenu() {
	// // Label and textfield to enter new school name.
	// JLabel addSchoolInstructionLbl = new JLabel("School name: ");
	// newSchoolNameField = new JTextField(10); // int = entry spaces.
	// // Done button.
	// JButton doneBtn = new JButton("Done");
	// doneBtn.setActionCommand("done");
	// doneBtn.addActionListener(this);
	// // Layout.
	// newSchoolPanel = new JPanel(new GridLayout(5, 1));
	// newSchoolPanel.add(addSchoolInstructionLbl);
	// newSchoolPanel.add(newSchoolNameField);
	// newSchoolPanel.add(doneBtn);
	// add(newSchoolPanel);
	// semestersPanel.setVisible(false);
	// }

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("back")) {
			setVisible(false);
			previousFrame.setVisible(true);
		}
	}
}