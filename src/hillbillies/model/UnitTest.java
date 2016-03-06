package hillbillies.model;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import ogp.framework.util.ModelException;

public class UnitTest {

	@Test
	public void test() throws ModelException {
		Unit Kobbe = new Unit("Kobbe", new int[] {0,0,0},50,50,50,50, false);
		assertTrue(Kobbe.name == "Kobbe");
		assertTrue(Kobbe.getStrength()== 50);
		assertTrue(Kobbe.getOrientation() == Math.PI / 2.0);
		System.out.println(Arrays.toString(Kobbe.getPosition()));
		double[] target = new double[]{1,2,3};
		Kobbe.moveTo(target);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		Kobbe.advanceTime(0.1);
		

		System.out.println(Arrays.toString(Kobbe.getPosition()));

		
		
			
	}
}
