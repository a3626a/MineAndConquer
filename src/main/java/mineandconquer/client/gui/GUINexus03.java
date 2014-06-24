package mineandconquer.client.gui;

import mineandconquer.MineAndConquer;
import mineandconquer.inventory.ContainerNexus03;
import mineandconquer.lib.References;
import mineandconquer.lib.Strings;
import mineandconquer.network.SimpleNetMessageServer;
import mineandconquer.tileentities.TENexus;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUINexus03 extends GuiContainer {

	TENexus teNexus;
	ResourceLocation backgroundimage = new ResourceLocation(
			References.MODID.toLowerCase() + ":"
					+ "textures/gui/guinexus_chest.png");
	private InventoryPlayer player;

	public GUINexus03(InventoryPlayer player, TENexus nexus) {
		super(new ContainerNexus03(player, nexus));
		xSize = 194;
		ySize = 214;
		this.teNexus = nexus;
		this.player = player;
	}

	@Override
	public void updateScreen() {
		// TODO Auto-generated method stub
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
		if (x >= zeroX && x <= zeroX + 17 && y >= zeroY + 72 && y <= zeroY + 93) {

			SimpleNetMessageServer msg = new SimpleNetMessageServer(5,
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(this.player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);

			(this.player.player).openGui(MineAndConquer.instance,
					Strings.GuiNexusID04, teNexus.getWorldObj(),
					teNexus.xCoord, teNexus.yCoord, teNexus.zCoord);

		}
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
		int zeroY = (this.height - ySize - 30) / 2;
		this.mc.getTextureManager().bindTexture(backgroundimage);
		drawTexturedModalRect(zeroX, zeroY, 0, 0, xSize, ySize);

	}

}
