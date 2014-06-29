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
		this.buttonList.add(new GuiButton(0, zeroX+47, zeroY+77, 40, 20, "¡é5"));
		this.buttonList.add(new GuiButton(1, zeroX+87, zeroY+77, 40, 20, "¡é50"));
		this.buttonList.add(new GuiButton(2, zeroX+127, zeroY+77, 40, 20, "¡éMAX"));
		//Deposit
		this.buttonList.add(new GuiButton(3, zeroX+47, zeroY+96, 40, 20, "¡è5"));
		this.buttonList.add(new GuiButton(4, zeroX+87, zeroY+96, 40, 20, "¡è50"));
		this.buttonList.add(new GuiButton(5, zeroX+127, zeroY+96, 40, 20, "¡èMAX"));
		//Withdraw
		
	}
	
	@Override
	protected void actionPerformed(GuiButton button) {
		// TODO Auto-generated method stub
		super.actionPerformed(button);
		SimpleNetMessageServer msg;
		switch(button.id) {
		case 0 :
			msg = new SimpleNetMessageServer(6,
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);
			break;
		case 1:
			msg = new SimpleNetMessageServer(7,
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);
			break;
		case 2:
			msg = new SimpleNetMessageServer(8,
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);
			break;
		case 3:
			msg = new SimpleNetMessageServer(9,
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);
			break;
		case 4:
			msg = new SimpleNetMessageServer(10,
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);
			break;
		case 5:
			msg = new SimpleNetMessageServer(11,
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);
			break;
		}
	}
	
	@Override
	protected void keyTyped(char par1, int par2) {
		// TODO Auto-generated method stub
		super.keyTyped(par1, par2);
	}
	
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
		
	}
	
	@Override
	public void updateScreen() {
		// TODO Auto-generated method stub
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
		
		fontRendererObj.FONT_HEIGHT = 10;
		fontRendererObj.drawString("Xp in Nexus: " + Integer.toString((int)this.teNexus.getXp_point()), zeroX + 50, zeroY +120, 0);
	}

	@Override
	public boolean doesGuiPauseGame() {
		// TODO Auto-generated method stub
		return false;
	}
}
