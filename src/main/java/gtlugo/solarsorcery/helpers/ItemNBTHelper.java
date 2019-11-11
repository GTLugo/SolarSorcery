package gtlugo.solarsorcery.helpers;

/**
 * This class was created by <Vazkii>.
 * Edited by GTLugo
 *
 * Botania License: http://botaniamod.net/license.php
 **/

import gtlugo.solarsorcery.init.item.wand.wandparts.WandPart;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.ListNBT;

import javax.annotation.Nullable;
import java.util.UUID;

public final class ItemNBTHelper {

    private static final int[] EMPTY_INT_ARRAY = new int[0];

    // SETTERS ///////////////////////////////////////////////////////////////////

    public static void setBoolean(ItemStack stack, String tag, boolean b) {
        stack.getOrCreateTag().putBoolean(tag, b);
    }

    public static void setByte(ItemStack stack, String tag, byte b) {
        stack.getOrCreateTag().putByte(tag, b);
    }

    public static void setShort(ItemStack stack, String tag, short s) {
        stack.getOrCreateTag().putShort(tag, s);
    }

    public static void setInt(ItemStack stack, String tag, int i) {
        stack.getOrCreateTag().putInt(tag, i);
    }

    public static void setIntArray(ItemStack stack, String tag, int[] val) {
        stack.getOrCreateTag().putIntArray(tag, val);
    }

    public static void setLong(ItemStack stack, String tag, long l) {
        stack.getOrCreateTag().putLong(tag, l);
    }

    public static void setFloat(ItemStack stack, String tag, float f) {
        stack.getOrCreateTag().putFloat(tag, f);
    }

    public static void setDouble(ItemStack stack, String tag, double d) {
        stack.getOrCreateTag().putDouble(tag, d);
    }

    public static void setCompound(ItemStack stack, String tag, CompoundNBT cmp) {
        if(!tag.equalsIgnoreCase("ench")) // not override the enchantments
            stack.getOrCreateTag().put(tag, cmp);
    }

    public static void setString(ItemStack stack, String tag, String s) {
        stack.getOrCreateTag().putString(tag, s);
    }

    public static void setUuid(ItemStack stack, String tag, UUID value) {
        stack.getOrCreateTag().putUniqueId(tag, value);
    }

    public static void setList(ItemStack stack, String tag, ListNBT list) {
        stack.getOrCreateTag().put(tag, list);
    }

    public static void removeEntry(ItemStack stack, String tag) {
        stack.getOrCreateTag().remove(tag);
    }

    public static void setPart(ItemStack stack, String tag, WandPart p) {
        stack.getOrCreateTag().putString(tag + "_name", p._materialName);
        stack.getOrCreateTag().putFloat(tag + "_combat", p._combat);
        stack.getOrCreateTag().putFloat(tag + "_buff", p._buff);
        stack.getOrCreateTag().putFloat(tag + "_manip", p._manip);
    }

    // GETTERS ///////////////////////////////////////////////////////////////////

    public static boolean verifyExistance(ItemStack stack, String tag) {
        return !stack.isEmpty() && stack.getOrCreateTag().contains(tag);
    }

    public static boolean verifyPartExistance(ItemStack stack, String tag) {
        return !stack.isEmpty()
                && stack.getOrCreateTag().contains(tag + "_name")
                && stack.getOrCreateTag().contains(tag + "_combat")
                && stack.getOrCreateTag().contains(tag + "_buff")
                && stack.getOrCreateTag().contains(tag + "_manip");
    }

    public static WandPart getPart(ItemStack stack, String tag) {
        return verifyPartExistance(stack, tag) ? new WandPart(
                stack.getOrCreateTag().getString(tag + "_name"),
                stack.getOrCreateTag().getFloat(tag + "_combat"),
                stack.getOrCreateTag().getFloat(tag + "_buff"),
                stack.getOrCreateTag().getFloat(tag + "_manip")
        ) : null;
    }

    public static boolean getBoolean(ItemStack stack, String tag, boolean defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateTag().getBoolean(tag) : defaultExpected;
    }

    public static byte getByte(ItemStack stack, String tag, byte defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateTag().getByte(tag) : defaultExpected;
    }

    public static short getShort(ItemStack stack, String tag, short defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateTag().getShort(tag) : defaultExpected;
    }

    public static int getInt(ItemStack stack, String tag, int defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateTag().getInt(tag) : defaultExpected;
    }

    public static int[] getIntArray(ItemStack stack, String tag) {
        return verifyExistance(stack, tag) ? stack.getOrCreateTag().getIntArray(tag) : EMPTY_INT_ARRAY;
    }

    public static long getLong(ItemStack stack, String tag, long defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateTag().getLong(tag) : defaultExpected;
    }

    public static float getFloat(ItemStack stack, String tag, float defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateTag().getFloat(tag) : defaultExpected;
    }

    public static double getDouble(ItemStack stack, String tag, double defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateTag().getDouble(tag) : defaultExpected;
    }

    /** If nullifyOnFail is true it'll return null if it doesn't find any
     * compounds, otherwise it'll return a new one. **/
    public static CompoundNBT getCompound(ItemStack stack, String tag, boolean nullifyOnFail) {
        return verifyExistance(stack, tag) ? stack.getOrCreateTag().getCompound(tag) : nullifyOnFail ? null : new CompoundNBT();
    }

    public static String getString(ItemStack stack, String tag, String defaultExpected) {
        return verifyExistance(stack, tag) ? stack.getOrCreateTag().getString(tag) : defaultExpected;
    }

    @Nullable
    public static UUID getUuid(ItemStack stack, String tag) {
        return verifyExistance(stack, tag) ? stack.getOrCreateTag().getUniqueId(tag) : null;
    }

    public static ListNBT getList(ItemStack stack, String tag, int objtype, boolean nullifyOnFail) {
        return verifyExistance(stack, tag) ? stack.getOrCreateTag().getList(tag, objtype) : nullifyOnFail ? null : new ListNBT();
    }

    /**
     * Checks that one tag is a subset of another - that is, it's ok if the superset has more NBT keys than the subset, but the subset has to have at least all the same keys as the superset, and the values of the keys that *are* shared between the two must match.
     *
     * This is useful when, e.g. matching NBT tags in recipes.
     */
    public static boolean isTagSubset(@Nullable CompoundNBT subset, @Nullable CompoundNBT superset) {
        //an empty set is a subset of everything
        if(subset == null || subset.isEmpty()) return true;
        //an empty set is a superset of only another empty set (which was already checked above)
        if(superset == null || superset.isEmpty()) return false;
        //a subset can't be bigger than its superset
        if(subset.keySet().size() > superset.keySet().size()) return false;

        //it's not an easy case, so we actually have to check the contents of each tag
        for(String key : superset.keySet()) {
            //it's ok if the subset is missing a key from the superset
            if(!subset.contains(key)) continue;

            INBT supersetEntry = superset.get(key);
            INBT subsetEntry = subset.get(key);

            //if a value is present on both tags, but they do not match, fail
            if(supersetEntry instanceof CompoundNBT && subsetEntry instanceof CompoundNBT) {
                //recurse into tag compounds (this properly compares nested tag compounds)
                if(!isTagSubset((CompoundNBT) subsetEntry, (CompoundNBT) supersetEntry)) return false;
            } else {
                if(!supersetEntry.equals(subsetEntry)) return false;
            }
        }

        return true;
    }
}
