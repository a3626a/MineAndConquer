package mineandconquer.tileentities;

import mineandconquer.items.ModItems;
import mineandconquer.network.SimpleNetMessageClient;
import mineandconquer.network.SimpleNetMessageServer;
import mineandconquer.network.SimpleNetReceiver;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TEWallMaker extends TileEntity implements IInventory,
		SimpleNetReceiver {

	private ItemStack[] inventory;
	private int INVENTORY_SIZE = 14;
	// 0~11 : input
	// 12 : fuel
	// 13 : output

	public int wallWidth;
	public int wallHeight;

	public int currentWallWidth;
	public int currentWallHeight;

	/** The number of ticks that the furnace will keep burning */
	public int furnaceBurnTime;
	/**
	 * The number of ticks that a fresh copy of the currently-burning item would
	 * keep the furnace burning for
	 */
	public int currentItemBurnTime;
	/** The number of ticks that the current item has been cooking for */
	public int furnaceCookTime;
	public Block currentBlock;

	public TEWallMaker() {
		inventory = new ItemStack[INVENTORY_SIZE];
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

	/**
	 * Returns true if automation is allowed to insert the given stack (ignoring
	 * stack size) into the given slot.
	 */
	@Override
	public boolean isItemValidForSlot(int var1, ItemStack var2) {

		if (var1 >= 0 && var1 < 12) {
			Item temp = null;
			for (int i = 0; i < 12; i++) {
				if (inventory[i] == null)
					continue;
				temp = inventory[i].getItem();
				break;
			}
			return temp == var2.getItem() || temp == null;
		}
		if (var1 == 12) {
			return isItemFuel(var2);
		}
		if (var1 == 13) {
			return false;
		}

		return false;
	}

	/**
	 * Returns the number of ticks that the supplied fuel item will keep the
	 * furnace burning, or 0 if the item isn't fuel
	 */
	public static int getItemBurnTime(ItemStack p_145952_0_) {
		if (p_145952_0_ == null) {
			return 0;
		} else {
			Item item = p_145952_0_.getItem();

			if (item instanceof ItemBlock
					&& Block.getBlockFromItem(item) != Blocks.air) {
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.wooden_slab) {
					return 150;
				}

				if (block.getMaterial() == Material.wood) {
					return 300;
				}

				if (block == Blocks.coal_block) {
					return 16000;
				}
			}

			if (item instanceof ItemTool
					&& ((ItemTool) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item instanceof ItemSword
					&& ((ItemSword) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item instanceof ItemHoe
					&& ((ItemHoe) item).getToolMaterialName().equals("WOOD"))
				return 200;
			if (item == Items.stick)
				return 100;
			if (item == Items.coal)
				return 1600;
			if (item == Items.lava_bucket)
				return 20000;
			if (item == Item.getItemFromBlock(Blocks.sapling))
				return 100;
			if (item == Items.blaze_rod)
				return 2400;
			return GameRegistry.getFuelValue(p_145952_0_);
		}
	}

	public static boolean isItemFuel(ItemStack p_145954_0_) {
		/**
		 * Returns the number of ticks that the supplied fuel item will keep the
		 * furnace burning, or 0 if the item isn't fuel
		 */
		return getItemBurnTime(p_145954_0_) > 0;
	}

	/**
	 * Returns an integer between 0 and the passed value representing how close
	 * the current item is to being completely cooked
	 */
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int p_145953_1_) {
		return this.furnaceCookTime * p_145953_1_ / getCookTime();
	}

	/**
	 * Returns an integer between 0 and the passed value representing how much
	 * burn time is left on the current fuel item, where 0 means that the item
	 * is exhausted and the passed value means that the item is fresh
	 */
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int p_145955_1_) {
		if (this.currentItemBurnTime == 0) {
			this.currentItemBurnTime = 200;
		}

		return this.furnaceBurnTime * p_145955_1_ / this.currentItemBurnTime;
	}

	/**
	 * Furnace isBurning
	 */
	public boolean isBurning() {
		return this.furnaceBurnTime > 0;
	}

	public void setWallSize(int w, int h) {
		this.wallWidth = w;
		this.wallHeight = h;
	}

	private int getCookTime() {
		return Math.max(20, this.currentWallWidth * currentWallWidth * 4);
	}

	public void updateEntity() {

		boolean flag = this.furnaceBurnTime > 0;
		boolean flag1 = false;

		if (this.furnaceBurnTime > 0) {
			--this.furnaceBurnTime;
		}
		// Fundamental time ticking

		if (!this.worldObj.isRemote) {
			if (this.furnaceBurnTime == 0 && this.canSmelt()) {
				this.currentItemBurnTime = this.furnaceBurnTime = getItemBurnTime(this.inventory[12]);

				if (this.furnaceBurnTime > 0) {
					flag1 = true;

					if (this.inventory[12] != null) {
						--this.inventory[12].stackSize;

						if (this.inventory[12].stackSize == 0) {
							this.inventory[12] = inventory[13].getItem()
									.getContainerItem(inventory[12]);
						}
					}
				}
			}

			// need to assign how long the wall making will take.
			// primitive: size/10

			if (this.isBurning() && this.canSmelt()) {

				++this.furnaceCookTime;

				if (currentWallHeight != wallHeight) {
					currentWallHeight = wallHeight;
					this.furnaceCookTime = 0;
				}
				if (currentWallWidth != wallWidth) {
					currentWallWidth = wallWidth;
					this.furnaceCookTime = 0;
				}

				if (this.furnaceCookTime == getCookTime()) {
					this.furnaceCookTime = 0;
					for (int i = 0; i < 12; i++) {
						if (inventory[i] != null) {
							if (inventory[i].getItem() instanceof ItemBlock) {
								this.currentBlock = ((ItemBlock) (inventory[i]
										.getItem())).field_150939_a;
							}
							break;
						}
					}
					this.smeltItem();
					flag1 = true;
				}
			} else {
				this.furnaceCookTime = 0;
			}
		}

	}

	/**
	 * Turn one item from the furnace source stack into the appropriate smelted
	 * item in the furnace result stack
	 */
	public void smeltItem() {
		if (this.canSmelt()) {
			if (this.inventory[13] == null) {

				ItemStack itemstack = new ItemStack(ModItems.wallBuilder);
				itemstack.stackTagCompound = new NBTTagCompound();
				itemstack.stackTagCompound
						.setInteger("width", currentWallWidth);
				itemstack.stackTagCompound.setInteger("height",
						currentWallHeight);

				itemstack.stackTagCompound.setString("name",
						Block.blockRegistry.getNameForObject(currentBlock));

				this.inventory[13] = itemstack;
			} else {
				this.inventory[13].stackSize++;
			}
			// set the ouput

			// consume materials

			int leftToPay = currentWallHeight * currentWallWidth;
			for (int i = 0; i < 12; i++) {
				if (inventory[i] == null)
					continue;
				if (inventory[i].stackSize < leftToPay) {
					leftToPay -= inventory[i].stackSize;
					inventory[i] = null;
				} else {
					inventory[i].stackSize -= leftToPay;
					if (inventory[i].stackSize == 0) {
						inventory[i] = null;
					}
					break;
				}

			}

		}
	}

	/**
	 * Returns true if the furnace can smelt an item, i.e. has a source item,
	 * destination stack isn't full, etc.
	 */
	private boolean canSmelt() {

		if (this.wallWidth * this.wallHeight <= 0)
			return false;

		int numOfBlocks = 0;

		Block temp = null;

		for (int i = 0; i < 12; i++) {
			if (this.inventory[i] == null)
				continue;
			temp = ((ItemBlock) this.inventory[i].getItem()).field_150939_a;
			numOfBlocks += this.inventory[i].stackSize;
		}

		if (numOfBlocks < wallWidth * wallHeight)
			return false;
		// having enough materials?

		if (this.inventory[13] == null) {
			return true;
		} else {
			return Block.blockRegistry
					.getObject(this.inventory[13].stackTagCompound
							.getString("name")) == temp
					&& this.inventory[13].stackTagCompound.getInteger("height") == this.wallHeight
					&& this.inventory[13].stackTagCompound.getInteger("width") == this.wallWidth
					&& this.inventory[13].stackSize < this.inventory[13]
							.getItem().getItemStackLimit(this.inventory[13]);
		}
		// is the output slot empty?
	}

	public void readFromNBT(NBTTagCompound p_145839_1_) {
		super.readFromNBT(p_145839_1_);
		NBTTagList nbttaglist = p_145839_1_.getTagList("Items", 10);
		this.inventory = new ItemStack[this.getSizeInventory()];

		for (int i = 0; i < nbttaglist.tagCount(); ++i) {
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte b0 = nbttagcompound1.getByte("Slot");

			if (b0 >= 0 && b0 < this.inventory.length) {
				this.inventory[b0] = ItemStack
						.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		this.furnaceBurnTime = p_145839_1_.getShort("BurnTime");
		this.furnaceCookTime = p_145839_1_.getShort("CookTime");
		this.currentItemBurnTime = getItemBurnTime(this.inventory[12]);

		if (p_145839_1_.getString("Block") != "null") {
			this.currentBlock = Block.getBlockFromName(p_145839_1_
					.getString("Block"));
		} else {
			this.currentBlock = null;
		}
		this.wallHeight = p_145839_1_.getInteger("height");
		this.wallWidth = p_145839_1_.getInteger("width");
		this.currentWallHeight = p_145839_1_.getInteger("currentheight");
		this.currentWallWidth = p_145839_1_.getInteger("currentwidth");
	}

	public void writeToNBT(NBTTagCompound p_145841_1_) {
		super.writeToNBT(p_145841_1_);
		p_145841_1_.setShort("BurnTime", (short) this.furnaceBurnTime);
		p_145841_1_.setShort("CookTime", (short) this.furnaceCookTime);
		NBTTagList nbttaglist = new NBTTagList();

		for (int i = 0; i < this.inventory.length; ++i) {
			if (this.inventory[i] != null) {
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				this.inventory[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		p_145841_1_.setTag("Items", nbttaglist);
		if (currentBlock != null) {
			p_145841_1_.setString("Block",
					Block.blockRegistry.getNameForObject(currentBlock));
		} else {
			p_145841_1_.setString("Block", "null");
		}
		p_145841_1_.setInteger("width", wallWidth);
		p_145841_1_.setInteger("height", wallHeight);
		p_145841_1_.setInteger("currentwidth", currentWallWidth);
		p_145841_1_.setInteger("currentheight", currentWallHeight);

	}

	@Override
	public void onMessage(int index, SimpleNetMessageServer data) {
		// TODO Auto-generated method stub
		switch (index) {
		case 0:
			this.wallWidth = data.getInt();
			break;
		case 1:
			this.wallHeight = data.getInt();
			break;
		}
	}

	@Override
	public void onMessage(int index, SimpleNetMessageClient data) {
		// TODO Auto-generated method stub

	}
}
