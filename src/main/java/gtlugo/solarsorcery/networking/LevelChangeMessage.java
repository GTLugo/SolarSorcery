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
public class LevelChangeMessage {
	private final LevelChangeData _data;

	public LevelChangeMessage(LevelChangeData data) {
		this._data = data;
	}



	public static void encode(LevelChangeMessage msg, PacketBuffer buf) {
		buf.writeVarInt(msg._data.getNewLevel());
		buf.writeBoolean(msg._data.isKeepExperience());
		buf.writeBoolean(msg._data.isDelta());
	}

	public static LevelChangeMessage decode(PacketBuffer buf) {
		LevelChangeData data =  new LevelChangeData(
				buf.readVarInt(),
				buf.readBoolean(),
				buf.readBoolean());
		return new LevelChangeMessage(data);
	}

	public static void handle(final LevelChangeMessage msg, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			ServerPlayerEntity serverPlayer = ctx.get().getSender(); // try origination or reception?

			SolarSorcery.LOGGER.info("Changing level of" + serverPlayer.getName().getString()  + "...");

			PlayerData.getPlayerData(serverPlayer).ifPresent(data -> {
				int maxLevel = 400;
				int minLevel = 1;

				if (msg._data.isDelta()) {
					if (msg._data.getNewLevel() > 0) {
						int exp = (msg._data.isKeepExperience()) ? data.getExperience() : Math.max(data.getExperience() - data.getExpToLvl(), 0);
						data.changeLevel((int) Math.min(data.getLevel() + msg._data.getNewLevel(), maxLevel), exp);
					}
					else if (msg._data.getNewLevel() < 0) {
						//experience is always wiped when leveling down to prevent immediately leveling up
						data.changeLevel((int) Math.max(data.getLevel() + msg._data.getNewLevel(), minLevel), 0);
					}
					else {
						//do nothing if _change is zeo
					}
				}
				else {
					int exp = (msg._data.isKeepExperience()) ? data.getExperience() : Math.max(data.getExperience() - data.getExpToLvl(), 0);
					data.changeLevel((int) Math.min(msg._data.getNewLevel(), maxLevel), exp);
				}
			});
		});
		ctx.get().setPacketHandled(true);
	}

	public static class LevelChangeData {
		private int _newLevel;
		private boolean _keepExperience;
		private boolean _isDelta;

		public LevelChangeData(
				int newLevel,
				boolean keepExperience) {
			this._newLevel = newLevel;
			this._keepExperience = keepExperience;
			this._isDelta = true;
		}

		public LevelChangeData(
				int newLevel,
				boolean keepExperience,
				boolean isDelta) {
			this._newLevel = newLevel;
			this._keepExperience = keepExperience;
			this._isDelta = isDelta;
		}

		public int getNewLevel() {
			return this._newLevel;
		}

		public boolean isKeepExperience() {
			return this._keepExperience;
		}

		public boolean isDelta() { return this._isDelta; }
	}
}
