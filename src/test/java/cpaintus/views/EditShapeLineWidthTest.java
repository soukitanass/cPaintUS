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
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class EditShapeLineWidthTest {

	@Start
	private void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		LoadStage.loadApplication(stage, loader);
	}

	@Test
	void editShapeLineWidthTestFX(FxRobot robot) {

		ShapesDictionnary shapesDict = ShapesDictionnary.getInstance();
		LoadStage.createShape(robot);
		ComboBox<String> editLineWidth = robot.lookup("#editLineWidth").query();

		robot.clickOn("#attributes");
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		robot.clickOn("#editLineWidth").type(KeyCode.DOWN).type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		String widthStr = editLineWidth.getValue().replaceAll("[^0-9]", "");
		int newWidth = Integer.parseInt(widthStr);
		assertEquals(newWidth, shapesDict.getShapesList().get(0).getLineWidth());

	}

	@AfterEach
	public void basicAfterEach() throws TimeoutException {
		FxToolkit.cleanupStages();
	}
}
