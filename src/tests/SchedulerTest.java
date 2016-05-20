package tests;

import hillbillies.model.*;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

public class SchedulerTest {
	public void testNewUnit() throws ModelException {
		Unit Test = new Unit("Test", new int[]{0,0,0}, 50,50,50,50, false);
		
		int[][][] types = new int[3][3][3];
		types[1][2][0] = 1;
		types[1][1][0] = 1;
		types[1][0][1] = 3;
		
		World TestWorld = new World(types, new DefaultTerrainChangeListener());
		
		TestWorld.addUnit(Test);
		
		Scheduler scheduler1 = new Scheduler(Test.getFaction());
		
	}
}
