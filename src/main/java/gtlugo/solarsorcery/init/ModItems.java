package gtlugo.solarsorcery.init;

import gtlugo.solarsorcery.SolarSorcery;
import gtlugo.solarsorcery.init.item.wand.WandBase;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.LinkedHashMap;
import java.util.Map;

public class ModItems {
    static final Map<String, BlockItem> BLOCKS_TO_REGISTER = new LinkedHashMap<>();

    public static Item magicWand;
    public static WandBase wand;

    public static void init() {
        //ModelLoader.defaultModelGetter().apply()
    }

    public static void registerAll(RegistryEvent.Register<Item> event) {
        if (!event.getName().equals(ForgeRegistries.ITEMS.getRegistryName())) {
            return;
        }

        // Blocks //
        BLOCKS_TO_REGISTER.forEach(ModItems::register);

        // Items //
        magicWand = register("magic_wand", new Item(new Item.Properties().group(SolarSorceryGroup.INSTANCE)));
        wand = register("wand", new WandBase(new Item.Properties().group(SolarSorceryGroup.INSTANCE)));
    }

    private static <T extends Item> T registerWand(String name, T wand) {
        return wand;
    }

    private static <T extends Item> T register(String name, T item) {
        ResourceLocation id = SolarSorcery.getId(name);
        item.setRegistryName(id);
        ForgeRegistries.ITEMS.register(item);
        return item;
    }
}
