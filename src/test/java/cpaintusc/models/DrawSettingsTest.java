package cpaintusc.models;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintusc.models.DrawSettings;
import cpaintusc.models.shapes.ShapeType;
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
		Assertions.assertSame(DrawSettings.getInstance(), drawSettings);		
	}
	
	@Test
	void setShapeTest() {
		drawSettings.setShape(ShapeType.RECTANGLE);
		Assertions.assertSame(drawSettings.getShape(),ShapeType.RECTANGLE);
	}
	
	@Test
	void colorsTest() {
		drawSettings.setFillColor(fillcolor);
		drawSettings.setStrokeColor(strokecolor);
		Assertions.assertSame(drawSettings.getFillColor(), fillcolor);
		Assertions.assertSame(drawSettings.getStrokeColor(), strokecolor);
	}

}
