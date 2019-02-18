package cpaintus.views;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxRobot;
import org.testfx.api.FxRobotContext;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import cpaintus.controllers.SaveCloseSingleton;
import cpaintus.controllers.command.Invoker;
import cpaintus.models.observable.IObserver;
import cpaintus.models.observable.ObservableList;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeFactory;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;
import cpaintus.models.shapes.Size;
import cpaintus.models.shapes.Stroke;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

@ExtendWith(ApplicationExtension.class)
class ShapeTestFX {
	
    @Start
    private void start(Stage stage) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("root.fxml"));
		Parent root;
		try {
			root = loader.load();
			stage.setTitle("cPaintUS");
			stage.setScene(new Scene(root, 800, 600));
			stage.setMinWidth(800);
			stage.setMinHeight(600);
			stage.show();
			stage.setOnCloseRequest((WindowEvent event) -> SaveCloseSingleton.getInstance().triggerClose());
			KeyCombination cntrlZ = new KeyCodeCombination(KeyCode.Z, KeyCodeCombination.CONTROL_DOWN);
			KeyCombination cntrlY = new KeyCodeCombination(KeyCode.Y, KeyCodeCombination.CONTROL_DOWN);
			KeyCombination cntrlS = new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN);
			stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>(){
				@Override
				public void handle(KeyEvent event) {
					if(cntrlZ.match(event)){
						Invoker.getInstance().undo();
					}
					else if (cntrlY.match(event)) {
						Invoker.getInstance().redo();
					}
					else if (cntrlS.match(event)) {
						SaveCloseSingleton.getInstance().handleSave();
					}
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	@Test
	void createShapeTestFX(FxRobot robot) {
		AnchorPane centerPane = robot.lookup("#centerPane").queryAs(AnchorPane.class);
		AnchorPane leftPane = robot.lookup("#leftPane").queryAs(AnchorPane.class);

		Assertions.assertTrue(true);
	}

}
