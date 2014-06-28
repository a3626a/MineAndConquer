package mineandconquer.blocks;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block testBlock;
	public static Block blockWallMaker;
	public static Block blockNexus;
	
	public static void init()
	{
		testBlock = new BlockTestBlock();
		blockWallMaker = new BlockWallMaker();
		blockNexus = new BlockNexus();
	}

	public static String getName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(":") + 1);
		
	}
	
	public static String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
		
	}
	
	public static void register(Block block) {
		// TODO Auto-generated method stub
		GameRegistry.registerBlock(block, getName(block.getUnlocalizedName()));
	}
}
