package tests;

import static hillbillies.tests.util.PositionAsserts.assertDoublePositionEquals;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import hillbillies.model.objects.Path;
import hillbillies.model.objects.Unit;
import hillbillies.model.world.Cube;
import hillbillies.model.world.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

public class PathTest {

	@Test
	public void test() throws ModelException {
		int[][][] types = new int[3][3][3];
		types[1][2][0] = 1;
		types[1][1][0] = 1;
		types[1][0][1] = 3;
		types[1][1][1] = 1;
		
		World TestWorld = new World(types, new DefaultTerrainChangeListener());
		
		Path path = new Path(TestWorld.getCubeAtPos(0, 2, 0), TestWorld.getCubeAtPos(2, 2, 0));
		System.out.println("finished");
		System.out.println(Arrays.toString(path.getRoute().peek().getPosition()));
		for (Cube cube : path.getRoute())
			System.out.println(Arrays.toString(cube.getPosition()));
		
		
		
		
		Unit TestUnit = new Unit("Test", new int[]{0,0,0}, 50,50,50,50, false);
		TestWorld.addUnit(TestUnit);
		TestUnit.moveTo(new int[]{0,2,0});
		for (int i = 0; i<100; i++)
			TestWorld.advanceTime(0.1);
		assertDoublePositionEquals("Position must be the center of the cube", 0.5, 2.5, 0.5,
				TestUnit.getPosition());		
		
		
	}

}
