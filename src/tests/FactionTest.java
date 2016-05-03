package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

public class FactionTest {

	@Test
	public void testNewUnit() throws ModelException {
		Unit Test = new Unit("Test", new int[]{0,0,0}, 50,50,50,50, false);
		
		int[][][] types = new int[3][3][3];
		types[1][2][0] = 1;
		types[1][1][0] = 1;
		types[1][0][1] = 3;
		
		World TestWorld = new World(types, new DefaultTerrainChangeListener());
		
		TestWorld.addUnit(Test);
		assertEquals(TestWorld.getActiveFactions().size(), 1);
		assertTrue(Test.getWorld() == TestWorld);
				
		for (int i = 0; i<7; i++)
			TestWorld.addUnit(new Unit("Test", new int[]{0,0,0}, 50,50,50,50, false));
		
		assertEquals(TestWorld.getActiveFactions().size(), 5);
		assertEquals(TestWorld.getActiveUnits().size(), 8);
		
	}

}
