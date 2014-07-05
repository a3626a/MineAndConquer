package mineandconquer.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItems {
	public static Item wallBuilder;
	
	public static void init() {
		wallBuilder = new ItemWallBuilder();
	}

	public static String getUnwrappedUnlocalizedName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
		
	}
	public static String getName(String unlocalizedName)
	{
		return unlocalizedName.substring(unlocalizedName.indexOf(":") + 1);
		
	}
	
	public static void register(Item item) {
		// TODO Auto-generated method stub
		
		GameRegistry.registerItem(item, getName(item.getUnlocalizedName()));
	}
}
