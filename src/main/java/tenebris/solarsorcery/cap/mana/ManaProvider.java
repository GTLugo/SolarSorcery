package tenebris.solarsorcery.cap.mana;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class ManaProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(IMana.class) 
	public static final Capability<IMana> MANACAP = null; 

	private IMana instance = MANACAP.getDefaultInstance(); 

	@Override 
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) { 
		return capability == MANACAP; 
	} 

	@Override 
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) { 
		return capability == MANACAP ? MANACAP.<T> cast(this.instance) : null; 
	} 

	@Override 
	public NBTBase serializeNBT() { 
		return MANACAP.getStorage().writeNBT(MANACAP, this.instance, null); 
	} 

	@Override 
	public void deserializeNBT(NBTBase nbt) { 
		MANACAP.getStorage().readNBT(MANACAP, this.instance, null, nbt); 
	} 
}
