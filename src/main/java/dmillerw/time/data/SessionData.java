package dmillerw.time.data;

import com.google.common.io.ByteArrayDataInput;
import dmillerw.time.TooMuchTime;
import net.minecraftforge.common.Configuration;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author dmillerw
 */
public class SessionData {

	public static boolean modEnabled = true;

	public static int dayDuration;
	public static int nightDuration;

	public static boolean staticMoon;

	public static int staticAngle;

	public static void writeToStream(DataOutputStream dos) throws IOException {
		dos.writeInt(dayDuration);
		dos.writeInt(nightDuration);
		dos.writeBoolean(staticMoon);
		dos.writeInt(staticAngle);
	}

	public static void readFromStream(ByteArrayDataInput input) throws IOException {
		dayDuration = input.readInt();
		nightDuration = input.readInt();
		staticMoon = input.readBoolean();
		staticAngle = input.readInt();
	}

	public static void loadFromConfiguration(Configuration configuration) {
		modEnabled = true;
		dayDuration = configuration.get("general", "dayDuration", 12000, "Constant duration for each Minecraft day").getInt();
		nightDuration = configuration.get("general", "nightDuration", 12000, "Constant duration for each Minecraft night").getInt();
		staticMoon = configuration.get("general", "staticMoon", false, "Whether the moon should be the one affected by staticAngle. Setting this to false will make the sun be static instead").getBoolean(false);
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
		TooMuchTime.configuration.getCategory(category).get(key).set(value);
	}

	public static void setConfigurationProperty(String category, String key, boolean value) {
		TooMuchTime.configuration.getCategory(category).get(key).set(value);
	}
}
