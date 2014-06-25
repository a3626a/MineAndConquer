package mineandconquer.core.proxy;

import mineandconquer.MineAndConquer;
import mineandconquer.entities.EntityNexusGuardian;
import mineandconquer.lib.Strings;
import mineandconquer.tileentities.TENexus;
import mineandconquer.tileentities.TEWallMaker;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

public class CommonProxy {
	public void registerTileEntities()
    {
		GameRegistry.registerTileEntity(TEWallMaker.class, Strings.BlockWallMakerName);
		GameRegistry.registerTileEntity(TENexus.class, Strings.BlockNexusName);
		
    }
	
	public void registerEntities() {
		EntityRegistry.registerModEntity(EntityNexusGuardian.class, Strings.entityNexusGuardianName, Strings.entityNexusGuardianID, MineAndConquer.instance, 80, 3, false);
	}
}
