package dmillerw.time.network;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import dmillerw.time.data.SessionData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author dmillerw
 */
public class PacketHandler implements IPacketHandler {

	public static void sendSettingsToAll() {
		PacketDispatcher.sendPacketToAllPlayers(getSessionPacket());
	}

	public static void sendSettings(EntityPlayer player) {
		PacketDispatcher.sendPacketToPlayer(getSessionPacket(), (Player) player);
	}

	public static Packet250CustomPayload getSessionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);

		try {
			SessionData.writeToStream(dos);
		} catch(IOException ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload pkt = new Packet250CustomPayload();
		pkt.channel = "TooMuchTime";
		pkt.data = bos.toByteArray();
		pkt.length = bos.size();
		pkt.isChunkDataPacket = false;

		return pkt;
	}

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
		try {
			SessionData.readFromStream(dat);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
