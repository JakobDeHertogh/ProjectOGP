package tests;

import static hillbillies.tests.util.PositionAsserts.assertDoublePositionEquals;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import ogp.framework.util.ModelException;

public class UnitTest {
	

	@Test
	public void testName() throws ModelException {
		Unit Kobbe = new Unit("Kobbe", new int[] {0,0,0},50,50,50,50, false);
		try {
			Kobbe.setName("kobbe");
		
		} catch (ModelException ex){
			
		}
		
		assertEquals("This name is invalid because it doesn't start with a capital", "Kobbe", Kobbe.getName());
		
		try {
			Kobbe.setName("Prof. Steegmans");
		} catch (ModelException ex) {
			
		}
		assertEquals("This name is invalid because it contains the illegal character .", "Kobbe", Kobbe.getName());
		
		Kobbe.setName("Robin");
		assertEquals("This is a valid name", "Robin", Kobbe.getName());
		Kobbe.setName("Willy D'Hooghe");
		assertEquals("This is a valid name", "Willy D'Hooghe", Kobbe.getName());
		Kobbe.setName("Willy \"da boss\" D'Hooghe");
		assertEquals("This is a valid name", "Willy \"da boss\" D'Hooghe", Kobbe.getName());
		
		
	}
	
	@Test
	public void testAttributes() throws ModelException{
		Unit Kobbe = new Unit("Kobbe", new int[] {0,0,0},50,50,50,50, false);
		assertTrue("The values given in the constructor are correctly set", Kobbe.getAgility() == 50 && Kobbe.getStrength() == 50
				&& Kobbe.getToughness() == 50 && Kobbe.getWeight() == 50);
		
		Kobbe.setWeight(26);
		assertTrue("The value 26 should be replaced with a valid value",
				50 <= Kobbe.getWeight() && Kobbe.getWeight() <= 200);
		Kobbe.setAgility(150);
		assertTrue("A change in agility should change the weight as well",
				100 <= Kobbe.getWeight() && Kobbe.getWeight() <= 200);
		Kobbe.setStrength(250);
		assertTrue("The value of 250 should be corrected to a valid value and "
				+ "change the value of the strength as well.",
				175 <= Kobbe.getWeight() && Kobbe.getWeight() <= 200 &&
				Kobbe.getStrength() >= 1 && Kobbe.getStrength() <= 200 );

	}
	
	@Test
	public void testPosition() throws ModelException {
		Unit Kobbe = new Unit("Kobbe", new int[] {0,0,0},50,50,50,50, false);
		int[][][] types = new int[3][3][3];
		types[1][2][0] = 1;
		types[1][1][0] = 1;
		types[1][0][1] = 3;
		types[1][1][1] = 1;
		
		World TestWorld = new World(types, new DefaultTerrainChangeListener());
		TestWorld.addUnit(Kobbe);
		
		assertDoublePositionEquals("Position must be the center of the cube", 0.5, 0.5, 0.5,
				Kobbe.getPosition());
		
		try {
			Kobbe.setPosition(new double [] { 10 , 15, 80});
		} catch (ModelException ex) {
			
		}
		assertDoublePositionEquals("Position is out of bounds", 0.5, 0.5, 0.5,
				Kobbe.getPosition());
		try {
			Kobbe.setPosition(new double [] {1,2,0});
		} catch (ModelException ex){
			
		}
		assertDoublePositionEquals("Position is an invalid cube", 0.5, 0.5, 0.5,
				Kobbe.getPosition());
		
		Kobbe.setPosition(new double [] {1.8, 2.50, 2});
		assertDoublePositionEquals("Valid position", 1.8, 2.50, 2.0,
				Kobbe.getPosition());
	}
	
