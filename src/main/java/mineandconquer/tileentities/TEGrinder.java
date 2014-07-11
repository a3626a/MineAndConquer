package mineandconquer.tileentities;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.Constants;



public class TEGrinder extends TileEntity implements IInventory
{
    /**
     * Loads of methods. All required since we implement IInventory
     */
    private ItemStack[] inventory;
    private int INVENTORY_SIZE = 7;
    public int grindTime;
    
 
    //Initializes our inventory.
    public TEGrinder()
    {
        //Initializes our inventory (ItemStack array)
        inventory = new ItemStack[INVENTORY_SIZE];
    }
    
    public boolean isGrinding(){
    	return this.grindTime > 0;
    }
    
    public void updateEntity(){
    	if(this.grindTime>0){
    		this.grindTime--;
    	}
    	
    }
 
    //Returns the size of the inventory (aka number of slots, see INVENTORY_SIZE)
    @Override
    public int getSizeInventory()
    {
        return inventory.length;
    }
 
    //Returns the ItemStack in a slot
    @Override
    public ItemStack getStackInSlot(int slotIndex)
    {
        return inventory[slotIndex];
    }
 
    /*
    Decreases the stacksize.
    If the stack is not null, and the amount to decrease is more than the slot has, set to null
    else if the stack is not null, split the stack. If that makes the inv 0, make it null.
     */
    @Override
    public ItemStack decrStackSize(int slotIndex, int decrementAmount)
    {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null)
        {
            if (itemStack.stackSize <= decrementAmount)
            {
                setInventorySlotContents(slotIndex, null);
            }
            else
            {
                itemStack = itemStack.splitStack(decrementAmount);
                if (itemStack.stackSize == 0)
                {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }
        return itemStack;
    }
 
    /*
    Sets the stack on closing.
    If the stack is not null, set it to null
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex)
    {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null)
        {
            setInventorySlotContents(slotIndex, null);
        }
        return itemStack;
    }
 
    /*
    Sets the Inventory content.
    If the stack you place exeeds the maxStackSize, set the size to maxStacksize.
     */
    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack)
    {
        inventory[slotIndex] = itemStack;
        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit())
        {
            itemStack.stackSize = getInventoryStackLimit();
        }
    }
    /*
    Returns the Inv name, not req.
     */
    @Override
    public String getInventoryName()
    {
        return "aString";
    }
 
    /*
    IF you want, return true. Not req.
     */
    @Override
    public boolean hasCustomInventoryName()
    {
        return false;
    }
 
    /*
    Max stacksize in slot.
     */
    @Override
    public int getInventoryStackLimit()
    {
        return 64;
    }
 
    /*
    return true, or you wont be able to use it.
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer var1)
    {
        return true;
    }
 
    /*
    Do very little
     */
    @Override
    public void openInventory()
    {
 
    }
 
    /*
    Do as little as.
     */
    @Override
    public void closeInventory()
    {
 
    }
 
    /*
    You can put your custom items here, if you want
     */
    @Override
    public boolean isItemValidForSlot(int var1, ItemStack var2)
    {
        //TODO
        return true;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound nc){
    	super.readFromNBT(nc);
    	NBTTagList nbtlist = nc.getTagList("Items", Constants.NBT.TAG_COMPOUND);
    	this.inventory = new ItemStack[this.getSizeInventory()];
    	for(int i=0;i<nbtlist.tagCount();i++){
    		NBTTagCompound NC = nbtlist.getCompoundTagAt(i);
    		int j = NC.getByte("Slot");
    		if(j>=0 && j<this.inventory.length){
    			this.inventory[j] = ItemStack.loadItemStackFromNBT(NC);
    		}
    	}
    }
    
    @Override
    public void writeToNBT(NBTTagCompound nc){
    	super.writeToNBT(nc);
    	NBTTagList nbtlist = new NBTTagList();
    	for(int i=0;i<this.inventory.length;i++){
    		if(this.inventory != null){
    			NBTTagCompound NC = new NBTTagCompound();
    			NC.setByte("Slot",(byte)i);
    			this.inventory[i].writeToNBT(NC);
    			nbtlist.appendTag(NC);
    		}
    	}
    	nc.setTag("Items", nbtlist);
    }
}
