package cpaintus.controllers;

import java.util.Collections;
import java.util.List;

import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.control.ListView;

public class InvokerUpdateSingleton {
	
	private ListView<Shape> shapeList = new ListView<Shape>();
	private ShapesDictionnary shapesDict;
	
	private InvokerUpdateSingleton () {
		shapesDict = ShapesDictionnary.getInstance();
	}
	
	private static class InvokerUpdateSingletonHelper {
		private static final InvokerUpdateSingleton INSTANCE = new InvokerUpdateSingleton();
	}

	public static InvokerUpdateSingleton getInstance() {
		return InvokerUpdateSingletonHelper.INSTANCE;
	}
	
	public ListView<Shape> getShapeList() {
		return shapeList;
	}

	public void setShapeList(ListView<Shape> shapeList) {
		this.shapeList = shapeList;
	}

	public void updateList() {
		Shape shape =  shapeList.getSelectionModel().getSelectedItem();
		shapeList.getItems().clear();
		List<Shape> shallowCopy = shapesDict.getShapesList().subList(0, shapesDict.getShapesList().size());
		Collections.reverse(shallowCopy);
		shapeList.getItems().addAll(shallowCopy);
		shapeList.getSelectionModel().select(shape);
	}
	

}
