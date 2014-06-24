package mineandconquer.items;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class ModItems {
	public static Item wallBuilder;
	
	public static void init() {
		wallBuilder = new ItemWallBuilder();
	}

	public static void register(ItemWallBuilder item) {
		// TODO Auto-generated method stub
		GameRegistry.registerItem(item, item.getUnwrappedUnlocalizedName(item.getUnlocalizedName()));
	}
}
