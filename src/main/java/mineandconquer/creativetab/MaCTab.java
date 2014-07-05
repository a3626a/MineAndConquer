package mineandconquer.creativetab;

import mineandconquer.items.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class MaCTab extends CreativeTabs{
	public MaCTab(int id, String modid)
    {
        super(id, modid);
    }
 
    @Override
    public Item getTabIconItem()
    {
        return ModItems.wallBuilder;
    }
}
