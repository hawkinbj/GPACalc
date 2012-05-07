package calculator;

public class DriverTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		final SystemController controller = new SystemController();
		// WelcomeFrame myWindow = new WelcomeFrame(controller);
		// myWindow.setVisible(true);

		// FOR TESTING TO SKIP LOGIN -
		controller.login("test", "test");
		controller.addPanel(new MainMenuPanel(controller), "mainMenu");
		controller.showPanel("mainMenu", controller.panels.get("welcome"));

	}
}
