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

import cpaintus.models.shapes.Shape2D;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class EditShapeFillColorTest {

	@Start
	private void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		LoadStage.loadApplication(stage, loader);
	}

	@Test
	void editShapeFillColorTestFX(FxRobot robot) {

		ShapesDictionnary shapesDict = ShapesDictionnary.getInstance();
		LoadStage.createShape(robot);
		ColorPicker colorPicker = robot.lookup("#editFillColor").query();
		robot.clickOn("#attributes");
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		robot.clickOn("#editFillColor");
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		robot.moveBy(0, 100);
		robot.clickOn();
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		Color selectedColor = colorPicker.getValue();
		String fillColor = String.format("#%02X%02X%02X", (int) (selectedColor.getRed() * 255),
				(int) (selectedColor.getGreen() * 255), (int) (selectedColor.getBlue() * 255));
		assertEquals(fillColor, ((Shape2D) shapesDict.getLastCreatedShape()).getFillColor());

	}

	@AfterEach
	public void basicAfterEach() throws TimeoutException {
		FxToolkit.cleanupStages();
	}
}
