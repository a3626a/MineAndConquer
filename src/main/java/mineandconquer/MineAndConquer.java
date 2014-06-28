package mineandconquer;

import java.util.HashMap;

import mineandconquer.blocks.ModBlocks;
import mineandconquer.core.handler.ForgeEventHandler;
import mineandconquer.core.handler.GUIHandler;
import mineandconquer.core.proxy.ClientProxy;
import mineandconquer.core.proxy.CommonProxy;
import mineandconquer.entities.EntityNexusGuardian;
import mineandconquer.items.ModItems;
import mineandconquer.lib.References;
import mineandconquer.network.SimpleNetHandlerClient;
import mineandconquer.network.SimpleNetHandlerServer;
import mineandconquer.network.SimpleNetMessageClient;
import mineandconquer.network.SimpleNetMessageServer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityList;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
@Mod(modid = References.MODID, name = References.MODNAME, version = References.VERSION)

public class MineAndConquer {

	public static SimpleNetworkWrapper simpleChannel;
	
	public static HashMap<String, String> teamOfPlayer;

	
	
	@Mod.Instance
	public static MineAndConquer instance;
	
	@SidedProxy(clientSide = References.CLIENTPROXYLOCATION, serverSide = References.COMMONPROXYLOCATION)
	public static CommonProxy proxy;
	
	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		teamOfPlayer = new HashMap();
		ModBlocks.init();
		ModItems.init();
		proxy.registerTileEntities();
		proxy.registerEntities();
		if (proxy instanceof ClientProxy) {
			((ClientProxy)proxy).registerEntityRendering();
		}
	}
	@Mod.EventHandler
	public static void Init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GUIHandler());
		simpleChannel = NetworkRegistry.INSTANCE.newSimpleChannel(References.MODNAME);
		simpleChannel.registerMessage(SimpleNetHandlerServer.class, SimpleNetMessageServer.class, 1, Side.SERVER);
		simpleChannel.registerMessage(SimpleNetHandlerClient.class, SimpleNetMessageClient.class, 2, Side.CLIENT);
	}
	@Mod.EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new ForgeEventHandler());
	}	
	
}
