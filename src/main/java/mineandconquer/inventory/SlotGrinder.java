package mineandconquer.inventory;

import mineandconquer.blocks.ModBlocks;
import mineandconquer.items.ModItems;
import mineandconquer.lib.GrinderRecipes;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotGrinder extends Slot{
	public SlotGrinder(IInventory inven, int SlotIndex, int x, int y){
		super(inven, SlotIndex, x, y);
	}
	
	//only place item(only accept)
	@Override
	public boolean isItemValid(ItemStack par1IS){
		for(int i:GrinderRecipes.grindingBase.acceptID){
			if(i==Item.getIdFromItem(par1IS.getItem())) return true;
		}
		return false;
	}
}

