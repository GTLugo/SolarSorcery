package gtlugo.solarsorcery.items.tools;

import java.util.List;

import javax.annotation.Nonnull;

import gtlugo.solarsorcery.Reference;
import gtlugo.solarsorcery.handlers.NetworkHandler;
import gtlugo.solarsorcery.helpers.ItemNBTHelper;
import gtlugo.solarsorcery.items.ItemBase;
import gtlugo.solarsorcery.networking.messages.ManaChangeMessage;
import gtlugo.solarsorcery.networking.messages.ManaRegenMessage;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WandBase extends ItemBase implements IWandBase {
	
	public static String[] _woodTypesTag = { "oak", "birch", "spruce", "jungle", "acacia", "darkoak", "bone" };
	public static String[] _coreTypesTag = { "string", "gunpowder", "feather", "redstone"};
	public static String[] _capTypesTag = { "ironcapped", "goldcapped"};
	
	public static String _woodTypeTag = "wood_type";
	public static String _coreTypeTag = "core_type";
	public static String _capTypeTag = "cap_type";

	public static String _ownerTag = "owner";
	public static String _userNameTag = "user_name";

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
    	return "item." + ItemNBTHelper.getString(stack, _woodTypeTag, _woodTypesTag[0]) + "_" + ItemNBTHelper.getString(stack, _coreTypeTag, _coreTypesTag[0]) + "_" + ItemNBTHelper.getString(stack, _capTypeTag, _capTypesTag[0]);
     }
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
            tooltip.add("solarsorcmisc." + ItemNBTHelper.getString(stack, _coreTypeTag, _coreTypesTag[0]));
            tooltip.add("solarsorcmisc." + ItemNBTHelper.getString(stack, _capTypeTag, _capTypesTag[0]));
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
		IPlayerData data = player.getCapability(PlayerDataProvider.TAG_DATA, null);
		return (data.getCurrMana() + cost) >= 0;
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		// TODO Auto-generated method stubif (stack.hasTagCompound()) 
		this.setOwner(stack, playerIn);
		this.setUserName(stack, playerIn);
		super.onCreated(stack, worldIn, playerIn);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if (!worldIn.isRemote && entityIn instanceof EntityPlayer) {
			super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		}
	}
	
	@Override
	public String getUserName(ItemStack stack) {
		// TODO Auto-generated method stub
		return ItemNBTHelper.getString(stack, _userNameTag, "no_one");
	}

	@Override
	public String getOwner(ItemStack stack) {
		// TODO Auto-generated method stub
		return ItemNBTHelper.getString(stack, _ownerTag, null);
	}
	
	@Override
	public void setUserName(ItemStack stack, EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		ItemNBTHelper.setString(stack, _userNameTag, playerIn.getName());
	}

	@Override
	public void setOwner(ItemStack stack, EntityPlayer playerIn) {
		// TODO Auto-generated method stub
		ItemNBTHelper.setString(stack, _ownerTag, playerIn.getUniqueID().toString());

	}
	
