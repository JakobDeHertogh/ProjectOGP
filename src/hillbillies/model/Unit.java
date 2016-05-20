package hillbillies.model;

import java.util.*;

import org.junit.Test.None;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;
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
 * 
 * @author Kristof Van Cappellen
 * @author Jakob De Herthogh
 *
 */
public class Unit {
	/**
	 * 
	 * @param name
	 * 			The name for this new Unit.
	 * @param initialPosition
	 * 			The position of the Cube this new Unit will start on.
	 * @param weight
	 * 			The weight for this new Unit.
	 * @param agility
	 * 			The agility for this new Unit.
	 * @param strenght
	 * 			The strength for this new Unit.
	 * @param toughness
	 * 			The toughness for this new Unit.
	 * @param enableDefaultBehavior
	 * 			Whether default behavior is enabled for this Unit (true) or not (false).
	 * @post	The amount of experience points of this new Unit is set to 0.
	 * 			|new.experiencePoints == 0
	 * @post	The lifetime of this new Unit is set to 0.
	 * 			|new.lifetime == 0
	 * @post	The new Unit is alive.
	 * 			|new.isAlive == true
	 * @post	The amount of hit points of the new Unit is set to the maximum amount.
	 * 			|new.hitpoints == new.getMaxHitPoints()
	 * @post	The amount of stamina points of the new Unit is set to the maximum amount.
	 * 			|new.stamina == new.getMaxStaminaPoints()
	 * @effect	The name of this new Unit is set to the given name.
	 * 			|setName(name)
	 * @effect	The strength of this new Unit is set to a correct starting value.
	 * 			|setStrength(validStarVal(strength))
	 * @effect	The toughness of this new Unit is set to a correct starting value.
	 * 			|setToughness(validStarVal(toughness))
	 * @effect	The weight of this new Unit is set to a correct starting value.
	 * 			|setWeight(validStarVal(weight))
	 * @effect	The agility of this new Unit is set to a correct starting value.
	 * 			|setAgility(validStarVal(agility))
	 * @effect	The position of this new Unit is set to the center of the given starting Cube.
	 * 			|setPosition([initialPosition[0]+0.5, initialPosition[1]+0.5, initialPosition[2]]
	 * @effect	The default behavior is set to the given value.
	 * 			|setDefaultBehaviorEnabled(enableDefaultBehavior)
	 * @effect	The orientation of the new Unit is to PI/2.
	 * 			|new.setOrientation(Math.PI/2)
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
		this.experiencePoints = 0;
		this.fallingTo = this.getZPosition();
	}
	
	// FACTIONS
	/**
	 * Returns the faction of this Unit.
	 */
	public Faction getFaction(){
		return this.faction;
	}
	// Zou enkel via addUnit uit faction mogen opgeroepen worden. 
	/**
	 * Sets the faction of this Unit.
	 * @post	If the Unit did not have a faction, then the faction will be set to the given faction.
	 * 			|if (this.faction==null)
	 * 			|then new.faction == faction
	 * 			|and new.world == faction.getworld()
	 */
	public void setFaction(Faction faction){
		if (this.faction == null){// of isTerminated()
			this.faction = faction;
			this.world = faction.getWorld();
		}
	}
	
	/**
	 * Return the world this Unit is situated in.
	 */
	public World getWorld(){
		return this.world;
	}
	
	// EXPERIENCE
	/**
	 * Return the number of Experience Points this Unit has.
	 */
	public int getExpPoints(){
		return this.experiencePoints;
	}
	
	/**
	 * The Unit gains a given amount of experience points.
	 * @param dExp	The amount of experience to be added to this Unit.
	 * @post	The Unit's number of experience points is increased with the given amount.
	 * 			|new.experiencePoints == this.getExpPoints()+dExp
	 */
	public void gainExperience(int dExp){
		this.experiencePoints += dExp;
	}
	/**
	 * Levels up a random trait of the Unit if a sufficient number of Experience Points have been collected.
	 * @effect Depending on a randomly generated double, this Unit's toughness, agility or strength will be leveled up.
	 * 			|if (P<0.33333): setToughness(this.toughness + 1)
	 * 			|else if (P<0.66666): setToughness(this.agility + 1)
	 * 			|else: setStrength(this.strength + 1)
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
	 * @return Returns the cube which the Unit occupies.
	 */
	public Cube occupiesCube(){			
		return this.world.getCubeAtPos(this.getCubeCoordinate()[0], this.getCubeCoordinate()[1], this.getCubeCoordinate()[2]);
	}
	
	/**
	 * Returns a value from minStartVal to maxStartVal. In this
	 * case minStartVal equals 25 and maxStartVal equals 100. 
	 * @param val	The value which is meant to be used for a certain attribute of the Unit.
	 * @return	If the input value is between minStarval and maxStartVal, the given value is returned.
	 * 			| if (minStartval<val<maxStarval)
	 * 			| then result == val
	 * @return	If the input value exceeds the given range between minStartval and maxStarval, minStartval is returned.
	 * 			| if (val < minStartval) or (val > maxStartval)
	 * 			| then result == minStartVal
	 */
 	@Immutable @Raw
	public int validStartVal(int val){
		if ((val <= maxStartVal) && (val >= minStartVal))
			return val;
		else return minStartVal;
	}
	

