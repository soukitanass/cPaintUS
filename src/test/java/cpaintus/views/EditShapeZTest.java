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
import javafx.scene.control.Spinner;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class EditShapeZTest {

	@Start
	private void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		LoadStage.loadApplication(stage, loader);
	}

	@Test
	void editShapeZTest(FxRobot robot) {

		ShapesDictionnary shapesDict = ShapesDictionnary.getInstance();
		// firstShape
		LoadStage.createShape(robot);
		// secondShape
		LoadStage.createShape(robot);
		
		int z = shapesDict.getLastCreatedShape().getZ();
		
		assertEquals(z, shapesDict.getLastCreatedShape().getZ());
		robot.clickOn("#attributes");
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		Spinner<Integer> editZ = robot.lookup("#editZ").query();
		editZ.getValueFactory().decrement(1);
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		assertEquals(z, shapesDict.getLastCreatedShape().getZ());
	}

	@AfterEach
	public void basicAfterEach() throws TimeoutException {
		FxToolkit.cleanupStages();
	}
}