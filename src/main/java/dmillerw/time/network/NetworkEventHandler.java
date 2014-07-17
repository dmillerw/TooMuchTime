package dmillerw.time.network;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import dmillerw.time.TooMuchTime;
import dmillerw.time.data.SessionData;
import dmillerw.time.network.packet.PacketServerSettings;
import net.minecraft.entity.player.EntityPlayerMP;

/**
 * @author dmillerw
 */
public class NetworkEventHandler {

	public static void register() {
		FMLCommonHandler.instance().bus().register(new NetworkEventHandler());
	}

	@SubscribeEvent
	public void onClientLogin(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		if (!event.isLocal) {
			SessionData.modEnabled = false;
		}
	}

	@SubscribeEvent
	public void onClientLogout(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
		SessionData.loadFromConfiguration(TooMuchTime.configuration);
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
		PacketHandler.INSTANCE.sendTo(new PacketServerSettings(), (EntityPlayerMP) event.player);
	}
}
