package cpaintus.models;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import cPaintUS.models.shapes.ShapeType;
import javafx.scene.paint.Color;

import cpaintus.models.DrawSettings;

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
		Assertions.assertSame(DrawSettings.getInstance(), drawSettings);		
	}
	
	@Test
	void setShapeTest() {
		drawSettings.setShape(ShapeType.Rectangle);
		Assertions.assertSame(drawSettings.getShape(),ShapeType.Rectangle);
	}
	
	@Test
	void colorsTest() {
		drawSettings.setFillColor(fillcolor);
		drawSettings.setStrokeColor(strokecolor);
		Assertions.assertSame(drawSettings.getFillColor(), fillcolor);
		Assertions.assertSame(drawSettings.getStrokeColor(), strokecolor);
	}

}
