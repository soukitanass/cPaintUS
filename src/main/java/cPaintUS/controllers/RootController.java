package cPaintUS.controllers;

import java.io.IOException;

import cPaintUS.controllers.popup.NewController;
import cPaintUS.models.observable.IObserver;
import cPaintUS.models.observable.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RootController {

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

}
