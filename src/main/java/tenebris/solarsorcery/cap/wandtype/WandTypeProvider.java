package tenebris.solarsorcery.cap.wandtype;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class WandTypeProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(IWandType.class) 
	public static final Capability<IWandType> WANDCAP = null; 

	private IWandType instance = WANDCAP.getDefaultInstance(); 

	@Override 
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) { 
		return capability == WANDCAP; 
	} 

	@Override 
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) { 
		return capability == WANDCAP ? WANDCAP.<T> cast(this.instance) : null; 
	} 

	@Override 
	public NBTBase serializeNBT() { 
		return WANDCAP.getStorage().writeNBT(WANDCAP, this.instance, null); 
	} 

	@Override 
	public void deserializeNBT(NBTBase nbt) { 
		WANDCAP.getStorage().readNBT(WANDCAP, this.instance, null, nbt); 
	} 
}
