package cpaintus.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class InvokerUpdateSingleton {
	
	private TreeView<Shape> tree = new TreeView<Shape>();
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
	
	public TreeView<Shape> getTree() {
		return tree;
	}

	public void setShapeTree(TreeView<Shape> newTree) {
		this.tree = newTree;
	}

	public void updateList() {
		if (tree.getRoot() == null) {
			tree.setRoot(new TreeItem<Shape>());
			tree.setShowRoot(false);
		}
		
		tree.getRoot().getChildren().clear();
		buildTree(tree.getRoot(), shapesDict.getShapesList());
		tree.getSelectionModel().selectLast();
	}
	
	private void buildTree(TreeItem<Shape> root, List<Shape> shapes) {
		List<Shape> shallowCopy = shapes.subList(0, shapes.size());
		Collections.sort(shallowCopy, new Comparator<Shape>() {
			@Override
			public int compare(Shape s1, Shape s2) {
				return s2.getZ() - s1.getZ();
			}
		});
		
		List<TreeItem<Shape>> children = root.getChildren();
		for (Shape shape : shapes) {
			TreeItem<Shape> item = new TreeItem<Shape>(shape);
			if (shape.getShapeType() == ShapeType.GROUP)
				buildTree(item, ((ShapesGroup) shape).getShapes());
			children.add(item);
		}
	}
	

}
