package calculator;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final SystemController controller = new SystemController();
		controller.addPanel(new WelcomePanel(controller), "welcome");

	}
}
