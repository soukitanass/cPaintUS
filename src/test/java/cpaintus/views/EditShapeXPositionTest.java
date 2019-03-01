package cpaintus.views;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class EditShapeXPositionTest {

	@Start
	private void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		LoadStage.loadApplication(stage, loader);
	}

	@Test
	void editShapeXPositionTestFX(FxRobot robot) {

		ShapesDictionnary shapesDict = ShapesDictionnary.getInstance();
		LoadStage.createShape(robot);
		TextField editX = robot.lookup("#editX").query();
		robot.clickOn("#attributes");
		editX.clear();
		robot.clickOn("#editX");
		robot.write("140");
		robot.type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		assertEquals(140, shapesDict.getLastCreatedShape().getX());
	}

	@AfterEach
	public void basicAfterEach() throws TimeoutException {
		FxToolkit.cleanupStages();
	}
}
