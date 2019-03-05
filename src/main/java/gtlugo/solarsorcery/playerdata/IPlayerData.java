package gtlugo.solarsorcery.playerdata;

public interface IPlayerData {

	public void useMana(float points);

	public void giveMana(float points);

	public void changeMana(float points);

	public void setMaxMana(float points);

	public void setCurrMana(float points);

	public float getMaxMana();

	public float getCurrMana();

	public boolean isCanRegen();

	public void setCanRegen(boolean canRegen);
	
	public int getLevel();
	
	public int getExperience();
	
	public void setLevel(int level);
	
	public void addExp(int experience, boolean overrideFlag);
	
	public void setExpToLvl(int experience);
	
	public int getExpToLvl();
	
	public void changeLevel(int level, int expOverflow);

	public String getWood();

	public String getCore();

	public String getCap();

	public void setWood(String wood);

	public void setCore(String core);

	public void setCap(String cap);
	
}
