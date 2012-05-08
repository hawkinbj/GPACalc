package calculator;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CalcPanel extends GUIPanel {

	private static final long serialVersionUID = -8336708968135465850L;
	private Course course;
	private JPanel statsPanel;

	public CalcPanel(SystemController controller, Course course) {
		super(controller);
		this.course = course;
		addComponentsToPane();
		System.out.println(this.getClass());
	}

	private void addComponentsToPane() {

		double totalPointsEarned = course.getTotalPointsEarned();
		double totalPointsPossible = course.getTotalPointsPossible();
		double WeightedTotalPointsEarned = course
				.getWeightedTotalPointsEarned();
		double average = (WeightedTotalPointsEarned / totalPointsPossible);
		statsPanel = new JPanel(new GridLayout(3, 2));
		statsPanel.add(new JLabel("Total points earned: "));
		statsPanel.add(new JLabel(Double.toString(totalPointsEarned)));
		statsPanel.add(new JLabel("Total points possible: "));
		statsPanel.add(new JLabel(Double.toString(totalPointsPossible)));
		statsPanel.add(new JLabel("Current average: "));
		statsPanel.add(new JLabel(Double.toString(average)));
		add(statsPanel);

	}
}
