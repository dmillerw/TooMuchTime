package dmillerw.time.data;

import net.minecraftforge.common.config.Configuration;

/**
 * @author dmillerw
 */
public class SessionData {

	public static boolean modEnabled = true;

	public static int dayDuration;
	public static int nightDuration;

	public static void loadFromConfiguration(Configuration configuration) {
		modEnabled = true;
		dayDuration = Math.max(1, configuration.get("general", "dayDuration", 12000).getInt());
		nightDuration = Math.max(1, configuration.get("general", "nightDuration", 12000).getInt());

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}
}
