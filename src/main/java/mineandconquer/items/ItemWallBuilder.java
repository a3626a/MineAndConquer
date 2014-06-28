package mineandconquer.items;

import java.util.List;

import mineandconquer.lib.References;
import mineandconquer.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemWallBuilder extends Item {

	/**
	 * �� �������� ������ ������ ���� �� �ֵ��� �����ش�
	 * 
	 * �� �������� ������ ���� ������ ������. (NBTTag)
	 * width : ������ ����
	 * height : ������ ����
	 * name : ���� ���
	 * 
	 * ������ �� ������ �������� ���Ǵ� ���ȸ� �ο��ȴ�.
	 * 
	 * x : ������ ����Ǵ� ������ ���� ����
	 * y
	 * z
	 * direction : ������ ����Ǵ� ����
	 * 
	 * 
	 * ���ϴ� ��ġ�� ��Ŭ�����ϸ� ���� ���̱� �����Ѵ�.
	 * ���� ��� ���̸� ������ ����� �ڿ������� ������.
	 * 
	 * �߰��� ������ ���� �۾��� �� ��� ������ �ߴܵȴ�.
	 * 1. �������� ������.
	 * 
	 * ����� �����ų� �ߴܵǸ� �������� �Ҹ�ȴ�.
	 * 
	 * state: true-using, false-not using.
	 * count: ���� �󸶳� ���� �ִ���
	 * ��Ŭ���� ����߸� ��밡���ѻ��°� �ȴ�.
	 * 
	 * Note!
	 * �� �������� ���콺�� ���� �� ������ ���ǵ��� �� �����̾���.
	 * �׷��� ���콺�� ������ �ִ� ���¿��� Q�� �̿��ؼ� �������� ������ ����
	 * ���콺�� �����ٰ� �ٽ� Ŭ������ �ʾƵ� ���ȴ�.
	 * ���� �÷��̿� ū ������ �������� ������ �����ؾ��� �����̴�.
	 * 
	 */
	
	public ItemWallBuilder() {
		this.setUnlocalizedName(References.RESOURCESPREFIX+Strings.ItemWallBuilderName);
		//this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setMaxStackSize(4);
		ModItems.register(this);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister iconRegister) {
		// TODO Auto-generated method stub
		this.itemIcon = iconRegister.registerIcon(ModItems.getUnwrappedUnlocalizedName(super.getUnlocalizedName()));;
	}

	@Override
	public void onCreated(ItemStack itemStack, World world,
			EntityPlayer player) {
		// TODO Auto-generated method stub
		itemStack.stackTagCompound = new NBTTagCompound();
	}
		
	@Override
	public boolean onItemUse(ItemStack itemStack,
			EntityPlayer player, World world, int blockX, int blockY,
			int blockZ, int blockSide, float posX, float posY, float posZ) {
		
		if (itemStack.stackTagCompound.getBoolean("state")) return false;
		// �̹� ����� ���¶�� ����� �� ����.
		
		int Width = itemStack.stackTagCompound.getInteger("width");
		int Height = itemStack.stackTagCompound.getInteger("height");
		// ������ �ױ����� �����ۿ� ����� ������ �ҷ��´�.
		
		double px = player.posX;
		double pz = player.posZ;
		// �÷��̾��� ��ǥ
		
		double bx = blockX + 0.5;
		double bz = blockZ + 0.5; 
		// �÷��̾ ������� ������ ���� �߽� ��ǥ
		
		boolean B1 = (px-bx)+bz > pz;
		boolean B2 = -(px-bx)+bz > pz;
		// ���� ������ �����ϱ� ���� ��ǥ
		
		int x=0,y=0,z=0;
		switch (blockSide) {
		case 0:
			x = blockX;
			y = blockY-1;
			z = blockZ;
			break;
		case 1:
			x = blockX;
			y = blockY+1;
			z = blockZ;
			break;
		case 2:
			x = blockX;
			y = blockY;
			z = blockZ-1;
			break;
		case 3:
			x = blockX;
			y = blockY;
			z = blockZ+1;
			break;
		case 4:
			x = blockX-1;
			y = blockY;
			z = blockZ;
			break;
		case 5:
			x = blockX+1;
			y = blockY;
			z = blockZ;
			break;
		}
		// �鿡 ���� ������ ���� ���� ��ǥ�� ����
		
		itemStack.stackTagCompound.setInteger("x", x);
		itemStack.stackTagCompound.setInteger("y", y);
		itemStack.stackTagCompound.setInteger("z", z);
		// ���� ��ǥ�� ����
		
		if (B1&&B2) {
			//z+
			itemStack.stackTagCompound.setString("direction", "z+");
		} else if (B1) {
			//x-
			itemStack.stackTagCompound.setString("direction", "x-");
		} else if (B2) {
			//x+
			itemStack.stackTagCompound.setString("direction", "x+");
		} else {
			//z-
			itemStack.stackTagCompound.setString("direction", "z-");
		}
		// ���� ��ǥ�� ������ �Է��Ѵ�.
		
		itemStack.stackTagCompound.setBoolean("state", true);
		itemStack.stackTagCompound.setInteger("count", 0);
		// ���� ���� �������� '�����' ���·� �����.
		
		return true;
	}
	
	@Override
	public void onUpdate(ItemStack itemStack, World world,
			Entity entity, int currentItem, boolean isInHand) {
		
		EntityPlayer player= ((EntityPlayer)entity);
		
		if (!itemStack.stackTagCompound.getBoolean("state")) return;
		
		int count = itemStack.stackTagCompound.getInteger("count");
		int Width = itemStack.stackTagCompound.getInteger("width");
		int Height = itemStack.stackTagCompound.getInteger("height");
		
		
		if (count == Width*Height) {
			itemStack.stackTagCompound.setBoolean("state", false);
			itemStack.stackTagCompound.setInteger("count", 0);
			player.inventory.decrStackSize(currentItem, 1);
			return ;
		}
		// ��� �Ϸ��
	
		String dir = itemStack.stackTagCompound.getString("direction");
		// ������ ���� �⺻���� ������ �ҷ��´�.
	
		int i = count/Height; // x, z
		int j = count%Height; // y
		// ������ ������ �������°��� Ȯ���Ѵ�
		
		int x= itemStack.stackTagCompound.getInteger("x");
		int y= itemStack.stackTagCompound.getInteger("y");
		int z= itemStack.stackTagCompound.getInteger("z");
		// ���� ��ġ�� ã�´�.
		
		Block block = Block.getBlockFromName(itemStack.stackTagCompound.getString("name"));
		// ������ ���� ���� �ҷ��´�.
		
		if (dir == "x+") {
			if (player.worldObj.isAirBlock(x+i, y+j, z))
				player.worldObj.setBlock(x+i, y+j, z, block);
			
		} else if (dir == "x-") {
			if (player.worldObj.isAirBlock(x-i, y+j, z))
				player.worldObj.setBlock(x-i, y+j, z, block);
			
		} else if (dir == "z+") {
			if (player.worldObj.isAirBlock(x, y+j, z+i))
				player.worldObj.setBlock(x, y+j, z+i, block);
		} else {
			if (player.worldObj.isAirBlock(x, y+j, z-i))
				player.worldObj.setBlock(x, y+j, z-i, block);
		}
		// ���� ��ġ�Ѵ�.
		
		itemStack.stackTagCompound.setInteger("count", count+1);
	}
	
	@Override
	public void addInformation(ItemStack itemStack,
			EntityPlayer player, List list, boolean par4) {
		if (itemStack.stackTagCompound != null) {
			list.add("Width: "
					+ itemStack.stackTagCompound.getInteger("width"));
			list.add("Height: "
					+ itemStack.stackTagCompound.getInteger("height"));
			list.add("Material: "
					+ Block.getBlockFromName(itemStack.stackTagCompound.getString("name")).getLocalizedName());
		}
	}

}
