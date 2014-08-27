package dmillerw.time.handler;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import dmillerw.time.TooMuchTime;
import dmillerw.time.data.SessionData;
import dmillerw.time.network.PacketHandler;
import dmillerw.time.network.packet.PacketServerSettings;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandTime;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CommandEvent;

/**
 * @author dmillerw
 */
public class CommandEventHandler {

	public static void register() {
		MinecraftForge.EVENT_BUS.register(new CommandEventHandler());
	}

	@SubscribeEvent
	public void onCommand(CommandEvent event) {
		// If they used the time command, we override and provide our own implementation
		if (event.command instanceof CommandTime) {
			event.setCanceled(true);

			if (event.parameters.length > 1) {
				int time;

                if (event.parameters[0].equalsIgnoreCase("reload")) {
                    SessionData.loadFromConfiguration(TooMuchTime.configuration);
                } else if (event.parameters[0].equals("set")) {
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
							PacketHandler.INSTANCE.sendToAll(new PacketServerSettings());
							return;
						} else if (event.parameters[1].equals("moon")) {
							int setAngle = CommandBase.parseIntBounded(event.sender, event.parameters[2], 0, 180);
							SessionData.staticMoon = true;
							SessionData.staticAngle = setAngle;
							SessionData.setConfigurationProperty("general", "staticMoon", true);
							SessionData.setConfigurationProperty("general", "staticAngle", setAngle);
							CommandBase.notifyAdmins(event.sender, "tmt.commands.time.stop", StatCollector.translateToLocal("tmt.misc.moon"), setAngle);
							PacketHandler.INSTANCE.sendToAll(new PacketServerSettings());
							return;
						}
					}
				} else if (event.parameters[0].equals("set-day")) {
					int setTime = CommandBase.parseInt(event.sender, event.parameters[1]);
					SessionData.dayDuration = setTime;
					SessionData.setConfigurationProperty("general", "dayDuration", setTime);
					CommandBase.notifyAdmins(event.sender, "tmt.commands.time.set", StatCollector.translateToLocal("tmt.misc.day"), setTime);
					PacketHandler.INSTANCE.sendToAll(new PacketServerSettings());
					return;
				} else if (event.parameters[0].equals("set-night")) {
					int setTime = CommandBase.parseInt(event.sender, event.parameters[1]);
					SessionData.nightDuration = setTime;
					SessionData.setConfigurationProperty("general", "nightDuration", setTime);
					CommandBase.notifyAdmins(event.sender, "tmt.commands.time.set", StatCollector.translateToLocal("tmt.misc.night"), setTime);
					PacketHandler.INSTANCE.sendToAll(new PacketServerSettings());
					return;
				}
			} else if (event.parameters.length == 1) {
				if (event.parameters[0].equals("start")) {
					SessionData.staticAngle = -1;
					SessionData.setConfigurationProperty("general", "staticMoon", -1);
					CommandBase.notifyAdmins(event.sender, "tmt.commands.time.start");
					PacketHandler.INSTANCE.sendToAll(new PacketServerSettings());
					return;
				} else if (event.parameters[0].equals("set-default")) {
					SessionData.dayDuration = 12000;
					SessionData.nightDuration = 12000;
					SessionData.setConfigurationProperty("general", "dayDuration", 12000);
					SessionData.setConfigurationProperty("general", "nightDuration", 12000);
					CommandBase.notifyAdmins(event.sender, "tmt.commands.time.default");
					PacketHandler.INSTANCE.sendToAll(new PacketServerSettings());
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
