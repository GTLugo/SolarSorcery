package tenebris.solarsorcery.items.tools;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import tenebris.solarsorcery.cap.mana.IMana;
import tenebris.solarsorcery.cap.mana.ManaProvider;

public class SwordBase extends ItemSword {

	public SwordBase(String name, ToolMaterial material) {
		super(material);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		// TODO Auto-generated method stub
		if (!worldIn.isRemote) {
			EntityPlayerMP serverPlayer = (EntityPlayerMP) entityIn;
			IMana mana = serverPlayer.getCapability(ManaProvider.MANACAP, null);
			this.setMaxDamage((int) mana.getMaxMana());
			this.setDamage(stack, (int) (mana.getMaxMana() - mana.getCurrMana()));
			super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		}
	}
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}
}
