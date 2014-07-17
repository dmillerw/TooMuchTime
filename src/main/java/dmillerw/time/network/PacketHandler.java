package dmillerw.time.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import dmillerw.time.network.packet.PacketServerSettings;

/**
 * @author dmillerw
 */
public class PacketHandler {

	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("TooMuchTime");

	public static void initialize() {
		INSTANCE.registerMessage(PacketServerSettings.class, PacketServerSettings.class, 0, Side.CLIENT);
	}
}
