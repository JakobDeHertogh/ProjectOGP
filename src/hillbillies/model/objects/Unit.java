package hillbillies.model.objects;

import java.util.*;

import org.junit.Test.None;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
import hillbillies.model.Data;
import hillbillies.model.Faction;
import hillbillies.model.world.Cube;
import hillbillies.model.world.WorkTypes;
import ogp.framework.util.ModelException;



/**
 * A class of game Units. Each Unit has a name, position and characteristics like weight, 
 * strength, agility and toughness. Units can move, work, rest and fight each other. Default
 * behavior makes them select a random activity. 
 * @invar	The name that applies to all units must be a valid name.
 * 			| isValidName(getName())
 * @invar	The position of every unit is a valid position.
 * 			| isValidPosition(getPosition())
 * @invar	Each unit has a valid amount of hit points and stamina points at all time.
 * 			| isValidHP(getCurrentHP())
 * 			| isValidStamina(getCurrentStamina())
 * @invar	The starting value for each of the attributes of every unit must be valid.
 * 			| isValidStartVal(weight) && isValidStartVal(toughness) isValidStartVal(agility) 
 * 				&& isValidStartVal(strength)
 * 
 * @version 0.51
 * @author Kristof Van Cappellen
 * @author Jakob De Herthogh
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
	 * 
	 * @post 	Unit's new name is the given name, if that meets the requirements. 
	 * 			| new name == Unit.setName(name)
	 * @post	Unit's weight, agility, strength and toughness are limited to values
	 * 			between 25 and 100. 
	 * 			| new weight/agility/strength/toughness == set... (validStartVal(...))
	 * @post 	Unit's defaultBehavior is enabled if asked so. 
	 * 			... 
	 *
	 * 
	 * 
	 * @throws ModelException 
	 * 		If the name does not meet the requirements. 
	 */
	
	public Unit(String name, int[] initialPosition, int weight, int agility, int strength,
			int toughness, boolean enableDefaultBehavior) throws ModelException{
		//set name and basic values
		this.setName(name);
		this.setAgility(validStartVal(agility));
		this.setStrength(validStartVal(strength));
		this.setToughness(validStartVal(toughness));
		this.setWeight(validStartVal(weight));
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
		this.stamina= this.getMaxStaminaPoints();
		this.hitpoints = this.getMaxHitPoints();
		this.lifetime = 0;
		this.isAlive = true;
		
		this.faction = null;
		this.experiencePoints = 0;
		this.fallingTo = this.getZPosition();
	}
	
	// FACTIONS
	/**
	 * @returns Returns the faction of this Unit.
	 */
	public Faction getFaction(){
		return this.faction;
	}
	// Zou enkel via addUnit uit faction mogen opgeroepen worden. 
	/**
	 * Sets the faction of this Unit.
	 * @post	If the Unit did not have a faction, then the faction will be set to the given faction.
	 */
	public void setFaction(Faction faction){
		if (this.faction == null)// of isTerminated()
			this.faction = faction;
	}
	
	// EXPERIENCE
	/**
	 * @returns The number of Experience Points this Unit has.
	 */
	public int getExpPoints(){
		return this.experiencePoints;
	}
	/**
	 * Levels up a random trait of the Unit if a sufficient number of Experience Points have been collected.
	 * @post 
	 */
	public void levelUp(){
		double P = Math.random();
		if (P < 0.33333)
			this.setToughness(this.toughness + 1);
		else if (P < 0.66666)
			this.setAgility(this.agility + 1);
		else if (P < 1)
			this.setStrength(this.strength + 1);
	}
	
	// FALLING 
	/**
	 * 
	 * @return Returns the cube which the Unit occupies.
	 */
	public Cube occupiesCube(){
		return this.faction.getWorld().getCubeAtPos(this.getCubeCoordinate()[0], this.getCubeCoordinate()[1], this.getCubeCoordinate()[2]);
	}
	
	/**
	 * Returns a value between minStartVal and maxStartVal. In this
	 * case minStartVal equals 25 and maxStartVal equals 100. 
	 * @param val The value which is meant to be used for a certain attribute of the Unit.
	 * @post	If the input value is between minStarval and maxStartVal, the given value is returned.
	 * 			| if (minStartval<val<maxStarval)
	 * 			| then (return val) 
	 * @post	If the input value exceeds the given range between minStartval and maxStarval, minStartval is returned.
	 * 			| if (val < minStartval) or (val > maxStartval)
	 * 			| then (return minStartval)
	 * @return 	Returns a valid starting value.	
	 */
 	@Immutable @Raw
	public int validStartVal(int val){
		if ((val <= maxStartVal) && (val >= minStartVal))
			return val;
		else return minStartVal;
	}
	
	@Basic
	/**
	 * Returns the name of this Unit. 
	 */
	public String getName(){
		return this.name;
	}
	/**
	 * Sets the name of this unit to the given Name
	 * 
	 * @param newName
	 * 			The name we wish to give the Unit
	 * @post	The name of the Unit is changed to the given newName
	 * 			| new.name = newName
	 * @throws ModelException
	 * 			The given name doesn't start with a capital letter, doesn't have a length of 2 or more, or contains
	 * 			other characters besides letters, spaces or quotes.
	 * 			| !( (newName.matches("[A-Z][a-zA-Z\\s\'\"]*")) && (newName.length()>=2) )
	 */
	public void setName(String newName) throws ModelException{
		if ((newName.matches("[A-Z][a-zA-Z\\s\'\"]*")) && (newName.length()>=2))
			this.name = newName;
		else throw new ModelException(newName);
	}
	
	/**
	 * Returns the current position of this Unit
	 */
	@Basic @Raw
	public double[] getPosition(){
		return this.position;
	}
	
	/**
	 * Sets the position of this Unit to the given position
	 * @param	newposition
	 * 			The position the Unit is to be put on
	 * @post
	 * 			The Unit is placed on the given position.
	 * 			| new.position == newposition
	 * @throws ModelException
	 * 			The given position is not a valid position
	 * 			| ! isValidPosition
	 */
	public void setPosition(double[] newposition) throws ModelException{
		if (!isValidPosition(newposition))
			throw new ModelException();
		this.position = newposition;
		this.fallingTo = this.getZPosition();
	}
	
	/**
	/**
	 * Checks if the position is a valid position in the game world. Each 
	 * coordinate has a minimum value and a maximum value. 
	 * @param	position
	 * 			The position we wish to check.
	 * @return	true if and only if every coordinate of position ranges from 0 to 50.
	 * 			| result == ((position[0]>=minXPos) && (position[0]<maxXPos) && 
				| (position[1]>=minYPos) && (position[1]<maxYPos) && 
				| (position[2]>=minZPos) && (position[2]<maxZPos))
	 */
	public boolean isValidPosition(double[] pos){
		
		return ((pos[0]>=minXPos) && (pos[0]<maxXPos) && 
				(pos[1]>=minYPos) && (pos[1]<maxYPos) && 
				(pos[2]>=minZPos) && (pos[2]<maxZPos));
	}
	
	/**
	 * Returns the X-component of the current position.
	 */
	@Raw
	public double getXPosition(){
		return this.position[0];
	}
	
	/**
	 * Returns the Y-component of the current position.
	 */
	@Raw
	public double getYPosition(){
		return this.position[1];
	}
	
	/**
	 * Returns the Z-component of the current position.
	 */
	@Raw
	public double getZPosition(){
		return this.position[2];
	}


	/**
	 * Gets the coordinate of the cube the Unit occupies. 
	 * @return
	 */
	@Raw
	public int[] getCubeCoordinate (){
		int[] cubecoordinate = new int[3];
		cubecoordinate[0] = (int)Math.floor(this.getXPosition());
		cubecoordinate[1] = (int)Math.floor(this.getYPosition());
		cubecoordinate[2] = (int)Math.floor(this.getZPosition());
		return cubecoordinate;
	}
	
	
	/**
	 * Returns the weight of this Unit. 
	 */
	@Basic @Raw
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
	 * 			| if (minValue<= newValue  <= maxValue)
	 * 			| then (this.weight == newValue)
	 * 			| else if (newValue < minValue)
	 * 			| then (this.weight == minValue)
	 *			| else if (newValue > maxValue)
	 *			| then (this.weight == maxValue)
	 * @param	newValue
	 * 			The chosen value to set as weight
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
	 * Returns the strength of this Unit. 
	 */
	@Basic @Raw
	public int getStrength(){
		return this.strength;
	}
	
	/**
	 * Sets the strength of this Unit to the given strength
	 * 
	 * @post	If the given strength exceeds the maximum strength, the strength is 
	 * 			set to the maximum strength. If the given strength is less than the 
	 * 			minimum strength, the strength is set to the minimum strength. If the 
	 * 			strength is in between minimum and maximum value, the strength is set 
	 * 			to the given strength.
	 * 			| if (minValue<= newValue  <= maxValue)
	 * 			| then (this.strength == newValue)
	 * 			| else if (newValue < minValue)
	 * 			| then (this.strength == minValue)
	 *			| else if (newValue > maxValue)
	 *			| then (this.strength == maxValue)
	 * @param	newValue
	 * 			The chosen value to set as strength
	 */
	public void setStrength(int newValue){
		if ((newValue >= minValue) && (newValue <= maxValue))
			this.strength = newValue;
		else if (newValue <= minValue)
			this.strength = minValue;
		else if (newValue >= maxValue)
			this.strength = maxValue;
		setWeight(this.weight);
	}
	
	/**
	 * Returns the current agility of this unit
	 */
	@Basic @Raw
	public int getAgility(){
		return this.agility;
	}
	
	/**
	 * Sets the agility of this Unit to the given value
	 * 
	 * @post	If the given value exceeds the maximum agility, the strength is 
	 * 			set to the maximum agility. If the given value is less than the 
	 * 			minimum agility, the agility is set to the minimum agility. If the 
	 * 			value is in between minimum and maximum value, the agility is set 
	 * 			to the given value.
	 * 			| if (minValue<= newValue  <= maxValue)
	 * 			| then (this.weight == newValue)
	 * 			| else if (newValue < minValue)
	 * 			| then (this.weight == minValue)
	 *			| else if (newValue > maxValue)
	 *			| then (this.weight == maxValue)
	 * @param	newValue
	 * 			The chosen value to set as agility
	 */
	public void setAgility(int newValue){
		if ((newValue >= minValue) && (newValue <= maxValue))
			this.agility = newValue;
		else if (newValue <= minValue)
			this.agility = minValue;
		else if (newValue >= maxValue)
			this.agility = maxValue;
		setWeight(this.weight);
	}
	/**
	 * Returns the current toughness of this unit
	 */
	@Basic @Raw
	public int getToughness(){
		return this.toughness;
	}

	/**
	 * Sets the toughness of this Unit to the given value
	 * 
	 * @post	If the given value exceeds the maximum toughness, the toughness is 
	 * 			set to the maximum toughness. If the given value is less than the 
	 * 			minimum toughness, the toughness is set to the minimum toughness. If the 
	 * 			value is in between minimum and maximum value, the toughness is set 
	 * 			to the given value.
	 * 			| if (minValue<= newValue  <= maxValue)
	 * 			| then (this.toughness == newValue)
	 * 			| else if (newValue < minValue)
	 * 			| then (this.toughness == minValue)
	 *			| else if (newValue > maxValue)
	 *			| then (this.toughness == maxValue)
	 * @param	newValue
	 * 			The chosen value to set as toughness
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
	 * Returns the maximal amount of hitpoints of this unit
	 */
	@Basic
	public int getMaxHitPoints(){
		return (int) (0.02 * this.getWeight() * this.getToughness());
	}
	/**
	 * Sets the hitpoints of the unit to the given value.
	 * @param newValue
	 * 			The value we wish to set the hitpoints of the unit on.
	 */
	public void setHitpoints(double newValue){
		if (isValidHP((int)newValue))
			this.hitpoints = newValue;
	}
	/**
	 * Returns the current ammount of hitpoints this Unit has. 
	 * @return
	 */
	public int getCurrentHitPoints(){
		return (int) this.hitpoints;
	}
	/**
	 * Checks if the given hp is not negative and below the maxHP limit. 
	 * @param hp
	 * @return
	 */
	public boolean isValidHP( int hp){
		return (hp<= this.getMaxHitPoints()) && (hp>=0);
	}
	/**
	 * Returns the maximum amount of stamina points this Unit can have.
	 * @return
	 */
	public int getMaxStaminaPoints(){
		return (int)( 0.02* this.getWeight() * this.getToughness());
	}
	/**
	 * ...
	 * @param value
	 */
	public void setStamina(double value){
		this.stamina = value;
	}
	/**
	 * Checks if the given stamina value is not negative and below the max stamina limit.
	 * @param value
	 * @return
	 */
	public boolean isValidStamina(int value){
		return ((this.stamina>=0) && (this.stamina <= this.getMaxStaminaPoints()));
	}
	/**
	 * Returns the current amount of stamina points this Unit has. 
	 * @return
	 */
	public int getCurrentStaminaPoint(){
		return (int) this.stamina;
	}
	/**
	 * Sets the orientation of this Unit to the given amount
	 * @param orientation
	 * 
	 * @post	The orientation of this Unit will be between 0 radians 
	 * 			and 2*PI radians. If the value exceeds this value it will
	 * 			be refactored. 
	 */
	public void setOrientation(double orientation){
		this.orientation = ((2*Math.PI)+(orientation%(2*Math.PI)))%(2*Math.PI);
	}
	/**
	 * returns the current value of the orientation as a floating point number.
	 */
	public double getOrientation(){
		return this.orientation;
	}
	
	/**
	 * Adapts the Unit's current position, hit points and stamina depending
	 * on the activity the Unit is currently executing. 
	 * @param dt
	 */
	public void advanceTime(double dt) throws ModelException{
		//Initialiseren lokale variabelen voor rustmomenten en regeneratie van hitpoints en stamina.
		this.lifetime += dt;
		if (this.getCurrentHitPoints() <= 0){
			this.die();
		}
		double c = (int)(this.lifetime/180); 
		double d = (int)((this.lifetime - dt)/180);
		if (c != d){
			this.rest(); //Om de drie minuten zal de unit automatisch gaan rusten.
		}
		
		//MOGELIJKE ACTIES
		// Falling

		if (this.fallingTo == this.getZPosition()){

			if (! this.occupiesCube().isValidCube()){

				this.fallingTo = this.getZPosition() -1;
			}
		}
		else {
			System.out.println("fallingto = " + this.fallingTo);
			System.out.println("getZpos = " + this.getZPosition());
			if (this.fallingTo - this.getZPosition() >= dt*this.fallingSpeed){
				//this.setPosition(new double[]{this.getXPosition(), this.getYPosition(), this.fallingTo});
				this.position[2] = this.fallingTo;
			}
				
			else 
				//this.setPosition(new double[]{this.getXPosition(), this.getYPosition(), 
				//		this.getZPosition() + dt*this.fallingSpeed});

				this.position[2] += dt*this.fallingSpeed;
		}
		
		
		//Resting
		if (this.isResting()){
			boolean a = false;
			boolean b = false;
			if (this.getRegenHptime() <= dt){
				this.setHitpoints(this.getMaxHitPoints());
				a = true;
			}
			else if (this.getRegenHptime()>= dt)
				setHitpoints(this.hitpoints +(this.toughness * dt)/(200*0.2));
			
			else if (this.getRegenStaminatime() <= dt){
				this.setStamina(this.getMaxStaminaPoints());
				b = true;
			}
			else 
				this.setStamina(this.stamina + (this.toughness * dt)/(100*0.2));
			
			if (a&&b)
				this.isresting = false;
		}
			
		//Movement
		else if (this.adjacant != null){
			
			if (this.distance < this.getCurrentSpeed()*dt){
				this.setPosition(adjacant);
				this.adjacant = null;
				this.currentspeed = 0;
				
				// Completed movement step => +1 exp
				this.experiencePoints += 1;
			}
			else {
				double[] newposition = new double[3];
				newposition[0] = this.getXPosition() + dt * this.xspeed;
				newposition[1] = this.getYPosition() + dt * this.yspeed;
				newposition[2] = this.getZPosition() + dt * this.zspeed;
				this.distance -= this.getCurrentSpeed()*dt;
				setPosition(newposition);
				if (isSprinting())
					if (this.stamina<=0)
						stopSprinting();
					else if (this.stamina > 0)
							this.stamina -= dt/0.1;
			}
		}
		else if ((this.goal != null) && (this.goal.getCubeCenter() != this.position)){
			int[] target = new int[3];
			for (int i = 0; i<target.length; i++)
				target[i] = (int) goal.getPosition()[i];
			this.moveTo(target);
		}
		//Work
		else if (this.isWorking()){
			if (this.worktime < dt){
				this.worktime = 0;
				for (WorkTypes i : WorkTypes.values()){
					if (i.check(this, workAtCube)){
						i.execute(this, workAtCube);
						break;
					}
				}
			}
			else
				this.worktime = this.worktime - dt;
		}
		//Attack
		else if (isattacking)
			this.attacktime = this.attacktime - dt;
			
		
		//Defense
		else if (isdefending)
			this.defendtime -= dt;
		
	}
	
	
	/**
	 * Sets the current speed depending on the target the Unit is heading. 
	 * 
	 * @param dx
	 * @param dy
	 * @param dz
	 * 
	 * @post	if the Unit moves upwards it moves at half base speed. 
	 * 
	 * @post	if the Unit moves downwards it moves at increased speed. 
	 * 
	 * @post 	if the Unit moves on a flat level, it moves at base speed. 
	 * @throws ModelException 
	 */
	
	public void setCurrentspeed(double[] start, double[] end){
		double dz = end[2]-start[2];
		double vb = getvb();
		if (start==end)
			this.currentspeed = 0;
		else
			if (dz ==-1)
				this.currentspeed = 0.5*vb;
			else if (dz==1)
				this.currentspeed = 1.2*vb;
			else
				this.currentspeed = vb;	
	}
	
	/**
	 * Moves the Unit to an adjacant cube center. 
	 * @param current
	 * @param target
	 * @post 	The Unit's target position is equal to the current position + the 
	 * 			given values for dx, dy and dz. 
	 * 			|| new.postition == [xposition + dx, yposition + dy, zposition + dz]
	 * @post 	The Unit's speed is set towards the target position. 
	 * 	
	 * @post 	The distance the Unit has to walk is set. 
	 * 
	 * @throws ModelException 
	 */
	public void moveToAdjacant(int dx, int dy, int dz) throws ModelException{
		// Eerst definieren we de positie waar we naartoe zullen bewegen.
//		double[] newposition = new double[3];
//		newposition[0]= Math.floor(this.getXPosition() + dx) + 0.5;
//		newposition[1]= Math.floor(this.getYPosition() + dy) + 0.5;
//		newposition[2]= Math.floor(this.getZPosition() + dz) + 0.5;
		
		Cube newCube = this.occupiesCube().getWorld().getCubeAtPos(this.occupiesCube().getXPosition() + dx, 
				this.occupiesCube().getYPosition() + dy, this.occupiesCube().getZPosition()+dz);
		
		if (! newCube.isValidCube())
			throw new ModelException();

		this.adjacant = newCube.getCubeCenter();
				
		
		// Goede positie -> berekenen verplaatsingssnelheid en -vector berekenen;
		double xdistance =(this.adjacant[0]-this.getXPosition());
		double ydistance =(this.adjacant[1]-this.getYPosition());
		double zdistance = (this.adjacant[2]-this.getZPosition());
		
		//We maken alle parameters klaar voor de verplaatsing naar een andere cube.
		this.distance = Math.sqrt(Math.pow((this.adjacant[0]-this.getXPosition()), 2) 
				+ Math.pow((this.adjacant[1]-this.getYPosition()), 2) 
				+ Math.pow((this.adjacant[2]-this.getZPosition()), 2));
		setCurrentspeed(this.position, this.adjacant);
		if (isSprinting())
			this.currentspeed = 2* this.currentspeed;
		setSpeedVector(xdistance, ydistance, zdistance, this.distance);
	}

	/**
	 * Sets the speed vector depending on the current speed and the target
	 * direction. 
	 * @param xdistance
	 * @param ydistance
	 * @param zdistance
	 * @param totaldistance
	 * 
	 * @post 	The Unit's orientation is set so it faces the target position.
	 * @post 	The Unit's speed is directed towards the target position. 
	 */
	public void setSpeedVector(double xdistance, double ydistance, double zdistance, double totaldistance){
		this.xspeed = this.currentspeed * xdistance / totaldistance;
		this.yspeed = this.currentspeed * ydistance / totaldistance;
		this.zspeed = this.currentspeed * zdistance / totaldistance;
		setOrientation(Math.atan2(this.yspeed, this.xspeed));
	}
	/**
	 * Calculates the base speed value for the Unit's current weight, 
	 * strength and agility. 
	 * @return
	 */
	private double getvb(){
		return 1.5* (this.agility+ this.strength) / (2.0*this.weight);
	}
	/**
	 * Returns the current speed of the unit. 
	 * @return
	 */
	public double getCurrentSpeed(){
		return this.currentspeed;
	}
	/**
	 * Checks whether the Unit is currently moving. 
	 * @return
	 */
	public boolean isMoving(){
		return (this.currentspeed != 0);
	}
	/**
	 * ...
	 * @return
	 */
	public boolean isSprinting(){
		return this.issprinting;
	}
	/**
	 * ... 
	 */
	public void startSprinting(){
		this.issprinting = true;
	}
	/**
	 * ...
	 */
	public void stopSprinting(){
		this.issprinting = false;
	}

	/**
	 * Sets a target where the Unit will move to. Initiates the movement
	 * with a first moveToAdjacant. 
	 * @param cube
	 * 
	 * @post	The Unit's goal is set to the targetcube's location. 
	 * @post 	The Unit starts moving towards the adjacant cube in the
	 * 			target's direction. 
	 */
	public void moveTo(int[] targetcube) throws ModelException{
		this.worktime = 0;
		
		this.goal = this.getFaction().getWorld().getCubeAtPos(targetcube[0], targetcube[1], targetcube[2]);
		Data goalData = new Data(this.goal, 0);
		
		if (this.occupiesCube() != this.goal){
			this.Q.add(goalData);
			this.CubeQueue.add(goalData.getCube());
			
			while((!this.CubeQueue.contains(this.occupiesCube()))&&(this.it.hasNext())){
				// zoek path
				Data next = (Data) it.next();
				this.search(next);
			}
			if (this.CubeQueue.contains(this.occupiesCube())){
				// path available
				while (this.CubeQueue.peek()!= null){
					if (this.occupiesCube().getSurroundingCubes().contains(this.CubeQueue.peek())){
						Cube next = this.CubeQueue.poll();
						int dx = next.getXPosition() - this.occupiesCube().getXPosition();
						int dy = next.getYPosition() - this.occupiesCube().getYPosition();
						int dz = next.getZPosition() - this.occupiesCube().getZPosition();
						moveToAdjacant(dx, dy, dz);
					}
				}
			}
			else // no path available
				this.goal = null;
		}
	}
	
	
	/**
	 * Checks whether the chosen neighbor of the startcube is the lowest cost of all neighbors.
	 * @param start
	 * @param CostofChosen
	 * @param possibilities
	 * @return
	 */
	public boolean IsCheapestOfNeighbors(Cube start, int CostofChosen, ArrayList<Data> possibilities){
		for (Data data: possibilities){
			if ((start.getSurroundingCubes().contains(data.getCube())) &&(data.getCost() < CostofChosen)){
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Method that searches the route that needs to be taken to reach the goal data.
	 * @param data The Data of the Unit's destination.
	 * @post
	 */
	public void search(Data data){
		for (Cube cube : data.getCube().getSurroundingCubes()){
			Data dataCube = new Data(cube, data.getCost() + 1);
			if ((cube.isValidCube())&&(!this.Q.contains(dataCube)))
				this.Q.add(dataCube);
				this.CubeQueue.add(dataCube.getCube());
				this.CostQueue.add(dataCube.getCost());
				this.CubeListQ.add(dataCube.getCube());
		}
	}

	
	/**
	 * The Unit starts working for a fixed time depending on its strength. 
	 * The current moveTo is interrupted. 
	 */
	public void work(){
		this.worktime = 500.0/this.strength;
		this.goal = null;
	}
	
	public boolean isValidWorkingCube(Cube cube){
		if (this.occupiesCube() == cube)
			return true;
		for (Cube i : cube.getSurroundingCubes())
			if (this.occupiesCube() == i)
				return true;
		return false;
	}
	
	public void workAt(int x, int y, int z) throws ModelException{
		this.work();
		Cube targetcube = this.getFaction().getWorld().getCubeAtPos(x, y, z);
		if (!isValidWorkingCube(targetcube))
			throw new ModelException("Target cube not in range!");
		
		this.workAtCube = targetcube;
	}
	
	/**
	 * ...
	 * @return
	 */
	public boolean isWorking(){		
		return (this.worktime != 0);
	}
	
	public boolean isCarryingBoulder(){
		return !(this.isCarryingBoulder == null);
	}
	
	public boolean isCarryingLog(){
		return !(this.isCarryingLog == null);
	}
	
	public void pickUpLog(Log log){
		this.isCarryingLog = log;
		log.isCarriedBy = this;
		this.weight += log.getWeight();
	}
	
	public void pickUpBoulder(Boulder boulder){
		this.isCarryingBoulder = boulder;
		boulder.isCarriedBy = this;
		this.weight += boulder.getWeight();
	}
	
	public void putDownLog(Cube cube){
		this.isCarryingLog.isCarriedBy = null;
		this.isCarryingLog.setPosition(new double[] {cube.getXPosition(), 
				cube.getYPosition(), cube.getZPosition()});
		this.setWeight(this.weight - this.isCarryingLog.getWeight());
		this.isCarryingLog = null;
	}
	
	public void putDownBoulder(Cube cube){
		this.isCarryingBoulder.isCarriedBy = null;
		this.isCarryingBoulder.setPosition(new double[] {cube.getXPosition(), 
				cube.getYPosition(), cube.getZPosition()});
		this.setWeight(this.weight - this.isCarryingBoulder.getWeight());
		this.isCarryingBoulder = null;
	}
	
	/**
	 * The Unit attacks another Unit if it is in range.
	 * @param other
	 * @throws ModelException
	 */
	public void fight(Unit other) throws ModelException{
		if (isAttackable(other))
			attack(other);
	}
	/**
	 * Checks whether target Unit is in range to attack. Units must be on the 
	 * same plane and within one cube of eachother. 
	 * @param other
	 * @return
	 */
	private boolean isAttackable(Unit other){
		if ((Math.abs(this.position[0] - other.position[0])<=1) && (Math.abs(this.position[1]- other.position[1]) <=1) && (this.faction!=other.faction))
			if (this.position[2] == other.position[2])
				return true;
		return false;
	}
	/**
	 * Attacks the nearby unit. 
	 */
	public void attack(Unit other) throws ModelException{
		this.goal = null;
		other.goal = null;
		this.attacktime = 1;
		other.defendtime = 1;
		this.isattacking = true;
		other.isdefending = true;
		this.setOrientation(Math.atan2(other.getYPosition()-this.getYPosition(), 
				other.getXPosition()-this.getXPosition()));
		other.setOrientation(Math.atan2(this.getYPosition()-other.getYPosition(), 
				this.getXPosition()-other.getXPosition()));
		other.defend(this);
	}
	/**
	 * ...
	 * @return
	 */
	public boolean isAttacking(){
		return this.attacktime > 0;
	}
	/**
	 * Unit defends itself against an attacking Unit. The Unit will either
	 * dodge, block or take the damage. 
	 * @param other
	 * @throws ModelException 
	 * 
	 */
	public void defend(Unit other)throws ModelException{
		this.goal = null;
		double Pdodge = 0.20*(this.agility)/(other.agility);
		double random = Math.random();
		double Pblock = 0.25*(this.strength + this.agility)/(other.strength + other.agility);
		//DODGE
		if (random<= Pdodge)
			runAwayFrom(other.getPosition(), other);
		//DAMAGE
		else if (random >= Pblock)
			setHitpoints(getCurrentHitPoints() - other.strength /10);
		//BLOCK: gebeurt niets, dus niet nodig te vermelden!
	}
	/**
	 * Unit runs away from another unit.
	 * @param position
	 * @param other
	 * @throws ModelException
	 */
	public void runAwayFrom(double[] position, Unit other) throws ModelException{
		double[] newpos = new double[3];
		int x;
		int y = new Random().nextInt(2);
		while ((!isValidPosition(newpos)) && (this.position !=position) && (newpos!= other.position));
			x = new Random().nextInt(2);
			y = new Random().nextInt(2);
			newpos[0] = this.getXPosition() + x;
			newpos[1] = this.getYPosition() + y;
			newpos[2] = this.getZPosition();
		setPosition(newpos);
	}
	
	
	/**
	 * Unit will rest until maxHP and maxStamina are achieved. 
	 */
	public void rest(){
		this.goal = null;
		this.isresting = true;
	}
	/**
	 * Returns whether the Unit is resting or not.
	 * @return
	 */
	public boolean isResting(){
		return this.isresting;
	}
	/**
	 * Calculates the time needed to regain to full HP. 
	 * @return
	 */
	public double getRegenHptime(){
		int dHP = this.getMaxHitPoints() - this.getCurrentHitPoints();
		double regenPerSecond = this.toughness / 200.0 * 5;
		return dHP/regenPerSecond;
	}
	/**
	 * ...
	 * @return
	 */
	public double getRegenStaminatime(){
		int dSP = this.getMaxStaminaPoints() - this.getCurrentStaminaPoint();
		double regenPerSecond = this.toughness / 100.0 * 5;
		return dSP/regenPerSecond;
	}
	/**
	 * Enables or disables the default behavior. 
	 * @param value
	 */
	public void setDefaultBehaviorEnabled(boolean value){
		this.defaultBehaviorEnabled = value;
	}
	/**
	 * ...
	 * @return
	 */
	public boolean isDefaultBehaviorEnabled(){
		return this.defaultBehaviorEnabled;
	}
	
	/**
	 * 
	 */
	public void die(){
		if (this.isCarryingBoulder()){
			this.putDownBoulder(this.occupiesCube());
		}
		if (this.isCarryingLog()){
			this.putDownLog(this.occupiesCube());
		}
		
		this.getFaction().removeUnit(this);
		this.isAlive = false;
	}
	
	public String name;
	private int weight;
	private int agility;	
	private int strength;
	private int toughness;
	private static int minValue = 0;
	private static int maxValue = 200;
	private double hitpoints;
	private double stamina;
	private double[] position;
	private double orientation;
	private double distance;
	private double currentspeed;
	private static int minXPos = 0;
	private static int maxXPos = 50;
	private static int minYPos = 0;
	private static int maxYPos = 50;
	private static int minZPos = 0;
	private static int maxZPos = 50;
	private static int minStartVal = 25;
	private static int maxStartVal = 100;
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
	private Queue<Data> Q = new PriorityQueue<Data>();
	private Queue<Cube> CubeQueue;
	private Queue<Integer> CostQueue;
	private ArrayList<Cube> CubeListQ;
	private Iterator<Data> it = Q.iterator();	
	private Cube goal;
	private double[] adjacant;
	private double lifetime;
	private Faction faction;
	private int experiencePoints;
	private boolean defaultBehaviorEnabled;
	private double fallingTo;
	private int fallingSpeed = -3;
	private Cube workAtCube;
	private Boulder isCarryingBoulder = null;
	private Log isCarryingLog = null;
	public boolean isAlive;
}
