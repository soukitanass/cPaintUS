package cPaintUS.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class RootController {

  @FXML private LeftPaneController leftPaneController;
  @FXML private RightPaneController rightPaneController;
  @FXML private CenterPaneController centerPaneController;
  @FXML private BottomPaneController bottomPaneController;
  @FXML private TopPaneController topPaneController;
  
  
  public  RootController() {
	  
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
}