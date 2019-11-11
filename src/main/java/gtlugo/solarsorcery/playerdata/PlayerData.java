package gtlugo.solarsorcery.playerdata;

import gtlugo.solarsorcery.SolarSorcery;
import gtlugo.solarsorcery.handlers.NetworkHandler;
import gtlugo.solarsorcery.init.item.wand.wandparts.WandPart;
import gtlugo.solarsorcery.lib.Reference;
import gtlugo.solarsorcery.networking.DataSyncMessage;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

import static gtlugo.solarsorcery.init.item.wand.wandparts.WandMaterials.*;

public class PlayerData implements IPlayerData {
	public static final ResourceLocation DATA_ID = SolarSorcery.getId("data");

	@CapabilityInject(IPlayerData.class)
	public static final Capability<IPlayerData> DATA_CAPABILITY = null;
	public static final Direction DEFAULT_FACING = null;

	public static int _tickCount = 0;


	private boolean _canRegen = true;
	private float _maxMana = 10.0f;
	private float _currMana = 10.0f;
	private int _cooldown = 20;
	
	private int _level = 1;
	private int _experience = 0;
	private int _expToLvl = 1000;
	
	private String _wood = null;
	private String _core = null;
	private String _deco = null;

	//region Getters and Setters
	/*
	 * MANA SYSTEM
	 */

	private void useMana(float points) {
		this._currMana = Math.max(this._currMana + points, 0.0f);
	} 

	private void giveMana(float points) {
		this._currMana = Math.min(this._currMana + points, this._maxMana); 
	} 
	
	@Override
	public void changeMana(float points) {
		if (points > 0.0F) giveMana(points);
		else useMana(points);
	}
	
	@Override
	public void setMaxMana(float points) { 
		this._maxMana = points; 
	} 
	
	@Override
	public void setCurrMana(float points) { 
		this._currMana = points; 
	} 
	
	@Override
	public void setCanRegen(boolean canRegen) {
		this._canRegen = canRegen;
	}
	
	@Override
	public float getMaxMana() {
		return this._maxMana; 
	} 
	
	@Override
	public float getCurrMana() { 
		return this._currMana; 
	}

	@Override
	public boolean isCanRegen() {
		return this._canRegen;
	}

	@Override
	public void setCooldown(int time) {
		this._cooldown = time;
	}

	@Override
	public int getCooldown() {
		return this._cooldown;
	}

	/*
	 * LEVELING SYSTEM
	 */

	@Override
	public void setLevel(int level) {
		this._level = level;
	}

	@Override
	public void addExp(int experience, boolean overrideFlag) {
		if (!overrideFlag) this._experience += experience;
		else this._experience = experience;
	}

	@Override
	public void setExpToLvl(int experience) {
		this._expToLvl = experience;
	}

	@Override
	public int getLevel() {
		return this._level;
	}

	@Override
	public int getExperience() {
		return this._experience;
	}

	@Override
	public int getExpToLvl() {
		return this._expToLvl;
	}

	@Override
	public void changeLevel(int level, int expOverflow) {
		this._level = level;
		this._experience = expOverflow;
		
		if (this._level < 50) this._expToLvl *= (40 * (Math.pow(this._level, 2)) + 1000); //function: 40x^2 + 1000
		else this._expToLvl = 100000;
		
		this._maxMana = (10.0f * (float) Math.pow(this._level, 2)); //function: 10x^2
		this._currMana = this._maxMana;
	}
	
	/*
	 * WANDTYPES
	 */

	@Override
	public void setWood(String wood) {
		this._wood = wood;
	}

	@Override
	public void setCore(String core) {
		this._core = core;
	}

	@Override
	public void setDeco(String deco) {
		this._deco = deco;
	}
	
	@Override
	public String getWood() {
		return this._wood;
	}

	@Override
	public String getCore() {
		return this._core;
	}

	@Override
	public String getDeco() {
		return this._deco;
	}

	public static LazyOptional<IPlayerData> getPlayerData(final PlayerEntity player) {
		return player.getCapability(DATA_CAPABILITY, DEFAULT_FACING);
	}

