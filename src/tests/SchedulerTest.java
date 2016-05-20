package tests;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import hillbillies.model.Faction;
import hillbillies.model.Scheduler;
import hillbillies.model.Task;
import hillbillies.model.Unit;
import hillbillies.model.World;
import hillbillies.part2.listener.DefaultTerrainChangeListener;
import hillbillies.part3.facade.Facade;
import hillbillies.part3.facade.IFacade;
import hillbillies.part3.programs.TaskParser;
import ogp.framework.util.ModelException;

public class SchedulerTest {
	
	private Facade facade;

	private static final int TYPE_AIR = 0;
	private static final int TYPE_ROCK = 1;
	private static final int TYPE_TREE = 2;
	private static final int TYPE_WORKSHOP = 3;
	@Before
	public void setup() {
		this.facade = new Facade();
	
	public void test2TaskExecuted() throws ModelException {
		int[][][] types = new int[3][3][3];
		types[1][1][0] = TYPE_ROCK;
		types[1][1][1] = TYPE_ROCK;
		types[1][1][2] = TYPE_TREE;
		types[2][2][2] = TYPE_WORKSHOP;

		World world = facade.createWorld(types, new DefaultTerrainChangeListener());
		Unit unit = facade.createUnit("Test", new int[] { 1, 1, 1 }, 50, 50, 50, 50, true);
		facade.addUnit(unit, world);
		Faction faction = facade.getFaction(unit);
		
		Scheduler scheduler = facade.getScheduler(faction);
		List<Task> tasks = TaskParser.parseTasksFromString(
				"name: \"move task\"\npriority: 69\nactivities: moveTo ( 1, 2, 2);", facade.createTaskFactory(),
				Collections.singletonList(new int[] { 1, 1, 2 }));
		
		scheduler.addTask(tasks);
		
		// tasks are created
		assertNotNull(tasks);
		// there's exactly one task
		assertEquals(1, tasks.size());
		Task task = tasks.get(0);
		// test name
		assertEquals("work task", facade.getName(task));
		// test priority
		assertEquals(69, facade.getPriority(task));System.out.println("Applaus");
		facade.schedule(scheduler, task);
		advanceTimeFor(facade, world, 100, 10);
		// work task is removed from scheduler
		assertFalse(facade.areTasksPartOf(scheduler, Collections.singleton(task)));
		
	}
}
