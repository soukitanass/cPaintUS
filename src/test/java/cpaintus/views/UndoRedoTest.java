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
class UndoRedoTest {

	@Start
	private void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		LoadStage.loadApplication(stage, loader);
	}

	@Test
	void undoRedoTest(FxRobot robot) {
		ShapesDictionnary shapesDict = ShapesDictionnary.getInstance();
		LoadStage.createShape(robot);

		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		double height = shapesDict.getLastCreatedShape().getHeight();
		TextField editX = robot.lookup("#editX").query();
		editX.clear();
		robot.clickOn("#attributes");
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		robot.clickOn("#editX");
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		robot.write("140");
		robot.type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		
		robot.clickOn("#editMenu");
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		robot.clickOn("#undo");
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		assertEquals(height, shapesDict.getLastCreatedShape().getHeight());
		
		robot.clickOn("#editMenu");
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		robot.clickOn("#redo");
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		assertEquals(140, shapesDict.getLastCreatedShape().getHeight());
	}

	@AfterEach
	public void basicAfterEach() throws TimeoutException {
		FxToolkit.cleanupStages();
	}
}
