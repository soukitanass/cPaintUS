package cpaintus.models;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import cpaintus.models.LineWidth;

class LineWidthTest {

	LineWidth lineWidth;

	@BeforeEach
	void setUp() throws Exception {
		lineWidth = LineWidth.getInstance();
	}

	@Test
	void getInstanceTest() {
		assertSame(LineWidth.getInstance(), lineWidth);
	}
}
