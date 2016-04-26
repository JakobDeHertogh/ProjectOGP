package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import hillbillies.model.objects.Unit;
import hillbillies.model.world.World;
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
				
		Unit Test2 = new Unit("Testt", new int[]{0,0,0}, 50,50,50,50, false);
		TestWorld.addUnit(Test2);
		Unit Test3 = new Unit("Testtt", new int[]{0,0,0}, 50,50,50,50, false);
		TestWorld.addUnit(Test3);
		Unit Test4 = new Unit("Testttt", new int[]{0,0,0}, 50,50,50,50, false);
		TestWorld.addUnit(Test4);
		Unit Test5 = new Unit("Testtttt", new int[]{0,0,0}, 50,50,50,50, false);
		TestWorld.addUnit(Test5);
		Unit Test6 = new Unit("Testttttt", new int[]{0,0,0}, 50,50,50,50, false);
		TestWorld.addUnit(Test6);
		Unit Test7 = new Unit("Testttttt", new int[]{0,0,0}, 50,50,50,50, false);
		TestWorld.addUnit(Test7);
		
		System.out.println(Test.getFaction());
		System.out.println(Test2.getFaction());
		System.out.println(Test3.getFaction());
		System.out.println(Test4.getFaction());
		System.out.println(Test5.getFaction());
		System.out.println(Test6.getFaction());
		System.out.println(Test7.getFaction());

		
		
		
		
		assertEquals(TestWorld.getActiveFactions().size(), 5);

		 

		
	}

}
