package gtlugo.solarsorcery.networking.messages;

import gtlugo.solarsorcery.handlers.ClientTickHandler;
import gtlugo.solarsorcery.networking.NetworkMessage;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings({ "serial", "rawtypes" })
public class ManaRegenMessage extends NetworkMessage {
	// A default constructor is always required

	public boolean _canRegen;
	
	public ManaRegenMessage(){}
	
	public ManaRegenMessage(boolean canRegen) {
		this._canRegen = canRegen;
	}
	
	@Override 
	public IMessage handleMessage(MessageContext ctx) {
		// This is the player the packet was sent to the server from
		EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
		IPlayerData data = serverPlayer.getCapability(PlayerDataProvider.TAG_DATA, null);
		// The value that was sent
		// Execute the action on the main server thread by adding it as a scheduled task
		ClientTickHandler.scheduledActions.add(() -> {
			data.setCanRegen(_canRegen);
		});
		// No response packet
		return null;
	}
}
