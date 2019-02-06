package cPaintUS.models;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class LineWidthTest {

	LineWidth lineWidth;

	@BeforeEach
	void setUp() throws Exception {
		lineWidth = LineWidth.getInstance();
	}

	@Test
	void getInstanceTest() {
		Assertions.assertSame(LineWidth.getInstance(), lineWidth);
	}
	
	@Test
	void getListTest () {
		Assertions.assertEquals(lineWidth.getStrings().size(),4);
		Assertions.assertEquals(lineWidth.getDefaultString(),"1px");
	}
}
