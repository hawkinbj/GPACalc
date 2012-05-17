package calculator;

import javax.swing.UIManager;

public class DriverTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final SystemController controller = new SystemController();

		// FOR TESTING TO SKIP LOGIN -
		controller.login("test", "test");
		controller.rootFrame.addPanel(new MainMenuPanel(controller),
				new WelcomePanel(controller));

	}
}
