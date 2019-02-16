package cpaintus.controllers.popup;

import cpaintus.models.BoundingBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.stage.Stage;

public class GridController {

	private Stage newDialog;private boolean isYesClicked = false;
	@FXML
	private CheckBox activateGridControl;
	@FXML
	private Spinner<Integer> gridStepControl;
	@FXML
	private Button yesBtn;
	@FXML
	private Button noBtn;
	
	private BoundingBox boundingBox;
	
	public void setNewDialog(Stage stage) {
		this.newDialog = stage;
	}
	
	@FXML
	public void handleYesClick() {
		this.boundingBox.setGridMod(this.activateGridControl.isSelected());
		this.boundingBox.setGridStep(this.gridStepControl.getValue());
		newDialog.close();
	}
	
	@FXML
	public void handleNoClick() {
		newDialog.close();
	}
	
	public GridController() {
		this.boundingBox = BoundingBox.getInstance();
	}
	
	@FXML
	public void initialize() {
		activateGridControl.selectedProperty().addListener(new ChangeListener<Boolean>() {
		    @Override
		    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		        gridStepControl.setDisable(!newValue);
		    }
		});
		activateGridControl.setSelected(this.boundingBox.getGridMod());
		
		if(boundingBox.getGridStep() < 1)
			boundingBox.setGridStep(1);
		if(boundingBox.getGridStep() > 250)
			boundingBox.setGridStep(250);
		SpinnerValueFactory<Integer> valueFactory = 
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 250, (int)boundingBox.getGridStep());
		gridStepControl.setValueFactory(valueFactory);
	}

}
