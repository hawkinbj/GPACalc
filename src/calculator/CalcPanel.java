package calculator;

public class CalcPanel extends GUIPanel {

	private static final long serialVersionUID = -8336708968135465850L;
	private Course course;

	public CalcPanel(SystemController controller, Course course) {
		super(controller);
		this.course = course;
		addComponentsToPane();
	}

	private void addComponentsToPane() {

	}

}
