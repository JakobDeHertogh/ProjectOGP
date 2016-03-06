package hillbillies.model;

import java.util.Arrays;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import ogp.framework.util.ModelException;




// Pls tell me this works.
/**
 * 
 * @author Gebruiker
 *
 */
public class Unit {
	/**
	 * 
	 * @param name
	 * @param initialPosition
	 * @param weight
	 * @param agility
	 * @param strenght
	 * @param toughness
	 * @param enableDefaultBehavior
	 * @throws ModelException 
	 */
	
	public Unit(String name, int[] initialPosition, int weight, int agility, int strength,
			int toughness, boolean enableDefaultBehavior) throws ModelException{
		//set name and basic values
		this.setName(name);
		this.setWeight(validStartVal(weight));
		this.setAgility(validStartVal(agility));
		this.setStrength(validStartVal(strength));
		this.setToughness(validStartVal(toughness));
		//convert int[] to double[] and add 0.5
		int[] initials = initialPosition;
		double[] pos = new double[initials.length];
		for (int i = 0;i<initials.length; i++){
			pos[i] = initials[i] + 0.5;
		}
		this.setPosition(pos);
		//set default behavior to given
		this.setDefaultBehaviorEnabled(enableDefaultBehavior);
		//set orientation to PI/2
		this.setOrientation(Math.PI / 2.0);
	}
	public int validStartVal(int val){
		if ((val <= maxStartVal) && (val >= minStartVal))
			return val;
		else return minStartVal;
	}
	
	@Basic
	public String getName(){
		return this.name;
	}
	/**
	 * Sets the name of this unit to the given Name
	 * 
	 * @param newName
	 * 
	 * @throws ModelException
	 */
	public void setName(String newName) throws ModelException{
		if ((newName.matches("[a-zA-Z\\s\'\"]+")) && (newName.length()>=2) 
				&& Character.isUpperCase(newName.charAt(0)))
			this.name = newName;
		else throw new ModelException(newName);
	}
	
	/**
	 * Returns the current position of this Unit
	 * @return
	 */
	@Basic
	public double[] getPosition(){
		return this.position;
	}
	/**
	 * Sets the position of this Unit to the given position
	 * @throws ModelException
	 */
	// Is wat ik tot nu toe heb gedaan van positie
	// zowel de array position als aparte coordinaten opgeslagen
	public void setPosition(double[] newposition) throws ModelException{
		if (!isValidPosition(newposition))
			throw new ModelException();
		this.position = newposition;
		this.xposition = newposition[0];
		this.yposition = newposition[1];
		this.zposition = newposition[2];
	}
	public boolean isValidPosition(double[] pos){
		
		return ((pos[0]>=minXPos) && (pos[0]<maxXPos) && 
				(pos[1]>=minYPos) && (pos[1]<maxYPos) && 
				(pos[2]>=minZPos) && (pos[2]<maxZPos));
	}

	public double getXPosition(){
		return this.xposition;
	}
	public double getYPosition(){
		return this.yposition;
	}
	public double getZPosition(){
		return this.zposition;
	}

	
	/**
	 * 
	 * @return
	 */
	public void setCubecoordinate(){
		this.cubex = Math.floor(this.xposition);
		this.cubey = Math.floor(this.yposition);
		this.cubez = Math.floor(this.zposition);
		this.cubecoordinate[0]= (int) Math.floor(this.xposition);
		this.cubecoordinate[1]= (int) Math.floor(this.yposition);
		this.cubecoordinate[2]= (int) Math.floor(this.zposition);
	}
	
	public int[] getCubeCoordinate (){
		return this.cubecoordinate;
	}
	
	private int[] cubecoordinate;
	
