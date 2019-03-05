package gtlugo.solarsorcery;

import gtlugo.solarsorcery.commands.AddManaCommand;
import gtlugo.solarsorcery.commands.SetManaCommand;
import gtlugo.solarsorcery.commands.SetManaLevelCommand;
import gtlugo.solarsorcery.commands.SolSorcCommand;
import gtlugo.solarsorcery.commands.SubManaCommand;
import gtlugo.solarsorcery.handlers.CapabilityHandler;
import gtlugo.solarsorcery.handlers.MainHandler;
import gtlugo.solarsorcery.handlers.NetworkHandler;
import gtlugo.solarsorcery.init.ModItems;
import gtlugo.solarsorcery.networking.MessageRegister;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataStorage;
import gtlugo.solarsorcery.proxies.IProxy;
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

@Mod(modid=Reference.MODID, name=Reference.MODNAME, version=Reference.VERSION, acceptedMinecraftVersions=Reference.ACCEPTED_MINECRAFT_VERSIONS)
public class SolarSorcery {
	@Instance
	public static SolarSorcery instance;
	
	@SidedProxy(modId=Reference.MODID, clientSide=Reference.CLIENTPROXY, serverSide=Reference.SERVERPROXY)
	public static IProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		System.out.println(Reference.MODID + ":preInit");
		ModItems.init();
		NetworkHandler.initMessages();
		MessageRegister.init();
		proxy.preInit(event);
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		System.out.println(Reference.MODID + ":init");
		CapabilityManager.INSTANCE.register(IPlayerData.class, new PlayerDataStorage(), PlayerData::new);
		
		MinecraftForge.EVENT_BUS.register(new CapabilityHandler());
		MinecraftForge.EVENT_BUS.register(new MainHandler());
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
	    event.registerServerCommand(new SolSorcCommand());
	    event.registerServerCommand(new AddManaCommand());
	    event.registerServerCommand(new SetManaCommand());
	    event.registerServerCommand(new SetManaLevelCommand());
	    event.registerServerCommand(new SubManaCommand());
	        
        proxy.serverStarting(event);
	}
}