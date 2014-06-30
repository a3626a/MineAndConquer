package mineandconquer.client.gui;

import mineandconquer.MineAndConquer;
import mineandconquer.inventory.ContainerWallMaker;
import mineandconquer.lib.References;
import mineandconquer.network.SimpleNetMessageServer;
import mineandconquer.tileentities.TEWallMaker;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUIWallMaker extends GuiContainer {
	
	private static final ResourceLocation backgroundimage = new ResourceLocation(References.MODID.toLowerCase() +":" + "textures/gui/guiwallmaker.png");
	private TEWallMaker teWallMaker;
	private GuiTextField TextField_widthInput;
	private GuiTextField TextField_heightInput;
	
	public GUIWallMaker(InventoryPlayer inventoryPlayer, TEWallMaker teWallMaker) {
		super(new ContainerWallMaker(inventoryPlayer, teWallMaker));
		xSize = 176;
		ySize = 214;
		this.teWallMaker = teWallMaker;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGui() {
		// TODO Auto-generated method stub
		super.initGui();
		int zeroX = (this.width-xSize)/2;
		int zeroY = (this.height-ySize-30)/2;		
		TextField_widthInput = new GuiTextField(fontRendererObj, zeroX + 130, zeroY + 20, 30, 10);
		TextField_widthInput.setFocused(false);
		TextField_widthInput.setMaxStringLength(20);
		TextField_heightInput = new GuiTextField(fontRendererObj, zeroX + 130, zeroY + 34, 30, 10);
		TextField_heightInput.setFocused(false);
		TextField_heightInput.setMaxStringLength(20);
	}
	
	
	
	@Override
	protected void keyTyped(char par1, int par2) {
		// TODO Auto-generated method stub
		super.keyTyped(par1, par2);
		if (TextField_widthInput.isFocused()) {
			TextField_widthInput.textboxKeyTyped(par1, par2);
			SimpleNetMessageServer msg = new SimpleNetMessageServer(TEWallMaker.MSGTOSERVER.SYNC_WALL_WIDTH.getValue(),teWallMaker.xCoord,teWallMaker.yCoord,teWallMaker.zCoord);
			if (TextField_widthInput.getText() != null) {
				try { 
				msg.setInt(Integer.parseInt(TextField_widthInput.getText()));
				} catch(NumberFormatException e) {
					TextField_widthInput.setText(TextField_widthInput.getText().substring(0,TextField_widthInput.getText().length()));
				}
			} else {
				msg.setInt(0);
			}
			MineAndConquer.simpleChannel.sendToServer(msg);
		}
		if (TextField_heightInput.isFocused()) {
			TextField_heightInput.textboxKeyTyped(par1, par2);
			SimpleNetMessageServer msg = new SimpleNetMessageServer(TEWallMaker.MSGTOSERVER.SYNC_WALL_HEIGHT.getValue(),teWallMaker.xCoord,teWallMaker.yCoord,teWallMaker.zCoord);
			if (TextField_heightInput.getText() != null) {
				try { 
				msg.setInt(Integer.parseInt(TextField_heightInput.getText()));
				} catch(NumberFormatException e) {
					TextField_heightInput.setText(TextField_heightInput.getText().substring(0,TextField_heightInput.getText().length()));
				}
			} else {
				msg.setInt(0);
			}
			MineAndConquer.simpleChannel.sendToServer(msg);
		}

		
	}
	
	public void mouseClicked(int i, int j, int k){
		super.mouseClicked(i, j, k);
		TextField_widthInput.mouseClicked(i, j, k);
		TextField_heightInput.mouseClicked(i, j, k);
	}
	
	public void updateScreen(){
		if (!TextField_widthInput.isFocused()) 
			TextField_widthInput.setText(Integer.toString(teWallMaker.wallWidth));
		if (!TextField_heightInput.isFocused())
			TextField_heightInput.setText(Integer.toString(teWallMaker.wallHeight));
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		// TODO Auto-generated method stub
		return false;
	}

	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		this.mc.getTextureManager().bindTexture(backgroundimage);
		
		int zeroX = (this.width-xSize)/2;
		int zeroY = (this.height-ySize-30)/2;
		
		drawTexturedModalRect(zeroX, zeroY, 0, 0,xSize, ySize);
		// TODO Auto-generated method stub
		if (this.teWallMaker.isBurning())
        {
            int i1 = this.teWallMaker.getBurnTimeRemainingScaled(12);
            this.drawTexturedModalRect(zeroX + 45, zeroY + 99 - i1, 176, 12 - i1, 14, i1 + 2);
        }

        int i1 = this.teWallMaker.getCookProgressScaled(24);
        this.drawTexturedModalRect(zeroX + 91, zeroY + 60, 176, 14, i1 + 1, 16);
        
        TextField_widthInput.drawTextBox();
		TextField_heightInput.drawTextBox();
		fontRendererObj.drawString("Width", zeroX + 90, zeroY + 20, 10);
		fontRendererObj.drawString("Height", zeroX + 90, zeroY + 34, 10);
	}
	
	
}
