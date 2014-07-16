package mineandconquer.lib;

import java.util.HashMap;
import java.util.HashSet;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class GrinderRecipes {
	private static HashMap<HashSet<Integer>, Integer> recipes = new HashMap<HashSet<Integer>, Integer>();
	public static HashSet<HashSet<Integer>> recipeslist = new HashSet<HashSet<Integer>>();
	public static final GrinderRecipes grindingBase = new GrinderRecipes();
	public static final int cobblestoneID = ItemID(Item.getItemFromBlock(Blocks.cobblestone));
	public static final int gravelID = ItemID(Item.getItemFromBlock(Blocks.gravel));
	public static final int sandID = ItemID(Item.getItemFromBlock(Blocks.sand));
	public static final int ironoreID = ItemID(Item.getItemFromBlock(Blocks.iron_ore));
	public static final int obsidianID = ItemID(Item.getItemFromBlock(Blocks.obsidian));
	public static final int diamondID = ItemID(Items.diamond);
	public static final int ironID = ItemID(Items.iron_ingot);
	public static int[] acceptID = {cobblestoneID,gravelID,sandID,ironoreID,obsidianID,diamondID,ironID};
	public static GrinderRecipes grinding(){
		return grindingBase;
	}
	
	private GrinderRecipes(){
		HashSet<Integer> set1 = new HashSet<Integer>();
		set1.add(cobblestoneID);
		set1.add(gravelID);
		recipeslist.add(set1);
		recipes.put(set1, sandID);
	}
	
	private static int ItemID(Item item){
		return Item.getIdFromItem(item);
	}
	
	public Item getGrindResult(HashSet<Integer> set){
		if(recipeslist.contains(set)){
			return Item.getItemById(recipes.get(set));
		}
		return null;
	}
}
