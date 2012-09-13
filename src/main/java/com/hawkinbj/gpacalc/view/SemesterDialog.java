package com.hawkinbj.gpacalc.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.Calendar;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;
import com.hawkinbj.gpacalc.model.Semester;

public class SemesterDialog extends GUIPanel {
	private static final long serialVersionUID = 4982367508399428981L;
	private JRadioButton fallRadioBtn;
	private JRadioButton springRadioBtn;
	private JRadioButton summerRadioBtn;
	private JSpinner spinner;
	private SpinnerNumberModel yearModel;
	private JPanel mainPanel;
	private JPanel navigationPanel;

	public SemesterDialog(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {
		this.fallRadioBtn = new JRadioButton("Fall", true);
		this.springRadioBtn = new JRadioButton("Spring", false);
		this.summerRadioBtn = new JRadioButton("Summer", false);

		ButtonGroup radioBtns = new ButtonGroup();
		radioBtns.add(this.fallRadioBtn);
		radioBtns.add(this.springRadioBtn);
		radioBtns.add(this.summerRadioBtn);

		this.mainPanel = new JPanel(new GridLayout(4, 1));
		createTitledBorder(this.mainPanel, "Select Term");

		int currentYear = Calendar.getInstance().get(1);
		this.yearModel = new SpinnerNumberModel(currentYear, currentYear - 10,
				currentYear + 10, 1);
		this.spinner = new JSpinner(this.yearModel);
		this.spinner.setEditor(new JSpinner.NumberEditor(this.spinner, "#"));
		this.mainPanel.add(this.spinner);
		this.mainPanel.add(this.fallRadioBtn);
		this.mainPanel.add(this.springRadioBtn);
		this.mainPanel.add(this.summerRadioBtn);

		this.navigationPanel = new JPanel(new GridLayout(2, 1));
		createTitledBorder(this.navigationPanel, "Navigation");
		this.navigationPanel.add(createButton("done", "Done"));
		this.navigationPanel.add(createButton("cancel", "Cancel"));

		setLayout(new BoxLayout(this, 3));
		add(this.mainPanel);
		add(this.navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("cancel")) {
			this.controller.getRootFrame().showPanel("SemesterPanel", this);
		}
		if (action.equals("done")) {
			Semester newSemester = new Semester(null, this.spinner.getValue()
					.toString());
			if (this.fallRadioBtn.isSelected())
				newSemester.setTerm("Fall");
			else if (this.springRadioBtn.isSelected())
				newSemester.setTerm("Spring");
			else if (this.summerRadioBtn.isSelected()) {
				newSemester.setTerm("Summer");
			}
			if (this.controller.getActiveTranscript().getSemesters()
					.containsKey(newSemester.toString())) {
				JOptionPane
						.showMessageDialog(
								this,
								"That semester already exists or no term was selected.",
								"Error", 0);
			} else {
				this.controller.getActiveTranscript().addSemester(
						newSemester.toString(), newSemester);
				this.controller.saveUserList();

				this.controller.getRootFrame().addPanel(
						new SemesterPanel(this.controller), this);
			}
		}
	}
}