package gtlugo.solarsorcery.items.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IWandBase {

	//public int getCurrMana(ItemStack stack);

	//public int getMaxMana(ItemStack stack);
	
	public String getCurrOwner(ItemStack stack);
	
	public String getUserName(ItemStack stack);
	
	public String getOrigOwner(ItemStack stack);

	//public void setCurrMana(ItemStack stack, int mana);
	
	//public void setMaxMana(ItemStack stack, int mana);

	public void setCurrOwner(ItemStack stack, EntityPlayer playerIn); //store
	
	public void setUserName(ItemStack stack, EntityPlayer playerIn);
	
	public void setOrigOwner(ItemStack stack, EntityPlayer playerIn);

	//public float getManaPercent(ItemStack stack);
}
