package gtlugo.solarsorcery.items.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IWandBase {

	//public int getCurrMana(ItemStack stack);

	//public int getMaxMana(ItemStack stack);
	
	public String getOwner(ItemStack stack);
	
	public String getUserName(ItemStack stack);

	//public void setCurrMana(ItemStack stack, int mana);
	
	//public void setMaxMana(ItemStack stack, int mana);

	public void setUserName(ItemStack stack, EntityPlayer playerIn);
	
	public void setOwner(ItemStack stack, EntityPlayer playerIn);

	//public float getManaPercent(ItemStack stack);
}
