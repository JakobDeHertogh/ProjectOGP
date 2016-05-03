package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import hillbillies.model.Faction;
import hillbillies.model.objects.Unit;
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
	
	@Test
	public void testUnits() throws ModelException {
		World TestWorld = new World(new int[10][10][10], 
				new DefaultTerrainChangeListener());
		for (int i=0; i<100;i++){
			TestWorld.addUnit(new Unit("Test", new int[]{5,5,5}, 50,50,50,50, false));
		}
		assertEquals("The world can contain 100 units", 
				TestWorld.getActiveUnits().size(), 100);
		for (Faction i: TestWorld.getActiveFactions()){
			assertEquals("The units should be evenly distributed across factions", i.getNbMembers(), 
					TestWorld.getActiveUnits().size() / TestWorld.getActiveFactions().size());
		}
		try{
			TestWorld.addUnit(new Unit("Test", new int[]{5,5,5}, 50,50,50,50, false));
		}catch (ModelException ex){
		}
		assertEquals("The world can not contain more than 100 units", 
				TestWorld.getActiveUnits().size(), 100);
	}
}
