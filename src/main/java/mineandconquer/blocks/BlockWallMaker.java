package mineandconquer.blocks;

import java.util.Random;

import mineandconquer.MineAndConquer;
import mineandconquer.lib.References;
import mineandconquer.lib.Strings;
import mineandconquer.tileentities.TEWallMaker;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockWallMaker extends BlockContainer {

	private final Random field_149933_a = new Random();

	
	public BlockWallMaker() {
		super(Material.rock);
		this.setBlockName(References.RESOURCESPREFIX + Strings.BlockWallMakerName);
		// unlocalizedName을 부여함
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setHardness(1.0F);
		ModBlocks.register(this);
	}
	
	public void breakBlock(World p_149749_1_, int x, int y, int z, Block block,
			int p_149749_6_) {
		
		
		TEWallMaker teWallMaker = (TEWallMaker) p_149749_1_
				.getTileEntity(x, y, z);

		if (teWallMaker != null) {
			for (int i1 = 0; i1 < teWallMaker.getSizeInventory(); ++i1) {
				ItemStack itemstack = teWallMaker.getStackInSlot(i1);

				if (itemstack != null) {
					float f = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
					float f1 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
					float f2 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;

					while (itemstack.stackSize > 0) {
						int j1 = this.field_149933_a.nextInt(21) + 10;

						if (j1 > itemstack.stackSize) {
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(p_149749_1_,
								(double) ((float) x + f),
								(double) ((float) y + f1),
								(double) ((float) z + f2), new ItemStack(
										itemstack.getItem(), j1,
										itemstack.getItemDamage()));

						if (itemstack.hasTagCompound()) {
							entityitem.getEntityItem().setTagCompound(
									(NBTTagCompound) itemstack.getTagCompound()
											.copy());
						}

						float f3 = 0.05F;
						entityitem.motionX = (double) ((float) this.field_149933_a
								.nextGaussian() * f3);
						entityitem.motionY = (double) ((float) this.field_149933_a
								.nextGaussian() * f3 + 0.2F);
						entityitem.motionZ = (double) ((float) this.field_149933_a
								.nextGaussian() * f3);
						p_149749_1_.spawnEntityInWorld(entityitem);
					}
				}
			}

			p_149749_1_.func_147453_f(x, y, z, block);
		}

		super.breakBlock(p_149749_1_, x, y, z, block, p_149749_6_);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		// TODO Auto-generated method stub
		this.blockIcon = iconRegister.registerIcon(this.getUnlocalizedName());
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z,
			EntityPlayer player, int p_149727_6_, float p_149727_7_,
			float p_149727_8_, float p_149727_9_) {
		// TODO Auto-generated method stub
		player.openGui(MineAndConquer.instance, Strings.GuiWallMakerID, world, x,
				y, z);
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
		return new TEWallMaker();
	}
}
