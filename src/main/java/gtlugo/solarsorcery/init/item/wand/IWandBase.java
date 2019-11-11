package gtlugo.solarsorcery.init.item.wand;

import gtlugo.solarsorcery.init.item.wand.wandparts.WandPart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.UUID;

public interface IWandBase {

    public void setOwnerUUID(ItemStack stack, PlayerEntity playerIn);

    public UUID getOwnerUUID(ItemStack stack);

    public boolean isOwner(ItemStack item, PlayerEntity player);

    public boolean hasOwner(ItemStack item);

    public void setOwnerName(ItemStack item, PlayerEntity playerIn);

    public String getOwnerName(ItemStack item);

    public void setWandWood(ItemStack item, WandPart woodType);

    public WandPart getWandWood(ItemStack item);

    public void setWandCore(ItemStack item, WandPart coreType);

    public WandPart getWandCore(ItemStack item);

    public void setWandDeco(ItemStack item, WandPart decoType);

    public WandPart getWandDeco(ItemStack item);
}
