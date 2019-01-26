package cPaintUS.controllers;

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
