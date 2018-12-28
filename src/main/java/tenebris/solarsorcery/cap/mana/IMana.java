package tenebris.solarsorcery.cap.mana;

import net.minecraft.entity.player.EntityPlayerMP;

public interface IMana {
	public void use(float pts); 
	public void give(float pts); 
	public void change(float pts);
	public void setMaxMana(float pts);
	public void setCurrMana(float pts);
	public float getMaxMana();
	public float getCurrMana(); 
	public boolean isCanRegen();
	public void setCanRegen(boolean canRegen);
	public void syncMana(EntityPlayerMP player);
}
