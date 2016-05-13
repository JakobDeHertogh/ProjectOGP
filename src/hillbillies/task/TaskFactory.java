package hillbillies.task;

import java.util.List;

import hillbillies.model.Task;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.task.expression.BinaryExpression;
import hillbillies.task.expression.Expression;
import hillbillies.task.expression.UnaryExpression;
import hillbillies.task.expression.ValueExpression;
import hillbillies.task.statement.Statement;
import hillbillies.task.type.BoolType;
import hillbillies.task.type.Type;
import hillbillies.task.type.UnitType;

public class TaskFactory implements ITaskFactory<Expression<? extends Type>, Statement, Task>{

	@Override
	public List createTasks(String name, int priority, Object activity, List selectedCubes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createAssignment(String variableName, Object value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createWhile(Object condition, Object body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createIf(Object condition, Object ifBody, Object elseBody, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createPrint(Object value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createSequence(List statements, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createMoveTo(Object position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createWork(Object position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createFollow(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createAttack(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createReadVariable(String variableName, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<BoolType> createIsSolid(Expression position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new UnaryExpression<PosType, BoolType>( a -> new BoolType(a.getValue() ), position)
	}

	@Override
	public Object createIsPassable(Object position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createIsFriend(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createIsEnemy(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createIsAlive(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createCarriesItem(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<BoolType> createNot(Expression expression, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new UnaryExpression<BoolType, BoolType>( a -> new BoolType(!a.getValue()), expression);
	}

	@Override
	public Expression<BoolType> createAnd(Expression left, Expression right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new BinaryExpression<BoolType, BoolType, BoolType>( (a,b) -> new BoolType(a.getValue() && b.getValue()),
				left, right);
	}

	@Override
	public Expression<BoolType> createOr(Expression left, Expression right, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new BinaryExpression<BoolType, BoolType, BoolType>( (a,b) -> new BoolType(a.getValue() || b.getValue()),
				left, right);
	}

	@Override
	public Object createHerePosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createLogPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createBoulderPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createWorkshopPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createSelectedPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createNextToPosition(Object position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createPositionOf(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<UnitType> createThis(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new ValueExpression<UnitType>(new UnitType())
	}

	@Override
	public Object createFriend(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createEnemy(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createAny(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<BoolType> createTrue(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new ValueExpression<BoolType>(new BoolType(true));
	}

	@Override
	public Expression<BoolType> createFalse(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new ValueExpression<BoolType>(new BoolType(false));
	}

}
