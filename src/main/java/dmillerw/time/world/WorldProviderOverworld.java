package dmillerw.time.world;

import dmillerw.time.handler.WorldTickHandler;
import net.minecraft.world.WorldProviderSurface;
import net.minecraftforge.common.DimensionManager;

/**
 * @author dmillerw
 */
public class WorldProviderOverworld extends WorldProviderSurface {

	public static void overrideDefault() {
		DimensionManager.unregisterProviderType(DimensionManager.getProviderType(0));
		DimensionManager.unregisterDimension(0);
		DimensionManager.registerProviderType(0, WorldProviderOverworld.class, false);
		DimensionManager.registerDimension(0, 0);
	}

	@Override
	public float calculateCelestialAngle(long time, float partial) {
		// This method eventually returns a values from 0 to 1

		// 0 OR 1 has the sun in the center
		// 0.75 is dawn
		// 0.50 has the moon in the center
		// 0.25 is dusk

		// 0.25F is the value size of one pass through the sky

		int absoluteTime = (int) (time % (WorldTickHandler.dayDuration + WorldTickHandler.nightDuration));
		boolean day = absoluteTime >= 0 && absoluteTime < WorldTickHandler.dayDuration;
		int cycleTime = day ? (absoluteTime % WorldTickHandler.dayDuration) : (absoluteTime % WorldTickHandler.nightDuration);
		float value = 0.5F * ((float) cycleTime + partial) / (day ? (float)WorldTickHandler.dayDuration : (float)WorldTickHandler.nightDuration);

		if (day) {
			value += 0.75F;
		} else {
			value += 0.25F;
		}

		return value;
	}
}
