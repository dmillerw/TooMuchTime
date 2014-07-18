package dmillerw.time.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import dmillerw.time.TooMuchTime;
import dmillerw.time.data.SessionData;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;

/**
 * @author dmillerw
 */
public class NetworkEventHandler implements IConnectionHandler {

	public static void register() {
		NetworkRegistry.instance().registerConnectionHandler(new NetworkEventHandler());
	}

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
		// Packet
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) { return null; }

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
		SessionData.modEnabled = false;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {}

	@Override
	public void connectionClosed(INetworkManager manager) {
		if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
			SessionData.loadFromConfiguration(TooMuchTime.configuration);
		}
	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {}
}
