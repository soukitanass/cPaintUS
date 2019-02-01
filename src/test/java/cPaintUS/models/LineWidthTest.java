package cPaintUS.models;

import static org.junit.Assert.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.Assert;

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
	
	@Test
	void getListTest () {
		Assert.assertEquals(lineWidth.getStrings().size(),4);
		Assert.assertEquals(lineWidth.getDefaultString(),"1px");
	}
}
