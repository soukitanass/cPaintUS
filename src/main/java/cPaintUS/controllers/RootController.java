package cPaintUS.controllers;

import cPaintUS.models.PaintModel;
import javafx.fxml.FXML;

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

	private PaintModel paintModel;

	public RootController() {
		paintModel = PaintModel.getInstance();
	}

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
	
	@FXML
	public void initialize () {
		this.topPaneController.setCenterPaneController(this.centerPaneController);
	}
}
