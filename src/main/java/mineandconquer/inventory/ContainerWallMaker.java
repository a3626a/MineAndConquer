package mineandconquer.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mineandconquer.tileentities.TEWallMaker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerWallMaker extends Container {

	private TEWallMaker tile;
	private int lastCookTime;
    private int lastBurnTime;
    private int lastItemBurnTime;
	private int lastWallWidth;
	private int lastWallHeight;
	private int lastCurrentWallWidth;
	private int lastCurrentWallHeight;
	
	public ContainerWallMaker(InventoryPlayer inventoryPlayer, TEWallMaker teWallMaker) {
		tile = teWallMaker;
		bindPlayerInventory(inventoryPlayer);
	}
	/**
	 * called when gui is opened
	 */
    public void addCraftingToCrafters(ICrafting par1ICrafting)
    {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0, this.tile.furnaceCookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1, this.tile.furnaceBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 2, this.tile.currentItemBurnTime);
        par1ICrafting.sendProgressBarUpdate(this, 3, this.tile.wallWidth);
        par1ICrafting.sendProgressBarUpdate(this, 4, this.tile.wallHeight);
        par1ICrafting.sendProgressBarUpdate(this, 5, this.tile.currentWallWidth);
        par1ICrafting.sendProgressBarUpdate(this, 6, this.tile.currentWallHeight);
    }
	
	/**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();

        for (int i = 0; i < this.crafters.size(); ++i)
        {
            ICrafting icrafting = (ICrafting)this.crafters.get(i);

            if (this.lastCookTime != this.tile.furnaceCookTime)
            {
                icrafting.sendProgressBarUpdate(this, 0, this.tile.furnaceCookTime);
            }

            if (this.lastBurnTime != this.tile.furnaceBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 1, this.tile.furnaceBurnTime);
            }

            if (this.lastItemBurnTime != this.tile.currentItemBurnTime)
            {
                icrafting.sendProgressBarUpdate(this, 2, this.tile.currentItemBurnTime);
            }
            
            if (this.lastWallWidth != this.tile.wallWidth)
            {
            	icrafting.sendProgressBarUpdate(this, 3, this.tile.wallWidth);
            }
            if (this.lastWallHeight != this.tile.wallHeight)
            {
            	icrafting.sendProgressBarUpdate(this, 4, this.tile.wallHeight);
            }
            if (this.lastCurrentWallWidth != this.tile.currentWallWidth)
            {
            	icrafting.sendProgressBarUpdate(this, 5, this.tile.currentWallWidth);
            }
            if (this.lastCurrentWallHeight != this.tile.currentWallHeight)
            {
            	icrafting.sendProgressBarUpdate(this, 6, this.tile.currentWallHeight);
            }
        }

        this.lastCookTime = this.tile.furnaceCookTime;
        this.lastBurnTime = this.tile.furnaceBurnTime;
        this.lastItemBurnTime = this.tile.currentItemBurnTime;
        this.lastWallWidth = this.tile.wallWidth;
        this.lastWallHeight = this.tile.wallHeight;
        this.lastCurrentWallWidth = this.tile.currentWallWidth;
        this.lastCurrentWallHeight = this.tile.currentWallHeight;
        
    }
	
	@SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.tile.furnaceCookTime = par2;
        }

        if (par1 == 1)
        {
            this.tile.furnaceBurnTime = par2;
        }

        if (par1 == 2)
        {
            this.tile.currentItemBurnTime = par2;
        }
        if (par1 == 3)
        {
        	this.tile.wallWidth =par2;
        }
        if (par1 == 4)
        {
        	this.tile.wallHeight = par2;
        }
        if (par1 == 5)
        {
        	this.tile.currentWallWidth =par2;
        }
        if (par1 == 6)
        {
        	this.tile.currentWallHeight = par2;
        }
    }
	
	private void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
		int id2 = 0;
		
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 117 + i * 18));
            }
        }

        for (int i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 175));
        }
		
		for (int i = 0; i < 3; i++) {
			for (int j = 0 ; j < 4; j++) {
				addSlotToContainer(new SlotWallMakerIn(tile, id2, i*18 + 26 , j*18 - 1));
				id2++;
			}
		}
		addSlotToContainer(new Slot(tile, id2, 44 , 89));
		id2++;
		addSlotToContainer(new SlotWallMakerOut(tile, id2, 134 , 46));
		id2++;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int slotIndex)
	{
	    return null;
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		// TODO Auto-generated method stub
		return true;
	}

}
