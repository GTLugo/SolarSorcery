package tenebris.solarsorcery.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tenebris.solarsorcery.Reference;
import tenebris.solarsorcery.cap.mana.IMana;
import tenebris.solarsorcery.cap.mana.ManaProvider;
import tenebris.solarsorcery.handlers.SolarsorcPacketHandler;

public class ManaUpdatePacket implements IMessage {
	// A default constructor is always required
	public ManaUpdatePacket(){}
	
	private static int _tickCount = 0;
	
	@Override 
	public void toBytes(ByteBuf buf) {
	}

	@Override 
	public void fromBytes(ByteBuf buf) {
	}
	
	public static int getTickCount() {
		return _tickCount;
	}

	public static void setTickCount(int tickCount) {
		ManaUpdatePacket._tickCount = tickCount;
	}

	public static class ManaUpdatePacketHandler implements IMessageHandler<ManaUpdatePacket, IMessage> {
		// Do note that the default constructor is required, but implicitly defined in this case
		@Override 
		public IMessage onMessage(ManaUpdatePacket message, MessageContext ctx) {
			// This is the player the packet was sent to the server from
			EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
			WorldServer serverWorld = serverPlayer.getServerWorld();
			IMana mana = serverPlayer.getCapability(ManaProvider.MANACAP, null);
			// The value that was sent
			// Execute the action on the main server thread by adding it as a scheduled task
			serverPlayer.getServerWorld().addScheduledTask(() -> {
				int timer = 1500;
				if (serverWorld.getLight(serverPlayer.getPosition()) > 7) {
					timer /= 3;
				}
				if (getTickCount() >= timer && mana.isCanRegen()) {
					SolarsorcPacketHandler.INSTANCE.sendToServer(new ManaChangePacket(1.0f));
					System.out.println(Reference.MODID + ": regen mana");
					setTickCount(0);
				}
				else setTickCount(getTickCount() + 1);
			});
			mana.syncMana(serverPlayer);
			// No response packet
			return null;
		}
	}
}
