package tenebris.solarsorcery;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.Side;
import tenebris.solarsorcery.cap.mana.IMana;
import tenebris.solarsorcery.cap.mana.Mana;
import tenebris.solarsorcery.cap.mana.ManaStorage;
import tenebris.solarsorcery.cap.wandtype.IWandType;
import tenebris.solarsorcery.cap.wandtype.WandType;
import tenebris.solarsorcery.cap.wandtype.WandTypeStorage;
import tenebris.solarsorcery.handlers.CapabilityHandler;
import tenebris.solarsorcery.handlers.MainHandler;
import tenebris.solarsorcery.handlers.RenderGuiHandler;
import tenebris.solarsorcery.handlers.SolarsorcPacketHandler;
import tenebris.solarsorcery.init.ModItems;
import tenebris.solarsorcery.packets.ClientSyncPacket;
import tenebris.solarsorcery.packets.ClientSyncPacket.ClientSyncPacketHandler;
import tenebris.solarsorcery.packets.ManaRegenPacket;
import tenebris.solarsorcery.packets.ManaRegenPacket.ManaRegenPacketHandler;
import tenebris.solarsorcery.packets.ManaUpdatePacket;
import tenebris.solarsorcery.packets.ManaUpdatePacket.ManaUpdatePacketHandler;
import tenebris.solarsorcery.packets.ManaChangePacket;
import tenebris.solarsorcery.packets.ManaChangePacket.ManaChangePacketHandler;
import tenebris.solarsorcery.proxies.IProxy;

@Mod(modid=Reference.MODID, name=Reference.MODNAME, version=Reference.VERSION, acceptedMinecraftVersions=Reference.ACCEPTED_MINECRAFT_VERSIONS)
public class SolarSorcery {
	@Instance
	public static SolarSorcery instance;
	
	@SidedProxy(modId=Reference.MODID, clientSide=Reference.CLIENTPROXY, serverSide=Reference.SERVERPROXY)
	public static IProxy proxy;
	
	static int id = 0;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(Reference.MODID + ":preInit");
		ModItems.init();
		proxy.preInit(event);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println(Reference.MODID + ":init");
		CapabilityManager.INSTANCE.register(IMana.class, new ManaStorage(), Mana.class);
		CapabilityManager.INSTANCE.register(IWandType.class, new WandTypeStorage(), WandType.class);
		
		MinecraftForge.EVENT_BUS.register(new RenderGuiHandler());
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(new MainHandler());
		
		SolarsorcPacketHandler.INSTANCE.registerMessage(ManaChangePacketHandler.class, ManaChangePacket.class, id++, Side.SERVER);
		SolarsorcPacketHandler.INSTANCE.registerMessage(ManaUpdatePacketHandler.class, ManaUpdatePacket.class, id++, Side.SERVER);
		SolarsorcPacketHandler.INSTANCE.registerMessage(ManaRegenPacketHandler.class, ManaRegenPacket.class, id++, Side.SERVER);
		SolarsorcPacketHandler.INSTANCE.registerMessage(ClientSyncPacketHandler.class, ClientSyncPacket.class, id++, Side.CLIENT);
		proxy.init(event);
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		System.out.println(Reference.MODID + ":postInit");
		proxy.postInit(event);
	}
	
	@EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
	    // DEBUG
	    System.out.println("Server starting");
	        
        proxy.serverStarting(event);
	}
}