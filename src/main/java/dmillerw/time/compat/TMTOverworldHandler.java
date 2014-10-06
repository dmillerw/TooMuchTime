package dmillerw.time.compat;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.quetzi.morpheus.api.INewDayHandler;

/**
 * @author dmillerw
 */
public class TMTOverworldHandler implements INewDayHandler {

    @Override
    public void startNewDay() {
        World world = MinecraftServer.getServer().worldServerForDimension(0);
        world.setWorldTime(0);
    }
}
