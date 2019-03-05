/**
 * This class was created by <Vazkii>. 
 * MODIFIED BY TENEBRIS
 **/
package gtlugo.solarsorcery.networking;

import gtlugo.solarsorcery.handlers.DropInHandler;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

@SuppressWarnings("serial")
public class MessageDropIn extends NetworkMessage<MessageDropIn> {

	public int slot;
	public ItemStack stack = ItemStack.EMPTY;
	
	public MessageDropIn() { }
	
	public MessageDropIn(int slot) { 
		this.slot = slot;
	}
	
	public MessageDropIn(int slot, ItemStack stack) { 
		this(slot);
		this.stack = stack;
	}
	
	@Override
	public IMessage handleMessage(MessageContext context) {
		EntityPlayerMP player = context.getServerHandler().player;
		player.mcServer.addScheduledTask(() -> DropInHandler.executeDropIn(player, slot, stack));
		
		return null;
	}
	
}
