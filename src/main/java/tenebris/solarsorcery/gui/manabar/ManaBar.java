package tenebris.solarsorcery.gui.manabar;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import tenebris.solarsorcery.Reference;
import tenebris.solarsorcery.cap.mana.IMana;
import tenebris.solarsorcery.cap.mana.ManaProvider;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = Reference.MODID)
public class ManaBar extends Gui {
	private Minecraft mc;
	//private static final ResourceLocation texture = new ResourceLocation("solarsorc", "textures/gui/mana_bar.png");

	public ManaBar(Minecraft mc) {
		super();
		this.mc = mc;
	}
	
	@SubscribeEvent(priority = EventPriority.HIGHEST)
	public void renderMana(RenderGameOverlayEvent event) {
		if (event.getType() == ElementType.ALL) {
			EntityPlayer player = this.mc.player;
			
			//SEND PACKET TO SERVER ASKING FOR MANA NUMBERS!!!!
			
			IMana mana = player.getCapability(ManaProvider.MANACAP, null);
			//int currMana = (int) mana.getCurrMana();
			//int maxMana = (int) mana.getMaxMana();
			
			ScaledResolution scaled = new ScaledResolution(mc);
	        //int width = scaled.getScaledWidth();
	        int height = scaled.getScaledHeight();
	        
	        if(mana != null) {
	        	String text = String.format("%d / %d Aether", (int) mana.getCurrMana(), (int) mana.getMaxMana()); 
	        	drawString(mc.fontRenderer, text, 4, (height) - 12, Integer.parseInt("FFAA00", 16));
	    		//System.out.println(Reference.MODID + ":drawing manaBar");
	        }
		}
	}
}
