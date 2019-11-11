package gtlugo.solarsorcery;

import gtlugo.solarsorcery.handlers.*;
import gtlugo.solarsorcery.init.ModBlocks;
import gtlugo.solarsorcery.init.ModItems;
import gtlugo.solarsorcery.init.item.wand.WandBase;
import gtlugo.solarsorcery.lib.Reference;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataStorage;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.event.lifecycle.*;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class SideProxy {
    SideProxy() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::enqueueIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(SideProxy::processIMC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModBlocks::registerAll);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ModItems::registerAll);
        MinecraftForge.EVENT_BUS.addListener(SideProxy::serverStarting);
    }

    private static void commonSetup(FMLCommonSetupEvent event) {
        SolarSorcery.LOGGER.debug(Reference.MOD_ID + ": commonSetup");
        CapabilityManager.INSTANCE.register(IPlayerData.class, new PlayerDataStorage(), () -> new PlayerData());
        MinecraftForge.EVENT_BUS.register(new PlayerData.CapabilityHandler());
        MinecraftForge.EVENT_BUS.register(new MainHandler());
        NetworkHandler.registerMessages();
    }

    static class Client extends SideProxy {
        Client() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Client::clientSetup);
        }

        private static void clientSetup(FMLClientSetupEvent event) {
            MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
            MinecraftForge.EVENT_BUS.register(new KeybindsHandler());
            MinecraftForge.EVENT_BUS.register(new HUDHandler());

            KeybindsHandler.init();
        }

    }
    static class Server extends SideProxy {
        Server() {
            FMLJavaModLoadingContext.get().getModEventBus().addListener(Server::serverSetup);
        }

        private static void serverSetup(FMLDedicatedServerSetupEvent event) {

        }
    }

    private static void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private static void processIMC(final InterModProcessEvent event) {

    }

    public static void serverStarting(FMLServerStartingEvent event) {

    }
}