	/**
	 * Returns the name of this Unit
	 * @return
	 */
	
	
	
	
	/**
	 * 
	 * @return
	 */
	@Basic
	public int getWeight(){
		return this.weight;
	}
	/**
	 * Sets the weight of this Unit to the given weight
	 * 
	 * @post	If the given weight exceeds the maximum weight, the weight is 
	 * 			set to the maximum weight. If the given weight is less than the 
	 * 			minimum weight, the weight is set to the minimum weight. If the 
	 * 			weight is in between minimum and maximum value, the weight is set 
	 * 			to the given weight. 
	 *
	 * @param newValue
	 */
	public void setWeight(int newValue){
		int minWeight = (this.strength + this.agility)/2;
		if ((newValue >= minWeight) && (newValue <= maxValue))
			this.weight = newValue;
		else if (newValue <= minWeight)
			this.weight = minWeight;
		else if (newValue >= maxValue)
			this.weight = maxValue;
	}
	/**
	 * Returns the weight of this Unit 
	 * @return
	 */
	@Basic
	public int getStrength(){
		return this.strength;
	}
	/**
	 * @post	If the given strength exceeds the maximum value, the strength is 
	 * 			set to the maximum value. If the given strength is less than the 
	 * 			minimum value, the strength is set to the minimum value. If the 
	 * 			strength is in between minimum and maximum value, the weight is set 
	 * 			to the given weight. 
	 * @param newValue
	 */
	public void setStrength(int newValue){
		if ((newValue >= minValue) && (newValue <= maxValue))
			this.strength = newValue;
		else if (newValue <= minValue)
			this.strength = minValue;
		else if (newValue >= maxValue)
			this.strength = maxValue;
		setWeight(this.weight);
		// Heb ik er aan toegevoegd, omdat het gewicht verandert afhankelijk van strenght
	}
	/**
	 * 
	 * @return
	 */
	@Basic
	public int getAgility(){
		return this.agility;
	}
	/**
	 * 
	 * @param newValue
	 */
	public void setAgility(int newValue){
		if ((newValue >= minValue) && (newValue <= maxValue))
			this.agility = newValue;
		else if (newValue <= minValue)
			this.agility = minValue;
		else if (newValue >= maxValue)
			this.agility = maxValue;
		setWeight(this.weight);
		//zelfde reden als bij setStrength()
	}
	/**
	 * 
	 * @return
	 */
	@Basic
	public int getToughness(){
		return this.toughness;
	}
	/**
	 * 
	 * @param newValue
	 */
	public void setToughness(int newValue){
		if ((newValue >= minValue) && (newValue <= maxValue))
			this.toughness = newValue;
		else if (newValue <= minValue)
			this.toughness = minValue;
		else if (newValue >= maxValue)
			this.toughness = maxValue;
	}
	
	/**
	 * 
	 * @return
	 */
	public int getMaxHitPoints(){
		return (int) (0.02 * this.getWeight() * this.getToughness());
	}
	/**
	 * 
	 * @return
	 */
	public int getCurrentHitPoints(){
		return this.hitpoints;
	}
	// Dit heb ik er al in gezet in geval van damage (hp mag niet onder 0 :p)
	public boolean isValidHP( int hp){
		return (hp<= this.getMaxHitPoints()) && (hp>=0);
	}
	/**
	 * 
	 * @return
	 */
	public int getMaxStaminaPoints(){
		return ((int) 0.02* this.getWeight() * this.getToughness());
	}
	/**
	 * 
	 * @return
	 */
	public int getCurrentStaminaPoint(){
		return this.stamina;
	}
	public void setOrientation(double orientation){
//		if (orientation >=2* Math.PI)
//			orientation = orientation - 2* Math.PI;
//			setOrientation(orientation);
//		else if (orientation<0)
//			orientation = 2* Math.PI + orientation;
//			setOrientation(orientation);
//		else
//			this.orientation = orientation;
//	 Volgens mij is het volgende een pak korter/efficienter:
		this.orientation = ((2*Math.PI)+(orientation%(2*Math.PI)))%(2*Math.PI);
	}
	/**
	 * returns the current value of the orientation as a floating point number.
	 */
	public double getOrientation(){
		return this.orientation;
	}
	
