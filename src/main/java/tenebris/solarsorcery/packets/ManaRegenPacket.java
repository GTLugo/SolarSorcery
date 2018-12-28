package tenebris.solarsorcery.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tenebris.solarsorcery.cap.mana.IMana;
import tenebris.solarsorcery.cap.mana.ManaProvider;

public class ManaRegenPacket implements IMessage {
	// A default constructor is always required
	public ManaRegenPacket(){}

	private boolean _canRegen;
	public ManaRegenPacket(boolean canRegen) {
		this._canRegen = canRegen;
	}
	
	public boolean getSent() {
		return this._canRegen;
	}
	
	@Override 
	public void toBytes(ByteBuf buf) {
		// Writes the int into the buf
		buf.writeBoolean(_canRegen);
	}

	@Override 
	public void fromBytes(ByteBuf buf) {
		// Reads the int back from the buf. Note that if you have multiple values, you must read in the same order you wrote.
		_canRegen = buf.readBoolean();
	}
	
	public static class ManaRegenPacketHandler implements IMessageHandler<ManaRegenPacket, IMessage> {
		// Do note that the default constructor is required, but implicitly defined in this case
		@Override 
		public IMessage onMessage(ManaRegenPacket message, MessageContext ctx) {
			// This is the player the packet was sent to the server from
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			IMana mana = serverPlayer.getCapability(ManaProvider.MANACAP, null);
			// The value that was sent
			boolean canRegen = message.getSent();
			// Execute the action on the main server thread by adding it as a scheduled task
			serverPlayer.getServerWorld().addScheduledTask(() -> {
				mana.setCanRegen(canRegen);
			});
			// No response packet
			return null;
		}
	}
}
