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

	@Override
	public void toBytes(ByteBuf buf) {
		SessionData.writeToBuffer(buf);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		SessionData.readFromBuffer(buf);
	}

	@Override
	public IMessage onMessage(PacketServerSettings message, MessageContext ctx) {
		SessionData.modEnabled = true;
		return null;
	}
}
