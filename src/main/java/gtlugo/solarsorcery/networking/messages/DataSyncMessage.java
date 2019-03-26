package gtlugo.solarsorcery.networking.messages;

import gtlugo.solarsorcery.SolarSorcery;
import gtlugo.solarsorcery.handlers.ClientTickHandler;
import gtlugo.solarsorcery.networking.NetworkMessage;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SuppressWarnings({ "serial", "rawtypes" })
public class DataSyncMessage extends NetworkMessage {
	// A default constructor is always required

	public boolean _canRegen;
	public int _regenCooldown;
	public float _maxMana;
	public float _currMana;
	
	public int _level;
	public int _experience;
	public int _expToLvl;
	
	public String _wood;
	public String _core;
	public String _cap;
	
	public DataSyncMessage() {
	}
	
	public DataSyncMessage(boolean canRegen, int regenCooldown, float maxMana, float currMana, int level, int experience, int expToLvl, String wood, String core, String cap) {
		_canRegen = canRegen;
		_regenCooldown = regenCooldown;
		_maxMana = maxMana;
		_currMana = currMana;
		
		_level = level;
		_experience = experience;
		_expToLvl = expToLvl;
		
		_wood = wood;
		_core = core;
		_cap = cap;
	}
	
	@Override 
	@SideOnly(Side.CLIENT)
	public IMessage handleMessage(MessageContext ctx) {
		ClientTickHandler.scheduledActions.add(() -> {
			IPlayerData data = SolarSorcery.proxy.getPlayerEntityFromContext(ctx).getCapability(PlayerDataProvider.TAG_DATA, null);
			data.setCanRegen(_canRegen);
			data.setRegenCooldown(_regenCooldown);
			data.setMaxMana(_maxMana);
			data.setCurrMana(_currMana);
			
			data.setLevel(_level);
			data.addExp(_experience, true);
			data.setExpToLvl(_expToLvl);
			
			data.setWood(_wood);
			data.setCore(_core);
			data.setCap(_cap);
		});
		return null;
	}
}
