package cPaintUS.models;



import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DrawSettingsTest {

	DrawSettings drawSettings;

	@BeforeEach
	void setUp() throws Exception {
		drawSettings = DrawSettings.getInstance();
	}

	@Test
	void getInstanceTest() {
		assertSame(DrawSettings.getInstance(), drawSettings);
	}

}
