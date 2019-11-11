package gtlugo.solarsorcery.init.item.wand.wandparts;

import com.google.common.collect.Lists;
import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.List;

public class WandMaterials {
    public static final List<WandPart> WoodMaterials = Lists.newArrayList();
    public static final List<WandPart> CoreMaterials = Lists.newArrayList();
    public static final List<WandPart> HandMaterials = Lists.newArrayList();
    public static final List<WandPart> BandMaterials = Lists.newArrayList();

    // Wand Woods
    public static final WandPart oakWood                = wood( Items.OAK_PLANKS,            "oak",                  1f,     1f,     1f );
    public static final WandPart birchWood              = wood( Items.BIRCH_PLANKS,          "birch",                1f,     1f,     1f );
    public static final WandPart spruceWood             = wood( Items.SPRUCE_PLANKS,         "spruce",               1f,     1f,     1f );
    public static final WandPart jungleWood             = wood( Items.JUNGLE_PLANKS,         "jungle",               1f,     1f,     1f );
    public static final WandPart acaciaWood             = wood( Items.ACACIA_PLANKS,         "acacia",               1f,     1f,     1f );
    public static final WandPart darkOakWood            = wood( Items.DARK_OAK_PLANKS,       "darkoak",              1f,     1f,     1f );
    public static final WandPart boneWood               = wood( Items.BONE,                  "bone",                 1f,     1f,     1f );
    public static final WandPart blazeWood              = wood( Items.BLAZE_ROD,             "blazerod",             1f,     1f,     1f );
    public static final WandPart bambooWood             = wood( Items.BAMBOO,                "bamboo",               1f,     1f,     1f );
    public static final WandPart prismarineWood         = wood( Items.PRISMARINE,            "prismarine",           1f,     1f,     1f );
                      
    // Wand Cores                     
    public static final WandPart stringCore             = core( Items.STRING,                "string",               1f,     1f,     1f );
    public static final WandPart gunpowderCore          = core( Items.GUNPOWDER,             "gunpowder",            1f,     1f,     1f );
    public static final WandPart featherCore            = core( Items.FEATHER,               "feather",              1f,     1f,     1f );
    public static final WandPart redstoneCore           = core( Items.REDSTONE,              "redstone",             1f,     1f,     1f );
    public static final WandPart blazeCore              = core( Items.BLAZE_POWDER,          "blazepowder",          1f,     1f,     1f );
    public static final WandPart enderPearlCore         = core( Items.ENDER_PEARL,           "enderpearl",           1f,     1f,     1f );
    public static final WandPart glowstoneCore          = core( Items.GLOWSTONE_DUST,        "glowstone",            1f,     1f,     1f );
    public static final WandPart chorusFruitCore        = core( Items.CHORUS_FRUIT,          "chorusfruit",          1f,     1f,     1f );
    public static final WandPart slimeBallCore          = core( Items.SLIME_BALL,            "slimeball",            1f,     1f,     1f );
    public static final WandPart netherWartCore         = core( Items.NETHER_WART,           "netherwart",           1f,     1f,     1f );
    public static final WandPart netherQuartzCore       = core( Items.QUARTZ,                "netherquartz",         1f,     1f,     1f );
    public static final WandPart nautilusShellCore      = core( Items.NAUTILUS_SHELL,        "nautilusshell",        1f,     1f,     1f );
    public static final WandPart heartOfTheSeaCore      = core( Items.HEART_OF_THE_SEA,      "heartofthesea",        1f,     1f,     1f );
    public static final WandPart netherStarCore         = core( Items.NETHER_STAR,           "netherstar",           1f,     1f,     1f );
    public static final WandPart prismarineCrystalsCore = core( Items.PRISMARINE_CRYSTALS,   "prismarinecrystals",   1f,     1f,     1f );
    public static final WandPart endCrystalCore         = core( Items.END_CRYSTAL,           "endcrystal",           1f,     1f,     1f );
  
    // Wand Handles  
    public static final WandPart oakHand                = hand( Items.OAK_LOG,               "oak",                  1f,     1f,     1f );
    public static final WandPart birchHand              = hand( Items.BIRCH_LOG,             "birch",                1f,     1f,     1f );
    public static final WandPart spruceHand             = hand( Items.SPRUCE_LOG,            "spruce",               1f,     1f,     1f );
    public static final WandPart jungleHand             = hand( Items.JUNGLE_LOG,            "jungle",               1f,     1f,     1f );
    public static final WandPart acaciaHand             = hand( Items.ACACIA_LOG,            "acacia",               1f,     1f,     1f );
    public static final WandPart darkOakHand            = hand( Items.DARK_OAK_LOG,          "darkoak",              1f,     1f,     1f );
    public static final WandPart boneHand               = hand( Items.BONE,                  "bone",                 1f,     1f,     1f );
    public static final WandPart blazeHand              = hand( Items.BLAZE_ROD,             "blazerod",             1f,     1f,     1f );
    public static final WandPart bambooHand             = hand( Items.BAMBOO,                "bamboo",               1f,     1f,     1f );
    public static final WandPart prismarineHand         = hand( Items.PRISMARINE,            "prismarine",           1f,     1f,     1f );

    // Wand Bands
    public static final WandPart ironBand               = band( Items.IRON_INGOT,            "iron",                 1f,     1f,     1f );
    public static final WandPart goldBand               = band( Items.GOLD_INGOT,            "gold",                 1f,     1f,     1f );
    public static final WandPart diamondBand            = band( Items.DIAMOND,               "diamond",              1f,     1f,     1f ); // temp
    public static final WandPart emeraldBand            = band( Items.EMERALD,               "emerald",              1f,     1f,     1f ); // temp
    public static final WandPart leatherBand            = band( Items.LEATHER,               "leather",              1f,     1f,     1f ); // temp

    private static WandPart wood(Item item, String name, float combat, float buff, float manip) {
        // make materials hidden by default, integration will make them visible if integrated
        WandPart part = new WandPart(
                name,
                combat,
                buff,
                manip
        );
        WoodMaterials.add(part);
        return part;
    }

    private static WandPart core(Item item, String name, float combat, float buff, float manip) {
        // make materials hidden by default, integration will make them visible if integrated
        WandPart part = new WandPart(
                name,
                combat,
                buff,
                manip
        );
        CoreMaterials.add(part);
        return part;
    }

    private static WandPart hand(Item item, String name, float combat, float buff, float manip) {
        // make materials hidden by default, integration will make them visible if integrated
        WandPart part = new WandPart(
                name,
                combat,
                buff,
                manip
        );
        HandMaterials.add(part);
        return part;
    }

    private static WandPart band(Item item, String name, float combat, float buff, float manip) {
        // make materials hidden by default, integration will make them visible if integrated
        WandPart part = new WandPart(
                name,
                combat,
                buff,
                manip
        );
        BandMaterials.add(part);
        return part;
    }
}