//	@SideOnly(Side.CLIENT)
//	@Nonnull
//	@Override
//	public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) { //ADD WAND TILT EFFECT
//		// Select the active spell, then cast it
//		ItemStack heldWand = playerIn.getHeldItem(handIn);
//		//IMana mana = player.getCapability(ManaProvider.MANACAP, null);
//		IPlayerData data = playerIn.getCapability(PlayerDataProvider.TAG_DATA, null);
//
//			float cost = -1.0f;
//			String playerUUID = playerIn.getUniqueID().toString();
//			if ((data.getWood() != ItemNBTHelper.getString(heldWand, _woodTypeTag, "oak") || data.getCore() != ItemNBTHelper.getString(heldWand, _coreTypeTag, "string") || data.getCap() != ItemNBTHelper.getString(heldWand, _capTypeTag, "ironcapped"))
//					&& !playerUUID.equals(ItemNBTHelper.getString(heldWand, _ownerTag, "null"))  && playerUUID.equals(ItemNBTHelper.getString(heldWand, _ownerTag, "null"))) {
//				cost = -5.0f;
//			}
//			boolean canFireFlag = canFire(playerIn, (int) cost);
//
//			//ActionResult<ItemStack> _return = ForgeEventFactory.onArrowNock(heldWand, worldIn, playerIn, handIn, canFireFlag);
//			//if (_return != null) return _return;
//
//			if (!canFireFlag) {
//				NetworkHandler.INSTANCE.sendToServer(new ManaRegenMessage(true));
//				return new ActionResult<>(EnumActionResult.FAIL, heldWand);
//			}
//			else {
//				playerIn.setActiveHand(handIn);
//				NetworkHandler.INSTANCE.sendToServer(new ManaRegenMessage(false));
//
//				//if (this.getUserName(heldWand) != "no_one") {
//					if (!worldIn.isRemote) {
//						NetworkHandler.INSTANCE.sendToServer(new ManaChangeMessage(cost));
//						System.out.println(Reference.MODID + ":casted spell");
//						worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.NEUTRAL, 0.75F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1 * 0.5F);
//					}
//				/*	else {
//						if (!worldIn.isRemote) {
//						this.setUserName(heldWand, playerIn);
//						this.setOrigOwner(heldWand, playerIn);
//						this.setCurrOwner(heldWand, playerIn);
//						}
//						return new ActionResult<>(EnumActionResult.SUCCESS, heldWand);
//					}
//				}*/
//				//String chatMessage = String.format("Player: %d / %d Aether | Wand: %d / %d Aether ", (int) mana.getCurrMana(), (int) mana.getMaxMana(), (int) this.getCurrMana(heldWand), (int) this.getMaxMana(heldWand));
//
//				return new ActionResult<>(EnumActionResult.SUCCESS, heldWand);
//			}
//	}
//
//	@Override
//	public void onPlayerStoppedUsing(@Nonnull ItemStack stack, @Nonnull World world, EntityLivingBase shooter, int useTicks) {
//		// TODO Auto-generated method stub
//		if (shooter instanceof EntityPlayer) {
//			EntityPlayer player = (EntityPlayer) shooter;
//			//IMana mana = player.getCapability(ManaProvider.MANACAP, null);
//
//			NetworkHandler.INSTANCE.sendToServer(new ManaRegenMessage(true));
//
//			IPlayerData data = player.getCapability(PlayerDataProvider.TAG_DATA, null);
//			float cost = -1.0f;
//			String playerUUID = player.getUniqueID().toString();
//			if ((data.getWood() != ItemNBTHelper.getString(stack, _woodTypeTag, "oak") || data.getCore() != ItemNBTHelper.getString(stack, _coreTypeTag, "string") || data.getCap() != ItemNBTHelper.getString(stack, _capTypeTag, "ironcapped"))
//					|| !playerUUID.equals(ItemNBTHelper.getString(stack, _ownerTag, "null"))) {
//				cost = -5.0f;
//			}
//			boolean flag = canFire(player, (int) cost);
//			int i = (int) ((getMaxItemUseDuration(stack) - useTicks) * chargeManaMultiplier());
//			i = net.minecraftforge.event.ForgeEventFactory.onArrowLoose(stack, world, player, i, flag);
//			if (i < 0) return;
//
//			player.addStat(StatList.getObjectUseStats(this));
//		}
//	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack heldWand = playerIn.getHeldItem(handIn);
		IPlayerData data = playerIn.getCapability(PlayerDataProvider.TAG_DATA, null);

		float cost = -1.0f;
		String playerUUID = playerIn.getUniqueID().toString();
		if (!(data.getWood().equals(ItemNBTHelper.getString(heldWand, _woodTypeTag, _woodTypesTag[0])) || !data.getCore().equals(ItemNBTHelper.getString(heldWand, _coreTypeTag, _coreTypesTag[0])) || !data.getCap().equals(ItemNBTHelper.getString(heldWand, _capTypeTag, _capTypesTag[0])))
				|| !playerUUID.equals(ItemNBTHelper.getString(heldWand, _ownerTag, "null"))) {
			cost = -5.0f;
		}
		boolean canFireFlag = canFire(playerIn, (int) cost);

		if(!worldIn.isRemote && canFireFlag) {
			NetworkHandler.INSTANCE.sendToServer(new ManaRegenMessage(false));
			NetworkHandler.INSTANCE.sendToServer(new ManaChangeMessage(cost));
			System.out.println(Reference.MODID + ":casted spell");
			worldIn.playSound(null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.NEUTRAL, 0.75F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + 1 * 0.5F);

			NetworkHandler.INSTANCE.sendToServer(new ManaRegenMessage(true));
			return ActionResult.newResult(EnumActionResult.SUCCESS, heldWand);
		}
		else {
			NetworkHandler.INSTANCE.sendToServer(new ManaRegenMessage(true));
			return ActionResult.newResult(EnumActionResult.FAIL, heldWand);
		}
	}

	@SideOnly(Side.CLIENT)
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> stacks) {
		for (String wood : _woodTypesTag) {
			for (String core : _coreTypesTag) {
				for (String cap : _capTypesTag) {
					// This creates a new ItemStack instance. The item parameter
					// supplied is this item.
					stacks.add(new ItemStack(this));
					ItemStack wandStack = new ItemStack(this);
					ItemNBTHelper.setString(wandStack, _woodTypeTag, wood);
					ItemNBTHelper.setString(wandStack, _coreTypeTag, core);
					ItemNBTHelper.setString(wandStack, _capTypeTag, cap);
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
