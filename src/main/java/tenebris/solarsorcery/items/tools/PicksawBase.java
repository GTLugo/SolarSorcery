package tenebris.solarsorcery.items.tools;

import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import tenebris.solarsorcery.cap.mana.IMana;
import tenebris.solarsorcery.cap.mana.ManaProvider;

public class PicksawBase extends ItemPickaxe{
	public PicksawBase(String name, ToolMaterial material) {
		super(material);
		this.setRegistryName(name);
		this.setUnlocalizedName(name);
	}
	
	private static final Set<Material> EFFECTIVE_ON = Sets.newHashSet(Material.WOOD, Material.PLANTS, Material.VINE, Material.GROUND, Material.SAND, Material.GRASS, Material.SNOW, Material.CRAFTED_SNOW);
	
	@Override
	public Set<String> getToolClasses(ItemStack stack) {
		return ImmutableSet.of("pickaxe", "axe");
	}
	
	@Override
	public boolean canHarvestBlock(IBlockState block) {
		return EFFECTIVE_ON.contains(block.getMaterial()) ? true : super.canHarvestBlock(block);
	}
	
	public float getDestroySpeed(ItemStack stack, IBlockState state) {
		return EFFECTIVE_ON.contains(state.getMaterial()) ? this.efficiency : super.getDestroySpeed(stack, state);
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
