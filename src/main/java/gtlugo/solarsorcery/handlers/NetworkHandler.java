/**
 * This class was created by <Vazkii>. 
 * MODIFIED BY TENEBRIS
 **/
package gtlugo.solarsorcery.handlers;

import gtlugo.solarsorcery.SolarSorcery;
import gtlugo.solarsorcery.lib.Reference;
import gtlugo.solarsorcery.networking.LevelChangeMessage;
import gtlugo.solarsorcery.networking.ManaChangeMessage;
//import gtlugo.solarsorcery.networking.ManaRegenMessage;
import gtlugo.solarsorcery.networking.DataSyncMessage;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class NetworkHandler {
	private static final String PROTOCOL_VERSION = Integer.toString(1);
	public static final SimpleChannel HANDLER = NetworkRegistry.newSimpleChannel(
			SolarSorcery.getId("solar_sorcery"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public static void registerMessages()
	{
		int i = 0;

		HANDLER.registerMessage(i++, DataSyncMessage.class, DataSyncMessage::encode, DataSyncMessage::decode, DataSyncMessage::handle);
		HANDLER.registerMessage(i++, LevelChangeMessage.class, LevelChangeMessage::encode, LevelChangeMessage::decode, LevelChangeMessage::handle);
		HANDLER.registerMessage(i++, ManaChangeMessage.class, ManaChangeMessage::encode, ManaChangeMessage::decode, ManaChangeMessage::handle);
		//HANDLER.registerMessage(i++, ManaRegenMessage.class, ManaRegenMessage::encode, ManaRegenMessage::decode, ManaRegenMessage::handle);
		//HANDLER.registerMessage(i++, DataSyncMessage.class, DataSyncMessage::encode, DataSyncMessage::decode, DataSyncMessage::handle);
	}



	/*
	Called Client side,
	Sends packet to server
	 */
	public static void sendToServer(Object msg)
	{
		HANDLER.sendToServer(msg);
	}

	/*
	Called Server side,
	Sends packet to client
	 */
	public static void sendTo(Object msg, ServerPlayerEntity player) {
		if (!(player instanceof FakePlayer)) {
			HANDLER.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT);

		}
	}
}
