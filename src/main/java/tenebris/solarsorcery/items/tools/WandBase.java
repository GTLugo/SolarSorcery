package tenebris.solarsorcery.items.tools;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tenebris.solarsorcery.Reference;
import tenebris.solarsorcery.cap.mana.IMana;
import tenebris.solarsorcery.cap.mana.ManaProvider;
import tenebris.solarsorcery.cap.wandtype.IWandType;
import tenebris.solarsorcery.cap.wandtype.WandTypeProvider;
import tenebris.solarsorcery.handlers.SolarsorcPacketHandler;
import tenebris.solarsorcery.helpers.ItemNBTHelper;
import tenebris.solarsorcery.items.ItemBase;
import tenebris.solarsorcery.items.materials.IWandBase;
import tenebris.solarsorcery.packets.ManaChangePacket;
import tenebris.solarsorcery.packets.ManaRegenPacket;

public class WandBase extends ItemBase implements IWandBase {
	
	public static String[] _woodTypesTag = { "oak", "birch", "spruce", "jungle", "acacia", "darkoak", "bone" };
	public static String[] _coreTypesTag = { "string", "gunpowder", "feather", "redstone"};
	public static String[] _capTypesTag = { "ironcapped", "goldcapped"};
	
	public static String _woodTypeTag = "wood_type";
	public static String _coreTypeTag = "core_type";
	public static String _capTypeTag = "cap_type";
	public static String _currOwnerTag = "current_owner";
	public static String _userNameTag = "user_name";
	public static String _origOwnerTag = "original_owner";

