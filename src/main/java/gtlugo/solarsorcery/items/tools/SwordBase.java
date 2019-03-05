package gtlugo.solarsorcery.items.tools;

import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;

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
			IPlayerData data = serverPlayer.getCapability(PlayerDataProvider.TAG_DATA, null);
			this.setMaxDamage((int) data.getMaxMana());
			this.setDamage(stack, (int) (data.getMaxMana() - data.getCurrMana()));
			super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		}
	}
	@Override
	public boolean showDurabilityBar(ItemStack stack) {
		return true;
	}
}