	/**
	 * 
	 * @param dt
	 */
	public void advanceTime(double dt) throws ModelException{
		
		//Movement
		System.out.println("adj  " + Arrays.toString(this.adjacant));
		System.out.println("goal: " + Arrays.toString(this.goal));
		if (this.adjacant != null)
			if (this.distance < this.getCurrentSpeed()*dt){
				System.out.println("ping!");
				this.setPosition(adjacant);
				this.adjacant = null;
				if (Arrays.equals(this.position, this.goal))
					this.goal = null;
				
			}
			else {
				double[] newposition = new double[3];
				newposition[0] = this.getXPosition() + dt * this.xspeed;
				newposition[1] = this.getYPosition() + dt * this.yspeed;
				newposition[2] = this.getZPosition() + dt * this.zspeed;
				this.distance -= this.getCurrentSpeed()*dt;
				System.out.println("currspeed2" +this.getCurrentSpeed());
				setPosition(newposition);
				System.out.println("newposition" + Arrays.toString(newposition));
			}
		else if ((this.goal != null) && (this.goal != this.position)){
			System.out.println("moveTo");
			int[] target = new int[3];
			for (int i = 0; i<target.length; i++)
				target[i] = (int) goal[i];
			this.moveTo(target);
		}
		//Work
		if (isWorking())
			this.worktime = this.worktime - dt;
		
		//Attack
		if (isattacking)
			this.attacktime = this.attacktime - dt;
			
		
		//Defense
		if (isdefending)
			this.defendtime -= dt;
			
		//Rest
		if (isresting)
			if (this.hitpoints < this.getMaxHitPoints())
				this.hitpoints += (this.toughness * dt)/(200*0.2);
			if (this.stamina < this.getMaxStaminaPoints())
				this.stamina += (this.toughness * dt)/(100*.2);
		
		
		
	}
	
	
	/**
	 * 
	 * @param dx
	 * @param dy
	 * @param dz
	 * @throws ModelException 
	 */
	
	public void setCurrentspeed(double[] start, double[] end){
		double dz = end[2]-start[2];
		System.out.println("dz" + dz);
		double vb = 1.5* (this.agility+ this.strength) / (2.0*this.weight);
		System.out.println("vb" + vb);
		if (start==end)
			this.currentspeed = 0;
		else
			if (dz ==-1)
				this.currentspeed = 0.5*vb;
			else if (dz==1)
				this.currentspeed = 1.2*vb;
			else
				this.currentspeed = this.vb;	
		System.out.println("currspeed" + this.currentspeed);
	}
	
	//Path finding method (zoals in bundel): opeenvolging van hierboven
	/**
	 * 
	 * @param current
	 * @param target
	 * @throws ModelException 
	 */
	public void moveToAdjacant(int dx, int dy, int dz) throws ModelException{
		// Eerst definieren we de positie waar we naartoe zullen bewegen.
		double[] newposition = new double[3];
		newposition[0]= Math.floor(this.getXPosition() + dx) + 0.5;
		newposition[1]= Math.floor(this.getYPosition() + dy) + 0.5;
		newposition[2]= Math.floor(this.getZPosition() + dz) + 0.5;
		this.adjacant = newposition;
		System.out.println("adjacant" + Arrays.toString(this.adjacant));
		
		// We kijken of die een mogelijke positie is, indien niet ModelException
		if (!isValidPosition(newposition))
			throw new ModelException();
		
		// Goede positie -> berekenen verplaatsingssnelheid en -vector berekenen;
		double xdistance = Math.pow((newposition[0]-this.getXPosition()), 2);
		double ydistance = Math.pow((newposition[1]-this.getYPosition()), 2);
		double zdistance = Math.pow((newposition[2]-this.getZPosition()), 2);
		
		//We maken alle parameters klaar voor de verplaatsing naar een andere cube.
		this.distance = Math.sqrt(xdistance + ydistance + zdistance);
		System.out.println("newpos" + Arrays.toString(newposition));
		setCurrentspeed(this.position, newposition);
		if (isSprinting())
			this.currentspeed = 2* this.currentspeed;
		setSpeedVector(xdistance, ydistance, zdistance, this.distance);
		System.out.println("speedvector " + Arrays.toString(this.speedVector));
	}

	
	public void setSpeedVector(double xdistance, double ydistance, double zdistance, double totaldistance){
		double[] speedvector = new double[3];
		speedvector[0] = this.currentspeed * xdistance / totaldistance;
		speedvector[1] = this.currentspeed * ydistance / totaldistance;
		speedvector[2] = this.currentspeed * zdistance / totaldistance;
		this.speedVector = speedvector;
		this.xspeed = speedvector[0];
		this.yspeed = speedvector[1];
		this.zspeed = speedvector[2];
	}
	/**
	 * 
	 * @return
	 */
	public double getCurrentSpeed(){
		return this.currentspeed;
	}
	/**
	 * 
	 * @return
	 */
	public boolean isMoving(){
		return (this.getCurrentSpeed() != 0);
	}
	
	public boolean isSprinting(){
		return this.issprinting;
	}
	/**
	 * 
	 */
	public void startSprinting(){
		this.issprinting = true;
	}
	/**
	 * 
	 */
	public void stopSprinting(){
		this.issprinting = false;
	}

