package mineandconquer.items;

import java.util.List;

import org.apache.logging.log4j.core.appender.rolling.OnStartupTriggeringPolicy;

import mineandconquer.lib.Strings;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ItemWallBuilder extends ItemRottenRich {

	/**
	 * 이 아이템은 성벽을 빠르게 지을 수 있도록 도와준다
	 * 
	 * 이 아이템은 다음과 같은 정보를 가진다. (NBTTag)
	 * width : 성벽의 길이
	 * height : 성벽의 높이
	 * name : 블럭의 재료
	 * 
	 * 다음의 네 변수는 아이템이 사용되는 동안만 부여된다.
	 * 
	 * x : 성벽이 건축되는 동안의 기준 원점
	 * y
	 * z
	 * direction : 성벽이 건축되는 방향
	 * 
	 * 
	 * 원하는 위치에 우클릭을하면 블럭이 쌓이기 시작한다.
	 * 블럭이 모두 쌓이면 아이템 사용은 자연스럽게 끝난다.
	 * 
	 * 중간에 다음과 같은 작업을 할 경우 건축이 중단된다.
	 * 1. 아이템을 버린다.
	 * 
	 * 사용이 끝나거나 중단되면 아이템은 소모된다.
	 * 
	 * state: true-using, false-not using.
	 * count: 현재 얼마나 짓고 있는지
	 * 우클릭을 때어야만 사용가능한상태가 된다.
	 * 
	 * Note!
	 * 이 아이템은 마우스를 누른 그 순간만 사용되도록 할 생각이었다.
	 * 그러나 마우스를 누르고 있던 상태에서 Q를 이용해서 아이템을 버리고 나면
	 * 마우스를 때었다가 다시 클릭하지 않아도 사용된다.
	 * 게임 플레이에 큰 문제는 없겠지만 언젠가 수정해야할 사항이다.
	 * 
	 */
	
	public ItemWallBuilder() {
		this.setUnlocalizedName(Strings.ItemWallBuilderName);
		//this.setCreativeTab(CreativeTabs.tabAllSearch);
		this.setMaxStackSize(4);
		ModItems.register(this);
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
		// 이미 사용중 상태라면 사용할 수 없다.
		
		int Width = itemStack.stackTagCompound.getInteger("width");
		int Height = itemStack.stackTagCompound.getInteger("height");
		// 성벽을 쌓기위해 아이템에 저장된 정보를 불러온다.
		
		double px = player.posX;
		double pz = player.posZ;
		// 플레이어의 좌표
		
		double bx = blockX + 0.5;
		double bz = blockZ + 0.5; 
		// 플레이어가 대상으로 설정한 블럭의 중심 좌표
		
		boolean B1 = (px-bx)+bz > pz;
		boolean B2 = -(px-bx)+bz > pz;
		// 건축 방향을 결정하기 위한 지표
		
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
		// 면에 따라 성벽이 쌓일 기준 좌표를 설정
		
		itemStack.stackTagCompound.setInteger("x", x);
		itemStack.stackTagCompound.setInteger("y", y);
		itemStack.stackTagCompound.setInteger("z", z);
		// 기준 좌표를 저장
		
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
		// 기준 좌표와 방향을 입력한다.
		
		itemStack.stackTagCompound.setBoolean("state", true);
		itemStack.stackTagCompound.setInteger("count", 0);
		// 가장 먼저 아이템을 '사용중' 상태로 만든다.
		
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
		// 사용 완료시
	
		String dir = itemStack.stackTagCompound.getString("direction");
		// 성벽에 대한 기본적인 정보를 불러온다.
	
		int i = count/Height; // x, z
		int j = count%Height; // y
		// 성벽이 어디까지 지어졌는가를 확인한다
		
		int x= itemStack.stackTagCompound.getInteger("x");
		int y= itemStack.stackTagCompound.getInteger("y");
		int z= itemStack.stackTagCompound.getInteger("z");
		// 기준 위치를 찾는다.
		
		Block block = Block.getBlockFromName(itemStack.stackTagCompound.getString("name"));
		// 성벽에 사용될 블럭을 불러온다.
		
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
		// 블럭을 설치한다.
		
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