	public WandBase(String name) {
		super(name);
		this.maxStackSize = 1;
		this.addPropertyOverride(new ResourceLocation("minecraft:pull"), (stack, worldIn, entityIn) -> {
			if (entityIn == null) {
				return 0.0F;
			}
			else {
				ItemStack itemstack = entityIn.getActiveItemStack();
				return !itemstack.isEmpty() && itemstack.getItem() instanceof WandBase ? (stack.getMaxItemUseDuration() - entityIn.getItemInUseCount()) * chargeManaMultiplier() / 20.0F : 0.0F;
			}
		});
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
    	return "item." + ItemNBTHelper.getString(stack, _woodTypeTag, "oak") + "_" + ItemNBTHelper.getString(stack, _coreTypeTag, "string") + "_" + ItemNBTHelper.getString(stack, _capTypeTag, "ironcapped");
     }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("solarsorcmisc." + ItemNBTHelper.getString(stack, _coreTypeTag, "string"));
            tooltip.add("solarsorcmisc." + ItemNBTHelper.getString(stack, _capTypeTag, "ironcapped"));
            tooltip.add("solarsorcmisc." + this.getUserName(stack));
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 36000;
    }
	
	float chargeManaMultiplier() {
		return 1F;
	}
	
	boolean canFire(EntityPlayer player, int cost) {
		IMana mana = player.getCapability(ManaProvider.MANACAP, null);
		return (mana.getCurrMana() + cost) >= 0;
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		// TODO Auto-generated method stubif (stack.hasTagCompound()) 
		this.setCurrOwner(stack, playerIn);
		this.setUserName(stack, playerIn);
		this.setOrigOwner(stack, playerIn);
		super.onCreated(stack, worldIn, playerIn);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!worldIn.isRemote && entityIn instanceof EntityPlayer) {
			super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		}
	}

	@Override
	public String getCurrOwner(ItemStack stack) {
		// TODO Auto-generated method stub
		return ItemNBTHelper.getString(stack, _currOwnerTag, null);
	}
	
	@Override
	public String getUserName(ItemStack stack) {
		// TODO Auto-generated method stub
		return ItemNBTHelper.getString(stack, _userNameTag, "no_one");
	}
	
	@Override
	public String getOrigOwner(ItemStack stack) {
		// TODO Auto-generated method stub
		return ItemNBTHelper.getString(stack, _origOwnerTag, null);
	}

	@Override
	public void setCurrOwner(ItemStack stack, EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		ItemNBTHelper.setString(stack, _currOwnerTag, playerIn.getUniqueID().toString());
		
	}
	
	@Override
	public void setUserName(ItemStack stack, EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		ItemNBTHelper.setString(stack, _userNameTag, playerIn.getName());
	}
	
	@Override
	public void setOrigOwner(ItemStack stack, EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		ItemNBTHelper.setString(stack, _origOwnerTag, playerIn.getUniqueID().toString());
		
	}
	
	@SideOnly(Side.CLIENT)
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) { //ADD WAND TILT EFFECT
		// Select the active spell, then cast it
		ItemStack heldWand = playerIn.getHeldItem(handIn);
		//IMana mana = player.getCapability(ManaProvider.MANACAP, null);
		IWandType wandType = playerIn.getCapability(WandTypeProvider.WANDCAP, null);
			
			float cost = -1.0f;
			String playerUUID = playerIn.getUniqueID().toString();
			if ((wandType.getWood() != ItemNBTHelper.getString(heldWand, _woodTypeTag, "oak") || wandType.getCore() != ItemNBTHelper.getString(heldWand, _coreTypeTag, "string") || wandType.getCap() != ItemNBTHelper.getString(heldWand, _capTypeTag, "ironcapped")) 
					&& !playerUUID.equals(ItemNBTHelper.getString(heldWand, _currOwnerTag, "null"))  && playerUUID.equals(ItemNBTHelper.getString(heldWand, _origOwnerTag, "null"))) {
				cost = -5.0f;
			}
			boolean flag = canFire(playerIn, (int) cost);
			
			SolarsorcPacketHandler.INSTANCE.sendToServer(new ManaRegenPacket(false));
			
			ActionResult<ItemStack> _return = ForgeEventFactory.onArrowNock(heldWand, worldIn, playerIn, handIn, flag);
			if (_return != null) return _return;

			if (!flag) {
				return new ActionResult<>(EnumActionResult.FAIL, heldWand);
			}
			else {
				playerIn.setActiveHand(handIn);

				//if (this.getUserName(heldWand) != "no_one") {
					if (!worldIn.isRemote) {
						SolarsorcPacketHandler.INSTANCE.sendToServer(new ManaChangePacket(cost));
						System.out.println(Reference.MODID + ":casted spell");
						worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.NEUTRAL, 0.75F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1 * 0.5F);
					}
				/*	else {
						if (!worldIn.isRemote) {
						this.setUserName(heldWand, playerIn);
						this.setOrigOwner(heldWand, playerIn);
						this.setCurrOwner(heldWand, playerIn);
						}
						return new ActionResult<>(EnumActionResult.SUCCESS, heldWand);
					}
				}*/
				//String chatMessage = String.format("Player: %d / %d Aether | Wand: %d / %d Aether ", (int) mana.getCurrMana(), (int) mana.getMaxMana(), (int) this.getCurrMana(heldWand), (int) this.getMaxMana(heldWand));
	            
				return new ActionResult<>(EnumActionResult.SUCCESS, heldWand);
			}
	}	
	
	@Override
	public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World world, EntityLivingBase shooter, int useTicks) {
		// TODO Auto-generated method stub
		if (shooter instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) shooter;
			//IMana mana = player.getCapability(ManaProvider.MANACAP, null);
				
			SolarsorcPacketHandler.INSTANCE.sendToServer(new ManaRegenPacket(true));
				
			IWandType wandType = player.getCapability(WandTypeProvider.WANDCAP, null);
			float cost = -1.0f;
			String playerUUID = player.getUniqueID().toString();
			if ((wandType.getWood() != ItemNBTHelper.getString(stack, _woodTypeTag, "oak") || wandType.getCore() != ItemNBTHelper.getString(stack, _coreTypeTag, "string") || wandType.getCap() != ItemNBTHelper.getString(stack, _capTypeTag, "ironcapped")) 
					&& !playerUUID.equals(ItemNBTHelper.getString(stack, _currOwnerTag, "null"))  && playerUUID.equals(ItemNBTHelper.getString(stack, _origOwnerTag, "null"))) {
				cost = -5.0f;
			}
			boolean flag = canFire(player, (int) cost);
			int i = (int) ((getMaxItemUseDuration(stack) - useTicks) * chargeManaMultiplier());
			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, world, player, i, flag);
			if (i < 0) return;
			
			player.addStat(StatList.getObjectUseStats(this));
		}
	}
	
	@SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
        for (int i = 0; i < _woodTypesTag.length; i++) {
        	for (int j = 0; j < _coreTypesTag.length; j++) {
        		for (int k = 0; k < _capTypesTag.length; k++) {
        			// This creates a new ItemStack instance. The item parameter 
        			// supplied is this item.
        			stacks.add(new ItemStack(this));
        			ItemStack wandStack = new ItemStack(this);
        			ItemNBTHelper.setString(wandStack, _woodTypeTag, _woodTypesTag[i]);
        			ItemNBTHelper.setString(wandStack, _coreTypeTag, _coreTypesTag[j]);
        			ItemNBTHelper.setString(wandStack, _capTypeTag, _capTypesTag[k]);
        			//setCurrMana(wandStack, 1);
        			//setMaxMana(wandStack, 1);
        			// And this adds it to the itemList, which is a list of all items
        			// in the creative tab.
        			stacks.add(wandStack);
        		}
        	}        	
        }
    }
	
	@SideOnly(Side.CLIENT)
	public void registerModels() {
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
	}
	
}