	public static ICapabilityProvider createProvider(final IPlayerData playerData) {
		return new PlayerDataProvider(DATA_CAPABILITY, DEFAULT_FACING, playerData);
	}
	//endregion

	@SuppressWarnings("unused")
	//@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
	public static class CapabilityHandler {

		@SubscribeEvent
		public void attachCapability(AttachCapabilitiesEvent<Entity> event) {
			if (!(event.getObject() instanceof PlayerEntity)) return;
			final PlayerData data = new PlayerData();
			event.addCapability(DATA_ID, createProvider(data));
		}

		@SubscribeEvent
		public void onPlayerTick(TickEvent.PlayerTickEvent event) {
			PlayerEntity player = event.player;
			if (player.world.isRemote) return;
			if (player instanceof ServerPlayerEntity) {
				if (player == null) return;
				getPlayerData(player).ifPresent(data -> {
					int timer = 20;

					if (!data.isCanRegen()) {
						if (data.getCooldown() > 0) {
							data.setCooldown(data.getCooldown() - 1);
						}
						else {
							data.setCanRegen(true);
						}
					}
					else {
						if (player.world.getLight(player.getPosition()) > 7) {
							timer /= 2;
						}
						if (_tickCount >= timer) {
							float regen = data.getMaxMana() * 0.05f;
							data.changeMana(regen);
							//NetworkHandler.sendToServer(new ManaChangeMessage(regen));
							//System.out.println(Reference.MOD_ID + ": regen mana");
							_tickCount = 0;
						}
						else ++_tickCount;
					}
					DataSyncMessage.DataSyncData syncMessage = new DataSyncMessage.DataSyncData(
							data.isCanRegen(),
							data.getMaxMana(),
							data.getCurrMana(),
							data.getCooldown(),
							data.getLevel(),
							data.getExperience(),
							data.getExpToLvl(),
							data.getWood(),
							data.getCore(),
							data.getDeco()
					);
					NetworkHandler.sendTo(new DataSyncMessage(syncMessage), (ServerPlayerEntity) player);
				});
			}
		}

		@SubscribeEvent
		public void onPlayerLogsIn(PlayerEvent.PlayerLoggedInEvent event) {
			PlayerEntity player = event.getPlayer();
			if (player.world.isRemote) return;
			getPlayerData(player).ifPresent(data -> {
				if (data.getWood() == null && data.getCore() == null && data.getDeco() == null) {
					Random rand = new Random();
					data.setWood(WoodMaterials.get(rand.nextInt(WoodMaterials.size()))._materialName);
					data.setCore(CoreMaterials.get(rand.nextInt(CoreMaterials.size()))._materialName);
					data.setDeco(DecoMaterials.get(rand.nextInt(DecoMaterials.size()))._materialName);
				}
				String message = String.format("Your personalized wand:");
				player.sendMessage(new StringTextComponent(message));

				message = String.format("[Wood: %s] [Core: %s] [Deco: %s]", data.getWood().toString(), data.getCore().toString(), data.getDeco().toString());
				player.sendMessage(new StringTextComponent(message));

				message = String.format("Aether: [ %d / %d ]", (int) data.getCurrMana(), (int) data.getMaxMana());
				player.sendMessage(new StringTextComponent(message));

				message = String.format("Level: [ %d ]", (int) data.getLevel());
				player.sendMessage(new StringTextComponent(message));
			});
		}

		@SubscribeEvent
		public void onPlayerClone(PlayerEvent.Clone event) {
			PlayerEntity player = event.getPlayer();
			if (player.world.isRemote) return;
			getPlayerData(event.getOriginal()).ifPresent(oldData -> {
				getPlayerData(player).ifPresent(data -> {
					data.setCanRegen(oldData.isCanRegen());
					data.setCurrMana(oldData.getCurrMana());
					data.setMaxMana(oldData.getMaxMana());
					data.setCooldown(oldData.getCooldown());

					data.setLevel(oldData.getLevel());
					data.addExp(oldData.getExperience(), true);
					data.setExpToLvl(oldData.getExpToLvl());

					data.setWood(oldData.getWood());
					data.setCore(oldData.getCore());
					data.setDeco(oldData.getDeco());
				});
			});

		}
	}
}
