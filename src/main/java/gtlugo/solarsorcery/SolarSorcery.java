package gtlugo.solarsorcery;

import gtlugo.solarsorcery.lib.Reference;
import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import java.util.Optional;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Reference.MOD_ID)
@Mod.EventBusSubscriber(modid = Reference.MOD_ID)
public class SolarSorcery {
	// Directly reference a log4j logger.
	public static final Logger LOGGER = LogManager.getLogger();

	public SolarSorcery() {
		//noinspection Convert2MethodRef
		DistExecutor.runForDist(
				() -> () -> new SideProxy.Client(),
				() -> () -> new SideProxy.Server()
		);
	}


	@Nonnull
	public static String getVersion() {
		Optional<? extends ModContainer> o = ModList.get().getModContainerById(Reference.MOD_ID);
		if (o.isPresent()) {
			return o.get().getModInfo().getVersion().toString();
		}
		return "NONE";
	}


	public static boolean isDevBuild() {
		String version = getVersion();
		return "NONE".equals(version);
	}


	@Nonnull
	public static ResourceLocation getId(String path) {
		return new ResourceLocation(Reference.MOD_ID, path);
	}

	// You can use EventBusSubscriber to automatically subscribe events on the contained class (this is subscribing to the MOD
	// Event bus for receiving Registry Events)
	@Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
	public static class RegistryEvents {
		@SubscribeEvent
		public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent) {
			// register a new block here
			LOGGER.info("HELLO from Register Block");
		}
	}
}