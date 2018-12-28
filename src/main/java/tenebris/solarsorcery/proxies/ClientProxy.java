package tenebris.solarsorcery.proxies;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tenebris.solarsorcery.Reference;
import tenebris.solarsorcery.SolarSorcery;
import tenebris.solarsorcery.cap.mana.IMana;
import tenebris.solarsorcery.cap.mana.ManaProvider;
import tenebris.solarsorcery.gui.manabar.ManaBar;
import tenebris.solarsorcery.packets.ClientSyncPacket;

public class ClientProxy implements IProxy {
	 // mouse helper
    //public static MouseHelper mouseHelperAI; // used to intercept user mouse movement for "bot" functionality
    public static KeyBinding[] keyBindings;

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        // DEBUG
        System.out.println("on Client side");
        MinecraftForge.EVENT_BUS.register(new ManaBar(Minecraft.getMinecraft()));
       // Minecraft.getMinecraft().mouseHelper = ClientProxy.mouseHelperAI;
        //RenderFactories.registerEntityRenderers();
    }

    @Override
    public void init(FMLInitializationEvent event) {
        // DEBUG
    	
        System.out.println("on Client side");
        keyBindings = new KeyBinding[2]; 
        
        // instantiate the key bindings
        
        keyBindings[0] = new KeyBinding("Add Aether", Keyboard.KEY_EQUALS, "Solar Sorcery");
        keyBindings[1] = new KeyBinding("Remove Aether", Keyboard.KEY_MINUS, "Solar Sorcery");
       
        // register all the key bindings
        for (int i = 0; i < keyBindings.length; ++i) {
        	ClientRegistry.registerKeyBinding(keyBindings[i]);
        }
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
    public void syncClientMana(ClientSyncPacket message, MessageContext ctx) {

		EntityPlayerSP player = (EntityPlayerSP) SolarSorcery.proxy.getPlayerEntityFromContext(ctx);
		IMana _manaCap = message.getMana();
		//IThreadListener thread = Minecraft.getMinecraft();
		System.out.println(Reference.MODID + ": syncing mana (part 1)");
		/*Runnable runnable = new Runnable() {
			@Override
			public void run() {*/
				if (player != null) {
					IMana mana = player.getCapability(ManaProvider.MANACAP, null);
					System.out.println(Reference.MODID + ": syncing mana (part 2)");
					if (mana != null && _manaCap != null) {
						mana.setCurrMana(_manaCap.getCurrMana());
						mana.setMaxMana(_manaCap.getMaxMana());
						System.out.println(Reference.MODID + ": syncing mana (part 3)");
					}
				}
		/*	} 
		};
		thread.addScheduledTask(runnable);*/
		System.out.println(Reference.MODID + ": syncing mana (part 4)");
		
    }

    @Override
    public EntityPlayer getPlayerEntityFromContext(MessageContext ctx) {
        return (ctx.side.isClient() ? Minecraft.getMinecraft().player : SolarSorcery.proxy.getPlayerEntityFromContext(ctx));
    }
}