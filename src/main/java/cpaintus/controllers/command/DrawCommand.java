package cpaintus.controllers.command;

import java.util.List;

import cpaintus.controllers.drawers.DrawerStrategyContext;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BlendMode;
import javafx.scene.layout.AnchorPane;

public class DrawCommand implements ICommand{
	
	private AnchorPane pane;
	private Canvas activeCanvas;
	private Shape shape;
	private DrawerStrategyContext drawerStrategyContext;
	private ShapesDictionnary shapesDict;
	private boolean premierefois;

	public DrawCommand () {
		drawerStrategyContext = DrawerStrategyContext.getInstance();
		shapesDict = ShapesDictionnary.getInstance();
	}
	
	public boolean isPremierefois() {
		return premierefois;
	}

	public void setPremierefois(boolean premierefois) {
		this.premierefois = premierefois;
	}
	
	public Canvas getActiveCanvas() {
		return activeCanvas;
	}

	public void setActiveCanvas(Canvas activeCanvas) {
		this.activeCanvas = activeCanvas;
	}


	public Shape getShape() {
		return shape;
	}


	public void setShape(Shape shape) {
		this.shape = shape;
	}


	public AnchorPane getPane() {
		return pane;
	}

	public void setPane(AnchorPane pane) {
		this.pane = pane;
	}

	public DrawerStrategyContext getDrawerStrategyContext() {
		return drawerStrategyContext;
	}

	public ShapesDictionnary getShapesDict() {
		return shapesDict;
	}

	public void execute () {
		if(this.shape != null) {
			if(pane.getChildren().size() > 2 && premierefois) {
				Canvas asupprCanvas = (Canvas) pane.getChildren().get(pane.getChildren().size() - 2);
				GraphicsContext gc = asupprCanvas.getGraphicsContext2D();
				asupprCanvas.setLayoutX(shape.getX()-(shape.getLineWidth()/2));
				asupprCanvas.setLayoutY(shape.getY()-(shape.getLineWidth()/2));
				asupprCanvas.setWidth(shape.getWidth()+shape.getLineWidth());
				asupprCanvas.setHeight(shape.getHeight()+shape.getLineWidth());
				gc.clearRect(0, 0, asupprCanvas.getWidth(), asupprCanvas.getHeight());
				List<Node> canvasList = pane.getChildren();
				canvasList.remove(asupprCanvas);
			}
			Canvas newCanvas = new Canvas();
			newCanvas.setMouseTransparent(true);
			newCanvas.setBlendMode(BlendMode.SRC_OVER);
			pane.getChildren().add(pane.getChildren().size() - 1, newCanvas);
			activeCanvas = newCanvas;
			shapesDict.addShape(shape);
			drawerStrategyContext.draw(shape, activeCanvas);
			System.out.println(pane.getChildren().size());
			System.out.println(activeCanvas.toString());
		}	
	}
	
	public void undo() {
		System.out.println("Active Canvas Undo : " + activeCanvas.toString());
		GraphicsContext gc = activeCanvas.getGraphicsContext2D();
		activeCanvas.setLayoutX(shape.getX()-(shape.getLineWidth()/2));
		activeCanvas.setLayoutY(shape.getY()-(shape.getLineWidth()/2));
		activeCanvas.setWidth(shape.getWidth()+shape.getLineWidth());
		activeCanvas.setHeight(shape.getHeight()+shape.getLineWidth());
		gc.clearRect(0, 0, activeCanvas.getWidth(), activeCanvas.getHeight());
		List<Node> canvasList = pane.getChildren();
		canvasList.remove(activeCanvas);
		shapesDict.removeShape(shape);
		premierefois = false;
	}
}
