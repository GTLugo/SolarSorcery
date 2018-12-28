package tenebris.solarsorcery.handlers;

import java.util.Random;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tenebris.solarsorcery.cap.mana.IMana;
import tenebris.solarsorcery.cap.mana.ManaProvider;
import tenebris.solarsorcery.cap.wandtype.IWandType;
import tenebris.solarsorcery.cap.wandtype.WandTypeProvider;
import tenebris.solarsorcery.packets.ManaUpdatePacket;
import tenebris.solarsorcery.packets.ManaChangePacket;
import tenebris.solarsorcery.proxies.ClientProxy;

public class MainHandler {
	@SubscribeEvent
	public void onTickUpdate(TickEvent event) {
		//EntityPlayer player = event.player; 
		//IMana mana = player.getCapability(ManaProvider.MANACAP, null);
		SolarsorcPacketHandler.INSTANCE.sendToServer(new ManaUpdatePacket());
	}

	/*@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if (event.player instanceof EntityPlayerMP) {
			EntityPlayerMP serverPlayer = (EntityPlayerMP) event.player;
			IMana mana = serverPlayer.getCapability(ManaProvider.MANACAP, null);
			
			SolarsorcPacketHandler.INSTANCE.sendTo(new ClientSyncPacket(mana), serverPlayer);
		
		}
	}*/
	
	@SubscribeEvent 
	public void onPlayerLogsIn(PlayerLoggedInEvent event) { 
		EntityPlayer player = event.player; 
		IMana mana = player.getCapability(ManaProvider.MANACAP, null); 
		
		String[] woodTypes = { "oak", "birch", "spruce", "jungle", "acacia", "darkoak", "bone" };
		String[] coreTypes = { "string", "gunpowder", "feather", "redstone"};
		String[] capTypes = { "ironcapped", "goldcapped"};
		IWandType wandType = player.getCapability(WandTypeProvider.WANDCAP, null);
		if (wandType.getWood() == null && wandType.getCore() == null && wandType.getCap() == null) {
			Random rand = new Random();
			wandType.setWood(woodTypes[rand.nextInt(woodTypes.length)]);
			wandType.setCore(coreTypes[rand.nextInt(coreTypes.length)]);
			wandType.setCap(capTypes[rand.nextInt(capTypes.length)]);
		}
		String message0 = String.format("Your personalized wand [Wood: %s] [core: %s] [cap: %s]", wandType.getWood(), wandType.getCore(), wandType.getCap()); 
		player.sendMessage(new TextComponentString(message0)); 
		
		String message1 = String.format("%d / %d Aether", (int) mana.getCurrMana(), (int) mana.getMaxMana()); 
		player.sendMessage(new TextComponentString(message1)); 
	}
	
	@SubscribeEvent 
	public void onPlayerClone(PlayerEvent.Clone event) { 
		EntityPlayer player = event.getEntityPlayer(); 
		IMana mana = player.getCapability(ManaProvider.MANACAP, null); 
		IMana oldMana = event.getOriginal().getCapability(ManaProvider.MANACAP, null); 

		mana.setCurrMana(oldMana.getCurrMana()); 
		mana.setMaxMana(oldMana.getMaxMana()); 
	}
	
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority=EventPriority.NORMAL, receiveCanceled=true)
	public void onEvent(KeyInputEvent event) {
	    KeyBinding[] keyBindings = ClientProxy.keyBindings;
	    
		if (keyBindings[0].isKeyDown()) {
			SolarsorcPacketHandler.INSTANCE.sendToServer(new ManaChangePacket(1.0f));
	    }
	    
	    if (keyBindings[1].isKeyDown()) {
			SolarsorcPacketHandler.INSTANCE.sendToServer(new ManaChangePacket(-1.0f));
	    }
	}
}