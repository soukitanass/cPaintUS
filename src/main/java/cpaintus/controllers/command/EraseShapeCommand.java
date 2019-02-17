package cpaintus.controllers.command;

import static org.junit.jupiter.api.Assertions.assertSame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import cpaintus.controllers.SnapshotSingleton;
import cpaintus.controllers.drawers.DrawerStrategyContext;
import cpaintus.models.BoundingBox;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.AnchorPane;

public class EraseShapeCommand extends Command {

	private SnapshotSingleton snapshotSingleton;
	private BoundingBox boundingBox;
	private AnchorPane pane; 
	private Canvas activeCanvas;
	private Shape shapeToDelete;
	private ShapesDictionnary shapesDictionnary;
	private DrawerStrategyContext drawerStrategyContext;
	
	public EraseShapeCommand() {
		snapshotSingleton = SnapshotSingleton.getInstance();
		boundingBox = BoundingBox.getInstance();
		pane = snapshotSingleton.getSnapshotPane();
		shapesDictionnary = ShapesDictionnary.getInstance();
		drawerStrategyContext = DrawerStrategyContext.getInstance();
	}
	@Override
	public void execute() {
		int hash = shapeToDelete.getCanvasHash();
		activeCanvas = (Canvas) pane.getChildren().stream().filter(child -> hash == child.hashCode()).findAny().orElse(null);
		GraphicsContext gc = activeCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, activeCanvas.getWidth(), activeCanvas.getHeight());
		shapesDictionnary.removeShape(shapeToDelete);
		boundingBox.setVisible(false);
	}

	@Override
	public void undo() {
		if(this.shapeToDelete != null && this.activeCanvas != null) {
			shapesDictionnary.addShape(shapeToDelete);
			drawerStrategyContext.draw(shapeToDelete, activeCanvas);
		}
	}
	
	public void setShapeToDelete(Shape shapeToDelete) {
		this.shapeToDelete = shapeToDelete;
		setCommandID("Erase : "+ shapeToDelete);
	}
	
	

}
