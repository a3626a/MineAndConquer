package mineandconquer.blocks;

import mineandconquer.lib.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockRottenRich extends Block {

	public BlockRottenRich()
	{
		super(Material.rock);
	}
	
	public BlockRottenRich(Material material) 
	{
		super(material);
		
	}

	public String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
		
	}
	
	@Override
	public String getUnlocalizedName()
	{
		return String.format("%s%s", References.RESOURCESPREFIX, getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
		//super.getUnlocalizedName()은 반환하는 String이 tile.unlocalizedName 형식을 가지고 있다.
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister)
	{
		this.blockIcon = iconRegister.registerIcon(References.RESOURCESPREFIX + getUnwrappedUnlocalizedName(super.getUnlocalizedName()));
	}
	
}
