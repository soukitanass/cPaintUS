package cPaintUS.controllers;

import cPaintUS.models.PaintModel;
import javafx.fxml.FXML;

public class RootController {

  @FXML private LeftPaneController _leftPaneController;
  @FXML private RightPaneController _rightPaneController;
  @FXML private CenterPaneController _centerPaneController;
  @FXML private BottomPaneController _bottomPaneController;
  @FXML private TopPaneController _topPaneController;
  
  private PaintModel paintModel;
  
  
  public  RootController() {
	  paintModel = PaintModel.getInstance();
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