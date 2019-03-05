package gtlugo.solarsorcery.networking.messages;

import gtlugo.solarsorcery.Reference;
import gtlugo.solarsorcery.handlers.ClientTickHandler;
import gtlugo.solarsorcery.handlers.NetworkHandler;
import gtlugo.solarsorcery.networking.NetworkMessage;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings({ "serial", "rawtypes" })
public class ManaUpdateMessage extends NetworkMessage {
	// A default constructor is always required
	public ManaUpdateMessage(){}
	
	public static int _tickCount = 0;
	
	public static class ManaUpdatePacketHandler implements IMessageHandler<ManaUpdateMessage, IMessage> {
		// Do note that the default constructor is required, but implicitly defined in this case
		@Override 
		public IMessage onMessage(ManaUpdateMessage message, MessageContext ctx) {
			// This is the player the packet was sent to the server from
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			WorldServer serverWorld = serverPlayer.getServerWorld();
			IPlayerData data = serverPlayer.getCapability(PlayerDataProvider.TAG_DATA, null);
			// The value that was sent
			// Execute the action on the main server thread by adding it as a scheduled task
			ClientTickHandler.scheduledActions.add(() -> {
				int timer = 1500;
				if (serverWorld.getLight(serverPlayer.getPosition()) > 7) {
					timer /= 3;
				}
				if (_tickCount >= timer && data.isCanRegen()) {
					NetworkHandler.INSTANCE.sendToServer(new ManaChangeMessage(1.0f));
					System.out.println(Reference.MODID + ": regen mana");
					_tickCount = 0;
				}
				else ++_tickCount;
			});
			//data.syncMana(serverPlayer);
			// No response packet
			return null;
		}
	}
}
