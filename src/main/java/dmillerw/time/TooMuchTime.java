package dmillerw.time;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import dmillerw.time.handler.WorldTickHandler;
import dmillerw.time.world.WorldProviderOverworld;
import net.minecraftforge.common.Configuration;

/**
 * @author dmillerw
 */
@Mod(modid = "TooMuchTime", name = "TooMuchTime", version = "%MOD_VERSION%", dependencies = "required-after:Forge@[%FORGE_VERSION%,)")
public class TooMuchTime {

	public static Configuration configuration;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		configuration = new Configuration(event.getSuggestedConfigurationFile());
		configuration.load();

		WorldTickHandler.dayDuration = Math.max(1, configuration.get("general", "dayDuration", 12000).getInt());
		WorldTickHandler.nightDuration = Math.max(1, configuration.get("general", "nightDuration", 12000).getInt());

		if (configuration.hasChanged()) {
			configuration.save();
		}

		WorldTickHandler.register();
		WorldProviderOverworld.overrideDefault();
	}
}
