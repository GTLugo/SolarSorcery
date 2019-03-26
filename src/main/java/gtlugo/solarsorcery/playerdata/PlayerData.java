package gtlugo.solarsorcery.playerdata;

public class PlayerData implements IPlayerData {

	private boolean _canRegen = true;
	private int _regenCooldown = 0;
	private float _maxMana = 10.0f;
	private float _currMana = 10.0f;
	
	private int _level = 1;
	private int _experience = 0;
	private int _expToLvl = 1000;
	
	private String _wood = null;
	private String _core = null;
	private String _cap = null;

	
	/*
	 * MANA SYSTEM
	 */
	
	@Override
	public void useMana(float points) { 
		this._currMana = Math.max(this._currMana + points, 0.0f);
	} 
	
	@Override
	public void giveMana(float points) { 
		this._currMana = Math.min(this._currMana + points, this._maxMana); 
	} 
	
	@Override
	public void changeMana(float points) {
		if (points > 0.0F) giveMana(points);
		else useMana(points);
	}
	
	@Override
	public void setMaxMana(float points) { 
		this._maxMana = points; 
	} 
	
	@Override
	public void setCurrMana(float points) { 
		this._currMana = points; 
	} 
	
	@Override
	public void setCanRegen(boolean canRegen) {
		this._canRegen = canRegen;
	}

	@Override
	public void setRegenCooldown(int regenCooldown) { this._regenCooldown = regenCooldown; }

	@Override
	public float getMaxMana() {
		return this._maxMana; 
	} 
	
	@Override
	public float getCurrMana() { 
		return this._currMana; 
	}
	
	@Override
	public boolean isCanRegen() {
		return this._canRegen;
	}

	@Override
	public int getRegenCooldown() { return this._regenCooldown; }

	/*
	 * LEVELING SYSTEM
	 */

	@Override
	public void setLevel(int level) {
		this._level = level;
	}

	@Override
	public void addExp(int experience, boolean overrideFlag) {
		if (!overrideFlag) this._experience += experience;
		else this._experience = experience;
	}

	@Override
	public void setExpToLvl(int experience) {
		this._expToLvl = experience;
	}

	@Override
	public int getLevel() {
		return this._level;
	}

	@Override
	public int getExperience() {
		return this._experience;
	}

	@Override
	public int getExpToLvl() {
		return this._expToLvl;
	}

	@Override
	public void changeLevel(int level, int expOverflow) {
		this._level = level;
		this._experience = expOverflow;
		
		if (this._level < 50) this._expToLvl *= (40 * (Math.pow(this._level, 2)) + 1000); //function: 40x^2 + 1000
		else this._expToLvl = 100000;
		
		this._maxMana = (10.0f * (float) Math.pow(this._level, 2)); //function: 10x^2
		this._currMana = this._maxMana;
	}
	
	/*
	 * WANDTYPES
	 */

	@Override
	public void setWood(String wood) {
		if (this._wood == null) { //Only apply if the player doesn't already have it set
			this._wood = wood;
		}
	}

	@Override
	public void setCore(String core) {
		if (this._core == null) { //Only apply if the player doesn't already have it set
			this._core = core;
		}
	}

	@Override
	public void setCap(String cap) {
		if (this._cap == null) { //Only apply if the player doesn't already have it set
			this._cap = cap;
		}
	}
	
	@Override
	public String getWood() {
		return this._wood;
	}

	@Override
	public String getCore() {
		return this._core;
	}

	@Override
	public String getCap() {
		return this._cap;
	}
}
