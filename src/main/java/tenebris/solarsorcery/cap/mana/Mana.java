package tenebris.solarsorcery.cap.mana;

import net.minecraft.entity.player.EntityPlayerMP;
import tenebris.solarsorcery.Reference;
import tenebris.solarsorcery.handlers.SolarsorcPacketHandler;
import tenebris.solarsorcery.packets.ClientSyncPacket;

/*Default*/
public class Mana implements IMana{
	private boolean canRegen = true;
	private float maxMana = 25.0f;
	private float currMana = 25.0f;
	
	public void use(float points) { 
		this.currMana = Math.max(this.currMana + points, 0.0f);
	} 

	public void give(float points) { 
		this.currMana = Math.min(this.currMana + points, this.maxMana); 
	} 
	
	public void change(float points) {
		if (points > 0.0F) give(points);
		else use(points);
	} 

	public void setMaxMana(float points) { 
		this.maxMana = points; 
	} 
	
	public void setCurrMana(float points) { 
		this.currMana = points; 
	} 

	public float getMaxMana() {
		return this.maxMana; 
	} 
	
	public float getCurrMana() { 
		return this.currMana; 
	}

	public boolean isCanRegen() {
		return this.canRegen;
	}

	public void setCanRegen(boolean canRegen) {
		this.canRegen = canRegen;
	}

	public void syncMana(EntityPlayerMP player) {
		System.out.println(Reference.MODID + ":starting mana sync");
		IMana mana = player.getCapability(ManaProvider.MANACAP, null);
		SolarsorcPacketHandler.INSTANCE.sendTo(new ClientSyncPacket(mana), player);
		System.out.println(Reference.MODID + ":synced mana");
	} 
}

