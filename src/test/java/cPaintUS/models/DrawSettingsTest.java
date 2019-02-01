package cPaintUS.models;

import static org.junit.Assert.assertSame;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cPaintUS.models.shapes.Line;
import cPaintUS.models.shapes.Rectangle;
import cPaintUS.models.shapes.ShapeType;
import javafx.scene.paint.Color;

class DrawSettingsTest {

	DrawSettings drawSettings;
	Color fillcolor;
	Color strokecolor;

	@BeforeEach
	void setUp() throws Exception {
		drawSettings = DrawSettings.getInstance();
		fillcolor = Color.AQUA;
		strokecolor = Color.DARKGREY;
	}

	@Test
	void getInstanceTest() {
		assertSame(DrawSettings.getInstance(), drawSettings);		
	}
	
	@Test
	void setShapeTest() {
		drawSettings.setShape(ShapeType.Rectangle);
		assertSame(drawSettings.getShape(),ShapeType.Rectangle);
	}
	
	@Test
	void colorsTest() {
		drawSettings.setFillColor(fillcolor);
		drawSettings.setStrokeColor(strokecolor);
		assertSame(drawSettings.getFillColor(), fillcolor);
		assertSame(drawSettings.getStrokeColor(), strokecolor);
	}

}
