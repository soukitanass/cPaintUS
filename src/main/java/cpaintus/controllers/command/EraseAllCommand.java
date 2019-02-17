package cpaintus.controllers.command;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import cpaintus.models.BoundingBox;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class EraseAllCommand extends Command {

	private Node boundingBoxNode;
	private Node baseCanvasNode;
	private AnchorPane pane;
	private BoundingBox boundingBox;
	private LinkedHashMap<String, Shape> shapesDict = new LinkedHashMap<String, Shape>();
	private List<Node> conservedList = new ArrayList<Node>();
	private ShapesDictionnary shapesDictionnary;

	public EraseAllCommand() {
		setCommandID("EraseAll");
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
		boundingBoxNode = pane.getChildren().get(pane.getChildren().size()-1);
		baseCanvasNode = pane.getChildren().get(0);
		for (int i = 1; i < pane.getChildren().size()-1; i++) {
			conservedList.add(pane.getChildren().get(i));
		}
		pane.getChildren().clear();
		pane.getChildren().add(baseCanvasNode);
		pane.getChildren().add(boundingBoxNode);
		boundingBox.setVisible(false);
		shapesDictionnary.clearShapes();
	}

	public void undo() {
		pane.getChildren().remove(boundingBoxNode);
		for (int i = 0; i<conservedList.size(); i++) {
			pane.getChildren().add(conservedList.get(i));
		}
		pane.getChildren().add(boundingBoxNode);
		conservedList.clear();
		boundingBox.setVisible(true);
		shapesDictionnary.setShapesDict(shapesDict);
	}

}
