package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.task.expression.Expression;
import hillbillies.task.expression.UnaryUnitExpression;
import hillbillies.task.expression.ValueExpression;
import hillbillies.task.type.*;
import ogp.framework.util.ModelException;

public class ExpressionTests {

	@Test
	public void test() throws ModelException {
		Unit Kobbe = new Unit("Kobbe", new int[] {0,2,0},50,50,50,50, false);
		int[][][] types = new int[3][3][3];
		types[1][2][0] = 1;
		types[1][1][0] = 1;
		types[1][0][1] = 3;
		types[1][1][1] = 1;
		types[0][1][1] = 3;
		
		World TestWorld = new World(types, new DefaultTerrainChangeListener());
		TestWorld.addUnit(Kobbe);
		
		
		
		Expression<BoolType> expr = new UnaryUnitExpression<PosType, UnitType, BoolType>( (a,b) -> new BoolType(b.getValue().getWorld().isPassable(a.getValue())),
				new ValueExpression<PosType>(new PosType(new int[]{1,1,1})));
		
		System.out.println(expr.evaluate(null, Kobbe).getValue());
		
	}

}
