package mineandconquer.blocks;



import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mineandconquer.lib.References;
import mineandconquer.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockTestBlock extends Block{

	public BlockTestBlock()
	{
		super(Material.rock);
		this.setBlockName(References.RESOURCESPREFIX + Strings.TestBlockName);
		//unlocalizedName을 부여함
		this.setCreativeTab(CreativeTabs.tabBlock);
		ModBlocks.register(this);
	}	
	
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon(ModBlocks.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
	@Override
	public void onBlockClicked(World p_149699_1_, int p_149699_2_,
			int p_149699_3_, int p_149699_4_, EntityPlayer player) {
		// TODO Auto-generated method stub
		super.onBlockClicked(p_149699_1_, p_149699_2_, p_149699_3_, p_149699_4_,
				player);
		player.addExperience(20);
	}
	
	@Override
	public boolean onBlockActivated(World p_149727_1_, int p_149727_2_,
			int p_149727_3_, int p_149727_4_, EntityPlayer player,
			int p_149727_6_, float p_149727_7_, float p_149727_8_,
			float p_149727_9_) {
		// TODO Auto-generated method stub
        int par1 = 10;
		if (par1 >= player.experienceTotal)
        {
            player.experience=0;
            player.experienceTotal=0;
            player.experienceLevel=0;
            return true;
        }

        player.experience -= (float)par1 / (float)player.xpBarCap();

        for (player.experienceTotal -= par1; player.experience <= 0.0F; player.experience /= (float)player.xpBarCap())
        {
        	float par2 = player.experience*player.xpBarCap();
        	player.addExperienceLevel(-1);
        	player.experience = par2+player.xpBarCap();
        }
		return true;
	}
}
