package mineandconquer.blocks;

import mineandconquer.MineAndConquer;
import mineandconquer.entities.EntityNexusGuardian;
import mineandconquer.lib.References;
import mineandconquer.lib.Strings;
import mineandconquer.tileentities.TENexus;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNexus extends BlockContainer {

	@SideOnly(Side.CLIENT)
	private IIcon blockIcon;
	@SideOnly(Side.CLIENT)
	private IIcon blockIcon_top;
	
	protected BlockNexus() {
		super(Material.rock);
		this.setBlockName(References.RESOURCESPREFIX + Strings.BlockNexusName);
		// unlocalizedName�� �ο���
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockUnbreakable();
		this.setResistance(6000000.0F);
		ModBlocks.register(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		// TODO Auto-generated method stub
		this.blockIcon = iconRegister.registerIcon(ModBlocks.getUnwrappedUnlocalizedName(super.getUnlocalizedName())+"_side");
		this.blockIcon_top = iconRegister.registerIcon(ModBlocks.getUnwrappedUnlocalizedName(super.getUnlocalizedName())+"_top");
	}

	@Override
	public IIcon getIcon(int side, int meta) {
		// TODO Auto-generated method stub
		return side < 2 ? blockIcon_top: blockIcon;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase player, ItemStack p_149689_6_) {
		// TODO Auto-generated method stub

		if (!world.isRemote) {
			EntityNexusGuardian entity = new EntityNexusGuardian(world);
			entity.setLocationAndAngles(x+0.5, y+1, z+0.5, 0.0F, 0.0F);
			entity.setCoordinate(x, y, z);
			world.spawnEntityInWorld(entity);
			
			((TENexus) world.getTileEntity(x, y, z)).setGuardian_entity(entity);
			((TENexus) world.getTileEntity(x, y, z)).addTeam_members(((EntityPlayer) player).getCommandSenderName());
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int p_149727_6_, float p_149727_7_,
			float p_149727_8_, float p_149727_9_) {
		// TODO Auto-generated method stub
		TENexus tile  = (TENexus)world.getTileEntity(x, y, z);
		if (tile.getTeam_members() != null && tile.getTeam_members().contains(player.getCommandSenderName())) {
			player.openGui(MineAndConquer.instance, Strings.GuiNexusID01, world, x, y, z);
		}
		
		
		return true;
	}


	@Override
	public boolean hasTileEntity() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) {
		// TODO Auto-generated method stub
		return new TENexus();
	}

}
