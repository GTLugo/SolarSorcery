package gtlugo.solarsorcery.playerdata;

import net.minecraft.entity.player.PlayerEntity;

public interface IPlayerData {

	public void changeMana(float points);

	public void setMaxMana(float points);

	public void setCurrMana(float points);

	public void setCanRegen(boolean canRegen);

	public void setCooldown(int time);

	public float getMaxMana();

	public float getCurrMana();

	public boolean isCanRegen();

	public int getCooldown();


	
	public void setLevel(int level);

	public void changeLevel(int level, int expOverflow);
	
	public void addExp(int experience, boolean overrideFlag);
	
	public void setExpToLvl(int experience);

	public int getLevel();

	public int getExperience();

	public int getExpToLvl();



	public void setWood(String wood);

	public void setCore(String core);

	public void setCap(String cap);

	public String getWood();

	public String getCore();

	public String getCap();
}
