package cpaintus.controllers.command;

import java.util.List;

import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class EditZCommand implements ICommand {
	
	private int newZ;
	private int oldZ; 
	private Node node;
	private ShapesDictionnary shapesDictionnary;
	private AnchorPane pane;
	
	public void setNewZ(int newZ) {
		this.newZ = newZ;
	}

	public void setOldZ(int oldZ) {
		this.oldZ = oldZ;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public void setPane(AnchorPane pane) {
		this.pane = pane;
	}

	public EditZCommand () {
		shapesDictionnary = ShapesDictionnary.getInstance();
	}

	public void execute() {
		System.out.println("Hauteur actuelle : " + pane.getChildren().indexOf(node));
		changeOrder(newZ,oldZ);
		System.out.println("Hauteur modifiée : " + pane.getChildren().indexOf(node));
	}

	public void undo() {
		System.out.println("Hauteur actuelle : " + pane.getChildren().indexOf(node));
		changeOrder(oldZ,newZ);
		System.out.println("Hauteur modifiée : " + pane.getChildren().indexOf(node));

	}
	
	private void changeOrder (int changingZ, int changedZ) {
		
		List<Node> nodes = pane.getChildren();		
		nodes.remove(changedZ);
		nodes.add(changingZ, node);
		
		int start;
		int end;
		
		if (changedZ < changingZ) {
			start = changedZ;
			end = changingZ;
		} else {
			start = changingZ + 1;
			end = changedZ + 1;
		}
		
		for (int i = start; i < end; i++) {
			int hash = nodes.get(i).hashCode();
			Shape shape = shapesDictionnary.getShapesList().stream()
				.filter(s -> hash == s.getCanvasHash())
				.findAny()
				.orElse(null);
			if (shape != null) shape.setZ(i);
		}

	}

}
