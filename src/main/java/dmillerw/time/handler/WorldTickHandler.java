package dmillerw.time.handler;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.world.World;

import java.util.EnumSet;

/**
 * @author dmillerw
 */
public class WorldTickHandler implements ITickHandler {

	public static void register() {
		TickRegistry.registerTickHandler(new WorldTickHandler(), Side.SERVER);
	}

	// These two values are used to intercept /time set day and /time set night commands
	public static final int DEFAULT_DAY_TIME = 1000;
	public static final int DEFAULT_NIGHT_TIME = 13000;

	public static int dayDuration;
	public static int nightDuration;

	private long lastWorldTime = 0L;

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {
		onWorldTick((World) tickData[0]);
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.WORLD);
	}

	@Override
	public String getLabel() {
		return null;
	}

	public void onWorldTick(World world) {
		long worldTime = world.getWorldTime();
		if (lastWorldTime != 0L && worldTime != lastWorldTime) {
			if (worldTime == DEFAULT_DAY_TIME) {
				world.setWorldTime(0);
			} else if (worldTime == DEFAULT_NIGHT_TIME) {
				world.setWorldTime(dayDuration);
			}
		}
		lastWorldTime = worldTime;
	}
}
