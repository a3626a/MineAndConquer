package mineandconquer.blocks;

import mineandconquer.MineAndConquer;
import mineandconquer.lib.References;
import mineandconquer.lib.Strings;
import mineandconquer.tileentities.TEGrinder;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockGrinder extends BlockContainer{
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons = new IIcon[6];
	
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
		if(side<=5) return icons[side];
		else return icons[0];
	}

	
	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		return new TEGrinder();
	}
}
