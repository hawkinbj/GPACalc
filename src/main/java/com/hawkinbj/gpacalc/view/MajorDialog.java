package com.hawkinbj.gpacalc.view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hawkinbj.gpacalc.controller.SystemController;
import com.hawkinbj.gpacalc.model.GUIPanel;

public class MajorDialog extends GUIPanel implements ActionListener {

	private static final long serialVersionUID = 5163970549266948075L;
	private JComboBox majorComboBox;

	private JPanel majorPanel;
	private JPanel navigationPanel;

	public MajorDialog(SystemController controller) {
		super(controller);

		majorComboBox = new JComboBox(controller.getMajors().keySet().toArray());

		addComponentsToPane();
	}

	private void addComponentsToPane() {

		majorPanel = new JPanel(new GridLayout(1, 2));
		majorPanel.add(new JLabel("Select major: "));

		String activeUserMajor = controller.getActiveUser().getMajor();

		if (activeUserMajor != null) {
			majorComboBox.setSelectedItem(activeUserMajor);
		} else {
			majorComboBox.setSelectedIndex(-1);
		}

		majorPanel.add(this.majorComboBox);

		navigationPanel = new JPanel(new GridLayout(2, 1));
		navigationPanel.add(createButton("done", "Done"));
		navigationPanel.add(createButton("cancel", "Cancel"));

		this.createTitledBorder(this.navigationPanel, "Navigation");

		setLayout(new BoxLayout(this, 3));
		add(majorPanel);
		add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();

		if (action.equals("cancel")) {
			controller.getRootFrame().addPanel(new PlanPanel(controller), this);
		} else if (action.equals("done")) {
			controller.getActiveUser().setMajor(
					(String) majorComboBox.getSelectedItem());

			controller.saveUserList();

			controller.getRootFrame().addPanel(new PlanPanel(controller), this);
		}
	}
}
