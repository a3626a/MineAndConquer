package mineandconquer.inventory;

import mineandconquer.tileentities.TEGrinder;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerGrinder extends Container
{
    //Create an Object of our TE.
    public TEGrinder tile;
 
    public ContainerGrinder(InventoryPlayer inventory, TEGrinder gc)
    {
        tile = gc;
        bindPlayerInventory(inventory);
    }
 
    /*
    Add slots to our GUI.
    The id's are for the slotnumbers.
    For the rest, the i * 18 and j * 18 is always the same.
    The other numbers can change, depending on your gui.
     */
 
    private void bindPlayerInventory(InventoryPlayer inventoryPlayer)
    {
        int id = 0;
        int id2 = 0;
 
        for(int i = 0; i < 9; i++)
        {
            addSlotToContainer(new Slot(inventoryPlayer, id, i * 18 + 8, 190)); //Adds player hotbar
            id++;
        }
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 9; j++)
            {
                addSlotToContainer(new Slot(inventoryPlayer, id ,j * 18 + 8, i * 18 + 132 )); //Adds player inventory
                id++;
            }
        }
 
        for(int i = 0; i < 3; i ++)
        {
            for(int j = 0; j < 2; j++)
            {
                addSlotToContainer(new SlotGrinder(tile, id2, i * 18 + 62, j * 18 + 20)); //Adds custom slots
                id2++;
            }
        }
        addSlotToContainer(new SlotGrinder(tile, id2, 81, 95)); //Adds custom output
    }
 
    @Override
    public boolean canInteractWith(EntityPlayer var1)
    {
        return true;
    }
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
    	// TODO Auto-generated method stub
    	return null;
    }
}
