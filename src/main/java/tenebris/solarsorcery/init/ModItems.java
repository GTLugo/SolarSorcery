package tenebris.solarsorcery.init;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tenebris.solarsorcery.Reference;
import tenebris.solarsorcery.items.tools.PicksawBase;
import tenebris.solarsorcery.items.tools.SwordBase;
import tenebris.solarsorcery.items.tools.WandBase;

@Mod.EventBusSubscriber(modid=Reference.MODID)
public class ModItems {
	public static final ToolMaterial SOLARMAT = EnumHelper.addToolMaterial("SOLARMATERIAL", 2, -1, 8.0f, 2.0f, 11);
	
	//public static Item magicWand;
	//public static Item oakWand;
	public static Item wand;
	public static Item solarSword;
	public static Item solarPicksaw;
	
	static final CreativeTabs TABSOLARSORC = (new CreativeTabs("tabSolarSorcery") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(solarSword);
		}
	});
	
	public static void init() {
		//magicWand = new WandBase("magic_wand").setCreativeTab(TABSOLARSORC);
		//oakWand = new WandBase("oak_wand").setCreativeTab(TABSOLARSORC);
		wand = new WandBase("wand").setCreativeTab(TABSOLARSORC);
		solarSword = new SwordBase("solar_sword", SOLARMAT).setCreativeTab(TABSOLARSORC);
		solarPicksaw = new PicksawBase("solar_picksaw", SOLARMAT).setCreativeTab(TABSOLARSORC);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		event.getRegistry().registerAll(
				//magicWand,
				//oakWand,
				wand,
				solarSword,
				solarPicksaw
				);
	}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		//registerRender(magicWand);
		//registerRender(oakWand);
		registerRender(wand);
		registerRender(solarSword);
		registerRender(solarPicksaw);
	}

	private static void registerRender(Item item) {
			ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation( item.getRegistryName(), "inventory"));
	}
}