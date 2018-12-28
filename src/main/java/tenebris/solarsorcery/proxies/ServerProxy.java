package tenebris.solarsorcery.proxies;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tenebris.solarsorcery.packets.ClientSyncPacket;

public class ServerProxy implements IProxy {
	@Override
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Override
    public void init(FMLInitializationEvent event) {
    }
    
    @Override
    public void postInit(FMLPostInitializationEvent event) {
    }
    
    @Override
    public void serverStarting(FMLServerStartingEvent event) {
       // event.registerServerCommand(new CommandStructureCapture());
    }
    
    @Override
    public EntityPlayer getPlayerEntityFromContext(MessageContext ctx) {
        return ctx.getServerHandler().player;
    }

	@Override
	public void syncClientMana(ClientSyncPacket message, MessageContext ctx) {
		// TODO Auto-generated method stub
		
	}
}