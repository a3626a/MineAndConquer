package mineandconquer.blocks;

import java.util.Random;

import mineandconquer.MineAndConquer;
import mineandconquer.lib.References;
import mineandconquer.lib.Strings;
import mineandconquer.tileentities.TEDoubleFurnace;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockDoubleFurnace extends BlockContainer
{
    private final Random field_149933_a = new Random();
    private boolean field_149932_b = false;
    private static boolean field_149934_M;
    @SideOnly(Side.CLIENT)
    private IIcon field_149935_N;
    @SideOnly(Side.CLIENT)
    private IIcon furnaceOff;
    @SideOnly(Side.CLIENT)
    private IIcon furnaceOn;
    private static final String __OBFID = "CL_00000248";
	public static boolean isBurning = false;

    protected BlockDoubleFurnace()
    {
        super(Material.rock);
        this.setCreativeTab(MineAndConquer.getCreativeTabs());
        this.setBlockName(References.RESOURCESPREFIX +Strings.BlockDoubleFurnaceName);
        ModBlocks.register(this);
    }    
    /**
     * Gets the block's texture. Args: side, meta
     */
   
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
    	if((meta & 4) == 4){
    		field_149932_b = true;
    		return side == 1 ? this.field_149935_N : (side == 0 ? this.field_149935_N : (side != ((meta & 3)+2)? this.blockIcon : this.furnaceOn));
    	}
    	return side == 1 ? this.field_149935_N : (side == 0 ? this.field_149935_N : (side != ((meta & 3)+2)? this.blockIcon : this.furnaceOff));
    }
    

    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(ModBlocks.getUnwrappedUnlocalizedName(this.getUnlocalizedName())+"_side");
        this.furnaceOff = p_149651_1_.registerIcon(ModBlocks.getUnwrappedUnlocalizedName(this.getUnlocalizedName())+"_front_off");
        this.furnaceOn = p_149651_1_.registerIcon(ModBlocks.getUnwrappedUnlocalizedName(this.getUnlocalizedName())+"_front_on");
        this.field_149935_N = p_149651_1_.registerIcon(ModBlocks.getUnwrappedUnlocalizedName(this.getUnlocalizedName())+"_top");
    }
    
    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World p_149727_1_, int x, int y, int z, EntityPlayer p_149727_5_, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_)
    {
    	p_149727_5_.openGui(MineAndConquer.instance, Strings.GuiDoubleFurnaceID, p_149727_1_,x,y,z);
    	return true;
    	
    }

    /**
     * Update which block the furnace is using depending on whether or not it is burning
     */
    
    
    

    /**
     * Returns a new instance of a block's tile entity class. Called on placing the block.
     */
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TEDoubleFurnace();
    }

    /**
     * Called when the block is placed in the world.
     */
    
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

        if (p_149689_6_.hasDisplayName())
        {
            ((TEDoubleFurnace)p_149689_1_.getTileEntity(p_149689_2_, p_149689_3_, p_149689_4_)).func_145951_a(p_149689_6_.getDisplayName());
        }
    }
    

    public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        if (!field_149934_M)
        {
            TEDoubleFurnace tileentityfurnace = (TEDoubleFurnace)p_149749_1_.getTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
            for (int i1 = 0; i1 < tileentityfurnace.getSizeInventory(); ++i1){
            	ItemStack itemstack = tileentityfurnace.getStackInSlot(i1);
            	if (itemstack != null)
            	{
            		float f = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
            		float f1 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
            		float f2 = this.field_149933_a.nextFloat() * 0.8F + 0.1F;
            		
            		while (itemstack.stackSize > 0)
            		{
            			int j1 = this.field_149933_a.nextInt(21) + 10;
            			
            			if (j1 > itemstack.stackSize)
            			{
            				j1 = itemstack.stackSize;
            			}
            			itemstack.stackSize -= j1;
            			EntityItem entityitem = new EntityItem(p_149749_1_, (double)((float)p_149749_2_ + f), (double)((float)p_149749_3_ + f1), (double)((float)p_149749_4_ + f2), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));
            			if (itemstack.hasTagCompound())
            			{
            				entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
            			}
            			float f3 = 0.05F;
            			entityitem.motionX = (double)((float)this.field_149933_a.nextGaussian() * f3);
            			entityitem.motionY = (double)((float)this.field_149933_a.nextGaussian() * f3 + 0.2F);
            			entityitem.motionZ = (double)((float)this.field_149933_a.nextGaussian() * f3);
            			p_149749_1_.spawnEntityInWorld(entityitem);
            		}
            	}
            }
            p_149749_1_.func_147453_f(p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_);
        }
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
    }
    
    /**
     * A randomly called display update to be able to add particles or other items for display
     */
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_)
    {
        if (this.field_149932_b)
        {
            int l = p_149734_1_.getBlockMetadata(p_149734_2_, p_149734_3_, p_149734_4_);
            float f = (float)p_149734_2_ + 0.5F;
            float f1 = (float)p_149734_3_ + 0.0F + p_149734_5_.nextFloat() * 6.0F / 16.0F;
            float f2 = (float)p_149734_4_ + 0.5F;
            float f3 = 0.52F;
            float f4 = p_149734_5_.nextFloat() * 0.6F - 0.3F;

            if (l == 6)
            {
                p_149734_1_.spawnParticle("smoke", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                p_149734_1_.spawnParticle("flame", (double)(f - f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 7)
            {
                p_149734_1_.spawnParticle("smoke", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
                p_149734_1_.spawnParticle("flame", (double)(f + f3), (double)f1, (double)(f2 + f4), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 4)
            {
                p_149734_1_.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
                p_149734_1_.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 - f3), 0.0D, 0.0D, 0.0D);
            }
            else if (l == 5)
            {
                p_149734_1_.spawnParticle("smoke", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
                p_149734_1_.spawnParticle("flame", (double)(f + f4), (double)f1, (double)(f2 + f3), 0.0D, 0.0D, 0.0D);
            }
        }
    }

    /**
     * If this returns true, then comparators facing away from this block will use the value from
     * getComparatorInputOverride instead of the actual redstone signal strength.
     */
    public boolean hasComparatorInputOverride()
    {
        return true;
    }

	@Override
	public boolean hasTileEntity(int meta){
		return true;
	}
    /**
     * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
     * strength when this block inputs to a comparator.
     */
    public int getComparatorInputOverride(World p_149736_1_, int p_149736_2_, int p_149736_3_, int p_149736_4_, int p_149736_5_)
    {
        return Container.calcRedstoneFromInventory((IInventory)p_149736_1_.getTileEntity(p_149736_2_, p_149736_3_, p_149736_4_));
    }

    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return Item.getItemFromBlock(this);
    }
}