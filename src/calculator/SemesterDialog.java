package calculator;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class SemesterDialog extends GUIPanel {

	private static final long serialVersionUID = 4982367508399428981L;
	private JRadioButton fallRadioBtn, springRadioBtn;
	private JSpinner spinner;
	private SpinnerNumberModel yearModel;
	private JPanel instructionPanel, mainPanel, navigationPanel;

	public SemesterDialog(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {

		// Instruction panel.
		instructionPanel = new JPanel();
		instructionPanel.setLayout(new BoxLayout(instructionPanel,
				BoxLayout.PAGE_AXIS));
		instructionPanel.add(new JLabel("Select a term:"));
		//
		fallRadioBtn = new JRadioButton("Fall", true);
		springRadioBtn = new JRadioButton("Spring", false);

		ButtonGroup radioBtns = new ButtonGroup();
		radioBtns.add(fallRadioBtn);
		radioBtns.add(springRadioBtn);

		mainPanel = new JPanel(new GridLayout(4, 1));
		mainPanel.add(fallRadioBtn);
		mainPanel.add(springRadioBtn);

		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		yearModel = new SpinnerNumberModel(currentYear, currentYear - 10,
				currentYear + 10, 1);
		spinner = new JSpinner(yearModel);
		spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
		mainPanel.add(spinner);

		// add cancel button and navigation panel
		navigationPanel = new JPanel(new GridLayout(3, 1));
		navigationPanel.add(createButton("done", "Done"));
		navigationPanel.add(createButton("cancel", "Cancel"));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(instructionPanel);
		add(mainPanel);
		add(navigationPanel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			controller.rootFrame.showPanel("SemesterPanel", this);
		} else if (action.equals("done")) {
			String semesterName = spinner.getValue().toString();

			if (fallRadioBtn.isSelected()) {
				semesterName += " Fall";
			}
			if (springRadioBtn.isSelected()) {
				semesterName += " Spring";
			}
			if (controller.activeUser
					.getTranscript(controller.activeSchool.getName())
					.getSemesters().containsKey(semesterName)) {

				JOptionPane
						.showMessageDialog(
								this,
								"That semester already exists or no term was selected.",
								"Error", JOptionPane.ERROR_MESSAGE);
				controller.rootFrame.showPanel("semesterPanel", this);
			} else {

				controller.activeUser.getTranscript(
						controller.activeSchool.getName()).addSemester(
						semesterName, new Semester(semesterName));
				controller.saveUserList();
				// Re-create previous panel with update info.
				controller.rootFrame.addPanel(new SemesterPanel(controller),
						this);
			}
		}
	}
}