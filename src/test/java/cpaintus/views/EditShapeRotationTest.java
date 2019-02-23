package cpaintus.views;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import cpaintus.controllers.SaveCloseSingleton;
import cpaintus.controllers.command.Invoker;
import cpaintus.models.DrawSettings;
import cpaintus.models.shapes.ShapeType;
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
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@ExtendWith(ApplicationExtension.class)
class EditShapeRotationTest {


	@Start
	private void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		Parent root;
		try {
			root = loader.load();
			stage.setTitle("cPaintUS");
			stage.setScene(new Scene(root, 1200, 1200));
			stage.setMinWidth(800);
			stage.setMinHeight(600);
			stage.show();
			stage.setOnCloseRequest((WindowEvent event) -> SaveCloseSingleton.getInstance().triggerClose());
			KeyCombination cntrlZ = new KeyCodeCombination(KeyCode.Z, KeyCodeCombination.CONTROL_DOWN);
			KeyCombination cntrlY = new KeyCodeCombination(KeyCode.Y, KeyCodeCombination.CONTROL_DOWN);
			KeyCombination cntrlS = new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN);
			stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (cntrlZ.match(event)) {
						Invoker.getInstance().undo();
					} else if (cntrlY.match(event)) {
						Invoker.getInstance().redo();
					} else if (cntrlS.match(event)) {
						SaveCloseSingleton.getInstance().handleSave();
					}
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterEach
	public void tearDown() throws TimeoutException {
		FxToolkit.cleanupStages();
		FxToolkit.hideStage();
	}

	@Test
	void editShapeRotationTestFX(FxRobot robot) {

		ShapesDictionnary shapesDict = ShapesDictionnary.getInstance();
		createShape(robot);
		TextField editY = robot.lookup("#rotate").query();
		robot.clickOn("#attributes");
		editY.clear();
		robot.clickOn("#rotate");
		robot.write("140");
		robot.type(KeyCode.ENTER);
		WaitForAsyncUtils.waitForFxEvents();
		robot.sleep(1000);
		assertEquals(140, shapesDict.getShapesList().get(0).getRotation());

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
