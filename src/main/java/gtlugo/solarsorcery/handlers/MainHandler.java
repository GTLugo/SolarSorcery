package gtlugo.solarsorcery.handlers;

import java.util.Random;

import gtlugo.solarsorcery.networking.messages.DataSyncMessage;
import gtlugo.solarsorcery.networking.messages.ManaUpdateMessage;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class MainHandler {
	
	@SubscribeEvent
	public void onTick(TickEvent event) {
		//EntityPlayer player = event.player; 
		//IMana mana = player.getCapability(ManaProvider.MANACAP, null);
	}

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {
		if (event.player instanceof EntityPlayerMP) {
			EntityPlayerMP serverPlayer = (EntityPlayerMP) event.player;
			IPlayerData data = serverPlayer.getCapability(PlayerDataProvider.TAG_DATA, null);
			
			ManaUpdateMessage updateMessage = new ManaUpdateMessage();
			NetworkHandler.INSTANCE.sendToServer(updateMessage);
			DataSyncMessage syncMessage = new DataSyncMessage(data.isCanRegen(), data.getMaxMana(), data.getCurrMana(), data.getLevel(), data.getExperience(), data.getExpToLvl(), data.getWood(), data.getCore(), data.getCap());
			NetworkHandler.INSTANCE.sendTo(syncMessage, serverPlayer);
		}
	}
	
	@SubscribeEvent 
	public void onPlayerLogsIn(PlayerLoggedInEvent event) { 
		EntityPlayer player = event.player; 
		IPlayerData data = player.getCapability(PlayerDataProvider.TAG_DATA, null); 
		
		String[] woodTypes = { "oak", "birch", "spruce", "jungle", "acacia", "darkoak", "bone" };
		String[] coreTypes = { "string", "gunpowder", "feather", "redstone"};
		String[] capTypes = { "ironcapped", "goldcapped"};
		if (data.getWood() == null && data.getCore() == null && data.getCap() == null) {
			Random rand = new Random();
			data.setWood(woodTypes[rand.nextInt(woodTypes.length)]);
			data.setCore(coreTypes[rand.nextInt(coreTypes.length)]);
			data.setCap(capTypes[rand.nextInt(capTypes.length)]);
		}
		String message0 = String.format("Your personalized wand [Wood: %s] [core: %s] [cap: %s]", data.getWood(), data.getCore(), data.getCap()); 
		player.sendMessage(new TextComponentString(message0)); 
		
		String message1 = String.format("%d / %d Aether", (int) data.getCurrMana(), (int) data.getMaxMana()); 
		player.sendMessage(new TextComponentString(message1));
		
		String message2 = String.format("Level %d", (int) data.getLevel()); 
		player.sendMessage(new TextComponentString(message2));
	}
	
	@SubscribeEvent 
	public void onPlayerClone(PlayerEvent.Clone event) { 
		EntityPlayer player = event.getEntityPlayer(); 
		IPlayerData data = player.getCapability(PlayerDataProvider.TAG_DATA, null); 
		IPlayerData oldData = event.getOriginal().getCapability(PlayerDataProvider.TAG_DATA, null); 

		data.setCanRegen(oldData.isCanRegen());
		data.setCurrMana(oldData.getCurrMana()); 
		data.setMaxMana(oldData.getMaxMana()); 
		
		data.setLevel(oldData.getLevel());
		data.addExp(oldData.getExperience(), true);
		data.setExpToLvl(oldData.getExpToLvl());
		
		data.setCap(oldData.getCap());
		data.setCore(oldData.getCore());
		data.setWood(oldData.getWood());
	}
}