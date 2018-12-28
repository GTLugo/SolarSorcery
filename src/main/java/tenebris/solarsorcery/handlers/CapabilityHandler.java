package tenebris.solarsorcery.handlers;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tenebris.solarsorcery.Reference;
import tenebris.solarsorcery.cap.mana.ManaProvider;
import tenebris.solarsorcery.cap.wandtype.WandTypeProvider;

public class CapabilityHandler { 
	public static final ResourceLocation MANA_CAP = new ResourceLocation(Reference.MODID, "mana");
	public static final ResourceLocation WAND_CAP = new ResourceLocation(Reference.MODID, "wandType");  
	
	@SubscribeEvent 
	public void attachCapability(AttachCapabilitiesEvent<Entity> event) { 
		if (!(event.getObject() instanceof EntityPlayer)) return;
		event.addCapability(MANA_CAP, new ManaProvider());
		event.addCapability(WAND_CAP, new WandTypeProvider());
	} 
}
