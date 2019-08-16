package gtlugo.solarsorcery.handlers;

import java.util.Random;

//import gtlugo.solarsorcery.networking.DataSyncMessage;
//import gtlugo.solarsorcery.networking.messages.ManaUpdateMessage;
import gtlugo.solarsorcery.lib.Reference;
import gtlugo.solarsorcery.networking.DataSyncMessage;
import gtlugo.solarsorcery.networking.ManaChangeMessage;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class MainHandler {
	
	@SubscribeEvent
	public void onTick(TickEvent event) {
		//EntityPlayer player = event.player; 
		//IMana mana = player.getCapability(ManaProvider.MANACAP, null);
	}
}