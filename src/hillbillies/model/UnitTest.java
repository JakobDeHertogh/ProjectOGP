package hillbillies.model;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import ogp.framework.util.ModelException;

public class UnitTest {

	@SuppressWarnings("deprecation")
	@Test
	public void test() throws ModelException {
		Unit Kobbe = new Unit("Kobbe", new int[] {0,0,0},50,50,50,50, false);
		assertTrue(Kobbe.name == "Kobbe");
		assertTrue(Kobbe.getStrength()== 50);
		assertEquals(Kobbe.getOrientation() , Math.PI / 2.0);
		
	}
}
