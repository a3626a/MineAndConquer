package mineandconquer.inventory;

import java.util.ArrayList;
import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mineandconquer.MineAndConquer;
import mineandconquer.network.SimpleNetMessageClient;
import mineandconquer.tileentities.TENexus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNexus01 extends Container {

	TENexus tile;
	InventoryPlayer inventoryPlayer;

	private String lastTeam_Name;
	private ArrayList<String> lastTeam_Members;

	public ContainerNexus01(InventoryPlayer player, TENexus nexus) {
		this.tile = nexus;
		this.inventoryPlayer = player;
		
		this.lastTeam_Name = new String();
		this.lastTeam_Members = (ArrayList)this.tile.getTeam_members().clone();
		bindPlayerInventory();
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1iCrafting) {
		// TODO Auto-generated method stub
		super.addCraftingToCrafters(par1iCrafting);

		SimpleNetMessageClient msg = new SimpleNetMessageClient(
				TENexus.MSGTOCLIENT.SYNC_TEAM_NAME.getValue(), tile.xCoord,
				tile.yCoord, tile.zCoord);
		msg.setString(tile.getTeam_name());
		MineAndConquer.simpleChannel.sendToAll(msg);
		
		for (String i : tile.getTeam_members()) {
			SimpleNetMessageClient msg2 = new SimpleNetMessageClient(
					TENexus.MSGTOCLIENT.SYNC_TEAM_MEMBERS_ADDED.getValue(),
					tile.xCoord, tile.yCoord, tile.zCoord);
			msg2.setString(i);
			MineAndConquer.simpleChannel.sendToAll(msg2);
		}
		
	}

	@Override
	public void detectAndSendChanges() {
		// TODO Auto-generated method stub
		super.detectAndSendChanges();
		
		if (this.lastTeam_Members.size() < this.tile.getTeam_members().size()) {
			for (String i : tile.getTeam_members()) {
				SimpleNetMessageClient msg2 = new SimpleNetMessageClient(
						TENexus.MSGTOCLIENT.SYNC_TEAM_MEMBERS_ADDED.getValue(),
						tile.xCoord, tile.yCoord, tile.zCoord);
				msg2.setString(i);
				MineAndConquer.simpleChannel.sendToAll(msg2);
			}
		}
		
		if (this.lastTeam_Members.size() > this.tile.getTeam_members().size()) {
			for (String i : tile.getTeam_members()) {
				SimpleNetMessageClient msg2 = new SimpleNetMessageClient(
						TENexus.MSGTOCLIENT.SYNC_TEAM_MEMBERS_DELETED.getValue(),
						tile.xCoord, tile.yCoord, tile.zCoord);
				msg2.setString(i);
				MineAndConquer.simpleChannel.sendToAll(msg2);
			}
		}
		
		if (!this.lastTeam_Name.equals(this.tile.getTeam_name())) {
			SimpleNetMessageClient msg = new SimpleNetMessageClient(
					TENexus.MSGTOCLIENT.SYNC_TEAM_NAME.getValue(), tile.xCoord,
					tile.yCoord, tile.zCoord);
			msg.setString(tile.getTeam_name());
			MineAndConquer.simpleChannel.sendToAll(msg);
		}
		
		this.lastTeam_Members = (ArrayList)this.tile.getTeam_members().clone();
		this.lastTeam_Name = this.tile.getTeam_name().substring(0);
	}

	public void bindPlayerInventory() {
		// TODO Auto-generated method stub
		int id = 0;

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
