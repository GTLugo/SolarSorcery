package tenebris.solarsorcery.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tenebris.solarsorcery.cap.mana.IMana;
import tenebris.solarsorcery.cap.mana.ManaProvider;

public class ManaChangePacket implements IMessage {
	// A default constructor is always required
	public ManaChangePacket(){}

	private float _manaPoints;
	public ManaChangePacket(float manaPoints) {
		this._manaPoints = manaPoints;
	}
	
	public float getSent() {
		return this._manaPoints;
	}
	
	@Override 
	public void toBytes(ByteBuf buf) {
		// Writes the int into the buf
		buf.writeFloat(_manaPoints);
	}

	@Override 
	public void fromBytes(ByteBuf buf) {
		// Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
		_manaPoints = buf.readFloat();
	}
	
	public static class ManaChangePacketHandler implements IMessageHandler<ManaChangePacket, IMessage> {
		// Do note that the default constructor is required, but implicitly defined in this case
		@Override 
		public IMessage onMessage(ManaChangePacket message, MessageContext ctx) {
			// This is the player the packet was sent to the server from
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			IMana mana = serverPlayer.getCapability(ManaProvider.MANACAP, null);
			// The value that was sent
			float change = message.getSent();
			// Execute the action on the main server thread by adding it as a scheduled task
			serverPlayer.getServerWorld().addScheduledTask(() -> {
				String chatMessage = "NULL";
				if ((mana.getCurrMana() + change) < 0.0) { 
					chatMessage = "Not enough Aether"; 
				}
				else if ((mana.getCurrMana() + change) > mana.getMaxMana()) { 
					chatMessage = "Aether maxed out";
					mana.setCurrMana(mana.getMaxMana());
				}
				else { 
					mana.change(change);
					chatMessage = String.format("%d / %d Aether (change = %f)", (int) mana.getCurrMana(), (int) mana.getMaxMana(), change); 
				}
		    	Minecraft.getMinecraft().player.sendMessage(new TextComponentString(chatMessage));
			});
			// No response packet
			return null;
		}
	}
}
