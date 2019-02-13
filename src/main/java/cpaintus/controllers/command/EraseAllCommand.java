package cpaintus.controllers.command;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cpaintus.models.BoundingBox;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class EraseAllCommand implements ICommand {

	private AnchorPane pane;
	private BoundingBox boundingBox;
	private LinkedHashMap<String, Shape> shapesDict = new LinkedHashMap<String, Shape>();
	private List<Node> conservedList = new ArrayList<Node>();
	private ShapesDictionnary shapesDictionnary;

	public EraseAllCommand() {
		boundingBox = BoundingBox.getInstance();
		shapesDictionnary = ShapesDictionnary.getInstance();
	}

	public void setPane(AnchorPane pane) {
		this.pane = pane;
	}

	public void setShapesDict(LinkedHashMap<String, Shape> shapesDict) {
		this.shapesDict = (LinkedHashMap<String, Shape>) shapesDict.clone();
	}
	
	public void execute() {
		setShapesDict(shapesDictionnary.getShapesDict());
		for (int i = 0; i < pane.getChildren().size(); i++) {
			conservedList.add(pane.getChildren().get(i));
		}
		pane.getChildren().clear();
		boundingBox.setVisible(false);
		shapesDictionnary.clearShapes();
	}

	public void undo() {
		for (int i = 0; i<conservedList.size(); i++) {
			pane.getChildren().add(conservedList.get(i));
		}
		conservedList.clear();
		boundingBox.setVisible(true);
		shapesDictionnary.setShapesDict(shapesDict);
	}

}
