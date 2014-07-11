package mineandconquer.inventory;

import mineandconquer.items.ModItems;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotGrinder extends Slot{
	public SlotGrinder(IInventory inven, int SlotIndex, int x, int y){
		super(inven, SlotIndex, x, y);
	}
	
	//only place item(only accept)
	@Override
	public boolean isItemValid(ItemStack par1IS){
		return true;
	}
}

