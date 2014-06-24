package mineandconquer.core.proxy;

import mineandconquer.lib.Strings;
import mineandconquer.tileentities.TENexus;
import mineandconquer.tileentities.TEWallMaker;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void registerTileEntities()
    {
		GameRegistry.registerTileEntity(TEWallMaker.class, Strings.BlockWallMakerName);
		GameRegistry.registerTileEntity(TENexus.class, Strings.BlockNexusName);
    }
}
