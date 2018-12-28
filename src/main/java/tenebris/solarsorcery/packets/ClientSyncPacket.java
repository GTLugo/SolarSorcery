package tenebris.solarsorcery.packets;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tenebris.solarsorcery.SolarSorcery;
import tenebris.solarsorcery.cap.mana.IMana;

public class ClientSyncPacket implements IMessage {
	// A default constructor is always required

	private IMana _manaCap;
	
	public ClientSyncPacket() {
	}
	
	public ClientSyncPacket(IMana mana) {
		this._manaCap = mana;
	}
	
	public IMana getMana() {
		return this._manaCap;
	}
	
	public void setMana(IMana mana) {
		this._manaCap = mana;
	}
	
	@Override 
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(this._manaCap.getCurrMana());
		buf.writeFloat(this._manaCap.getMaxMana());
	}

	@Override 
	public void fromBytes(ByteBuf buf) {
		this._manaCap.setCurrMana(buf.readFloat());
		this._manaCap.setMaxMana(buf.readFloat());
	}
	
	public static class ClientSyncPacketHandler implements IMessageHandler<ClientSyncPacket, IMessage> {
		// Do note that the default constructor is required, but implicitly defined in this case
		@Override 
		public IMessage onMessage(ClientSyncPacket message, MessageContext ctx) {
			// This is the player the packet was sent to the server from
			
			FMLCommonHandler.instance().getWorldThread(ctx.netHandler).addScheduledTask(() -> SolarSorcery.proxy.syncClientMana(message, ctx));
			return null;
		}
	}
}
