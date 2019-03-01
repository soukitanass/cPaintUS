package cpaintus.views;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import cpaintus.controllers.SaveCloseSingleton;
import cpaintus.controllers.command.Invoker;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@ExtendWith(ApplicationExtension.class)
class EditShapeYPositionTest {

	@Start
	private void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		LoadStage.loadApplication(stage, loader);
	}

	@Test
	void editShapeYPositionTestFX(FxRobot robot) {

		ShapesDictionnary shapesDict = ShapesDictionnary.getInstance();
		LoadStage.createShape(robot);
		TextField editY = robot.lookup("#editY").query();
		editY.clear();
		robot.clickOn("#attributes");
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		robot.clickOn("#editY");
		robot.write("140");
		robot.type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		
		assertEquals(140, shapesDict.getLastCreatedShape().getY());
	}

	@AfterEach
	public void basicAfterEach() throws TimeoutException {
		FxToolkit.cleanupStages();
	}
}
