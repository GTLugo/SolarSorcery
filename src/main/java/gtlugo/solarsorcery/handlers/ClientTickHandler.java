//CREATED BY VASKII AS PART OF PSI
package gtlugo.solarsorcery.handlers;

import java.util.ArrayDeque;
import java.util.Queue;

import gtlugo.solarsorcery.lib.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientTickHandler {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static volatile Queue<Runnable> scheduledActions = new ArrayDeque();

	public static int ticksInGame = 0;
	public static float partialTicks = 0;
	public static float delta = 0;
	public static float total = 0;

	private void calcDelta() {
		float oldTotal = total;
		total = ticksInGame + partialTicks;
		delta = total - oldTotal;
	}

	@SubscribeEvent
	public void renderTick(RenderTickEvent event) {
		if(event.phase == Phase.START)
			partialTicks = event.renderTickTime;
	}

	@SubscribeEvent
	public void clientTickEnd(ClientTickEvent event) {
		if(event.phase == Phase.END) {
			Minecraft mc = Minecraft.getInstance();
			/*if(mc.world == null)
				PlayerDataHandler.cleanup();
			else*/ if(mc.player != null) {
				while(!scheduledActions.isEmpty())
					scheduledActions.poll().run();
			}

			HUDHandler.tick();

			Screen gui = mc.currentScreen;
			if(gui == null || !gui.isPauseScreen()) {
				ticksInGame++;
				partialTicks = 0;
			}

			calcDelta();
		}
	}

}