package gtlugo.solarsorcery.playerdata;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class PlayerDataProvider implements ICapabilitySerializable<INBT> {

	protected final Capability<IPlayerData> DATA_CAPABILITY;
	private final Direction facing;
	private final IPlayerData instance;
	private final LazyOptional<IPlayerData> lazyOptional;

	//private IPlayerData instance = DATA_CAPABILITY.getDefaultInstance();

	public PlayerDataProvider(final Capability<IPlayerData> capability, @Nullable final Direction facing, @Nullable final IPlayerData instance) {

		this.DATA_CAPABILITY = capability;
		this.facing = facing;

		this.instance = instance;

		if (this.instance != null) {
			lazyOptional = LazyOptional.of(() -> this.instance);
		} else {
			lazyOptional = LazyOptional.empty();
		}
	}

	public final Capability<IPlayerData> getCapability() {
		return DATA_CAPABILITY;
	}

	@Nullable
	public final IPlayerData getInstance() {
		return instance;
	}

	@Nullable
	public final Direction getFacing() {
		return facing;
	}

	@Override
	public <T> LazyOptional<T> getCapability(final Capability<T> capability, Direction facing) {

		return getCapability().orEmpty(capability, lazyOptional);
	}

	/*
        @Nullable
        @Override
        public INBT serializeNBT() {
            final IPlayerData instance = getInstance();

            if (instance == null) {
                return null;
            }

            return getCapability().writeNBT(instance, getFacing());
        }

        @Override
        public void deserializeNBT(final INBT nbt) {
            final IPlayerData instance = getInstance();

            if (instance == null) {
                return;
            }

            getCapability().readNBT(instance, getFacing(), nbt);
        }
    */
	@Override
	public INBT serializeNBT() {
		final IPlayerData instance = getInstance();
		if (instance == null) return null;
		return DATA_CAPABILITY.getStorage().writeNBT(getCapability(), getInstance(), null);
	}

	@Override
	public void deserializeNBT(INBT nbt) {
		final IPlayerData instance = getInstance();
		if (instance == null) return;
		DATA_CAPABILITY.getStorage().readNBT(getCapability(), getInstance(), null, nbt);
	}
	/*
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
		return null;
	}

	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
		return null;
	}

	 */
}
