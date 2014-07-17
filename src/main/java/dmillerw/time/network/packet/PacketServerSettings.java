package dmillerw.time.network.packet;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import dmillerw.time.data.SessionData;
import io.netty.buffer.ByteBuf;

/**
 * @author dmillerw
 */
public class PacketServerSettings implements IMessage, IMessageHandler<PacketServerSettings, IMessage> {

	public int dayDuration;
	public int nightDuration;

	public PacketServerSettings() {

	}

	public PacketServerSettings(int dayDuration, int nightDuration) {
		this.dayDuration = dayDuration;
		this.nightDuration = nightDuration;
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(dayDuration);
		buf.writeInt(nightDuration);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		dayDuration = buf.readInt();
		nightDuration = buf.readInt();
	}

	@Override
	public IMessage onMessage(PacketServerSettings message, MessageContext ctx) {
		SessionData.modEnabled = true;
		SessionData.dayDuration = message.dayDuration;
		SessionData.nightDuration = message.nightDuration;
		return null;
	}
}
