package mineandconquer.blocks;

import mineandconquer.MineAndConquer;
import mineandconquer.lib.References;
import mineandconquer.lib.Strings;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;

public class BlockGrinder extends BlockContainer{
	public BlockGrinder(){
		super(Material.rock);
		this.setBlockName(References.RESOURCESPREFIX + Strings.BlockGrinderName);
		this.setCreativeTab(MineAndConquer.getCreativeTabs());
		ModBlocks.register(this);
	}
}
