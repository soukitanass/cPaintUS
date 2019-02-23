package cpaintus.controllers;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import cpaintus.models.composite.ShapesGroup;
import cpaintus.models.shapes.Shape;
import cpaintus.models.shapes.ShapeType;
import cpaintus.models.shapes.ShapesDictionnary;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class InvokerUpdateSingleton {
	
	private TreeView<Shape> tree;
	private ShapesDictionnary shapesDictionnary;

	
	public TreeView<Shape> getTree() {
		return tree;
	}

	public void setTree(TreeView<Shape> tree) {
		this.tree = tree;
	}
	
	private InvokerUpdateSingleton () {
		shapesDictionnary = ShapesDictionnary.getInstance();
	}

	private static class InvokerUpdateSingletonHelper {
		private static final InvokerUpdateSingleton INSTANCE = new InvokerUpdateSingleton();
	}

	public static InvokerUpdateSingleton getInstance() {
		return InvokerUpdateSingletonHelper.INSTANCE;
	}
	
	public void updateTree() {
		if (tree != null) {
			if (tree.getRoot() == null) {
				tree.setRoot(new TreeItem<Shape>());
				tree.setShowRoot(false);
			}

			tree.getRoot().getChildren().clear();
			buildTree(tree.getRoot(), shapesDictionnary.getShapesList());
		}
	}

	private void buildTree(TreeItem<Shape> root, List<Shape> shapes) {
		List<Shape> shallowCopy = shapes.subList(0, shapes.size());
		Collections.sort(shallowCopy, new Comparator<>() {
			@Override
			public int compare(Shape s1, Shape s2) {
				return s2.getZ() - s1.getZ();
			}
		});

		List<TreeItem<Shape>> children = root.getChildren();
		for (Shape shape : shapes) {
			TreeItem<Shape> item = new TreeItem<>(shape);
			if (shape.getShapeType() == ShapeType.GROUP)
				buildTree(item, ((ShapesGroup) shape).getShapes());
			children.add(item);
		}

	}
	
	

}
