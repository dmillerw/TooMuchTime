package dmillerw.time.handler;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;

/**
 * @author dmillerw
 */
public class WorldTickHandler {

	public static void register() {
		FMLCommonHandler.instance().bus().register(new WorldTickHandler());
	}

	// These two values are used to intercept /time set day and /time set night commands
	public static final int DEFAULT_DAY_TIME = 1000;
	public static final int DEFAULT_NIGHT_TIME = 13000;

	public static int dayDuration;
	public static int nightDuration;

	private long lastWorldTime = 0L;

	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {
		long worldTime = event.world.getWorldTime();
		if (lastWorldTime != 0L && worldTime != lastWorldTime) {
			if (worldTime == DEFAULT_DAY_TIME) {
				event.world.setWorldTime(0);
			} else if (worldTime == DEFAULT_NIGHT_TIME) {
				event.world.setWorldTime(dayDuration);
			}
		}
		lastWorldTime = worldTime;
	}
}
