package gtlugo.solarsorcery.handlers;

import gtlugo.solarsorcery.Reference;
import gtlugo.solarsorcery.playerdata.PlayerDataProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CapabilityHandler { 
	public static final ResourceLocation TAG_DATA = new ResourceLocation(Reference.MODID, "data");
	
	@SubscribeEvent 
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) { 
		if (!(event.getObject() instanceof EntityPlayer)) return;
		event.addCapability(TAG_DATA, new PlayerDataProvider());
	} 
}
