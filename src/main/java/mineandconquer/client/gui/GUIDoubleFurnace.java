package mineandconquer.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mineandconquer.inventory.ContainerDoubleFurnace;
import mineandconquer.lib.References;
import mineandconquer.tileentities.TEDoubleFurnace;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerFurnace;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIDoubleFurnace extends GuiContainer
{
    private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(References.MODID.toLowerCase()+":"+"textures/gui/doublefurgui.png");
    private TEDoubleFurnace tileFurnace;
    private static final String __OBFID = "CL_00000758";

    public GUIDoubleFurnace(InventoryPlayer par1InventoryPlayer, TEDoubleFurnace par2TileEntityFurnace)
    {
        super(new ContainerDoubleFurnace(par1InventoryPlayer, par2TileEntityFurnace));
        this.tileFurnace = par2TileEntityFurnace;
        xSize=176;
        ySize=166;
    }	

    /**
     * Draw the foreground layer for the GuiContainer (everything in front of the items)
     */
    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
        String s="Double Furnace";
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2, 6, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1;
        
        if (this.tileFurnace.isBurning())
        {
            i1 = this.tileFurnace.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(k + 56 -17, l + 36 + 12 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        i1 = this.tileFurnace.getCookProgressScaled(24);
        this.drawTexturedModalRect(k + 79 -9, l + 34, 176, 14, i1 + 1, 16);
    }
}
