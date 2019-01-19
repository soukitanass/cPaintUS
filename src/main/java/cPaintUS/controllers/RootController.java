package cPaintUS.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RootController {

  @FXML private LeftPaneController _leftPaneController;
  @FXML private RightPaneController _rightPaneController;
  @FXML private CenterPaneController _centerPaneController;
  @FXML private BottomPaneController _bottomPaneController;
  @FXML private TopPaneController _topPaneController;
  
  
  public  RootController() {
	  
  }
  
  public LeftPaneController getLeftPaneController() {
	  return this._leftPaneController;
  }
  
  public RightPaneController getRightPaneController() {
	  return this._rightPaneController;
  }
  
  public CenterPaneController getCenterPaneController() {
	  return this._centerPaneController;
  }
  
  public BottomPaneController getBottomPaneController() {
	  return this._bottomPaneController;
  }
  
  public TopPaneController getTopPaneController() {
	  return this._topPaneController;
  }
}