package hillbillies.model.objects;

import static hillbillies.tests.util.PositionAsserts.assertDoublePositionEquals;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

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
			Kobbe.setName("Kobbe&co");
		} catch (ModelException ex) {
			
		}
		
		assertEquals("This name is invalid because it contains the illegal character &", "Kobbe", Kobbe.getName());
		
		
	}
	
	@Test
	public void testAttributes() throws ModelException{
		Unit Kobbe = new Unit("Kobbe", new int[] {0,0,0},50,50,50,50, false);
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
		assertDoublePositionEquals("Position must be the center of the cube", 0.5, 0.5, 0.5,
				Kobbe.getPosition());
		try {
			Kobbe.setPosition(new double [] { 10 , 15, 80});
		} catch (ModelException ex) {
			
		}
		assertDoublePositionEquals("Position is out of bounds", 0.5, 0.5, 0.5,
				Kobbe.getPosition());
		try {
			Kobbe.setPosition(new double [] {50,50,50});
		} catch (ModelException ex){
			
		}
		assertDoublePositionEquals("Position is out of bounds", 0.5, 0.5, 0.5,
				Kobbe.getPosition());
		
		Kobbe.setPosition(new double [] {1.8, 2.50, 5});
		assertDoublePositionEquals("Valid position", 1.8, 2.50, 5,
				Kobbe.getPosition());
		
	}
	
	@Test 
	public void testMovement() throws ModelException {
		Unit Kobbe = new Unit("Kobbe", new int[] {10,10,10},50,50,50,50, false);
		Kobbe.moveToAdjacant(1, 0, 0);
		for (int i = 1 ; i < 20; i++){
			Kobbe.advanceTime(0.5);
		}
		assertDoublePositionEquals("Valid position", 11.5, 10.5, 10.5,
				Kobbe.getPosition());
		for (int i = 1 ; i<12; i++){
			Kobbe.moveToAdjacant(-1, 0, 0);
			for (int j = 1 ; j < 20; j++){
				Kobbe.advanceTime(0.5);
			}
		}
		assertDoublePositionEquals("Valid position", 0.5, 10.5, 10.5,
				Kobbe.getPosition());
		try {
			Kobbe.moveToAdjacant(-1,0,0);
		} catch (ModelException ex){
			
		}
		assertDoublePositionEquals("Position out of bounds", 0.5, 10.5, 10.5,
				Kobbe.getPosition());
		
		Kobbe.moveTo(new int [] {5,5,5});
		for (int i = 1 ; i < 50; i++){
			Kobbe.advanceTime(0.5);
		}
		assertDoublePositionEquals("Valid position", 5.5, 5.5, 5.5,
				Kobbe.getPosition());
		
		try {
			Kobbe.moveTo(new int [] {-1,-5,-5});
			for (int i = 1 ; i < 10; i++){
				Kobbe.advanceTime(0.5);
			}
		} catch (ModelException ex) {
			
		}
		assertDoublePositionEquals("Position out of bounds", 5.5, 5.5, 5.5,
				Kobbe.getPosition());
		
		try {
			Kobbe.moveTo(new int [] {100,50,5});
			for (int i = 1 ; i < 10; i++){
				Kobbe.advanceTime(0.5);
			}
		} catch (ModelException ex) {
			
		}
		assertDoublePositionEquals("Position out of bounds", 5.5, 5.5, 5.5,
				Kobbe.getPosition());

	}
	
	@Test
	public void testLevelUp() throws ModelException{
		Unit Kobbe = new Unit("Kobbe", new int[] {10,10,10},50,50,50,50, false);
		Kobbe.levelUp();
		Kobbe.levelUp();
		Kobbe.levelUp();
		assertEquals(Kobbe.getStrength() + Kobbe.getToughness() + Kobbe.getAgility(), 153);
	}
}