	/**
	 * 
	 * @param cube
	 */
	public void moveTo(int[] targetcube) throws ModelException{
		this.goal = new double[3];
		this.goal[0] = targetcube[0] + 0.5;
		this.goal[1] = targetcube[1] + 0.5;
		this.goal[2] = targetcube[2] + 0.5;
		System.out.println("goal" + Arrays.toString(this.goal));
		System.out.println("pos " + Arrays.toString(this.position));
		int dx = 0;
		int dy = 0;
		int dz = 0;
		if (this.position[0] == targetcube[0])
			dx = 0;
		else if (this.position[0]<targetcube[0])
			dx = 1;
		else if (this.position[0]>targetcube[0])
			dx = -1;
		if (this.position[1] == targetcube[1])
			dy = 0;
		else if (this.position[1]<targetcube[1])
			dy = 1;
		else if (this.position[1]>targetcube[1])
			dy = -1;
		if (this.position[2] == targetcube[2])
			dz = 0;
		else if (this.position[2]<targetcube[2])
			dz = 1;
		else if (this.position[2]>targetcube[2])
			dz = -1;
		System.out.println("dx  " + dx + "dy  " + dy + "dz " + dz);
		moveToAdjacant(dx, dy, dz);
	}
	/**
	 * 
	 */
	
	public void work(){
		this.worktime = 500.0/this.strength;
		this.goal = null;
	}
	/**
	 * 
	 * @return
	 */

	public boolean isWorking(){
		return (this.worktime > 0);
	}
	public void fight(Unit other){
		attack(other);
	}
	/**
	 * 
	 */
	public void attack(Unit other){
		this.goal = null;
		this.attacktime = 1;
		other.defendtime = 1;
		this.isattacking = true;
		other.isdefending = true;
		;
	}
	public boolean isAttacking(){
		return this.attacktime > 0;
	}
	/**
	 * 
	 * @param other
	 * @throws ModelException 
	 */
	public void defend(Unit other) throws ModelException{
		this.goal = null;
		double Pdodge = 0.20*(this.agility)/(other.agility);
		double random = Math.random();
		double Pblock = 0.25*(this.strength + this.agility)/(other.strength + other.agility);
		//DODGE
		if (random<= Pdodge)
			runAwayFrom(other.getPosition());
		//DAMAGE
		else if (random >= Pblock)
			this.hitpoints = other.strength /10;
		//BLOCK: gebeurt niets, dus niet nodig te vermelden!
	}
	
	public void runAwayFrom(double[] position) throws ModelException{
		double[] newpos = new double[3];
		int x;
		int y = new Random().nextInt(2);
		while ((!isValidPosition(newpos)) && (this.position !=position));
			x = new Random().nextInt(2);
			y = new Random().nextInt(2);
			newpos[0] = this.getXPosition() + x;
			newpos[1] = this.getYPosition() + y;
			newpos[2] = this.getZPosition();
		setPosition(newpos);
	}
	
	/**
	 * 
	 */
	public void rest(){
		this.goal = null;
		this.isresting = true;
	}
	public boolean isResting(){
		return this.isresting;
	}
	/**
	 * 
	 * @param value
	 */
	public void setDefaultBehaviorEnabled(boolean value){
		
	}
	/**
	 * 
	 * @return
	 */
	public boolean isDefaultBehaviorEnabled(){
		return true;
	}
	
	String name;
	private int weight;
	private int agility;
	private int strength;
	private int toughness;
	private static int minValue = 0;
	private static int maxValue = 200;
	private int hitpoints;
	private int stamina;
	private double[] position;
	private double xposition;
	private double yposition;
	private double zposition;
	private double orientation;
	private double cubex;
	private double cubey;
	private double cubez;
	private double distance;
	private boolean isWalking;
	private double vb = 1.5* (this.agility+ this.strength) / (2.0*this.weight);
	private double currentspeed;
	private static int minXPos = 0;
	private static int maxXPos = 50;
	private static int minYPos = 0;
	private static int maxYPos = 50;
	private static int minZPos = 0;
	private static int maxZPos = 50;
	private static int minStartVal = 25;
	private static int maxStartVal = 100;
	private double[] speedVector;
	private double xspeed;
	private double yspeed;
	private double zspeed;
	private double worktime;
	private double attacktime;
	private double defendtime;
	private boolean issprinting;
	private boolean isresting;
	private boolean isdefending;
	private boolean isattacking;
	double [] goal;
	private double[] adjacant;
	
}
