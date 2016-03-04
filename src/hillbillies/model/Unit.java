package hillbillies.model;

import be.kuleuven.cs.som.annotate.Basic;
import ogp.framework.util.ModelException;

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
		this.setName(name);
		
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
	public boolean isValidPosition(double[] position){
		return ((position[0]>=0) && (position[0]<=30) && 
				(position[1]>=0) && (position[1]<=30) && (position[2]>=0) && (position[2]<=30));
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
		this.cubecoordinate[0]= Math.floor(this.xposition);
		this.cubecoordinate[1]= Math.floor(this.yposition);
		this.cubecoordinate[2]= Math.floor(this.zposition);
	}
	public double[] getCubeCoordinate (){
		return this.cubecoordinate;
	}
	private double[] cubecoordinate;
	
	/**
	 * Returns the name of this Unit
	 * @return
	 */
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
	public void setName(String NewName) throws ModelException{
		if (!isValidName(NewName))
			throw new ModelException(NewName);
		this.name = NewName;
	}
	
	public boolean isValidName(String Name){
		return !((name.matches("[a-zA-Z\\s\'\"]+")) && (name.length()<=2) && Character.isUpperCase(name.charAt(0)));
	//	if (Name.length() <2)
	//		return false;
	//	else if (Name.matches("[A-Za-z \"']+"))
	// Dit stond er al in, wat is volgens jou het beste?		
	}
	
	public static int getMaxValue(){
		return 200;
	}
	
	public static int getMinValue(){
		return 1;
	}
	
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
		int minWeight = (this.strength*this.agility)/2;
		if ((newValue >= minWeight) && (newValue <= getMaxValue()))
			this.weight = newValue;
		else if (newValue <= minWeight)
			this.weight = minWeight;
		else if (newValue >= getMaxValue())
			this.weight = getMaxValue();
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
		if ((newValue >= getMinValue()) && (newValue <= getMaxValue()))
			this.strength = newValue;
		else if (newValue <= getMinValue())
			this.strength = getMinValue();
		else if (newValue >= getMaxValue())
			this.strength = getMaxValue();
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
		if ((newValue >= getMinValue()) && (newValue <= getMaxValue()))
			this.agility = newValue;
		else if (newValue <= getMinValue())
			this.agility = getMinValue();
		else if (newValue >= getMaxValue())
			this.agility = getMaxValue();
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
	public void setThoughness(int newValue){
		if ((newValue >= getMinValue()) && (newValue <= getMaxValue()))
			this.toughness = newValue;
		else if (newValue <= getMinValue())
			this.toughness = getMinValue();
		else if (newValue >= getMaxValue())
			this.toughness = getMaxValue();
	}
	/**
	 * 
	 * @return
	 */
	public int getMaxHitPoints(){
		return 200/10000 * this.getWeight() * this.getToughness();
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
		return 200/10000 * this.getWeight() * this.getToughness();
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
	public void advanceTime(double dt){
		
	}
	/**
	 * 
	 * @param dx
	 * @param dy
	 * @param dz
	 * @throws ModelException 
	 */
	public void moveToAdjacant(int dx, int dy, int dz) throws ModelException{
		double[] newposition = new double[3];
		newposition[0]= this.getXPosition() + dx;
		newposition[1]= this.getYPosition()+dy;
		newposition[2]= this.getZPosition()+dz;
		setPosition(newposition); // wat is hier mis?
	}
	//Path finding method (zoals in bundel): opeenvolging van hierboven
	/**
	 * 
	 * @param current
	 * @param target
	 * @throws ModelException 
	 */
	public void moveTo(double[] current, double[]target) throws ModelException{
		int dx = 0;
		int dy = 0;
		int dz = 0;
		while (current != target)
				if (current[0] == target[0])
					dx = 0;
				else if (current[0]<target[0])
					dx = 1;
				else if (current[0]>target[0])
					dx = -1;
				if (current[1] == target[1])
					dy = 0;
				else if (current[1]<target[1])
					dy = 1;
				else if (current[1]>target[1])
					dy = -1;
				if (current[2] == target[2])
					dz = 0;
				else if (current[2]<target[2])
					dz = 1;
				else if (current[2]>target[2])
					dz = -1;
				moveToAdjacant(dx, dy, dz);
				current = this.getPosition();
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
		return (getCurrentSpeed() != 0);
	}
	/**
	 * 
	 */
	public void startSprinting(){
		
	}
	/**
	 * 
	 */
	public void stopSprinting(){
		
	}
	/**
	 * 
	 * @return
	 */
	public boolean isSprinting(){
		return true; //zodat het momenteel geen error meer geeft :D
	}
	/**
	 * 
	 * @param cube
	 */
	public void moveTo(int[] cube){
		
	}
	/**
	 * 
	 */
	
	public void work(){
		
	}
	/**
	 * 
	 * @return
	 */
	public boolean isWorking(){
		return true;
	}
	/**
	 * 
	 */
	public void fight(Unit other){
		
	}
	/**
	 * 
	 * @return
	 */
	public boolean isAttacking(){
		return true;
	}
	/**
	 * 
	 */
	public void rest(){
		
	}
	/**
	 * 
	 * @return
	 */
	public boolean isResting(){
		return true;
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
	
	private String name;
	private int weight;
	private int agility;
	private int strength;
	private int toughness;
	private int minValue;
	private int maxValue;
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
	private double currentspeed;
	
}
