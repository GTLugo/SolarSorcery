package gtlugo.solarsorcery.networking.messages;

import gtlugo.solarsorcery.handlers.ClientTickHandler;
import gtlugo.solarsorcery.networking.NetworkMessage;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings({ "rawtypes", "serial" })
public class LevelChangeMessage extends NetworkMessage {

	public int _newLevel;
	public boolean _keepExperience;
	public boolean _isDelta;
	
	public LevelChangeMessage(){}
	
	public LevelChangeMessage(int newLevel, boolean keepExperience) {
		this._newLevel = newLevel;
		this._keepExperience = keepExperience;
		this._isDelta = true;
	}
	
	public LevelChangeMessage(int newLevel, boolean keepExperience, boolean notDeltaFlag) {
		this._newLevel = newLevel;
		this._keepExperience = keepExperience;
		this._isDelta = notDeltaFlag;
	}
	
	@Override 
	public IMessage handleMessage(MessageContext ctx) {
		EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
		IPlayerData data = serverPlayer.getCapability(PlayerDataProvider.TAG_DATA, null);
		ClientTickHandler.scheduledActions.add(() -> {
			//String chatMessage = "NULL";
			int maxLevel = 400;
			int minLevel = 1;
			
			if (_isDelta) {
				if (_newLevel > 0) {
					int exp = (_keepExperience) ? data.getExperience() : Math.max(data.getExperience() - data.getExpToLvl(), 0);
					data.changeLevel((int) Math.min(data.getLevel() + _newLevel, maxLevel), exp);
				}
				else if (_newLevel < 0) {
					//experience is always wiped when leveling down to prevent immediately leveling up
					data.changeLevel((int) Math.max(data.getLevel() + _newLevel, minLevel), 0);
				}
				else {
					//do nothing if _change is zeo
				}
			}
			else {
				int exp = (_keepExperience) ? data.getExperience() : Math.max(data.getExperience() - data.getExpToLvl(), 0);
				data.changeLevel((int) Math.min(_newLevel, maxLevel), exp);
			}
	    	//Minecraft.getMinecraft().player.sendMessage(new TextComponentString(chatMessage));
		});
		return null;
	}
}
