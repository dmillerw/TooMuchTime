package dmillerw.time;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import dmillerw.time.data.SessionData;
import dmillerw.time.handler.WorldTickHandler;
import dmillerw.time.network.NetworkEventHandler;
import dmillerw.time.network.PacketHandler;
import dmillerw.time.world.WorldProviderOverworld;
import net.minecraftforge.common.Configuration;

/**
 * @author dmillerw
 */
@Mod(modid = "TooMuchTime", name = "TooMuchTime", version = "%MOD_VERSION%", dependencies = "required-after:Forge@[%FORGE_VERSION%,)")
@NetworkMod(channels = {"TooMuchTime"}, serverSideRequired = true, clientSideRequired = false, packetHandler = PacketHandler.class)
public class TooMuchTime {

	public static Configuration configuration;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configuration = new Configuration(event.getSuggestedConfigurationFile());
		configuration.load();

		SessionData.loadFromConfiguration(configuration);

		NetworkEventHandler.register();
		WorldTickHandler.register();
		WorldProviderOverworld.overrideDefault();
	}
}
