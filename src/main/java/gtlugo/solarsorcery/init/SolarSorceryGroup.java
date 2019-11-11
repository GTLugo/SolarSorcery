package gtlugo.solarsorcery.init;

import gtlugo.solarsorcery.helpers.ItemNBTHelper;
import gtlugo.solarsorcery.init.item.wand.WandBase;
import gtlugo.solarsorcery.lib.Reference;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import java.util.Comparator;

import javax.annotation.Nonnull;

import static gtlugo.solarsorcery.init.item.wand.wandparts.WandMaterials.*;
import static gtlugo.solarsorcery.init.item.wand.wandparts.WandMaterials.DecoMaterials;

public class SolarSorceryGroup extends ItemGroup {

    public static final SolarSorceryGroup INSTANCE = new SolarSorceryGroup();
    private NonNullList<ItemStack> list;

    public SolarSorceryGroup() {
        super(Reference.MOD_ID);
        setNoTitle();
        hasSearchBar();
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModItems.magicWand);
    }

    @Override
    public boolean hasSearchBar() {
        return false;
    }

    @Override
    public void fill(@Nonnull NonNullList<ItemStack> list) {
        this.list = list;

        addItem(ModItems.magicWand);
        addItem(ModItems.wand);

        for (int i = 0; i < WoodMaterials.size(); i++) {
            for (int j = 0; j < CoreMaterials.size(); j++) {
                for (int k = 0; k < DecoMaterials.size(); k++) {
                    // This creates a new ItemStack instance. The item parameter
                    // supplied is this item.
                    ItemStack wandStack = new ItemStack(ModItems.wand);
                    ItemNBTHelper.setPart(wandStack, WandBase.TAG_WOOD, WoodMaterials.get(i));
                    ItemNBTHelper.setPart(wandStack, WandBase.TAG_CORE, CoreMaterials.get(j));
                    ItemNBTHelper.setPart(wandStack, WandBase.TAG_DECO, DecoMaterials.get(k));
                    //setCurrMana(wandStack, 1);
                    //setMaxMana(wandStack, 1);
                    // And this adds it to the itemList, which is a list of all items
                    // in the creative tab.
                    list.add(wandStack);
                }
            }
        }
    }

    private void addTag(ResourceLocation tagId) {
        ItemTags.getCollection().getOrCreate(tagId).getAllElements().stream()
                .sorted(Comparator.comparing(ForgeRegistryEntry::getRegistryName))
                .forEach(this::addItem);
    }

    private void addItem(IItemProvider item) {
        item.asItem().fillItemGroup(this, list);
    }
}