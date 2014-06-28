package mineandconquer.client.gui;

import mineandconquer.MineAndConquer;
import mineandconquer.inventory.ContainerNexus04;
import mineandconquer.lib.References;
import mineandconquer.lib.Strings;
import mineandconquer.network.SimpleNetMessageServer;
import mineandconquer.tileentities.TENexus;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUINexus04 extends GuiContainer {

	TENexus teNexus;
	ResourceLocation backgroundimage = new ResourceLocation(
			References.MODID.toLowerCase() + ":"
					+ "textures/gui/guinexus_xp.png");
	private InventoryPlayer player;

	private GuiTextField TextField_xp;
	// 입출금할 경험치
	
	public GUINexus04(InventoryPlayer player, TENexus nexus) {
		super(new ContainerNexus04(player, nexus));
		xSize = 194;
		ySize = 214;
		this.teNexus = nexus;
		this.player = player;
	}

	@Override
	public void initGui() {
		// TODO Auto-generated method stub
		int zeroX = (this.width - xSize) / 2;
		int zeroY = (this.height - ySize-30) / 2;
		
		super.initGui();
		this.buttonList.add(new GuiButton(0, zeroX+105, zeroY+10, 40, 20, "Deposit"));
		this.buttonList.add(new GuiButton(1, zeroX+145, zeroY+10, 40, 20, "Withdraw"));
	
		TextField_xp = new GuiTextField(fontRendererObj, zeroX + 70, zeroY + 15, 30, 10);
		TextField_xp.setFocused(false);
		TextField_xp.setMaxStringLength(20);
	}
	
	@Override
	protected void keyTyped(char par1, int par2) {
		// TODO Auto-generated method stub
		super.keyTyped(par1, par2);
		if (TextField_xp.isFocused()) {
			TextField_xp.textboxKeyTyped(par1, par2);
		}
	}
	
	
	/**
	 * 
	 */
	@Override
	protected void mouseClicked(int x, int y, int state) {
		// TODO Auto-generated method stub
		super.mouseClicked(x, y, state);
		int zeroX = (this.width - xSize) / 2;
		int zeroY = (this.height - ySize - 30) / 2;
		if (x >= zeroX && x <= zeroX + 17 && y >= zeroY && y <= zeroY + 21) {

			SimpleNetMessageServer msg = new SimpleNetMessageServer(2,
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(this.player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);

			(this.player.player).openGui(MineAndConquer.instance,
					Strings.GuiNexusID01, teNexus.getWorldObj(),
					teNexus.xCoord, teNexus.yCoord, teNexus.zCoord);
		}
		if (x >= zeroX && x <= zeroX + 17 && y >= zeroY + 24 && y <= zeroY + 45) {

			SimpleNetMessageServer msg = new SimpleNetMessageServer(3,
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(this.player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);

			(this.player.player).openGui(MineAndConquer.instance,
					Strings.GuiNexusID02, teNexus.getWorldObj(),
					teNexus.xCoord, teNexus.yCoord, teNexus.zCoord);

		}
		if (x >= zeroX && x <= zeroX + 17 && y >= zeroY + 48 && y <= zeroY + 69) {

			SimpleNetMessageServer msg = new SimpleNetMessageServer(4,
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(this.player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);

			(this.player.player).openGui(MineAndConquer.instance,
					Strings.GuiNexusID03, teNexus.getWorldObj(),
					teNexus.xCoord, teNexus.yCoord, teNexus.zCoord);
		}
		
		TextField_xp.mouseClicked(x, y, state);
	}
	
	@Override
	public void updateScreen() {
		// TODO Auto-generated method stub
	}



	@Override
	public boolean doesGuiPauseGame() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		// TODO Auto-generated method stub
		int zeroX = (this.width - xSize) / 2;
		int zeroY = (this.height - ySize-30) / 2;
		this.mc.getTextureManager().bindTexture(backgroundimage);
		drawTexturedModalRect(zeroX, zeroY, 0, 0, xSize, ySize);

		fontRendererObj.FONT_HEIGHT = 10;
		fontRendererObj.drawString("Your XP", zeroX + 25, zeroY + 5, 0);
		
		fontRendererObj.FONT_HEIGHT = 10;
		fontRendererObj.drawString(": " + Integer.toString((int)this.player.player.experienceTotal), zeroX + 25, zeroY + 15, 0);
		
		TextField_xp.drawTextBox();
		
	}

}
