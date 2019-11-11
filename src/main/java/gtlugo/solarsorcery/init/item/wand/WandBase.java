package gtlugo.solarsorcery.init.item.wand;

import gtlugo.solarsorcery.helpers.ItemNBTHelper;
import gtlugo.solarsorcery.init.item.ItemBase;
import gtlugo.solarsorcery.init.item.wand.wandparts.WandPart;
import gtlugo.solarsorcery.lib.Reference;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import static gtlugo.solarsorcery.init.item.wand.wandparts.WandMaterials.*;

public class WandBase extends ItemBase implements IWandBase {
    /*
    *Store these properly in NBT
    */


    public static final String TAG_WOOD = "wood_type";
    public static final String TAG_CORE = "core_type";
    public static final String TAG_DECO = "deco_type";
    public static final String TAG_OWNERUUID = "owneruuid";
    public static final String TAG_OWNERNAME = "ownername";

    public WandBase(Properties properties) {
        super(properties);

        /*
        for (int i = 0; i < WoodMaterials.size(); ++i) {
            if (WoodMaterials.get(i)._materialName.equals(wood)) {
                this._wandWood = WoodMaterials.get(i);
            }
        }
        for (int i = 0; i < CoreMaterials.size(); ++i) {
            if (CoreMaterials.get(i)._materialName.equals(core)) {
                this._wandCore = CoreMaterials.get(i);
            }
        }
        for (int i = 0; i < DecoMaterials.size(); ++i) {
            if (DecoMaterials.get(i)._materialName.equals(deco)) {
                this._wandDeco = DecoMaterials.get(i);
            }
        }
        */
        //WoodMaterials.indexOf(wood);
    }

    @Override
    public ITextComponent getDisplayName(ItemStack item) {
        return new TranslationTextComponent(
                "item." + Reference.MOD_ID + ".wand_" +
                        ((this.getWandWood(item) != null) ? this.getWandWood(item)._materialName : "null")
        ).applyTextStyles(TextFormatting.YELLOW, TextFormatting.BOLD);
    }

    //region Getters and Setters
    @Override
    public UUID getOwnerUUID(ItemStack item) {
        return ItemNBTHelper.getUuid(item, TAG_OWNERUUID);
    }

    @Override
    public boolean isOwner(ItemStack item, PlayerEntity player) {
        if (player == null) return false;
        return player.getUniqueID() == getOwnerUUID(item);
    }

    @Override
    public boolean hasOwner(ItemStack item) {
        return getOwnerUUID(item) != null;
    }

    @Override
    public void setOwnerUUID(ItemStack item, PlayerEntity playerIn) {
        ItemNBTHelper.setUuid(item, TAG_OWNERUUID, playerIn.getUniqueID());
    }

    @Override
    public void setOwnerName(ItemStack item, PlayerEntity playerIn) {
        ItemNBTHelper.setString(item, TAG_OWNERNAME, playerIn.getName().getString());
    }

    @Override
    public String getOwnerName(ItemStack item) {
        return ItemNBTHelper.getString(item, TAG_OWNERNAME, null);
    }

    @Override
    public void setWandWood(ItemStack item, WandPart woodType) {
        ItemNBTHelper.setString(item, TAG_WOOD, woodType._materialName);
    }

    @Override
    public WandPart getWandWood(ItemStack item) {
        return ItemNBTHelper.getPart(item, TAG_WOOD);
    }

    @Override
    public void setWandCore(ItemStack item, WandPart coreType) {
        ItemNBTHelper.setString(item, TAG_CORE, coreType._materialName);
    }

    @Override
    public WandPart getWandCore(ItemStack item) {
        return ItemNBTHelper.getPart(item, TAG_CORE);
    }

    @Override
    public void setWandDeco(ItemStack item, WandPart decoType) {
        ItemNBTHelper.setString(item, TAG_DECO, decoType._materialName);
    }

    @Override
    public WandPart getWandDeco(ItemStack item) {
        return ItemNBTHelper.getPart(item, TAG_DECO);
    }
    //endregion

    @Override
    public void addInformation(ItemStack item, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(
                new TranslationTextComponent(
                        "tooltip." +  Reference.MOD_ID + "." +
                                ((this.getWandCore(item) != null) ? this.getWandCore(item)._materialName : "null_core")
                ).applyTextStyle(TextFormatting.GRAY)
        );
        tooltip.add(
                new TranslationTextComponent(
                        "tooltip." + Reference.MOD_ID + "." +
                                ((this.getWandDeco(item) != null) ? this.getWandDeco(item)._materialName : "null_deco")
                ).applyTextStyle(TextFormatting.GRAY)
        );
        String ownerPrefix = "tooltip." + Reference.MOD_ID + ".owner";
        String ownerName = (this.getOwnerName(item) != null) ? this.getOwnerName(item) : "tooltip." + Reference.MOD_ID + ".unclaimed";
        tooltip.add(
                new TranslationTextComponent(ownerPrefix)
                        .applyTextStyle(TextFormatting.GRAY)
                        .appendText(": ")
                        .appendSibling(
                                new TranslationTextComponent(ownerName)
                                        .applyTextStyle((ownerName.equals("tooltip." + Reference.MOD_ID + ".unclaimed")) ? TextFormatting.RED : TextFormatting.WHITE)
                        )
        );

    }
}
