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
	
	//public static int _tickCount = 0;
	public static int _cooldown = 0;

	@Override
	public IMessage handleMessage(MessageContext ctx) {
			// This is the player the packet was sent to the server from
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			WorldServer serverWorld = serverPlayer.getServerWorld();
			IPlayerData data = serverPlayer.getCapability(PlayerDataProvider.TAG_DATA, null);
			// The value that was sent
			// Execute the action on the main server thread by adding it as a scheduled task
			ClientTickHandler.scheduledActions.add(() -> {
				float manaIncrease = (data.getMaxMana() / 200); // 1/10th of max mana renewed per second... 20 ticks per second...
				if (serverWorld.getLight(serverPlayer.getPosition()) < 7) {
					manaIncrease /= 3;
				}
				if ((data.isCanRegen() && data.getRegenCooldown() < 1)) {
					NetworkHandler.INSTANCE.sendToServer(new ManaChangeMessage(manaIncrease));
					//System.out.println(Reference.MODID + ": regen mana");
				}
				else {
					data.setRegenCooldown(data.getRegenCooldown() - 1);
				}
			});
			//data.syncMana(serverPlayer);
			// No response packet
			return null;
		}
	}
