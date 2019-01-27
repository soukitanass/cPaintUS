package cPaintUS.controllers;

import java.io.IOException;

import cPaintUS.controllers.popup.CloseController;
import cPaintUS.controllers.popup.NewController;
import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RootController implements IObserver {

	@FXML
	private LeftPaneController leftPaneController;
	@FXML
	private RightPaneController rightPaneController;
	@FXML
	private CenterPaneController centerPaneController;
	@FXML
	private BottomPaneController bottomPaneController;
	@FXML
	private TopPaneController topPaneController;
	

	public LeftPaneController getLeftPaneController() {
		return leftPaneController;
	}

	public RightPaneController getRightPaneController() {
		return rightPaneController;
	}

	public CenterPaneController getCenterPaneController() {
		return centerPaneController;
	}

	public BottomPaneController getBottomPaneController() {
		return bottomPaneController;
	}

	public TopPaneController getTopPaneController() {
		return topPaneController;
	}

	@Override
	public void update(ObservableList obs) {
		switch (obs) {
		case CLOSE_APPLICATION:
			triggerClose();
			break;
		default:
			break;
		}
	}

	public void triggerClose() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/cPaintUS/views/popup/Close.fxml"));
		Parent parent;
		try {
			parent = fxmlLoader.load();
			Scene scene = new Scene(parent, 220, 100);
			Stage stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setTitle("Close");
			stage.setScene(scene);

			CloseController controller = fxmlLoader.getController();
			controller.setNewDialog(stage);
			stage.showAndWait();
			if (controller.isYesClicked()) {
				this.topPaneController.handleSave();
			}
			System.exit(0);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
