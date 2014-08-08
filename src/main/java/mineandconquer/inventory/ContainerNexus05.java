package mineandconquer.inventory;

import mineandconquer.MineAndConquer;
import mineandconquer.network.SimpleNetMessageClient;
import mineandconquer.tileentities.TENexus;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;

public class ContainerNexus05 extends Container {

	TENexus tile;
	InventoryPlayer inventoryPlayer;

	private int lastRevival_time;
	private int lastRevival_stone;
	private int lastXP_level;
	
	public ContainerNexus05(InventoryPlayer player, TENexus nexus) {
		this.tile = nexus;
		inventoryPlayer = player;
		bindPlayerInventory();
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1iCrafting) {
		// TODO Auto-generated method stub
		super.addCraftingToCrafters(par1iCrafting);
		SimpleNetMessageClient msg1 = new SimpleNetMessageClient(
				TENexus.MSGTOCLIENT.SYNC_REVIVAL_TIME.getValue(), tile.xCoord,
				tile.yCoord, tile.zCoord);
		msg1.setInt(tile.getRevival_time());
		EntityPlayerMP entityplayermp1 = MinecraftServer.getServer().getConfigurationManager().func_152612_a(this.inventoryPlayer.player.getCommandSenderName());
		MineAndConquer.simpleChannel.sendTo(msg1, entityplayermp1);
		
		SimpleNetMessageClient msg2 = new SimpleNetMessageClient(
				TENexus.MSGTOCLIENT.SYNC_REVIVAL_STONE.getValue(), tile.xCoord,
				tile.yCoord, tile.zCoord);
		msg2.setInt(tile.getRevival_numOfStone());
		EntityPlayerMP entityplayermp2 = MinecraftServer.getServer().getConfigurationManager().func_152612_a(this.inventoryPlayer.player.getCommandSenderName());
		MineAndConquer.simpleChannel.sendTo(msg2, entityplayermp2);
		
		SimpleNetMessageClient msg3 = new SimpleNetMessageClient(
				TENexus.MSGTOCLIENT.SYNC_XP_LEVEL.getValue(), tile.xCoord,
				tile.yCoord, tile.zCoord);
		msg3.setInt(tile.getXp_level());
		EntityPlayerMP entityplayermp3 = MinecraftServer.getServer().getConfigurationManager().func_152612_a(this.inventoryPlayer.player.getCommandSenderName());
		MineAndConquer.simpleChannel.sendTo(msg3, entityplayermp3);
	}

	@Override
	public void detectAndSendChanges() {
		// TODO Auto-generated method stub
		super.detectAndSendChanges();
		if (this.lastXP_level != this.tile.getXp_level()) {
			SimpleNetMessageClient msg3 = new SimpleNetMessageClient(
					TENexus.MSGTOCLIENT.SYNC_XP_LEVEL.getValue(), tile.xCoord,
					tile.yCoord, tile.zCoord);
			msg3.setInt(tile.getXp_level());
			EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().func_152612_a(this.inventoryPlayer.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendTo(msg3, entityplayermp);
		}
		
		if (this.lastRevival_stone != this.tile.getRevival_numOfStone()) {
			SimpleNetMessageClient msg2 = new SimpleNetMessageClient(
					TENexus.MSGTOCLIENT.SYNC_REVIVAL_STONE.getValue(), tile.xCoord,
					tile.yCoord, tile.zCoord);
			msg2.setInt(tile.getRevival_numOfStone());
			EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().func_152612_a(this.inventoryPlayer.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendTo(msg2, entityplayermp);
		}
		
		if (this.lastRevival_time != this.tile.getRevival_time()) {
			SimpleNetMessageClient msg = new SimpleNetMessageClient(
					TENexus.MSGTOCLIENT.SYNC_REVIVAL_TIME.getValue(), tile.xCoord,
					tile.yCoord, tile.zCoord);
			msg.setInt(tile.getRevival_time());
			EntityPlayerMP entityplayermp = MinecraftServer.getServer().getConfigurationManager().func_152612_a(this.inventoryPlayer.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendTo(msg, entityplayermp);
		}
		
		this.lastXP_level = tile.getXp_level();
		this.lastRevival_stone = tile.getRevival_numOfStone();
		this.lastRevival_time = tile.getRevival_time();
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
