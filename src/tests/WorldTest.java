package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import hillbillies.model.world.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

public class WorldTest {

	@Test
	public void testConstructor() throws ModelException {
		int[][][] types = new int[3][3][3];
		types[1][2][0] = 1;
		types[1][1][0] = 1;
		types[1][0][1] = 3;
		
		World TestWorld = new World(types, new DefaultTerrainChangeListener());

		assertTrue(TestWorld.getNbCubesX() == 3);
		
		assertTrue(TestWorld.getCubeTypeOf(1, 2, 0) == 1);
		assertTrue(TestWorld.getCubeTypeOf(1, 0, 0) == 0);
	}
	
	@Test 
	public void testCaveIn() throws ModelException{
		
		int[][][] types = new int[3][3][3];
		types[1][2][0] = 1;
		types[1][1][0] = 1;
		types[1][0][1] = 3;
		
		World TestWorld = new World(types, new DefaultTerrainChangeListener());
		
		TestWorld.caveInCube(1, 2, 0);
		assertEquals(TestWorld.getCubeTypeOf(1, 2, 0), 0);
	}
	
}
