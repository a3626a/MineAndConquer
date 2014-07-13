package mineandconquer.blocks;

import mineandconquer.MineAndConquer;
import mineandconquer.lib.References;
import mineandconquer.lib.Strings;
import mineandconquer.tileentities.TEDoubleFurnace;
import mineandconquer.tileentities.TEGrinder;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGrinder extends BlockContainer{
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons = new IIcon[6];
	public IIcon grinding;
	
	public BlockGrinder(){
		super(Material.rock);
		this.setBlockName(References.RESOURCESPREFIX + Strings.BlockGrinderName);
		this.setCreativeTab(MineAndConquer.getCreativeTabs());
		ModBlocks.register(this);
	}
	
	@Override
	public boolean hasTileEntity(int meta){
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x,int y, int z, EntityPlayer entityPlayer,int side, float posx, float posy,float posz) {
		entityPlayer.openGui(MineAndConquer.instance, Strings.GuiGrinderID, world, x, y, z);
		return true;
	}
	
	public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_)
    {
        int l = MathHelper.floor_double((double)(p_149689_5_.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 0, 2);
        }

        if (l == 1)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 3, 2);
        }

        if (l == 2)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 1, 2);
        }

        if (l == 3)
        {
            p_149689_1_.setBlockMetadataWithNotify(p_149689_2_, p_149689_3_, p_149689_4_, 2, 2);
        }
    }
	@Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister)
    {
		for(int i=0;i<icons.length;i++){
			String name;
			switch(i){
				case 0: name = "_bottom";
				break;
				case 1: name = "_top";
				break;
				case 2: name = "_front_off";
				break;
				case 3:	
				case 4:
				case 5: name = "_side";
				break;
				default: name = "_side";
			}
			icons[i] = iconRegister.registerIcon(ModBlocks.getUnwrappedUnlocalizedName(super.getUnlocalizedName() + name));
		}
		grinding = iconRegister.registerIcon(ModBlocks.getUnwrappedUnlocalizedName(super.getUnlocalizedName()+"_front_on"));
    }
	
	/**
     * Returns the desired texture for the side.
     * 0: bottom
     * 1: top
     * 2-5: sides.
     *
     * @param side side of the block
     * @param meta metadata of the block.
     * @return IIcon
     */
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta){
		return side == 0? this.icons[0] : (side == 1? this.icons[1] : (side == (meta&3)+2? this.icons[2]:this.icons[3]));
	}

	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TEGrinder();
	}
}
