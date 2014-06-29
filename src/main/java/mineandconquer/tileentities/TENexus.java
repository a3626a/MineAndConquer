package mineandconquer.tileentities;

import java.util.ArrayList;

import mineandconquer.MineAndConquer;
import mineandconquer.core.handler.ModEventHandler;
import mineandconquer.lib.Strings;
import mineandconquer.network.SimpleNetMessageClient;
import mineandconquer.network.SimpleNetMessageServer;
import mineandconquer.network.SimpleNetReceiver;
import mineandconquer.tools.ToolXP;
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
	public enum MSGTOSERVER {
		SYNC_TEAM_NAME(0),
		SYNC_TEAM_MEMBERS(1),
		OPENGUI_NEXUS01(2),
		OPENGUI_NEXUS02(3),
		OPENGUI_NEXUS03(4),
		OPENGUI_NEXUS04(5),
		MOVEXP_TONEXUS5(6),
		MOVEXP_TONEXUS50(7),
		MOVEXP_TONEXUSMAX(8),
		MOVEXP_TOPLAYER5(9),
		MOVEXP_TOPLAYER50(10),
		MOVEXP_TOPLAYERMAX(11)
		;
		private int value;
		private MSGTOSERVER(int value) {
			this.value = value;
		}
		public static MSGTOSERVER get(int value) {
			for (MSGTOSERVER i : MSGTOSERVER.values()) {
				if (i.value == value) {
					return i;
				}
			}
			return null;
		}
	};
	public enum MSGTOCLIENT {
		SYNC_TEAM_NAME(0),
		SYNC_TEAM_MEMBERS(1),
	    SYNC_SHOP_DIAMOND(2),
		SYNC_XP_LEVEL(3),
		SYNC_XP_POINT(4)
		;
		private int value;
		private MSGTOCLIENT(int value) {
			this.value = value;
		}
		public static MSGTOCLIENT get(int value) {
			for (MSGTOCLIENT i : MSGTOCLIENT.values()) {
				if (i.value == value) {
					return i;
				}
			}
			return null;
		}
	};
	
	private ItemStack[] inventory;
	private int INVENTORY_SIZE = 9 ;
	
	private String team_name;
	private ArrayList<String> team_members;
	
	private int shop_diamondValue;
	private int xp_level;
	private int xp_point;
	
	public TENexus() {
		inventory = new ItemStack[INVENTORY_SIZE];
		team_members = new ArrayList();
		team_name = "";
		shop_diamondValue = 0;
		xp_level = 1;
		xp_point = 0;
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

/**
 * 
 * @param var1 : 얼마를 더할 것인가
 * @return : 경험치 보관의 최소/최대 제한을 고려하여 실질적으로 들어간(나간) 경험치 량.
 */
	public int addExperience(int var1) {
		if (this.getExperienceCap() - this.xp_point < var1) {
			var1 = this.getExperienceCap() - this.xp_point;
		}
		this.xp_point+=var1;
		return var1;
	}
	
	/***
	 * 
	 * @param var1 : 얼마를 뺄 것인가
	 * @return : 최종적으로 뺀 경험치 량.
	 */
	public int extractExperience(int var1) {
		if (var1 > this.xp_point) {
			var1 = this.xp_point;
		}
		this.xp_point-=var1;
		return var1;
	}
	
	/***
	 * 
	 * @return : 현재 경험치 용량
	 */
	public int getExperienceCap() {
		return this.xp_level*100;
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
		
		switch (MSGTOSERVER.get(index)) {
		case SYNC_TEAM_NAME:
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
		case SYNC_TEAM_MEMBERS:
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
						break;
					}
				}
			}
			break;
		case OPENGUI_NEXUS01:
			String pname1 = data.getString();
			
			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname1)) {
					((EntityPlayer)i).openGui(MineAndConquer.instance, Strings.GuiNexusID01, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
					break;
				}
			}
			break;
		case OPENGUI_NEXUS02:
			String pname2 = data.getString();
			
			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname2)) {
					((EntityPlayer)i).openGui(MineAndConquer.instance, Strings.GuiNexusID02, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
					break;
				}
			}
			break;
		case OPENGUI_NEXUS03:
			String pname3 = data.getString();
			
			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname3)) {
					((EntityPlayer)i).openGui(MineAndConquer.instance, Strings.GuiNexusID03, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
					break;
				}
			}
			break;
		case OPENGUI_NEXUS04:
			String pname4 = data.getString();
			
			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname4)) {
					((EntityPlayer)i).openGui(MineAndConquer.instance, Strings.GuiNexusID04, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
					break;
				}
			}
			break;
		case MOVEXP_TONEXUS5:
			String pname5 = data.getString();

			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname5)) {
					int xpTotal = ((EntityPlayer)i).experienceTotal;	
					int xpAdded = this.addExperience(Math.min(5, xpTotal));
					ToolXP.extractXP((EntityPlayer)i,xpAdded);
					
					SimpleNetMessageClient msg2 = new SimpleNetMessageClient(4,
							this.xCoord, this.yCoord, this.zCoord);
					msg2.setInt(this.xp_point);
					MineAndConquer.simpleChannel.sendToAll(msg2);
					break;
				}
			}
			break;
		case MOVEXP_TONEXUS50:
			String pname6 = data.getString();

			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname6)) {
					int xpTotal = ((EntityPlayer)i).experienceTotal;	
					int xpAdded = this.addExperience(Math.min(50, xpTotal));
					ToolXP.extractXP((EntityPlayer)i,xpAdded);
					SimpleNetMessageClient msg2 = new SimpleNetMessageClient(4,
							this.xCoord, this.yCoord, this.zCoord);
					msg2.setInt(this.xp_point);
					MineAndConquer.simpleChannel.sendToAll(msg2);
					break;
				}
			}
			break;
		case MOVEXP_TONEXUSMAX:
			String pname7 = data.getString();

			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname7)) {
					int xpTotal = ((EntityPlayer)i).experienceTotal;	
					int xpAdded = this.addExperience(xpTotal);
					ToolXP.extractXP((EntityPlayer)i,xpAdded);
					SimpleNetMessageClient msg2 = new SimpleNetMessageClient(4,
							this.xCoord, this.yCoord, this.zCoord);
					msg2.setInt(this.xp_point);
					MineAndConquer.simpleChannel.sendToAll(msg2);
					break;
				}
			}
			break;
		case MOVEXP_TOPLAYER5:
			String pname8 = data.getString();

			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname8)) {
					int xpextracted = this.extractExperience(5);
					((EntityPlayer)i).addExperience(xpextracted);
					SimpleNetMessageClient msg2 = new SimpleNetMessageClient(4,
							this.xCoord, this.yCoord, this.zCoord);
					msg2.setInt(this.xp_point);
					MineAndConquer.simpleChannel.sendToAll(msg2);
					break;
				}
			}
			break;
		case MOVEXP_TOPLAYER50:
			String pname9 = data.getString();

			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname9)) {
					int xpextracted = this.extractExperience(50);
					((EntityPlayer)i).addExperience(xpextracted);
					SimpleNetMessageClient msg2 = new SimpleNetMessageClient(4,
							this.xCoord, this.yCoord, this.zCoord);
					msg2.setInt(this.xp_point);
					MineAndConquer.simpleChannel.sendToAll(msg2);
					break;
				}
			}
			break;
		case MOVEXP_TOPLAYERMAX:
			String pname10 = data.getString();

			for (Object i : this.worldObj.playerEntities) {
				if (((EntityPlayer)i).getCommandSenderName().equals(pname10)) {
					int xpTotal = this.xp_point;
					int xpextracted = this.extractExperience(xpTotal);
					((EntityPlayer)i).addExperience(xpextracted);
					SimpleNetMessageClient msg2 = new SimpleNetMessageClient(4,
							this.xCoord, this.yCoord, this.zCoord);
					msg2.setInt(this.xp_point);
					MineAndConquer.simpleChannel.sendToAll(msg2);
					break;
				}
			}
			break;
		}
	}

	@Override
	public void onMessage(int index, SimpleNetMessageClient data) {
		// TODO Auto-generated method stub
		switch (MSGTOCLIENT.get(index)) {
		case SYNC_TEAM_NAME:
			this.team_name = data.getString();
			break;
		case SYNC_TEAM_MEMBERS:
			if (!this.team_members.contains(data.getString())) {
				this.team_members.add(data.getString());
			}
			break;
		case SYNC_SHOP_DIAMOND:
			this.shop_diamondValue = data.getInt();
			break;
		case SYNC_XP_LEVEL:
			this.xp_level = data.getInt();
			break;
		case SYNC_XP_POINT:
			this.xp_point = data.getInt();
			break;
		}
	}

	
	
	
	public void readFromNBT(NBTTagCompound tag) {
		super.readFromNBT(tag);
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
		this.team_name = tag.getString("team");
		this.shop_diamondValue = tag.getInteger("shop_diamondValue");
		this.xp_level = tag.getInteger("xp_level");
		this.xp_point = tag.getInteger("xp_point");
		
		NBTTagList nbttaglist = tag.getTagList("members", 10);
		for (int i = 0; i < nbttaglist.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			team_members.add(nbttagcompound1.getString("member"));
		}
	}

	public void writeToNBT(NBTTagCompound tag) {
		super.writeToNBT(tag);
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
			tag.setString("team", team_name);
		}
		tag.setInteger("shop_diamondValue", shop_diamondValue);
		tag.setInteger("xp_level", xp_level);
		tag.setInteger("xp_point", xp_point);
		
		NBTTagList nbttaglist = new NBTTagList();
		for (String i : team_members) {
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setString("member", i);
			nbttaglist.appendTag(nbttagcompound1);
		}
		tag.setTag("members", nbttaglist);
	}

	
	
	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public ArrayList<String> getTeam_members() {
		return team_members;
	}

	public void setTeam_members(ArrayList<String> team_members) {
		this.team_members = team_members;
	}

	public void addTeam_members(String member) {
		this.team_members.add(member);
	}
	
	public int getShop_diamondValue() {
		return shop_diamondValue;
	}

	public void setShop_diamondValue(int shop_diamondValue) {
		this.shop_diamondValue = shop_diamondValue;
	}

	public int getXp_level() {
		return xp_level;
	}

	public void setXp_level(int xp_level) {
		this.xp_level = xp_level;
	}

	public int getXp_point() {
		return xp_point;
	}

	public void setXp_point(int xp_point) {
		this.xp_point = xp_point;
	}

}
