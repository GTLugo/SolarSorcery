//MADE BY VASKII, EDITED BY TENEBRIS
package gtlugo.solarsorcery.networking;

import gtlugo.solarsorcery.handlers.NetworkHandler;
import gtlugo.solarsorcery.networking.messages.DataSyncMessage;
import gtlugo.solarsorcery.networking.messages.LevelChangeMessage;
import gtlugo.solarsorcery.networking.messages.ManaChangeMessage;
import gtlugo.solarsorcery.networking.messages.ManaRegenMessage;
import gtlugo.solarsorcery.networking.messages.ManaUpdateMessage;
import net.minecraftforge.fml.relauncher.Side;

public class MessageRegister {

	@SuppressWarnings("unchecked")
	public static void init() {
		NetworkHandler.register(ManaChangeMessage.class, Side.SERVER);
		NetworkHandler.register(ManaRegenMessage.class, Side.SERVER);
		NetworkHandler.register(ManaUpdateMessage.class, Side.SERVER);
		NetworkHandler.register(DataSyncMessage.class, Side.CLIENT);
		NetworkHandler.register(LevelChangeMessage.class, Side.SERVER);
	}
	
}
