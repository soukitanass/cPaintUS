package cpaintus.views;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import cpaintus.models.DrawSettings;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class EraseAllTest {
	@Start
	private void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		LoadStage.loadApplication(stage, loader);
	}

	@Test
	void eraseAllTestFX(FxRobot robot) {

		ShapesDictionnary shapesDict = ShapesDictionnary.getInstance();
		// firstShape
		createShape(robot);
		// secondShape
		createShape(robot);
		robot.clickOn("#eraseAllBtn");
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		assertTrue(shapesDict.getFullShapesList().isEmpty());

		try {
			FxToolkit.cleanupStages();
		} catch (TimeoutException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void createShape(FxRobot robot) {
		ShapesDictionnary shapesDict = ShapesDictionnary.getInstance();
		DrawSettings drawSettings = DrawSettings.getInstance();
		drawSettings.setShape(ShapeType.RECTANGLE);

		AnchorPane centerPane = robot.lookup("#centerPane").queryAs(AnchorPane.class);
		robot.drag(centerPane.getScene(), MouseButton.PRIMARY).dropBy(100, 100);

		Assertions.assertTrue(!shapesDict.getFullShapesList().isEmpty());
	}

}
