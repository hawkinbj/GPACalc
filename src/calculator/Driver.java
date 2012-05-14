package calculator;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final SystemController controller = new SystemController();
		controller.rootFrame.addPanel(new WelcomePanel(controller),
				new GUIPanel(controller));

	}
}
