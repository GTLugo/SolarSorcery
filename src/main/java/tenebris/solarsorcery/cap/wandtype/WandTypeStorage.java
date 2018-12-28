package tenebris.solarsorcery.cap.wandtype;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class WandTypeStorage implements IStorage<IWandType> 
{ 
	@Override 
	public NBTBase writeNBT(Capability<IWandType> capability, IWandType instance, EnumFacing side) { 
		NBTTagCompound tag = new NBTTagCompound();
		tag.setString("wand_wood", instance.getWood());
		tag.setString("wand_core", instance.getCore());
		tag.setString("wand_cap", instance.getCap());
		return tag;
	} 

	@Override 
	public void readNBT(Capability<IWandType> capability, IWandType instance, EnumFacing side, NBTBase nbt) {
		instance.setWood(((NBTTagCompound) nbt).getString("wand_wood"));
		instance.setCore(((NBTTagCompound) nbt).getString("wand_core"));
		instance.setCap(((NBTTagCompound) nbt).getString("wand_cap"));
	} 
}
