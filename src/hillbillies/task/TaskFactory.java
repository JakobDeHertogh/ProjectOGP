package hillbillies.task;

import java.util.List;

import hillbillies.model.Task;
import hillbillies.part3.programs.ITaskFactory;
import hillbillies.part3.programs.SourceLocation;
import hillbillies.task.expression.*;
import hillbillies.task.statement.Statement;
import hillbillies.task.type.*;


public class TaskFactory implements ITaskFactory<Expression<? extends Type>, Statement, Task>{

	@Override
	public List<Task> createTasks(String name, int priority, Statement activity, List selectedCubes) {
		// TODO Auto-generated method stub
		return null;
	}

	// STATEMENTS // 
	@Override
	public Statement createAssignment(String variableName, Expression value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createWhile(Expression condition, Statement body, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new WhileStatement(condition, body);
	}

	@Override
	public Statement createIf(Expression condition, Statement ifBody, Statement elseBody, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new IfStatement(condition, ifBody, elseBody);
	}

	@Override
	public Statement createBreak(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new BreakStatement();
	}

	@Override
	public Statement createPrint(Expression value, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new PrintStatement(value);
	}

	@Override
	public Statement createSequence(List statements, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new SequenceStatement(statements);
	}

	@Override
	public Statement createMoveTo(Expression position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new ActionStatement(a -> moveTo(position));
	}

	@Override
	public Statement createWork(Expression position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new ActionStatement(a -> work());
	}

	@Override
	public Statement createFollow(Expression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new ActionStatement(a -> follow(unit));
	}

	@Override
	public Statement createAttack(Expression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new ActionStatement(a -> attack(unit));
	}

	@Override
	public Expression<? extends Type> createReadVariable(String variableName, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	// EXPRESSIONS // 
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
	public Expression<BoolType> createIsFriend(Expression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createIsEnemy(Object unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<BoolType> createIsAlive(Expression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new UnaryExpression<UnitType, BoolType>(a-> new BoolType(a.getValue().isAlive), unit);
	}

	@Override
	public Expression<BoolType> createCarriesItem(Expression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new UnaryExpression<UnitType, BoolType>(a-> new BoolType(
				a.getValue().isCarryingBoulder() || a.getValue().isCarryingLog()), unit);
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
	public Expression<PosType> createHerePosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<PosType> createLogPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<PosType> createBoulderPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<PosType> createWorkshopPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<PosType> createSelectedPosition(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<PosType> createNextToPosition(Expression position, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<PosType> createPositionOf(Expression unit, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<PosType> createLiteralPosition(int x, int y, int z, SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new ValueExpression<PosType>(new PosType(new int[]{x,y,z}));
	}

	@Override
	public Expression<UnitType> createThis(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new ValueExpression<UnitType>(new UnitType())
	}

	@Override
	public Expression<UnitType> createFriend(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return new ValueExpression<UnitType>(new UnitType())
	}

	@Override
	public Expression<UnitType> createEnemy(SourceLocation sourceLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression<UnitType> createAny(SourceLocation sourceLocation) {
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
