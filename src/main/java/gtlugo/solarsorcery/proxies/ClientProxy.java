package gtlugo.solarsorcery.proxies;

import gtlugo.solarsorcery.SolarSorcery;
import gtlugo.solarsorcery.handlers.ClientTickHandler;
import gtlugo.solarsorcery.handlers.DropInHandler;
import gtlugo.solarsorcery.handlers.HUDHandler;
import gtlugo.solarsorcery.handlers.KeybindsHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ClientProxy implements IProxy {
	 // mouse helper
    //public static MouseHelper mouseHelperAI; // used to intercept user mouse movement for "bot" functionality

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        // DEBUG
        System.out.println("on Client side");
		DropInHandler.register();
		MinecraftForge.EVENT_BUS.register(new ClientTickHandler());
		MinecraftForge.EVENT_BUS.register(new KeybindsHandler());
		MinecraftForge.EVENT_BUS.register(new HUDHandler());
        //MinecraftForge.EVENT_BUS.register(new ManaBar(Minecraft.getMinecraft()));
       // Minecraft.getMinecraft().mouseHelper = ClientProxy.mouseHelperAI;
        //RenderFactories.registerEntityRenderers();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        // DEBUG
    	
        System.out.println("on Client side");
        
        KeybindsHandler.init();
    }

    @Override
    public void postInit(FMLPostInitializationEvent event) {
        // DEBUG
        System.out.println("on Client side");
    }

    @Override
    public void serverStarting(FMLServerStartingEvent event) {
        // This will never get called on client side
    }
    
    @Override
    public EntityPlayer getPlayerEntityFromContext(MessageContext ctx) {
        return (ctx.side.isClient() ? Minecraft.getMinecraft().player : SolarSorcery.proxy.getPlayerEntityFromContext(ctx));
    }
}