package gtlugo.solarsorcery.networking.messages;

import gtlugo.solarsorcery.handlers.ClientTickHandler;
import gtlugo.solarsorcery.networking.NetworkMessage;
import gtlugo.solarsorcery.playerdata.IPlayerData;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings({ "rawtypes", "serial" })
public class ManaChangeMessage extends NetworkMessage {

	public float _change;
	
	public ManaChangeMessage(){}
	
	public ManaChangeMessage(float manaPoints) {
		this._change = manaPoints;
	}
	
	@Override 
	public IMessage handleMessage(MessageContext ctx) {
		EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
		IPlayerData data = serverPlayer.getCapability(PlayerDataProvider.TAG_DATA, null);
		ClientTickHandler.scheduledActions.add(() -> {
			//String chatMessage = "NULL";
			if ((data.getCurrMana() + _change) < 0.0) { 
				//chatMessage = "Not enough Aether"; 
			}
			else if ((data.getCurrMana() + _change) > data.getMaxMana()) { 
				//chatMessage = "Aether maxed out";
				data.setCurrMana(data.getMaxMana());
			}
			else { 
				data.changeMana(_change);
				//chatMessage = String.format("%d / %d Aether (change = %f)", (int) data.getCurrMana(), (int) data.getMaxMana(), _change); 
			}
	    	//Minecraft.getMinecraft().player.sendMessage(new TextComponentString(chatMessage));
		});
		return null;
	}
}
