package dmillerw.time.handler;

import dmillerw.time.data.SessionData;
import dmillerw.time.network.PacketHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandTime;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ForgeSubscribe;

/**
 * @author dmillerw
 */
public class CommandEventHandler {

	public static void register() {
		MinecraftForge.EVENT_BUS.register(new CommandEventHandler());
	}

	@ForgeSubscribe
	public void onCommand(CommandEvent event) {
		// If they used the time command, we override and provide our own implementation
		if (event.command instanceof CommandTime) {
			event.setCanceled(true);

			if (event.parameters.length > 1) {
				int time;

				if (event.parameters[0].equals("set")) {
					if (event.parameters[1].equals("day")) {
						time = 0;
					} else if (event.parameters[1].equals("noon")) {
						time = SessionData.dayDuration / 2;
					} else if (event.parameters[1].equals("night")) {
						time = SessionData.dayDuration;
					} else {
						time = CommandBase.parseIntWithMin(event.sender, event.parameters[1], 0);
					}

					setTime(time);

					CommandBase.notifyAdmins(event.sender, "commands.time.set", time);

					return;
				} else if (event.parameters[0].equals("add")) {
					time = CommandBase.parseIntWithMin(event.sender, event.parameters[1], 0);

					addTime(time);

					CommandBase.notifyAdmins(event.sender, "commands.time.added", time);

					return;
				} else if (event.parameters[0].equals("stop")) {
					if (event.parameters.length == 3) {
						if (event.parameters[1].equals("sun")) {
							int setAngle = CommandBase.parseIntBounded(event.sender, event.parameters[2], 0, 180);
							SessionData.staticMoon = false;
							SessionData.staticAngle = setAngle;
							SessionData.setConfigurationProperty("general", "staticMoon", false);
							SessionData.setConfigurationProperty("general", "staticAngle", setAngle);
							CommandBase.notifyAdmins(event.sender, "tmt.commands.time.stop", StatCollector.translateToLocal("tmt.misc.sun"), setAngle);
							PacketHandler.sendSettingsToAll();
							return;
						} else if (event.parameters[1].equals("moon")) {
							int setAngle = CommandBase.parseIntBounded(event.sender, event.parameters[2], 0, 180);
							SessionData.staticMoon = true;
							SessionData.staticAngle = setAngle;
							SessionData.setConfigurationProperty("general", "staticMoon", true);
							SessionData.setConfigurationProperty("general", "staticAngle", setAngle);
							CommandBase.notifyAdmins(event.sender, "tmt.commands.time.stop", StatCollector.translateToLocal("tmt.misc.moon"), setAngle);
							PacketHandler.sendSettingsToAll();
							return;
						}
					}
				} else if (event.parameters[0].equals("set-day")) {
					int setTime = CommandBase.parseInt(event.sender, event.parameters[1]);
					SessionData.dayDuration = setTime;
					SessionData.setConfigurationProperty("general", "dayDuration", setTime);
					CommandBase.notifyAdmins(event.sender, "tmt.commands.time.set", StatCollector.translateToLocal("tmt.misc.day"), setTime);
					PacketHandler.sendSettingsToAll();
					return;
				} else if (event.parameters[0].equals("set-night")) {
					int setTime = CommandBase.parseInt(event.sender, event.parameters[1]);
					SessionData.nightDuration = setTime;
					SessionData.setConfigurationProperty("general", "nightDuration", setTime);
					CommandBase.notifyAdmins(event.sender, "tmt.commands.time.set", StatCollector.translateToLocal("tmt.misc.night"), setTime);
					PacketHandler.sendSettingsToAll();
					return;
				}
			} else if (event.parameters.length == 1) {
				if (event.parameters[0].equals("start")) {
					SessionData.staticAngle = -1;
					SessionData.setConfigurationProperty("general", "staticMoon", -1);
					CommandBase.notifyAdmins(event.sender, "tmt.commands.time.start");
					PacketHandler.sendSettingsToAll();
					return;
				} else if (event.parameters[0].equals("set-default")) {
					SessionData.dayDuration = 12000;
					SessionData.nightDuration = 12000;
					SessionData.setConfigurationProperty("general", "dayDuration", 12000);
					SessionData.setConfigurationProperty("general", "nightDuration", 12000);
					CommandBase.notifyAdmins(event.sender, "tmt.commands.time.default");
					PacketHandler.sendSettingsToAll();
					return;
				}
			}

			throw new WrongUsageException("commands.time.usage");
		}
	}

	private void setTime(int time) {
		MinecraftServer.getServer().worldServers[0].setWorldTime((long) time);
	}

	private void addTime(int time) {
		WorldServer worldserver = MinecraftServer.getServer().worldServers[0];
		worldserver.setWorldTime(worldserver.getWorldTime() + (long) time);
	}
}