	/**
	 * Returns the name of this Unit. 
	 */
	@Basic
	public String getName(){
		return this.name;
	}
	/**
	 * Sets the name of this unit to the given Name
	 * 
	 * @param newName
	 * 			The name we wish to give the Unit
	 * @post	The name of the Unit is changed to the given newName
	 * 			| new.name == newName
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
		if (this.world != null){
			try{
				Cube cube = this.world.getCubeAtPos((int)pos[0], (int)pos[1], (int)pos[2]);
				if (!cube.isValidCube())
					return false;
			} catch (IndexOutOfBoundsException ex){
				return false;
			}
			
		}
		return true;
	}
	
	/**
	 * Returns the X-component of the current position.
	 */
	@Basic @Raw
	public double getXPosition(){
		return this.position[0];
	}
	
	/**
	 * Returns the Y-component of the current position.
	 */
	@Basic @Raw
	public double getYPosition(){
		return this.position[1];
	}
	
	/**
	 * Returns the Z-component of the current position.
	 */
	@Basic @Raw
	public double getZPosition(){
		return this.position[2];
	}


	/**
	 * Gets the coordinate of the cube the Unit occupies.
	 */
	@Raw @Basic
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
	 * 			| then (new.weight == newValue)
	 * 			| else if (newValue < minValue)
	 * 			| then (new.weight == minValue)
	 *			| else if (newValue > maxValue)
	 *			| then (new.weight == maxValue)
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
	 * 			| then (new.strength == newValue)
	 * 			| else if (newValue < minValue)
	 * 			| then (new.strength == minValue)
	 *			| else if (newValue > maxValue)
	 *			| then (new.strength == maxValue)
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
	 * 			| then (new.weight == newValue)
	 * 			| else if (newValue < minValue)
	 * 			| then (new.weight == minValue)
	 *			| else if (newValue > maxValue)
	 *			| then (new.weight == maxValue)
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
	 * 			| then (new.toughness == newValue)
	 * 			| else if (newValue < minValue)
	 * 			| then (new.toughness == minValue)
	 *			| else if (newValue > maxValue)
	 *			| then (new.toughness == maxValue)
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
	 * Sets the hit points of the unit to the given value.
	 * @param newValue
	 * 			The value we wish to set the hit points of the unit on.
	 * @pre	The new value is a valid number of hit points.
	 * 		|isValidHP(newValue);
	 */
	public void setHitpoints(double newValue){
		assert(isValidHP((int) newValue));
			this.hitpoints = newValue;
	}
	/**
	 * Returns the current amount of hit points this Unit has. 
	 */
	@Basic
	public int getCurrentHitPoints(){
		return (int) this.hitpoints;
	}
	/**
	 * Checks if the given amount of hit points is valid. 
	 * @param hp The value for the Unit's hit points that needs to be checked.
	 * @return	true if and only if the given value is smaller than or equal to 
	 * 			the maximum amount of hit points for this Unit and is greater than or equal to 0.
	 * 			else return false.
	 * 			|result == (hp<= this.getMaxHitPoints()) && (hp>=0)
	 */
	public boolean isValidHP( int hp){
		return (hp<= this.getMaxHitPoints()) && (hp>=0);
	}
	/**
	 * Returns the maximum amount of stamina points this Unit can have.
	 * @return
	 */
	@Basic
	public int getMaxStaminaPoints(){
		return (int)( 0.02* this.getWeight() * this.getToughness());
	}
	/**
	 * Sets the stamina of this Unit to the given value.
	 * @param value
	 */
	public void setStamina(double value){
		assert(isValidStamina((int)value));
		this.stamina = value;
	}
	/**
	 * Checks if the given stamina value is not negative and below the max stamina limit.
	 * @param value
	 * @return true if and only if the given value is greater than or equal to 0,
	 * 			and is less than or equal to the maximum amount of stamina for this Unit.
	 * 			|result == ((this.stamina>=0) && (this.stamina <= this.getMaxStaminaPoints()))
	 */
	public boolean isValidStamina(int value){
		return ((this.stamina>=0) && (this.stamina <= this.getMaxStaminaPoints()));
	}
	/**
	 * Returns the current amount of stamina points this Unit has. 
	 */
	@Basic
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
	 * Returns the current value of the orientation as a floating point number.
	 */
	@Basic
	public double getOrientation(){
		return this.orientation;
	}
	
