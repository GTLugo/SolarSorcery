package gtlugo.solarsorcery.playerdata;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class PlayerDataStorage implements IStorage<IPlayerData> { 
	private static final String TAG_CANREGEN = "canRegen";
	private static final String TAG_REGENCOOLDOWN = "regenCooldown";
	private static final String TAG_MAXMANA = "maxMana";
	private static final String TAG_CURRMANA = "currMana";
	
	private static final String TAG_LEVEL = "level";
	private static final String TAG_EXPERIENCE = "experience";
	private static final String TAG_EXPTOLEVEL = "expToLevel";
	
	private static final String TAG_WANDWOOD = "wandWood";
	private static final String TAG_WANDCORE = "wandCore";
	private static final String TAG_WANDCAP = "wandCap";
	
	@Override 
	public NBTBase writeNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side) { 
		NBTTagCompound tag = new NBTTagCompound();
		tag.setBoolean(TAG_CANREGEN, instance.isCanRegen());
		tag.setInteger(TAG_REGENCOOLDOWN, instance.getRegenCooldown());
		tag.setFloat(TAG_MAXMANA, instance.getMaxMana());
		tag.setFloat(TAG_CURRMANA, instance.getCurrMana());
		
		tag.setInteger(TAG_LEVEL, instance.getLevel());
		tag.setInteger(TAG_EXPERIENCE, instance.getExperience());
		tag.setInteger(TAG_EXPTOLEVEL, instance.getExpToLvl());
		
		tag.setString(TAG_WANDWOOD, instance.getWood());
		tag.setString(TAG_WANDCORE, instance.getCore());
		tag.setString(TAG_WANDCAP, instance.getCap());
		return tag; 
	} 

	@Override 
	public void readNBT(Capability<IPlayerData> capability, IPlayerData instance, EnumFacing side, NBTBase nbt) {
		instance.setCanRegen(((NBTTagCompound) nbt).getBoolean(TAG_CANREGEN));
		instance.setRegenCooldown((((NBTTagCompound) nbt).getInteger(TAG_REGENCOOLDOWN)));
		instance.setMaxMana(((NBTTagCompound) nbt).getFloat(TAG_MAXMANA)); 
		instance.setCurrMana(((NBTTagCompound) nbt).getFloat(TAG_CURRMANA)); 
		
		instance.setLevel(((NBTTagCompound) nbt).getInteger(TAG_LEVEL));
		instance.addExp(((NBTTagCompound) nbt).getInteger(TAG_EXPERIENCE), true);
		instance.setExpToLvl(((NBTTagCompound) nbt).getInteger(TAG_EXPTOLEVEL));
		
		instance.setWood(((NBTTagCompound) nbt).getString(TAG_WANDWOOD));
		instance.setCore(((NBTTagCompound) nbt).getString(TAG_WANDCORE));
		instance.setCap(((NBTTagCompound) nbt).getString(TAG_WANDCAP));
	} 
}
