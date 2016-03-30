package hillbillies.model;

import static org.junit.Assert.*;

import org.junit.Test;

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
		
		System.out.println(TestWorld.getCubeTypeOf(1,2,0));
		System.out.println(TestWorld.getCubeTypeOf(1,0,0));
		
	}

}
