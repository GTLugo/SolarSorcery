/**
 * This class was created by <Vazkii>. 
 * MODIFIED BY TENEBRIS
 **/
package gtlugo.solarsorcery.handlers;

import gtlugo.solarsorcery.networking.MessageDropIn;
import gtlugo.solarsorcery.networking.NetworkMessage;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class NetworkHandler {
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("solarsorc");

	private static int i = 0;

	public static <T extends NetworkMessage<T>> void register(Class<T> clazz, Side handlerSide) {
		INSTANCE.registerMessage(clazz, clazz, i++, handlerSide);
	}
	
	public static void initMessages() {
		register(MessageDropIn.class, Side.SERVER);
	}

}