	/**
	 * Adapts the Unit's current position, hit points and stamina depending
	 * on the activity the Unit is currently executing. 
	 * @param dt	The amount of game time to be advanced for this Unit.
	 * 
	 * @post	The lifetime of this Unit is increased with dt.
	 * 			|new.lifetime == this.lifetime +dt
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
		int currentExp = this.getExpPoints();
		//MOGELIJKE ACTIES
		// Falling

		if (this.fallingTo == this.getZPosition()){

			if (! this.occupiesCube().isValidCube()){

				this.fallingTo = this.getZPosition() -1;
			}
		}
		else {
			if (this.fallingTo - this.getZPosition() >= dt*this.fallingSpeed){
				this.position[2] = this.fallingTo;
			}
				
			else 
				this.position[2] += dt*this.fallingSpeed;
		}
		
		// execute task for same duration as advanceTime
		if (this.getTask() != null){
			this.getTask().execute(dt);
		}
		
		// check default action
		if (this.currentActivity == null){
			if (this.isDefaultBehaviorEnabled()){
				if (this.getTask() == null){
					try{
						this.assignTask(this.getFaction().getScheduler().retrieveTask());
					} catch (NullPointerException ex){
					}
				}
				
				else{
					//Select random activity
					Random x = new Random();
					List<Activity> allAct = Arrays.asList(Activity.values());				
					Activity randomAct = allAct.get(x.nextInt(allAct.size()));
					randomAct.defaultAction(this);
				}
			}
		}
		else{
		switch(currentActivity){
		case REST: 
			boolean a = false;
			boolean b = false;
			if (this.getRegenHptime() <= dt){
				this.setHitpoints(this.getMaxHitPoints());
				a = true;
			}
			else if (this.getRegenHptime()>= dt)
				setHitpoints(this.hitpoints +(this.toughness * dt)/(200*0.2));
			
			if (this.getRegenStaminatime() <= dt){
				this.setStamina(this.getMaxStaminaPoints());
				b = true;
			}
			else if (a== true)
				this.setStamina(this.stamina + (this.toughness * dt)/(100*0.2));
			
			if (a&&b)
				this.currentActivity = null;
			break;
		
		case MOVE:
			if (this.adjacant != null){
				
				if (this.distance < this.getCurrentSpeed()*dt){
					this.setPosition(adjacant);
					this.adjacant = null;
					this.currentspeed = 0;
					
					// Completed movement step => +1 exp
					this.gainExperience(1);
					
					if (this.occupiesCube() == this.goal){
						this.goal = null;
					}
					if (this.goal == null && this.adjacant == null)
						this.currentActivity = null;
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
			break;
		
		case WORK: 
			if (this.worktime < dt){
				this.worktime = 0;
				for (WorkTypes i : WorkTypes.values()){
					if (i.check(this, workAtCube)){
						i.execute(this, workAtCube);
						this.gainExperience(20);
						break;
					}
				}
				this.currentActivity = null;
			}
			else
				
				this.worktime = this.worktime - dt;
			break;
			
		case FIGHT: 
			if (this.isAttacking())
				this.attacktime -= dt;
			else if (this.isdefending)
				this.defendtime -= dt;
			break;
		}
		}
		
		//Update Experience
		int updatedExp = this.getExpPoints();
		while (updatedExp/10 != currentExp/10){
			this.levelUp();
			currentExp += 10;
		}
		
	}
	
	
	/**
	 * Sets the current speed depending on the target the Unit is heading. 
	 * 
	 * @param start	The starting position of the movement.
	 * @param end	The ending position of the movement.
	 * @post	If the start equals the end, the speed will be zero.
	 * 			|if start == end
	 * 			|then new.currentspeed == 0
	 * @post	If the Unit moves upwards it moves at half base speed.
	 * 			|if end[2] - start[2] == 1
	 * 			|then new.currentspeed = 0.5*getvb()
	 * 
	 * @post	If the Unit moves downwards it moves faster with a factor of 1.2 . 
	 * 			|if end[2] - start[2] == -1
	 * 			|then new.currentspeed == 1.2*getvb()
	 * 
	 * @post 	if the Unit moves on a flat level, it moves at base speed. 
	 * 			|if end[2]-start[2] == 0
	 * 			|then new.currentspeed == getvb()
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
	 * @param dx	The distance this Unit must move along the x-axis.
	 * @param dy	The distance this Unit must move along the y-axis.
	 * @param dz	The distance this Unit must move along the z-axis.
	 * @post 	The Unit's target position is equal to the current position + the 
	 * 			given values for dx, dy and dz. 
	 * 			| new.postition == [this.xposition + dx, this.yposition + dy, this.zposition + dz]
	 * @post	If the Unit is sprinting, the speed will be doubled.
	 * 			|new.currenstpeed == 2*this.currentpeed
	 * @post	The Unit's current activity is set to moving.
	 * 			|new.currentActivity = Activity.MOVE
	 * @post 	The distance the Unit has to walk is set.
	 * 			|new.distance == Math.sqrt(Math.pow((this.adjacant[0]-this.getXPosition()), 2) 
				+ Math.pow((this.adjacant[1]-this.getYPosition()), 2) 
				+ Math.pow((this.adjacant[2]-this.getZPosition()), 2))
	 * @effect 	The Unit's speed is set towards the target position.
	 * 			|setCurrentSpeed(this.position, this.adjacant)
	 * @effect	The Unit's speed vector is set.
	 * 			|setSpeedVector(xdistance, ydistance, zdistance, this.dstance)
	 * @throws ModelException if the target lies in a Cube which is not valid.
	 * 			| !((this.world.getCubeAtPos(this.occupiesCube().getXPosition() + dx, 
	 *				this.occupiesCube().getYPosition() + dy, this.occupiesCube().getZPosition()+dz)).isValidCube)
	 */
	public void moveToAdjacant(int dx, int dy, int dz) throws ModelException{

		try{
			Cube newCube = this.world.getCubeAtPos(this.occupiesCube().getXPosition() + dx, 
					this.occupiesCube().getYPosition() + dy, this.occupiesCube().getZPosition()+dz);
			
			if (! newCube.isValidCube())
				throw new ModelException("Invalid target cube");
			this.adjacant = newCube.getCubeCenter();
			
			this.currentActivity = Activity.MOVE;
			
		} catch (IndexOutOfBoundsException ex){
			throw new ModelException("You can't move outside the world");
		}		
				
		
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
	 * @param xdistance	The distance this Unit must move along the x-axis. 
	 * @param ydistance	The distance this Unit must move along the y-axis.
	 * @param zdistance	The distance this Unit must move along the z-axis.
	 * @param totaldistance	The total distance this Unit must move.
	 * 
	 * @post	The speed of the unit along the x-axis is set to its currentspeed times the xdistance divided by the total distance.
	 * 			|new.xspeed == this.currentspeed * xdistance / totaldistance;
	 * @post	The speed of the unit along the y-axis is set to its currentspeed times the ydistance divided by the total distance.
	 * 			|new.yspeed == this.currentspeed * ydistance / totaldistance;
	 * @post	The speed of the unit along the z-axis is set to its currentspeed times the zdistance divided by the total distance.
	 * 			|new.zspeed == this.currentspeed * zdistance / totaldistance;
	 * 
	 * @effect 	The Unit's orientation is set so it faces the target position.
	 * 			|setOrientation(Math.atan2(this.yspeed, this.xspeed)
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
	 */
	@Basic
	private double getvb(){
		return 1.5* (this.agility+ this.strength) / (2.0*this.weight);
	}
	/**
	 * Returns the current speed of the unit. 
	 */
	@Basic
	public double getCurrentSpeed(){
		return this.currentspeed;
	}
	/**
	 * Checks whether the Unit is currently moving. 
	 * @return true if and only if the Unit's current activity is moving.
	 * 			|result == (this.currenActivity == Activity.MOVE)
	 */
	public boolean isMoving(){
		return this.currentActivity == Activity.MOVE;
		//return (this.currentspeed != 0);
	}
	/**
	 * Returns whether the Unit is sprinting.
	 * @return true if and only if the Unit is sprinting.
	 * 			|result == (this.issprinting ==true)
	 */
	public boolean isSprinting(){
		return this.issprinting;
	}
	/**
	 * The Unit starts sprinting.
	 * @post 	The Unit is sprinting.
	 * 			|new.issprinting == true;
	 */
	public void startSprinting(){
		this.issprinting = true;
	}
	/**
	 * The Unit stops sprinting.
	 * @post 	The Unit is not sprinting.
	 * 			|new.issprinting == false;
	 */
	public void stopSprinting(){
		this.issprinting = false;
	}

	/**
	 * Sets a target where the Unit will move to. Initiates the movement
	 * with a first moveToAdjacant. 
	 * @param targetcube	The position the Unit will move to.
	 * @post	The time this Unit had to work is set to 0.
	 * 			|new.worktime ==0
	 * @post	The Unit's goal is set to the targetcube's location. 
	 * 			|new.goal = this.world.getCubeAtPost(targetcube[0], targetcube[1], targetcube[2])
	 * @effect 	The Unit starts moving towards the adjacant cube in the
	 * 			target's direction.
	 * 			|moveToAdjacant(dx, dy, dz)
	 */
	public void moveTo(int[] targetcube) throws ModelException{
		this.worktime = 0;
		
		try{
			this.goal = this.world.getCubeAtPos(targetcube[0], targetcube[1], targetcube[2]);
		} catch (IndexOutOfBoundsException ex){
			throw new ModelException("Given position out of bounds");
		}
		Cube start = this.occupiesCube();
		Path path = new Path(start, goal);
		try{
			Cube next = path.getRoute().pop();
			int dx = next.getXPosition() - start.getXPosition();
			int dy = next.getYPosition() - start.getYPosition();
			int dz = next.getZPosition() - start.getZPosition();
			this.moveToAdjacant(dx, dy, dz);
		} catch (EmptyStackException ex){
			throw new ModelException("No path available");
		}
	}
	
	
	/**
	 * The Unit starts working for a fixed time depending on its strength. 
	 * The current moveTo is interrupted.
	 * @post	The time the Unit still has to work is set to 500 divided by the Unit's strength.
	 * 			|new.worktime = 500.0/this.getStrength();
	 * @post	The goal where the Unit is moving to is null.
	 * 			|new.goal == null
	 * @post	The current activity of this Unit is working.
	 * 			|new.currentActivity == Activity.WORK
	 */
	public void work(){
		this.worktime = 500.0/this.getStrength();
		this.goal = null;
		this.currentActivity = Activity.WORK;
	}
	
	/**
	 * Returns whether the given Cube is a valid Cube to work on.
	 * @param cube
	 * 		  The Cube the Unit must work on.
	 * @return true if and only if the target Cube is the Cube which is occupied by the Unit or that Cube's surrounding Cubes.
	 * 		   false if the above conditions are not met.
	 * 			|if (this.occupiesCube() == cube)
	 * 			|reslt == true
	 * 			|for (Cube i : cube.getSurroundingCubes())
	 *			|	if (this.occupiesCube() == i)
	 *			|		result == true
	 *			|else result == false
	 * 
	 */
	public boolean isValidWorkingCube(Cube cube){
		if (this.occupiesCube() == cube)
			return true;
		for (Cube i : cube.getSurroundingCubes())
			if (this.occupiesCube() == i)
				return true;
		return false;
	}
	/**
	 * Makes the Unit work on the Cube at the given x, y and z coordinates.
	 * @param x	The x-coordinate of the Cube where the Unit will work on.
	 * @param y	The y-coordinate of the Cube where the Unit will work on.
	 * @param z	The z-coordinate of the Cube where the Unit will work on.
	 * @throws ModelException if the target Cube is not a valid Cube to work at.
	 * 			|!isValidWorkingCube(targetcube)
	 * @post	The workAtCube is set to the cube at the target position.
	 * 			|new.workAtCube == targetcube
	 * @effect	This Unit's orientation will be adjusted.
	 * 			|setOrientation(newOrientation)
	 * @effect	This Unit will work.
	 * 			|work()
	 */
	public void workAt(int x, int y, int z) throws ModelException{
		Cube targetcube = this.getFaction().getWorld().getCubeAtPos(x, y, z);
		if (!isValidWorkingCube(targetcube))
			throw new ModelException("Target cube not in range!");	
		this.workAtCube = targetcube;
		double newOrientation = Math.atan2(targetcube.getCubeCenter()[1]-this.getYPosition(), 
				targetcube.getCubeCenter()[0]-this.getXPosition());
		this.setOrientation(newOrientation);
		this.work();
	}
	
	/**
	 * Returns whether the Unit is working.
	 * @return true if and only this Unit's curernt activity is working.
	 * 			|result == (this.currentActivity == Activity.WORK)
	 */
	public boolean isWorking(){		
		return this.currentActivity == Activity.WORK;
		//return (this.worktime != 0);
	}
	
	/**
	 * Returns whether this Unit is carrying a Boulder.
	 * @return true if and only if this Unit is carrying a Boulder.
	 * 			|result == !(this.CarriesBoulder == null)
	 */
	public boolean isCarryingBoulder(){
		return !(this.CarriesBoulder == null);
	}
	
	/**
	 * Returns whether this Unit is carrying a Log.
	 * @return	true if and only if this Unit is carrying a Log.
	 * 			|result == !(this.CarriesLog == null)
	 */
	public boolean isCarryingLog(){
		return !(this.CarriesLog == null);
	}
	
	/**
	 * Makes the Unit pick up a Log.
	 * @param log	The Log this Unit must pick up.
	 * @post	This Unit is carrying the target Log.
	 * 			|new.CarriesLog = log
	 * @post	The target Log is carried by this Unit.
	 * 			|log.isCarriedBy = this
	 * @post	The weight of this Unit is increased by the weight of the Log.
	 * 			|new.weight == this.getWeight + log.getWeight()
	 * @effect	The Log is removed from its Cube.
	 * 			|removeLog(log)
	 * @effect	The Log is removed from the World.
	 * 			|this.getWorld.removeLog
	 */
	public void pickUpLog(Log log){
		this.CarriesLog = log;
		log.isCarriedBy = this;
		this.weight += log.getWeight();
		Cube cube = this.getWorld().getCubeAtPos((int)Math.floor(log.getPosition()[0]),
				(int)Math.floor(log.getPosition()[1]),(int) Math.floor(log.getPosition()[2]));
		cube.removeLog(log);
		this.getWorld().removeLog(log);
	}
	
	/**
	 * Makes the Unit pick up a Boulder.
	 * @param boulder The Boulder the Unit must pick up.
	 * @post	This Unit is carrying the target Boulder.
	 * 			|new.CarriesBoulder = boulder
	 * @post	The target Log is carried by this Unit.
	 * 			|boulder.isCarriedBy = this
	 * @post	The weight of this Unit is increased by the weight of the Boulder.
	 * 			|new.weight == this.getWeight + boulder.getWeight()
	 * @effect	The Boulder is removed from its Cube.
	 * 			|removeLog(boulder)
	 * @effect	The Boulder is removed from the World.
	 * 			|this.getWorld.removeBoulder
	 */
	public void pickUpBoulder(Boulder boulder){
		this.CarriesBoulder = boulder;
		boulder.isCarriedBy = this;
		this.weight += boulder.getWeight();
		Cube cube = this.getWorld().getCubeAtPos((int)Math.floor(boulder.getPosition()[0]), 
				(int)Math.floor(boulder.getPosition()[1]),(int) Math.floor(boulder.getPosition()[2]));
		cube.removeBoulder(boulder);
		this.getWorld().removeBoulder(boulder);
	}
	
	/**
	 * Makes the Unit put down a Log at the given Cube.
	 * @param cube	The Cube this must put the Log on.
	 * @post	The Log is no longer being carried by this Unit.
	 * 			|this.CarriesLog.isCarriedBy = null
	 * @post	The position of the Log is set to the center of the target Cube.
	 * 			|this.CarriesLog.setPosition(new double[] {cube.getXPosition()+0.5, cube.getYPosition()+0.5, cube.getZPosition()+0.5});
	 * @post	The Log will be added to the 
	 * @post	The Unit is no longer carrying a Log.
	 * 			|new.CarriesLog == null
	 * @effect 	The weight of the Unit is adjusted for the loss of the Log.
	 * 			|setWeight(this.weight - this.isCarryingLog.getWeight())
	 */
	public void putDownLog(Cube cube){
		this.CarriesLog.isCarriedBy = null;
		this.CarriesLog.setPosition(new double[] {cube.getXPosition()+0.5, 
				cube.getYPosition()+0.5, cube.getZPosition()+0.5});
		cube.addLog(this.CarriesLog);
		this.getWorld().addLog(this.CarriesLog);
		this.setWeight(this.weight - this.CarriesLog.getWeight());
		this.CarriesLog = null;
	}
	
	/**
	 * Makes the Unit put down a Boulder at a given Cube.
	 * @param cube
	 * @effect	The weight of the Unit is adjusted for the loss of the Boulder.
	 * 			|setWeight(this.weight - this.isCarryingBoulder.getWeight())
	 */
	public void putDownBoulder(Cube cube){
		this.CarriesBoulder.isCarriedBy = null;
		this.CarriesBoulder.setPosition(new double[] {cube.getXPosition()+0.5, 
				cube.getYPosition()+0.5, cube.getZPosition()+0.5});
		this.getWorld().addBoulder(this.CarriesBoulder);
		cube.addBoulder(this.CarriesBoulder);
		this.setWeight(this.weight - this.CarriesBoulder.getWeight());
		this.CarriesBoulder = null;
	}
	
	/**
	 * The Unit attacks another Unit if it is in range.
	 * @param other	The Unit this Unit must fight.
	 * @throws ModelException
	 * 			The target Unit is not attackable.
	 * 			|!isAttackable(Unit other)
	 * @post	The current activity of this Unit is set to FIGHT.
	 * 			|new.currentActivity = Activity.FIGHT
	 * 
	 */
	public void fight(Unit other) throws ModelException{
		if (!isAttackable(other)){
			throw new ModelException("not a valid target");
		}
		attack(other);
		this.currentActivity = Activity.FIGHT;
			
	}
	/**
	 * Checks whether target Unit is in range to attack. Units must be on the 
	 * same plane and within one cube of eachother. 
	 * @param other
	 * @return true if and only if this unit and its target are on the same z-level,
	 * 			are neighbouring each other, and belong to different factions.
	 * 			|result == ((Math.abs(this.position[0] - other.position[0])<=1) && 
	 * 				(Math.abs(this.position[1]- other.position[1]) <=1) && (this.faction!=other.faction))
	 */
	public boolean isAttackable(Unit other){
		if ((Math.abs(this.position[0] - other.position[0])<=1) && (Math.abs(this.position[1]- other.position[1]) <=1) && (this.faction!=other.faction))
			if (this.position[2] == other.position[2])
				return true;
		return false;
	}
	/**
	 * Attacks a nearby Unit.
	 * @param other	The Unit this Unit will attack.
	 * @post	This Unit's goal is set to null, its attacktime to 1, he is attacking, and its orientation is adjusted to the target.
	 * 			|new.goal == null
	 * 			|new.attacktime == 1
	 * 			|new.isattacking == true
	 * @post	The other Unit's goal is set to null, its defendtime to 1, he is defending, and its orientation is adjusted to this Unit.
	 * 			|new.goal == null
	 * 			|new.defendtime == 1
	 * 			|new.isdefending == true
	 * @effect 	This Unit's orientation is adjusted to the target Unit, and the target Unit's orientation is adjusted to this Unit.
	 * 			|setOrientation(Math.atan2(other.getYPosition()-this.getYPosition(), other.getXPosition()-this.getXPosition()))
	 * 			|other.setOrientation(Math.atan2(this.getYPosition()-other.getYPosition(), this.getXPosition()-other.getXPosition()))
	 * @effect	The other Unit defends.
	 * 			|other.defend(this)
	 * @throws ModelException if the defending Unit tries to run away to an invalid position.
	 * 
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
	 * Checks whether this Unit is currently attacking.
	 * @return true if and only if this Unit's current activity is fighting.
	 * 			|result == (this.currentActivity == Activity.FIGHT)
	 */
	public boolean isAttacking(){
		return this.currentActivity == Activity.FIGHT;
//		return this.attacktime > 0;
	}
	/**
	 * Unit defends itself against an attacking Unit. The Unit will either
	 * dodge, block or take the damage. 
	 * @param other	The Unit that is attacking this Unit.
	 * @effect If this Unit can dodge the attack, this Unit will run away from his attacking Unit.
	 * 			|if random <= Pdodge:
	 * 			|	runAwayFrom(other.getPosition(), other)
	 * @effect If this Unit cannot block the attack, its hit points will be decreased by the other Unit's strength, divided by 10.
	 * 			|if random >= Pblock:
	 * 			|	setHitpoints(getCurrentHitPoints() - other.strength /10)
	 * @throws ModelException if the Unit tries to run away to an invalid position.
	 * 			|!isValidPosition(runawayposition)
	 * 
	 */
	public void defend(Unit other)throws ModelException{
		this.goal = null;
		double Pdodge = 0.20*(this.agility)/(other.agility);
		double random = Math.random();
		double Pblock = Pdodge + 0.25*(this.strength + this.agility)/(other.strength + other.agility);
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
	 * @param position The position this Unit is standing on.
	 * @param other	The Unit this Unit has to run away from.
	 * @effect	This Unit moves to a neighbouring Cube on the same z level.
	 * 			|setPosition(newposition)
	 * @throws ModelException if the position this Unit runs to is not valid.
	 * 			|!isValidPosition(newpos)
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
	 * @post 	The goal which the Unit could be moving to is set to null.
	 * 			|new.goal == null
	 * @post	The current activity of this Unit is set to resting.
	 * 			|new.currenActivity == Activity.REST
	 */
	public void rest(){
		this.goal = null;
		this.currentActivity = Activity.REST;
	}
	/**
	 * Returns whether the Unit is resting or not.
	 * @return true if and only if the Unit's current activity is resting.
	 * 			|result == this.currentActivity == Activity.REST
	 */
	public boolean isResting(){
		return this.currentActivity == Activity.REST;
	}
	/**
	 * Returns the time the Unit needs to fully recover its hit points.
	 * @return	The time this Unit needs to recover all its hit points
	 * 			|result == (this.getMaxHitPoints() - this.getCurrentHitPoints())/(this.toughness / 200.0 * 5)
	 */
	public double getRegenHptime(){
		int dHP = this.getMaxHitPoints() - this.getCurrentHitPoints();
		double regenPerSecond = this.toughness / 200.0 * 5;
		return dHP/regenPerSecond;
	}
	/**
	 * Returns the time the Unit needs to fully recover its stamina points.
	 * @return The time this Unit needs to recover all its stamina.
	 * 			|result == (this.getMaxStaminaPoints() - this.getCurrentStaminaPoint())/(this.toughness / 100.0 * 5)
	 */
	public double getRegenStaminatime(){
		int dSP = this.getMaxStaminaPoints() - this.getCurrentStaminaPoint();
		double regenPerSecond = this.toughness / 100.0 * 5;
		return dSP/regenPerSecond;
	}
	/**
	 * Enables or disables the default behavior. 
	 * @param value The value we wish to set.
	 * @post	defaultBehaviorEnabled is set to the value.
	 * 			|new.defaultBehaviorEnabled == value
	 */
	public void setDefaultBehaviorEnabled(boolean value){
		this.defaultBehaviorEnabled = value;
	}
	
	/**
	 * Checks whether default behavior is enabled for the Unit.
	 */
	@Basic
	public boolean isDefaultBehaviorEnabled(){
		return this.defaultBehaviorEnabled;
	}
	
	/**
	 * Returns the current activity of this Unit.
	 */
	@Basic
	public Activity getActivity(){
		return this.currentActivity;
	}
	
	/**
	 * The Unit dies, and is removed from the game world.
	 * @post	This Unit is no longer alive
	 * 			|new.isAlive == false
	 * @effect	If this Unit was carrying a Boulder, it will put it down on the Cube it's standing on.
	 * 			|if (this.isCarryingBoulder())
	 * 			|putDownBoulder(this.occupiesCube())
	 * @effect	If this Unit was carrying a Log, it will put it down on the Cube it's standing on.
	 * 			|if (this.isCarryingLog())
	 * 			|putDownLog(this.occupiesCube())
	 * @effect	This Unit will be removed from its Faction.
	 * 			|getFaction.removeUnit(this)
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
	
	/**
	 * Returns the Log that is closest to this Unit.
	 * @return The nearest Log in this World. All other Logs are further away. The distance is measured in the	
	 * 			amount of Cubes this Unit has to pass to reach the Log.
	 * 			|foreach log in getWorld().getLogs():
	 * 			|	distance >= minDistance
	 */
	public Log getNearestLog(){
		Log nearestLog = null;
		double minDistance = Double.MAX_VALUE;
		for (Log i : this.getWorld().getLogs()){
			Path path = new Path(this.occupiesCube(), i.occupiesCube());
			if (!path.getRoute().isEmpty()){
				int distance = path.countStepsinRoute();
				if ((distance < minDistance)){
					nearestLog = i;
					minDistance = distance;
				}
			}
		}
		return nearestLog;
	}
	
	/**
	 * Returns the Boulder that is closest to this Unit.
	 * @return The nearest Boulder in this World. All other Boulders are further away. The distance is measured in the	
	 * 			amount of Cubes this Unit has to pass to reach the Boulder.
	 * 			|foreach boulder in getWorld().getBoulders():
	 * 			|	distance >= minDistance
	 */
	public Boulder getNearestBoulder(){
		Boulder nearestBoulder = null;
		double minDistance = Double.MAX_VALUE;
		for (Boulder i : this.getWorld().getBoulders()){
			Path path = new Path(this.occupiesCube(), i.occupiesCube());
			if (!path.getRoute().isEmpty()){
				int distance = path.countStepsinRoute();
				if ((distance < minDistance)){
					nearestBoulder = i;
					minDistance = distance;
				}
			}
		}
		return nearestBoulder;
	}
	
	/**
	 * Return the Workshop Cube that is closest to this Unit.
	 * @return The nearest Workshop Cube in this World. All other Workshop Cubes are further away. The distance is measured in the	
	 * 			amount of Cubes this Unit has to pass to reach the Workshop Cube.
	 * 			|foreach workshop in getWorld().getWorkshops():
	 * 			|	distance >= minDistance
	 */
	public Cube getNearestWorkshop(){
		Cube nearestWorkshop = null;
		double minDistance = Double.MAX_VALUE;
		for (Cube i : this.getWorld().getWorkshops()){
			Path path = new Path(this.occupiesCube(), i);
			if (!path.getRoute().isEmpty()){
				int distance = path.countStepsinRoute();
				if ((distance < minDistance)){
					nearestWorkshop = i;
					minDistance = distance;
				}
			}
		}
		return nearestWorkshop;
	}
	
	
	/**
	 * Returns the nearest friend of this Unit.
	 * @return The nearest Unit from the same Faction. All other faction members are further away. The distance is measured in the	
	 * 			amount of Cubes this Unit has to pass to reach his friend.
	 * 			|foreach Unit in getFaction().getMembers():
	 * 			|	distance >= minDistance
	 */
	public Unit getNearestFriend(){
		Unit nearestFriend = null;
		double minDistance = Double.MAX_VALUE;
		for (Unit i : this.getFaction().getMembers()){
			Path path = new Path(this.occupiesCube(), i.occupiesCube());
			if (!path.getRoute().isEmpty()){
				int distance = path.countStepsinRoute();
				if ((distance < minDistance) && (i.getFaction() == this.getFaction())){
					nearestFriend = i;
					minDistance = distance;
				}
			}
		}
		return nearestFriend;
	}
	
	/**
	 * Returns the nearest Enemy of this Unit.
	 * @return The nearest Unit from a different Faction. All other Units of a different Faction are further away. 
	 * 			The distance is measured in the	amount of cubes this Unit has to pass to reach his enemy.
	 * 			|foreach unit in getWorld().getUnit():
	 * 			|	if unit.getFaction != this.getFaction:
	 * 			|		distance >= minDistance
	 */
	public Unit getNearestEnemy(){
		Unit nearestEnemy = null;
		double minDistance = Double.MAX_VALUE;
		for (Unit i : this.getFaction().getMembers()){
			Path path = new Path(this.occupiesCube(), i.occupiesCube());
			if (!path.getRoute().isEmpty()){
				int distance = path.countStepsinRoute();
				if ((distance < minDistance) && (i.getFaction() != this.getFaction())){
					nearestEnemy = i;
					minDistance = distance;
				}
			}
		}
		return nearestEnemy;
	}
	
	/**
	 * Checks whether a given Unit is a friend of this Unit.
	 * @param other	The Unit whom is to be checked if he is a friend of this Unit.
	 * @return true if and only if this Unit and the other Unit are part of the same Faction.
	 * 			|result == (this.getFaction() == other.getFaction())
	 */
	public boolean isFriend(Unit other){
		return this.getFaction() == other.getFaction();
	}
	
	/**
	 * Returns the current Task of the Unit.
	 */
	@Basic
	public Task getTask(){
		return this.currentTask;
	}
	
	/**
	 * Assigns a task to this Unit.
	 * @param task	The Task which is to be assigned to this Unit.
	 * @post	The Unit's current Task is set to the given Task.
	 * 			|new.currentTask == task
	 */
	void assignTask(Task task){
		this.currentTask = task;
		if (task !=  null){
			task.assignTo(this);
		}
	}
	
	/**
	 * Checks whether this Unit is next to the given Unit.
	 * @param other	The Unit that is to be checked if it's next to this Unit
	 * @return	true if and only if the other Unit occupies one of the surrounding Cubes of this Unit.
	 * 			|result == other.occupiesCube().getSurroundingCubes().contains(this.occupiesCube())
	 */
	public boolean isNextTo(Unit other){
		return other.occupiesCube().getSurroundingCubes().contains(this.occupiesCube());
	}
	
	/**
	 * Variable registering the name of this Unit.
	 */
	public String name;
	
	/**
	 * Variable registering the weight of this Unit.
	 */
	private int weight;
	
	/**
	 * Variable registering the agility of this Unit.
	 */
	private int agility;	
	
	/**
	 * Variable registering the strength of this Unit.
	 */
	private int strength;
	
	/**
	 * Variable registering the toughness of this Unit.
	 */
	private int toughness;
	
	/**
	 * Variable registering the amount of experiencePoints this Unit currently has.
	 */
	private int experiencePoints;
	
	/**
	 * Variable registering the hitpoints of this Unit.
	 */
	private double hitpoints;
	
	/**
	 * Variable registering the stamina points of this Unit.
	 */
	private double stamina;
	
	/**
	 * Variable registering the position of this Unit.
	 */
	private double[] position;
	
	/**
	 * Variable registering the orientation of this Unit.
	 */
	private double orientation;
	
	/**
	 * Variable registering the distance a Unit needs to walk to its goal Cube.
	 */
	private double distance;
	
	/**
	 * Variable registering the current speed of this Unit.
	 */
	private double currentspeed;
	
	/**
	 * Variable registering the x-component of this Unit's current speed.
	 */
	private double xspeed;
	
	/**
	 * Variable registering the y-component of this Unit's current speed.
	 */
	private double yspeed;
	
	/**
	 * Variable registering the z-component of this Unit's current speed.
	 */
	private double zspeed;
	
	/**
	 * Variable registering the time this Unit needs to complete a Work task.
	 */
	private double worktime;
	
	/**
	 * Variable registering the time this Unit needs to complete an attack phase.
	 */
	private double attacktime;
	
	/**
	 * Variable registering the time this Unit needs to complete a defense phase.
	 */
	private double defendtime;
	
	/**
	 * Variable registering the position this Unint is falling to.
	 */
	private double fallingTo;
	
	/**
	 * The center of a Cube which is adjacant to the Cube the Unit currently occupies.
	 */
	private double[] adjacant;
	
	/**
	 * Variable registering the lifetime of this Unit. The lifetime is the amount of time this Unit
	 * has been alive.
	 */
	private double lifetime;
	
	/**
	 * Constant registering the minimum value of an trait (strength, agility, toughness, weight) 
	 * of this Unit.
	 */
	private static int minValue = 0;
	
	/**
	 * Constant registering the maximum value of an trait (strength, agility, toughness, weight) 
	 * of this Unit.
	 */
	private static int maxValue = 200;
	
	/**
	 * Constant registering the minimum starting value of an trait (strength, agility, toughness, weight) 
	 * of this Unit.
	 */
	private static int minStartVal = 25;
	
	/**
	 * Constant registering the maximum starting value of an trait (strength, agility, toughness, weight) 
	 * of this Unit.
	 */
	private static int maxStartVal = 100;
	
	/**
	 * Constant registering the falling speed of this Unit (z-axis).
	 */
	private static int fallingSpeed = -3;
	
	/**
	 * Variable registering whether a Unit is sprinting.
	 */
	private boolean issprinting;
	
	/**
	 * Variable registering whether a Unit is resting.
	 */
	private boolean isresting;
	
	/**
	 * Variable registering whether a Unit is defending.
	 */
	private boolean isdefending;
	
	/**
	 * Variable registering whether a Unit is attacking.
	 */
	private boolean isattacking;
	
	/**
	 * Variable registering whether default behavior is enabled for this Unit.
	 */
	private boolean defaultBehaviorEnabled;
	
	/**
	 * Variable registering whether a Unit is alive.
	 */
	public boolean isAlive;
	
	/**
	 * Variable registering the goal (Cube) where this Unit is moving to.
	 */
	private Cube goal;
	
	/**
	 * Variable registering the faction this Unit belongs to.
	 */
	private Faction faction = null;
	
	/**
	 * Variable registering the world this Unit is situated in.
	 */
	private World world = null; 
	
	/**
	 * Variable registering the Cube this Unit is working at.
	 */
	private Cube workAtCube;
	
	/**
	 * Variable registering the Boulder this Unit is carrying.
	 */
	private Boulder CarriesBoulder = null;
	
	/**
	 * Variable registering the Log this Unit is carrying.
	 */
	private Log CarriesLog = null;
	
	/**
	 * Variable registering the current activity of this Unit.
	 */
	private Activity currentActivity;
	
	/**
	 * Variable registering the Task which this Unit is currently executing.
	 */
	private Task currentTask;
}
