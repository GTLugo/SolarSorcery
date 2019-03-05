package gtlugo.solarsorcery.playerdata;

import net.minecraft.nbt.NBTBase;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public class PlayerDataProvider implements ICapabilitySerializable<NBTBase> {
	@CapabilityInject(IPlayerData.class) 
	public static final Capability<IPlayerData> TAG_DATA = null; 

	private IPlayerData instance = TAG_DATA.getDefaultInstance(); 

	@Override 
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) { 
		return capability == TAG_DATA; 
	} 

	@Override 
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) { 
		return capability == TAG_DATA ? TAG_DATA.<T> cast(this.instance) : null; 
	} 

	@Override 
	public NBTBase serializeNBT() { 
		return TAG_DATA.getStorage().writeNBT(TAG_DATA, this.instance, null); 
	} 

	@Override 
	public void deserializeNBT(NBTBase nbt) { 
		TAG_DATA.getStorage().readNBT(TAG_DATA, this.instance, null, nbt); 
	} 
}
