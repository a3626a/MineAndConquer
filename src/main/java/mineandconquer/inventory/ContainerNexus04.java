package mineandconquer.inventory;

import mineandconquer.MineAndConquer;
import mineandconquer.network.SimpleNetMessageClient;
import mineandconquer.tileentities.TENexus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNexus04 extends Container {

	TENexus tile;
	InventoryPlayer inventoryPlayer;
	
	public ContainerNexus04(InventoryPlayer player, TENexus nexus) {
		this.tile = nexus;
		inventoryPlayer = player;
		bindPlayerInventory();
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1iCrafting) {
		// TODO Auto-generated method stub
		super.addCraftingToCrafters(par1iCrafting);
		if (tile != null) {
			SimpleNetMessageClient msg = new SimpleNetMessageClient(TENexus.MSGTOCLIENT.SYNC_XP_LEVEL.getValue(),
					tile.xCoord, tile.yCoord, tile.zCoord);
			msg.setInt(tile.getXp_level());
			MineAndConquer.simpleChannel.sendToAll(msg);
			
			SimpleNetMessageClient msg2 = new SimpleNetMessageClient(TENexus.MSGTOCLIENT.SYNC_XP_POINT.getValue(),
					tile.xCoord, tile.yCoord, tile.zCoord);
			msg2.setInt(tile.getXp_point());
			MineAndConquer.simpleChannel.sendToAll(msg2);
		}
	}

	@Override
	public void detectAndSendChanges() {
		// TODO Auto-generated method stub
		super.detectAndSendChanges();
	}
	
	public void bindPlayerInventory() {
		// TODO Auto-generated method stub
		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(inventoryPlayer,
						j + i * 9 + 9, 26 + j * 18, 117 + i * 18));
			}
		}

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(inventoryPlayer, i, 26 + i * 18,
					175));
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer,
			int slotIndex) {
		return null;
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		// TODO Auto-generated method stub
		return true;
	}

}
