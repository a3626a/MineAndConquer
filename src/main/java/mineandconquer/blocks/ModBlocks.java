package mineandconquer.blocks;

import net.minecraft.block.Block;
import cpw.mods.fml.common.registry.GameRegistry;

public class ModBlocks {
	public static Block testBlock;
	public static Block blockWallMaker;
	public static Block blockNexus;
	
	public static void init()
	{
		testBlock = new TestBlock();
		blockWallMaker = new BlockWallMaker();
		blockNexus = new BlockNexus();
	}

	public static void register(BlockRottenRich block) {
		// TODO Auto-generated method stub
		GameRegistry.registerBlock(block, block.getUnlocalizedName());
	}
}
