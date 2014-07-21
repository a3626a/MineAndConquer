package mineandconquer.core.handler;

import mineandconquer.blocks.ModBlocks;
import mineandconquer.items.ModItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;

public class CraftingHandler {
	public static void init(){
		registerRecipes();
	}

	
	public static void registerRecipes(){
		CraftingManager.getInstance().addRecipe(new ItemStack(ModBlocks.DoubleFurnace, 1), "#b#","sas","sss", '#',Blocks.furnace, 's', Blocks.cobblestone, 'a', Blocks.chest, 'b', Blocks.iron_block);
		//FurnaceRecipes.smelting().func_151396_a(ModItems.testItem, new ItemStack(ModItems.dirtItem,4), 4f);
		CraftingManager.getInstance().addRecipe(new ItemStack(ModBlocks.blockWallMaker,1), "###","#$#","@@@", '#',Blocks.furnace, '$',Blocks.chest,'@',Blocks.iron_block);
		CraftingManager.getInstance().addRecipe(new ItemStack(ModBlocks.blockGrinder, 1), "###","aba","###", '#',Blocks.obsidian, 'a', Items.diamond, 'b', Blocks.chest);
	}
}

