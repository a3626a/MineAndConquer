package mineandconquer.network;

import mineandconquer.tileentities.SimpleNetReceiver;
import mineandconquer.tileentities.TEWallMaker;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class SimpleNetHandlerServer implements
		IMessageHandler<SimpleNetMessageServer, SimpleNetMessageClient> {

	@Override
	public SimpleNetMessageClient onMessage(SimpleNetMessageServer message,
			MessageContext ctx) {
		// TODO Auto-generated method stub

		TileEntity te = MinecraftServer.getServer().worldServers[0].getTileEntity(message.getX(), message.getY(), message.getZ());
		if (te != null & te instanceof SimpleNetReceiver) {
			((SimpleNetReceiver)te).onMessage(message.getIndex(), message);
		}
		
		return null;
	}

}