	@Test 
	public void testMovement() throws ModelException {
		Unit Kobbe = new Unit("Kobbe", new int[] {0,2,0},50,50,50,50, false);
		int[][][] types = new int[3][3][3];
		types[1][2][0] = 1;
		types[1][1][0] = 1;
		types[1][0][1] = 3;
		types[1][1][1] = 1;
		
		World TestWorld = new World(types, new DefaultTerrainChangeListener());
		TestWorld.addUnit(Kobbe);
		
		
		Kobbe.moveToAdjacant(1, 0, 1);
		for (int i = 1 ; i < 20; i++){
			Kobbe.advanceTime(0.5);
		}
		assertDoublePositionEquals("Valid position", 1.5, 2.5, 1.5,
				Kobbe.getPosition());
		try {
			for (int i = 1 ; i<12; i++){
				Kobbe.moveToAdjacant(-1, 0, 0);
				for (int j = 1 ; j < 20; j++){
					Kobbe.advanceTime(0.5);
				}
			}
		} catch (ModelException ex) {
			
		}
		assertDoublePositionEquals("Position out of game world", 0.5, 2.5, 1.5,
				Kobbe.getPosition());

		
		Kobbe.moveTo(new int [] {2,2,2});
		for (int i = 1 ; i < 50; i++){
			Kobbe.advanceTime(0.5);
		}
		assertDoublePositionEquals("Valid position", 2.5, 2.5, 2.5,
				Kobbe.getPosition());
		assertTrue("The unit should have gained experience", Kobbe.getExpPoints() == 4);
		
		try {
			Kobbe.moveTo(new int [] {-1,-5,-5});
			for (int i = 1 ; i < 10; i++){
				Kobbe.advanceTime(0.5);
			}
		} catch (ModelException ex) {
			
		}
		assertDoublePositionEquals("Position out of bounds", 2.5, 2.5, 2.5,
				Kobbe.getPosition());
		
		try {
			Kobbe.moveTo(new int [] {100,50,5});
			for (int i = 1 ; i < 10; i++){
				Kobbe.advanceTime(0.5);
			}
		} catch (ModelException ex) {
		}
		assertDoublePositionEquals("Position out of bounds", 2.5, 2.5, 2.5,
				Kobbe.getPosition());

	}
	
	
	@Test 
	public void testWork() throws ModelException{
		Unit Kobbe = new Unit("Kobbe", new int[] {0,2,0},50,50,50,50, false);
		int[][][] types = new int[3][3][3];
		types[1][2][0] = 1;
		types[1][1][0] = 1;
		types[1][0][1] = 3;
		types[1][1][1] = 1;
		types[0][1][1] = 3;
		
		World TestWorld = new World(types, new DefaultTerrainChangeListener());
		TestWorld.addUnit(Kobbe);
		TestWorld.addBoulder(new int[]{0,1,1});
		TestWorld.addLog(new int[]{0,1,1});
		
		Kobbe.workAt(1, 2, 0);
		assertTrue("The unit should be working", Kobbe.isWorking());
		for (int i= 0; i<200; i++)
			TestWorld.advanceTime(0.1);
		assertTrue("The target cube should be of type AIR", TestWorld.getCubeTypeOf(1, 2, 0) == 0);
		assertTrue("The unit should have stopped working", !Kobbe.isWorking());
		assertTrue("The unit should have upgraded two attributes", 
				Kobbe.getAgility() + Kobbe.getStrength() + Kobbe.getToughness() == 152);
						
		Kobbe.workAt(0,1,1);
		for(int i=0; i<200; i++)
			TestWorld.advanceTime(0.1);
		assertTrue("The target cube should be of type WORKSHOP", TestWorld.getCubeTypeOf(0, 1, 1) == 3);
		assertTrue("The unit should have stopped working", !Kobbe.isWorking());
		assertTrue("The unit should have consumed the log and boulder", 
				TestWorld.getCubeAtPos(0, 1, 1).isOccupiedByBoulders().isEmpty() && 
				TestWorld.getCubeAtPos(0, 1, 1).isOccupiedByLogs().isEmpty());
	}
	
	@Test
	public void testRest() throws ModelException{
		Unit Kobbe = new Unit("Kobbe", new int[] {0,2,0},50,50,50,50, false);
		int[][][] types = new int[3][3][3];
		types[1][2][0] = 1;
		types[1][1][0] = 1;
		types[1][0][1] = 3;
		types[1][1][1] = 1;
		types[0][1][1] = 3;
		
		World TestWorld = new World(types, new DefaultTerrainChangeListener());
		TestWorld.addUnit(Kobbe);
		
		Kobbe.rest();
		assertTrue("The unit should be resting", Kobbe.isResting());
		System.out.println(Kobbe.currentActivity);
		Kobbe.advanceTime(0.1);
		assertTrue("The unit should have stopped resting since it's at maxHP", ! Kobbe.isResting());
		
		Kobbe.setHitpoints(20);
		Kobbe.setStamina(20);
		Kobbe.rest();
		for (int i = 0; i < 20; i++){
			Kobbe.advanceTime(0.1);
		}
		assertTrue("The unit should have restored some of its HP", Kobbe.getCurrentHitPoints() > 20);
		assertEquals("The unit should not have restored stamina yet", Kobbe.getCurrentStaminaPoint(), 20);
		for (int i = 0; i < 500; i++){
			Kobbe.advanceTime(0.1);
		}
		assertEquals("The unit should have restored all of its HP", Kobbe.getCurrentHitPoints(), Kobbe.getMaxHitPoints());
		assertEquals("The unit should have restored all of its stamina", Kobbe.getCurrentStaminaPoint(), Kobbe.getMaxStaminaPoints());
		assertTrue("The unit should have stopped resting", !Kobbe.isResting());
	}
	
	@Test
	public void testDefault() throws ModelException{
		Unit Kobbe = new Unit("Kobbe", new int[] {0,2,0},50,50,50,50, true);
		int[][][] types = new int[3][3][3];
		types[1][2][0] = 1;
		types[1][1][0] = 1;
		types[1][0][1] = 3;
		types[1][1][1] = 1;
		types[0][1][1] = 3;
		
		World TestWorld = new World(types, new DefaultTerrainChangeListener());
		TestWorld.addUnit(Kobbe);
		
		assertTrue("The unit's default behavior should be enabled", Kobbe.isDefaultBehaviorEnabled());
		assertEquals("The unit's current activity should be null", Kobbe.currentActivity, null);
		System.out.println(Kobbe.currentActivity);
		TestWorld.advanceTime(0.2);
		System.out.println(Kobbe.currentActivity);
		assertTrue("The unit's activity should no longer be null", Kobbe.currentActivity != null);
	}
}
