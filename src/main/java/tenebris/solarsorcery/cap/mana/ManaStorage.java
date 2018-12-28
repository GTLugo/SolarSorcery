package tenebris.solarsorcery.cap.mana;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class ManaStorage implements IStorage<IMana> 
{ 
	@Override 
	public NBTBase writeNBT(Capability<IMana> capability, IMana instance, EnumFacing side) { 
		NBTTagCompound tag = new NBTTagCompound();
		tag.setFloat("max_mana", instance.getMaxMana());
		tag.setFloat("current_mana", instance.getCurrMana());
		tag.setBoolean("canRegen", instance.isCanRegen());
		return tag; 
	} 

	@Override 
	public void readNBT(Capability<IMana> capability, IMana instance, EnumFacing side, NBTBase nbt) {
		instance.setMaxMana(((NBTTagCompound) nbt).getFloat("max_mana")); 
		instance.setCurrMana(((NBTTagCompound) nbt).getFloat("current_mana")); 
		instance.setCanRegen(((NBTTagCompound) nbt).getBoolean("canRegen"));
	} 
}
