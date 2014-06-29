package mineandconquer.blocks;

import mineandconquer.MineAndConquer;
import mineandconquer.core.handler.ModEventHandler;
import mineandconquer.entities.EntityNexusGuardian;
import mineandconquer.lib.References;
import mineandconquer.lib.Strings;
import mineandconquer.network.SimpleNetMessageClient;
import mineandconquer.tileentities.TENexus;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockNexus extends BlockContainer {

	protected BlockNexus() {
		super(Material.rock);
		this.setBlockName(References.RESOURCESPREFIX + Strings.BlockNexusName);
		// unlocalizedName을 부여함
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setBlockUnbreakable();
		ModBlocks.register(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		// TODO Auto-generated method stub
		this.blockIcon = iconRegister.registerIcon(this.getUnlocalizedName());
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z,
			EntityLivingBase player, ItemStack p_149689_6_) {
		// TODO Auto-generated method stub

		((TENexus) world.getTileEntity(x, y, z)).addTeam_members(((EntityPlayer) player).getCommandSenderName());
		
		if (!world.isRemote) {
			EntityNexusGuardian entity = new EntityNexusGuardian(world);
			entity.setLocationAndAngles(x+0.5, y, z+0.5, 0.0F, 0.0F);
			world.spawnEntityInWorld(entity);

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
