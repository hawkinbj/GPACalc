package calculator;

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
		System.out.println(this.getClass());

		instructionPanel = new JPanel();
		instructionPanel.setLayout(new BoxLayout(instructionPanel,
				BoxLayout.PAGE_AXIS));
		instructionPanel.add(new JLabel("Select a term:"));

		fallRadioBtn = new JRadioButton("Fall", true);
		fallRadioBtn.setActionCommand("radio");
		fallRadioBtn.addActionListener(this);
		springRadioBtn = new JRadioButton("Spring", false);
		springRadioBtn.setActionCommand("radio");
		springRadioBtn.addActionListener(this);

		ButtonGroup radioBtns = new ButtonGroup();
		radioBtns.add(fallRadioBtn);
		radioBtns.add(springRadioBtn);

		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		mainPanel.add(fallRadioBtn);
		mainPanel.add(springRadioBtn);

		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		yearModel = new SpinnerNumberModel(currentYear, currentYear - 10,
				currentYear + 10, 1);
		spinner = new JSpinner(yearModel);
		spinner.setEditor(new JSpinner.NumberEditor(spinner, "#"));
		mainPanel.add(spinner);

		// add cancel button and navigation panel
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel,
				BoxLayout.PAGE_AXIS));
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
		if (action.equals("done")) {
			String semesterName = spinner.getValue().toString();

			if (fallRadioBtn.isSelected()) {
				semesterName += " Fall";
			}
			if (springRadioBtn.isSelected()) {
				semesterName += " Spring";
			}
			SemesterPanel previousFrame = (SemesterPanel) controller.panels
					.get("semesterPanel");
			if (previousFrame.transcript.getSemesters().containsKey(
					semesterName)) {
				JOptionPane
						.showMessageDialog(
								this,
								"That semester already exists or no term was selected.",
								"Error", JOptionPane.ERROR_MESSAGE);
				return;
			} else {
				previousFrame.semestersPanel.add(previousFrame
						.createButton(semesterName));
				previousFrame.transcript.addSemester(semesterName,
						new Semester(semesterName));
				controller.saveUserList();
			}
		}
		// Always exit to previous panel no matter what.
		controller.showPanel("semesterPanel", this);
	}
}