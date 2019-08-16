package gtlugo.solarsorcery.networking;

import gtlugo.solarsorcery.SolarSorcery;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

@SuppressWarnings({ "rawtypes", "serial" })
public class ManaChangeMessage {

	private final float _change;
	
	public ManaChangeMessage(float manaPoints) {
		this._change = manaPoints;
	}

	public static void encode(ManaChangeMessage msg, PacketBuffer buf) {
		buf.writeFloat(msg._change);
	}

	public static ManaChangeMessage decode(PacketBuffer buf) {
		float data =  buf.readFloat();
		return new ManaChangeMessage(data);
	}

	public static void handle(final ManaChangeMessage msg, final Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(new Runnable() {
		@Override
		public void run() {
			ServerPlayerEntity serverPlayer = ctx.get().getSender(); // try origination or reception?
			if (serverPlayer != null) {
				SolarSorcery.LOGGER.info("Changing mana of " + serverPlayer.getName().getString() + "...");

				PlayerData.getPlayerData(serverPlayer).ifPresent(data -> {
					if ((data.getCurrMana() + msg._change) < 0.0) {
						//chatMessage = "Not enough Aether";
					} else if ((data.getCurrMana() + msg._change) > data.getMaxMana()) {
						//chatMessage = "Aether maxed out";
						data.setCurrMana(data.getMaxMana());
					} else {
						data.changeMana(msg._change);
						//chatMessage = String.format("%d / %d Aether (change = %f)", (int) data.getCurrMana(), (int) data.getMaxMana(), _change);
					}
				});
			}
			}
		});
		ctx.get().setPacketHandled(true);
	}
}
