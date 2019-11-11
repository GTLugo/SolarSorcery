package gtlugo.solarsorcery.playerdata;

import gtlugo.solarsorcery.lib.Reference;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

import javax.annotation.Nullable;

public class PlayerDataStorage implements IStorage<IPlayerData> {
	@Nullable
	@Override
	public INBT writeNBT(Capability<IPlayerData> capability, IPlayerData instance, Direction side) {
		CompoundNBT tag = new CompoundNBT();
		tag.putBoolean(Reference.TAG_CANREGEN, instance.isCanRegen());
		tag.putFloat(Reference.TAG_MAXMANA, instance.getMaxMana());
		tag.putFloat(Reference.TAG_CURRMANA, instance.getCurrMana());
		tag.putInt(Reference.TAG_COOLDOWN, instance.getCooldown());

		tag.putInt(Reference.TAG_LEVEL, instance.getLevel());
		tag.putInt(Reference.TAG_EXPERIENCE, instance.getExperience());
		tag.putInt(Reference.TAG_EXPTOLEVEL, instance.getExpToLvl());
		
		tag.putString(Reference.TAG_WANDWOOD, instance.getWood());
		tag.putString(Reference.TAG_WANDCORE, instance.getCore());
		tag.putString(Reference.TAG_WANDDECO, instance.getDeco());
		return tag; 
	}


	@Override
	public void readNBT(Capability<IPlayerData> capability, IPlayerData instance, Direction side, INBT nbt) {
		instance.setCanRegen(((CompoundNBT) nbt).getBoolean(Reference.TAG_CANREGEN));
		instance.setMaxMana(((CompoundNBT) nbt).getFloat(Reference.TAG_MAXMANA));
		instance.setCurrMana(((CompoundNBT) nbt).getFloat(Reference.TAG_CURRMANA));
		instance.setCooldown(((CompoundNBT) nbt).getInt(Reference.TAG_COOLDOWN));
		
		instance.setLevel(((CompoundNBT) nbt).getInt(Reference.TAG_LEVEL));
		instance.addExp(((CompoundNBT) nbt).getInt(Reference.TAG_EXPERIENCE), true);
		instance.setExpToLvl(((CompoundNBT) nbt).getInt(Reference.TAG_EXPTOLEVEL));
		
		instance.setWood(((CompoundNBT) nbt).getString(Reference.TAG_WANDWOOD));
		instance.setCore(((CompoundNBT) nbt).getString(Reference.TAG_WANDCORE));
		instance.setDeco(((CompoundNBT) nbt).getString(Reference.TAG_WANDDECO));
	}
}
