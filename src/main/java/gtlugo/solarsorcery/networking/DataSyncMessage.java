package gtlugo.solarsorcery.networking;

import gtlugo.solarsorcery.SolarSorcery;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

@SuppressWarnings({ "serial", "rawtypes" })
public class DataSyncMessage {
	private final DataSyncData _data;
	
	public DataSyncMessage(DataSyncData data) {
		this._data = data;
	}

	public static void encode(DataSyncMessage msg, PacketBuffer buf) {
		buf.writeBoolean(msg._data.isCanRegen());
		buf.writeFloat(msg._data.getMaxMana());
		buf.writeFloat(msg._data.getCurrMana());
		buf.writeVarInt(msg._data.getCooldown());

		buf.writeVarInt(msg._data.getLevel());
		buf.writeVarInt(msg._data.getExperience());
		buf.writeVarInt(msg._data.getExpToLvl());

		buf.writeString(msg._data.getWood());
		buf.writeString(msg._data.getCore());
		buf.writeString(msg._data.getCap());
	}

	public static DataSyncMessage decode(PacketBuffer buf) {
		DataSyncData data =  new DataSyncData(
					buf.readBoolean(),
		 			buf.readFloat(),
					buf.readFloat(),
					buf.readVarInt(),
					buf.readVarInt(),
					buf.readVarInt(),
					buf.readVarInt(),
					buf.readString(),
					buf.readString(),
					buf.readString());
		return new DataSyncMessage(data);
	}

	@OnlyIn(Dist.CLIENT)
	public static void handle(final DataSyncMessage msg, Supplier<NetworkEvent.Context> ctx)
	{
		ctx.get().enqueueWork(() -> {
			//if (ctx.get().getDirection().getOriginationSide().isClient()) {
			PlayerEntity player = Minecraft.getInstance().player; // try origination or reception?

				SolarSorcery.LOGGER.info("Syncing player data between client and server...");
				if (player != null)
				PlayerData.getPlayerData(player).ifPresent(data -> {
					data.setCanRegen(msg._data.isCanRegen());
					data.setMaxMana(msg._data.getMaxMana());
					data.setCurrMana(msg._data.getCurrMana());
					data.setCooldown(msg._data.getCooldown());

					data.setLevel(msg._data.getLevel());
					data.addExp(msg._data.getExperience(), true);
					data.setExpToLvl(msg._data.getExpToLvl());

					data.setWood(msg._data.getWood());
					data.setCore(msg._data.getCore());
					data.setCap(msg._data.getCap());
				});
			//}
		});
		ctx.get().setPacketHandled(true);
	}

	public static class DataSyncData {
		private boolean _canRegen;
		private float _maxMana;
		private float _currMana;
		private int _cooldown;

		private int _level;
		private int _experience;
		private int _expToLvl;

		private String _wood;
		private String _core;
		private String _cap;

		public DataSyncData(
				boolean canRegen,
				float maxMana,
				float currMana,
				int cooldown,
				int level,
				int experience,
				int expToLvl,
				String wood,
				String core,
				String cap) {
			this._canRegen = canRegen;
			this._maxMana = maxMana;
			this._currMana = currMana;
			this._cooldown = cooldown;

			this._level = level;
			this._experience = experience;
			this._expToLvl = expToLvl;

			this._wood = wood;
			this._core = core;
			this._cap = cap;
		}

		public boolean isCanRegen() {
			return this._canRegen;
		}

		public float getMaxMana() {
			return this._maxMana;
		}

		public float getCurrMana() {
			return this._currMana;
		}

		public int getCooldown() {
			return this._cooldown;
		}

		public int getLevel() {
			return this._level;
		}

		public int getExperience() {
			return this._experience;
		}

		public int getExpToLvl() {
			return this._expToLvl;
		}

		public String getWood() {
			return this._wood;
		}

		public String getCore() {
			return this._core;
		}

		public String getCap() {
			return this._cap;
		}
	}
}
