package mineandconquer.tileentities;

import java.util.ArrayList;

import mineandconquer.MineAndConquer;
import mineandconquer.core.handler.ModEventHandler;
import mineandconquer.lib.Strings;
import mineandconquer.network.SimpleNetMessageClient;
import mineandconquer.network.SimpleNetMessageServer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TENexus extends TileEntity implements IInventory,
		SimpleNetReceiver {

	public enum INVENTORY {shop_diamond_input, shop_cow_output, shop_sheep_output, shop_pig_output,shop_chicken_output ,shop_horse_output}
	
	private ItemStack[] inventory;
	private int INVENTORY_SIZE = 9 ;
	
	public String team_name;
	public ArrayList<String> team_members;
	
	public int shop_diamondValue;
	public int xp_level;
	public int xp_point;
	
	public TENexus() {
		inventory = new ItemStack[INVENTORY_SIZE];
		team_members = new ArrayList();
		team_name = "";
		shop_diamondValue = 0;
	}


	@Override
	public int getSizeInventory() {
		// TODO Auto-generated method stub
		return INVENTORY_SIZE;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		// TODO Auto-generated method stub
		return inventory[var1];
	}

	@Override
	public ItemStack decrStackSize(int slotIndex, int decrementAmount) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null) {
			if (itemStack.stackSize <= decrementAmount) {
				setInventorySlotContents(slotIndex, null);
			} else {
				itemStack = itemStack.splitStack(decrementAmount);
				if (itemStack.stackSize == 0) {
					setInventorySlotContents(slotIndex, null);
				}
			}
		}
		return itemStack;
	}

	/*
	 * Sets the stack on closing. If the stack is not null, set it to null
	 */
	@Override
	public ItemStack getStackInSlotOnClosing(int slotIndex) {
		ItemStack itemStack = getStackInSlot(slotIndex);
		if (itemStack != null) {
			setInventorySlotContents(slotIndex, null);
		}
		return itemStack;
	}

	/*
	 * Sets the Inventory content. If the stack you place exeeds the
	 * maxStackSize, set the size to maxStacksize.
	 */
	@Override
	public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
		inventory[slotIndex] = itemStack;
		if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
			itemStack.stackSize = getInventoryStackLimit();
		}
	}

	/*
	 * Returns the Inv name, not req.
	 */
	@Override
	public String getInventoryName() {
		return "aString";
	}

	/*
	 * IF you want, return true. Not req.
	 */
	@Override
	public boolean hasCustomInventoryName() {
		return true;
	}

	/*
	 * Max stacksize in slot.
	 */
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	/*
	 * return true, or you wont be able to use it.
	 */
	@Override
	public boolean isUseableByPlayer(EntityPlayer var1) {
		return true;
	}

	/*
	 * Do very little
	 */
	@Override
	public void openInventory() {

	}

	/*
	 * Do as little as.
	 */
	@Override
	public void closeInventory() {

	}

	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	public void updateEntity() {
		// TODO Auto-generated method stub
		if (!this.worldObj.isRemote) {
			updateShop();
		}
	}
	
	/**
	 * 넥서스의 상점 기능과 관련된 행위들을 처리한다.
	 */
	public void updateShop() {
		//기능1 : 다이아몬드가 들어오면 인식하고 소모한다..=
		if (this.inventory[INVENTORY.shop_diamond_input.ordinal()] != null && this.inventory[INVENTORY.shop_diamond_input.ordinal()].stackSize >= 1) {
			this.inventory[INVENTORY.shop_diamond_input.ordinal()].stackSize--;
			if (this.inventory[INVENTORY.shop_diamond_input.ordinal()].stackSize == 0) {
				this.inventory[INVENTORY.shop_diamond_input.ordinal()] = null;
			}
			this.shop_diamondValue+=1;
			
			SimpleNetMessageClient msg = new SimpleNetMessageClient(2,
					this.xCoord, this.yCoord, this.zCoord);
			msg.setInt(this.shop_diamondValue);
			MineAndConquer.simpleChannel.sendToAll(msg);
		}
		
		if (this.inventory[INVENTORY.shop_cow_output.ordinal()] == null) {
			this.inventory[INVENTORY.shop_cow_output.ordinal()] = new ItemStack((Item)Item.itemRegistry.getObject("spawn_egg"), 1,92);
		}
		if (this.inventory[INVENTORY.shop_sheep_output.ordinal()] == null) {
			this.inventory[INVENTORY.shop_sheep_output.ordinal()] = new ItemStack((Item)Item.itemRegistry.getObject("spawn_egg"), 1,91);
		}
		if (this.inventory[INVENTORY.shop_pig_output.ordinal()] == null) {
			this.inventory[INVENTORY.shop_pig_output.ordinal()] = new ItemStack((Item)Item.itemRegistry.getObject("spawn_egg"), 1,90);
		}
		if (this.inventory[INVENTORY.shop_chicken_output.ordinal()] == null) {
			this.inventory[INVENTORY.shop_chicken_output.ordinal()] = new ItemStack((Item)Item.itemRegistry.getObject("spawn_egg"), 1,93);
		}
		if (this.inventory[INVENTORY.shop_horse_output.ordinal()] == null) {
			this.inventory[INVENTORY.shop_horse_output.ordinal()] = new ItemStack((Item)Item.itemRegistry.getObject("spawn_egg"), 1,100);
		}
	}
	
	@Override
	public void onMessage(int index, SimpleNetMessageServer data) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			this.team_name = data.getString();
			SimpleNetMessageClient msg = new SimpleNetMessageClient(0,
					this.xCoord, this.yCoord, this.zCoord);
			msg.setString(team_name);
			MineAndConquer.simpleChannel.sendToAll(msg);
			
			ChatComponentText msg3 = new ChatComponentText("The team " + "\"" + team_name + "\"" + " has been established!");
			for (WorldServer i : MinecraftServer.getServer().worldServers) {
				for (Object j : i.playerEntities) {
					if (j instanceof EntityPlayer) {
						((EntityPlayer)j).addChatMessage(msg3);
					}
				}
			}
			ModEventHandler.onTeamMemberAdded(team_members.get(0), team_name);
			break;
		case 1:
			String member = data.getString();
			for (Object i : MinecraftServer.getServer().worldServers[0].playerEntities) {
				if (i instanceof EntityPlayer) {
					if (((EntityPlayer)i).getCommandSenderName().equals(member)) {
						team_members.add(member);
						SimpleNetMessageClient msg2 = new SimpleNetMessageClient(1,
								this.xCoord, this.yCoord, this.zCoord);
						msg2.setString(member);
						MineAndConquer.simpleChannel.sendToAll(msg2);
						
						ModEventHandler.onTeamMemberAdded(member, team_name);
					}
				}
			}
			break;
		case 2:
			String pname1 = data.getString();
			
			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname1)) {
					((EntityPlayer)i).openGui(MineAndConquer.instance, Strings.GuiNexusID01, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
				}
			}
			break;
		case 3:
			String pname2 = data.getString();
			
			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname2)) {
					((EntityPlayer)i).openGui(MineAndConquer.instance, Strings.GuiNexusID02, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
				}
			}
			break;
		case 4:
			String pname3 = data.getString();
			
			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname3)) {
					((EntityPlayer)i).openGui(MineAndConquer.instance, Strings.GuiNexusID03, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
				}
			}
			break;
		case 5:
			String pname4 = data.getString();
			
			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname4)) {
					((EntityPlayer)i).openGui(MineAndConquer.instance, Strings.GuiNexusID04, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
				}
			}
			break;
		}
	}

	@Override
	public void onMessage(int index, SimpleNetMessageClient data) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			this.team_name = data.getString();
			break;
		case 1:
			if (!this.team_members.contains(data.getString())) {
				this.team_members.add(data.getString());
			}
			break;
		case 2:
			this.shop_diamondValue = data.getInt();
		}
	}

	public void readFromNBT(NBTTagCompound p_145839_1_) {
		super.readFromNBT(p_145839_1_);
		/*
		 * NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
		 * this.inventory = new ItemStack[this.getSizeInventory()];
		 * 
		 * for (int i = 0; i < nbttaglist.tagCount(); ++i) { NBTTagCompound
		 * nbttagcompound1 = nbttaglist.getCompoundTagAt(i); byte b0 =
		 * nbttagcompound1.getByte("Slot");
		 * 
		 * if (b0 >= 0 && b0 < this.inventory.length) { this.inventory[b0] =
		 * ItemStack .loadItemStackFromNBT(nbttagcompound1); } }
		 */

		this.team_name = p_145839_1_.getString("team");
		this.shop_diamondValue = p_145839_1_.getInteger("shop_diamondValue");
		NBTTagList nbttaglist = p_145839_1_.getTagList("members", 10);
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			team_members.add(nbttagcompound1.getString("member"));
		}
	}

	public void writeToNBT(NBTTagCompound p_145841_1_) {
		super.writeToNBT(p_145841_1_);
		/*
		 * NBTTagList nbttaglist = new NBTTagList();
		 * 
		 * for (int i = 0; i < this.inventory.length; ++i) { if
		 * (this.inventory[i] != null) { NBTTagCompound nbttagcompound1 = new
		 * NBTTagCompound(); nbttagcompound1.setByte("Slot", (byte) i);
		 * this.inventory[i].writeToNBT(nbttagcompound1);
		 * nbttaglist.appendTag(nbttagcompound1); } }
		 * p_145841_1_.setTag("Items", nbttaglist);
		 */
		if (!team_name.equals("")) {
			p_145841_1_.setString("team", team_name);
		}
		p_145841_1_.setInteger("shop_diamondValue", shop_diamondValue);
		NBTTagList nbttaglist = new NBTTagList();
		for (String i : team_members) {
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setString("member", i);
			nbttaglist.appendTag(nbttagcompound1);
		}
		p_145841_1_.setTag("members", nbttaglist);
	}

}
