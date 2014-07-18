package dmillerw.time.data;

import dmillerw.time.TooMuchTime;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.common.config.Configuration;

/**
 * @author dmillerw
 */
public class SessionData {

	public static boolean modEnabled = true;

	public static int dayDuration;
	public static int nightDuration;

	public static boolean staticMoon;

	public static int staticAngle;

	public static void writeToBuffer(ByteBuf buffer) {
		buffer.writeInt(dayDuration);
		buffer.writeInt(nightDuration);
		buffer.writeBoolean(staticMoon);
		buffer.writeInt(staticAngle);
	}

	public static void readFromBuffer(ByteBuf buffer) {
		dayDuration = buffer.readInt();
		nightDuration = buffer.readInt();
		staticMoon = buffer.readBoolean();
		staticAngle = buffer.readInt();
	}

	public static void loadFromConfiguration(Configuration configuration) {
		modEnabled = true;
		dayDuration = configuration.get("general", "dayDuration", 12000, "Constant duration for each Minecraft day").getInt();
		nightDuration = configuration.get("general", "nightDuration", 12000, "Constant duration for each Minecraft night").getInt();
		staticMoon = configuration.get("general", "staticMoon", false, "Whether the moon should be the one affected by staticAngle. Setting this to false will make the sun be static instead").getBoolean();
		staticAngle = configuration.get("general", "staticAngle", -1, "Statically sets the sun/moon to a specific angle, can be used for infinite day/night. Set to -1 to disable").getInt();

		if (dayDuration <= 0) {
			dayDuration = 1;
		}

		if (nightDuration <= 0) {
			nightDuration = 1;
		}

		if (staticAngle < 0) {
			staticAngle = 0;
		} else if (staticAngle > 180) {
			staticAngle = 360;
		}

		if (configuration.hasChanged()) {
			configuration.save();
		}
	}

	public static void setConfigurationProperty(String category, String key, int value) {
		TooMuchTime.configuration.getCategory(category).get(key).setValue(value);
	}

	public static void setConfigurationProperty(String category, String key, boolean value) {
		TooMuchTime.configuration.getCategory(category).get(key).setValue(value);
	}
}
