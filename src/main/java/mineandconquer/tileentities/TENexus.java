package mineandconquer.tileentities;

import java.util.ArrayList;
import java.util.PriorityQueue;

import mineandconquer.MineAndConquer;
import mineandconquer.entities.EntityNexusGuardian;
import mineandconquer.lib.Strings;
import mineandconquer.network.SimpleNetMessageClient;
import mineandconquer.network.SimpleNetMessageServer;
import mineandconquer.network.SimpleNetReceiver;
import mineandconquer.tools.Coordinate;
import mineandconquer.tools.ToolXP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;

public class TENexus extends TileEntity implements IInventory,
		SimpleNetReceiver {

	public enum INVENTORY {
		shop_diamond_input, shop_cow_output, shop_sheep_output, shop_pig_output, shop_chicken_output, shop_horse_output
	}

	public enum MSGTOSERVER {
		SET_TEAM_NAME(0), ADD_TEAM_MEMBERS(13), DEL_TEAM_MEMBERS(14), EST_TEAM(
				15), OPENGUI_NEXUS01(2), OPENGUI_NEXUS02(3), OPENGUI_NEXUS03(4), OPENGUI_NEXUS04(
				5), MOVEXP_TONEXUS5(6), MOVEXP_TONEXUS50(7), MOVEXP_TONEXUSMAX(
				8), MOVEXP_TOPLAYER5(9), MOVEXP_TOPLAYER50(10), MOVEXP_TOPLAYERMAX(
				11), LEVELUP(12), DEATH(16);
		private int value;

		private MSGTOSERVER(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
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
		SYNC_IS_ACTIVE(5), SYNC_TEAM_NAME(0), SYNC_TEAM_MEMBERS(1), SYNC_SHOP_DIAMOND(
				2), SYNC_XP_LEVEL(3), SYNC_XP_POINT(4);
		private int value;

		private MSGTOCLIENT(int value) {
			this.value = value;
		}

		public int getValue() {
			return this.value;
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
	private int INVENTORY_SIZE = 9;

	private EntityNexusGuardian guardian_entity;

	private boolean isActive;

	private String team_name;
	private ArrayList<String> team_members;
	private int shop_diamondValue;
	private int xp_level;
	private int xp_point;
	private int revival_numOfStone;
	private PriorityQueue<String> revival_bannedPlayers;

	public TENexus() {
		inventory = new ItemStack[INVENTORY_SIZE];
		isActive = false;
		team_members = new ArrayList();
		team_name = "";
		shop_diamondValue = 0;
		xp_level = 1;
		xp_point = 0;
		revival_numOfStone = getRevivalStoneCap();
	}

	@Override
	public int getSizeInventory() {
		return INVENTORY_SIZE;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
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
		return false;
	}

	/**
	 * 
	 * @param var1
	 *            : 얼마를 더할 것인가
	 * @return : 경험치 보관의 최소/최대 제한을 고려하여 실질적으로 들어간(나간) 경험치 량.
	 */
	public int addExperience(int var1) {
		if (this.getExperienceCap() - this.xp_point < var1) {
			var1 = this.getExperienceCap() - this.xp_point;
		}
		this.xp_point += var1;
		return var1;
	}

	/***
	 * 
	 * @param var1
	 *            : 얼마를 뺄 것인가
	 * @return : 최종적으로 뺀 경험치 량.
	 */
	public int extractExperience(int var1) {
		if (var1 > this.xp_point) {
			var1 = this.xp_point;
		}
		this.xp_point -= var1;
		return var1;
	}

	/***
	 * 
	 * @return : 최대 경험치 용량
	 */
	public int getExperienceCap() {
		return this.xp_level * 100;
	}

	/***
	 * 
	 * @return : 부활석을 가지고 있을 수 있는 최대 개수
	 */
	public int getRevivalStoneCap() {
		return 1 + this.xp_level;
	}

	@Override
	public void updateEntity() {
		if (!this.worldObj.isRemote) {
			updateShop();
		}
	}

	/**
	 * 넥서스의 상점 기능과 관련된 행위들을 처리한다.
	 */
	public void updateShop() {
		// 기능1 : 다이아몬드가 들어오면 인식하고 소모한다..=
		if (this.inventory[INVENTORY.shop_diamond_input.ordinal()] != null
				&& this.inventory[INVENTORY.shop_diamond_input.ordinal()].stackSize >= 1) {
			this.inventory[INVENTORY.shop_diamond_input.ordinal()].stackSize--;
			if (this.inventory[INVENTORY.shop_diamond_input.ordinal()].stackSize == 0) {
				this.inventory[INVENTORY.shop_diamond_input.ordinal()] = null;
			}
			this.shop_diamondValue += 1;

		}

		// 기능 2 : 아이템을 리필한다.
		if (this.inventory[INVENTORY.shop_cow_output.ordinal()] == null) {
			this.inventory[INVENTORY.shop_cow_output.ordinal()] = new ItemStack(
					(Item) Item.itemRegistry.getObject("spawn_egg"), 1, 92);
		}
		if (this.inventory[INVENTORY.shop_sheep_output.ordinal()] == null) {
			this.inventory[INVENTORY.shop_sheep_output.ordinal()] = new ItemStack(
					(Item) Item.itemRegistry.getObject("spawn_egg"), 1, 91);
		}
		if (this.inventory[INVENTORY.shop_pig_output.ordinal()] == null) {
			this.inventory[INVENTORY.shop_pig_output.ordinal()] = new ItemStack(
					(Item) Item.itemRegistry.getObject("spawn_egg"), 1, 90);
		}
		if (this.inventory[INVENTORY.shop_chicken_output.ordinal()] == null) {
			this.inventory[INVENTORY.shop_chicken_output.ordinal()] = new ItemStack(
					(Item) Item.itemRegistry.getObject("spawn_egg"), 1, 93);
		}
		if (this.inventory[INVENTORY.shop_horse_output.ordinal()] == null) {
			this.inventory[INVENTORY.shop_horse_output.ordinal()] = new ItemStack(
					(Item) Item.itemRegistry.getObject("spawn_egg"), 1, 100);
		}
	}

	@Override
	public void onMessage(int index, SimpleNetMessageServer data) {

		switch (MSGTOSERVER.get(index)) {
		case SET_TEAM_NAME:
			this.team_name = data.getString();
			break;
		case ADD_TEAM_MEMBERS:
			String member = data.getString();

			if (team_members.contains(member)) {
				ChatComponentText chat = new ChatComponentText(member
						+ " has already been in your team");
				this.worldObj.getPlayerEntityByName(this.team_members.get(0))
						.addChatMessage(chat);
				break;
			}

			if (MinecraftServer.getServer().getConfigurationManager().playerEntityList
					.contains(member)) {
				team_members.add(member);
				// ModEventHandler.onTeamMemberAdded(member,
				// team_name);
			} else {
				ChatComponentText chat = new ChatComponentText("can't find "
						+ member);
				this.worldObj.getPlayerEntityByName(this.team_members.get(0))
						.addChatMessage(chat);
			}

			break;
		case DEL_TEAM_MEMBERS:
			String delMember = data.getString();
			if (!this.team_members.remove(delMember)) {
				ChatComponentText chat = new ChatComponentText(delMember
						+ " is not in your team");
				this.worldObj.getPlayerEntityByName(this.team_members.get(0))
						.addChatMessage(chat);
			}
			break;
		case EST_TEAM:
			if (this.team_name.isEmpty()) {
				ChatComponentText chat = new ChatComponentText(
						"Please set your team name first");
				this.worldObj.getPlayerEntityByName(this.team_members.get(0))
						.addChatMessage(chat);
				break;
			}

			if (MineAndConquer.teamOfPlayer.containsValue(this.getTeam_name())) {
				ChatComponentText chat = new ChatComponentText("The team "
						+ this.team_name + " already exists in this world");
				this.worldObj.getPlayerEntityByName(this.team_members.get(0))
						.addChatMessage(chat);
				break;
			}

			boolean flagForBreak = false;
			for (String i : this.team_members) {
				if (MineAndConquer.teamOfPlayer.containsKey(i)) {
					ChatComponentText chat = new ChatComponentText(i
							+ " has another team");
					this.worldObj.getPlayerEntityByName(
							this.team_members.get(0)).addChatMessage(chat);
					flagForBreak = true;
				}
			}
			if (flagForBreak)
				break;

			guardian_entity.setTeam_name(this.team_name);
			for (String i : this.team_members) {
				MineAndConquer.teamOfPlayer.put(i, this.team_name);
			}
			ChatComponentText chat = new ChatComponentText("The team " + "\""
					+ this.team_name + "\"" + " has been established!");
			for (Object i : MinecraftServer.getServer()
					.getConfigurationManager().playerEntityList) {
				((EntityPlayer) i).addChatMessage(chat);
			}
			MineAndConquer.coorOfTeam.put(this.team_name, new Coordinate(
					this.xCoord, this.yCoord, this.zCoord));
			revival_bannedPlayers = new PriorityQueue<String>(
					this.team_members.size());
			this.isActive = true;
			break;
		case OPENGUI_NEXUS01:
			String pname1 = data.getString();

			this.worldObj.getPlayerEntityByName(pname1).openGui(
					MineAndConquer.instance, Strings.GuiNexusID01,
					this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			break;
		case OPENGUI_NEXUS02:
			String pname2 = data.getString();
			this.worldObj.getPlayerEntityByName(pname2).openGui(
					MineAndConquer.instance, Strings.GuiNexusID02,
					this.worldObj, this.xCoord, this.yCoord, this.zCoord);

			break;
		case OPENGUI_NEXUS03:
			String pname3 = data.getString();

			this.worldObj.getPlayerEntityByName(pname3).openGui(
					MineAndConquer.instance, Strings.GuiNexusID03,
					this.worldObj, this.xCoord, this.yCoord, this.zCoord);

			break;
		case OPENGUI_NEXUS04:
			String pname4 = data.getString();

			this.worldObj.getPlayerEntityByName(pname4).openGui(
					MineAndConquer.instance, Strings.GuiNexusID04,
					this.worldObj, this.xCoord, this.yCoord, this.zCoord);

			break;
		case MOVEXP_TONEXUS5:
			String pname5 = data.getString();

			int xpTotal = this.worldObj.getPlayerEntityByName(pname5).experienceTotal;
			int xpAdded = this.addExperience(Math.min(5, xpTotal));
			ToolXP.extractXP(this.worldObj.getPlayerEntityByName(pname5),
					xpAdded);

			break;
		case MOVEXP_TONEXUS50:
			String pname6 = data.getString();

			int xpTotal2 = this.worldObj.getPlayerEntityByName(pname6).experienceTotal;
			int xpAdded2 = this.addExperience(Math.min(50, xpTotal2));
			ToolXP.extractXP(this.worldObj.getPlayerEntityByName(pname6),
					xpAdded2);

			break;
		case MOVEXP_TONEXUSMAX:
			String pname7 = data.getString();

			int xpTotal3 = this.worldObj.getPlayerEntityByName(pname7).experienceTotal;
			int xpAdded3 = this.addExperience(xpTotal3);
			ToolXP.extractXP(this.worldObj.getPlayerEntityByName(pname7),
					xpAdded3);

			break;
		case MOVEXP_TOPLAYER5:
			String pname8 = data.getString();

			int xpextracted = this.extractExperience(5);
			this.worldObj.getPlayerEntityByName(pname8).addExperience(
					xpextracted);

			break;
		case MOVEXP_TOPLAYER50:
			String pname9 = data.getString();

			int xpextracted2 = this.extractExperience(50);
			this.worldObj.getPlayerEntityByName(pname9).addExperience(
					xpextracted2);
			break;

		case MOVEXP_TOPLAYERMAX:
			String pname10 = data.getString();

			int xpTotal4 = this.xp_point;
			int xpextracted3 = this.extractExperience(xpTotal4);
			this.worldObj.getPlayerEntityByName(pname10).addExperience(
					xpextracted3);

			break;
		case LEVELUP:
			if (this.xp_point == getExperienceCap()) {
				this.xp_level += 1;
				this.xp_point = 0;
			}
			break;
		case DEATH:
			if (!this.isActive)
				break;

			if (this.revival_numOfStone > 0) {
				this.revival_numOfStone--;
				break;
			} else {
				this.revival_bannedPlayers.add(data.getString());
				for (Object i : MinecraftServer.getServer()
						.getConfigurationManager().playerEntityList) {
					if (((EntityPlayerMP) i).getCommandSenderName().equals(
							data.getString())) {
						((EntityPlayerMP) i).playerNetServerHandler
								.kickPlayerFromServer("Wait for revival!");
						break;
					}
				}
			}

			break;
		}
	}

	@Override
	public void onMessage(int index, SimpleNetMessageClient data) {
		switch (MSGTOCLIENT.get(index)) {
		case SYNC_TEAM_NAME:
			this.team_name = data.getString();
			break;
		case SYNC_TEAM_MEMBERS:
			this.team_members.clear();
			for (String i : data.getStringArray()) {
				this.team_members.add(i);
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
		case SYNC_IS_ACTIVE:
			this.isActive = data.getBoolean();
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
		this.isActive = tag.getBoolean("isActive");
		this.team_name = tag.getString("team");
		this.shop_diamondValue = tag.getInteger("shop_diamondValue");
		this.xp_level = tag.getInteger("xp_level");
		this.xp_point = tag.getInteger("xp_point");
		this.revival_numOfStone = tag.getInteger("revival_numOfStone");

		NBTTagList nbttaglist1 = tag.getTagList("members", 10);
		for (int i = 0; i < nbttaglist1.tagCount(); i++) {
			NBTTagCompound nbttagcompound1 = nbttaglist1.getCompoundTagAt(i);
			team_members.add(nbttagcompound1.getString("member"));
		}

		if (this.isActive) {
			NBTTagList nbttaglist = tag.getTagList("bannedPlayers", 10);
			if (nbttaglist != null) {
				for (int i = 0; i < nbttaglist.tagCount(); i++) {
					NBTTagCompound nbttagcompound1 = nbttaglist
							.getCompoundTagAt(i);
					this.revival_bannedPlayers = new PriorityQueue<String>(
							this.team_members.size());
					this.revival_bannedPlayers.add(nbttagcompound1
							.getString("bannedPlayer"));
				}
			} else {
				this.revival_bannedPlayers = new PriorityQueue<String>(this.team_members.size());
			}
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
		tag.setBoolean("isActive", this.isActive);
		if (!team_name.equals("")) {
			tag.setString("team", team_name);
		}
		tag.setInteger("shop_diamondValue", shop_diamondValue);
		tag.setInteger("xp_level", xp_level);
		tag.setInteger("xp_point", xp_point);
		tag.setInteger("revival_numOfStone", revival_numOfStone);

		NBTTagList nbttaglist1 = new NBTTagList();
		for (String i : team_members) {
			NBTTagCompound nbttagcompound1 = new NBTTagCompound();
			nbttagcompound1.setString("member", i);
			nbttaglist1.appendTag(nbttagcompound1);
		}
		tag.setTag("members", nbttaglist1);

		NBTTagList nbttaglist2 = new NBTTagList();

		if (this.isActive) {
			if (!this.revival_bannedPlayers.isEmpty()) {
				for (String i : this.revival_bannedPlayers) {
					NBTTagCompound nbttagcompound1 = new NBTTagCompound();
					nbttagcompound1.setString("bannedPlayer", i);
					nbttaglist2.appendTag(nbttagcompound1);
				}
				tag.setTag("bannedPlayers", nbttaglist2);
			}
		}
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

	public EntityNexusGuardian getGuardian_entity() {
		return guardian_entity;
	}

	public void setGuardian_entity(EntityNexusGuardian guardian_entity) {
		this.guardian_entity = guardian_entity;
	}

	public int getRevival_numOfStone() {
		return revival_numOfStone;
	}

	public void setRevival_numOfStone(int revival_numOfStone) {
		this.revival_numOfStone = revival_numOfStone;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
