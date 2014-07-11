package mineandconquer.client.gui;

import mineandconquer.inventory.ContainerGrinder;
import mineandconquer.lib.References;
import mineandconquer.tileentities.TEGrinder;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

public class GUIGrinder extends GuiContainer{
	private static final ResourceLocation backgroundimage = new ResourceLocation(References.MODID.toLowerCase()+":"+"textures/gui/grindgui.png");
	
	public GUIGrinder(InventoryPlayer inplayer, TEGrinder gc){
		super(new ContainerGrinder(inplayer, gc));
		xSize = 176;
		ySize = 214;
	}
	
	@Override
	public void drawScreen(int par1, int par2, float par3) {
		// TODO Auto-generated method stub
		super.drawScreen(par1, par2, par3);
		
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3){
		GL11.glColor4f(1F, 1F, 1F, 1F);
		this.mc.getTextureManager().bindTexture(backgroundimage);
		int a = (this.width-xSize)/2;
		int b = (this.height-ySize)/2;
		drawTexturedModalRect(a,b,0,0,xSize,ySize);
	}
}
