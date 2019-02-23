package cpaintus.views;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import cpaintus.models.DrawSettings;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

@ExtendWith(ApplicationExtension.class)
class DrawShapeTest {

	@Start
	private void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		LoadStage.loadApplication(stage, loader);
	}

	@Test
	void createShapeTestFX(FxRobot robot) {
		ShapesDictionnary shapesDict = ShapesDictionnary.getInstance();
		createShape(robot, ShapeType.ELLIPSE);
		assertFalse(shapesDict.getFullShapesList().isEmpty());

		createShape(robot, ShapeType.RECTANGLE);
		assertEquals(2, shapesDict.getFullShapesList().size());

		createShape(robot, ShapeType.POKEBALL);
		assertEquals(3, shapesDict.getFullShapesList().size());

		createShape(robot, ShapeType.HEART);
		assertEquals(4, shapesDict.getFullShapesList().size());

		createShape(robot, ShapeType.LINE);
		assertEquals(5, shapesDict.getFullShapesList().size());

		try {
			FxToolkit.cleanupStages();
		} catch (TimeoutException e) {
			e.printStackTrace();
		}
	}

	private void createShape(FxRobot robot, ShapeType shapeType) {
		DrawSettings drawSettings = DrawSettings.getInstance();
		drawSettings.setShape(shapeType);

		AnchorPane centerPane = robot.lookup("#centerPane").queryAs(AnchorPane.class);
		robot.drag(centerPane.getScene(), MouseButton.PRIMARY).dropBy(100, 100);

	}

}
