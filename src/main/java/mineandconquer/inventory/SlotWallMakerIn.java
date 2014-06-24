package mineandconquer.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SlotWallMakerIn extends Slot {

	public SlotWallMakerIn(IInventory par1iInventory, int par2, int par3, int par4) {
		super(par1iInventory, par2, par3, par4);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean isItemValid(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		
		// 재료 인벤토리에는 한 종의 블럭밖에 들어갈 수 없다. 예를 들어 빈 상태에서는 하나의 블럭을 넣을 수 있다. 그러나 그 상태에서는 동종의 블럭밖에 넣지 못하며 다른 블럭을 넣고자 하는 경우는 기존의 아이템을 제거해야한다.
		
		if (!(par1ItemStack.getItem() instanceof ItemBlock)) 
			return false;
		
    	Item temp = null;
    	for (int i = 0 ; i < 12 ; i++) {
    		if (inventory.getStackInSlot(i) == null) continue;
    		temp = inventory.getStackInSlot(i).getItem();
    		break;
    	}
    	return temp == par1ItemStack.getItem() || temp == null;
	}
}
