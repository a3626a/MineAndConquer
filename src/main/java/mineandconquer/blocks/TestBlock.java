package mineandconquer.blocks;


import mineandconquer.lib.Strings;
import net.minecraft.creativetab.CreativeTabs;

public class TestBlock extends BlockRottenRich{

	public TestBlock()
	{
		this.setBlockName(Strings.TestBlockName);
		//unlocalizedName을 부여함
		this.setCreativeTab(CreativeTabs.tabBlock);
		ModBlocks.register(this);
	}
}
