package cpaintus.views;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import cpaintus.models.shapes.ShapesDictionnary;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class FlipShapeTest {

	@Start
	private void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		LoadStage.loadApplication(stage, loader);
	}

	@Test
	void flipHorizontallyShapeTest(FxRobot robot) {
		ShapesDictionnary shapesDict = ShapesDictionnary.getInstance();
		LoadStage.createShape(robot);
		robot.clickOn("#attributes");
		robot.clickOn("#flipHorizontalBtn");
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(shapesDict.getLastCreatedShape().isFlippedHorizontally());
		robot.clickOn("#flipVerticalBtn");
		WaitForAsyncUtils.waitForFxEvents();
		assertTrue(shapesDict.getLastCreatedShape().isFlippedVertically());

	}
	

	@AfterEach
	public void basicAfterEach() throws TimeoutException {
		FxToolkit.cleanupStages();
	}


}
