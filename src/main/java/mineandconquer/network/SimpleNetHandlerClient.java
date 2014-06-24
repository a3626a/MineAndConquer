package mineandconquer.network;

import mineandconquer.tileentities.SimpleNetReceiver;
import mineandconquer.tileentities.TENexus;
import mineandconquer.tileentities.TEWallMaker;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class SimpleNetHandlerClient implements
		IMessageHandler<SimpleNetMessageClient, SimpleNetMessageServer> {

	@Override
	public SimpleNetMessageServer onMessage(SimpleNetMessageClient message,
			MessageContext ctx) {
		// TODO Auto-generated method stub

		TileEntity te = Minecraft.getMinecraft().theWorld.getTileEntity(message.getX(), message.getY(), message.getZ());
		if (te != null & te instanceof SimpleNetReceiver) {
			((SimpleNetReceiver)te).onMessage(message.getIndex(), message);
		}
		
		
		return null;
	}

}
