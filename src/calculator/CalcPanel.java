package calculator;

import java.awt.event.ActionEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalcPanel extends GUIPanel {

	private static final long serialVersionUID = -8336708968135465850L;
	private JPanel statsPanel, navigationPanel;

	public CalcPanel(SystemController controller) {
		super(controller);
		addComponentsToPane();
	}

	private void addComponentsToPane() {

		double totalPointsEarned = controller.activeCourse
				.getTotalPointsEarned();
		double totalPointsPossible = controller.activeCourse
				.getTotalPointsPossible();
		double WeightedTotalPointsEarned = controller.activeCourse
				.getWeightedTotalPointsEarned();
		double average = (WeightedTotalPointsEarned / totalPointsPossible);

		// Stats panel.
		statsPanel = new JPanel();
		statsPanel.setLayout(new BoxLayout(statsPanel, BoxLayout.PAGE_AXIS));
		statsPanel.add(new JLabel("Total points earned: "
				+ Double.toString(totalPointsEarned)));
		statsPanel.add(new JLabel("Total points possible: "
				+ Double.toString(totalPointsPossible)));
		statsPanel.add(new JLabel("Current average: "
				+ Double.toString(average)));
		statsPanel.add(new JLabel("Credit hours: "
				+ Integer.toString(controller.activeCourse.getCreditHours())));
		statsPanel.add(new JLabel("Final grade: "
				+ controller.activeCourse.getFinalGrade()));

		// Navigation panel.
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new BoxLayout(navigationPanel,
				BoxLayout.PAGE_AXIS));
		navigationPanel.add(createButton("back", "Back"));

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(statsPanel);
		add(navigationPanel);
	}

	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if (action.equals("back")) {
			controller.rootFrame.showPanel("CourseInfoPanel", this);
		}
	}
}
