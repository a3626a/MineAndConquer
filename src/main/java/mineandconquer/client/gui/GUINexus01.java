package mineandconquer.client.gui;

import java.util.ArrayList;

import mineandconquer.MineAndConquer;
import mineandconquer.inventory.ContainerNexus01;
import mineandconquer.lib.References;
import mineandconquer.lib.Strings;
import mineandconquer.network.SimpleNetMessageServer;
import mineandconquer.tileentities.TENexus;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GUINexus01 extends GuiContainer {

	TENexus teNexus;
	ResourceLocation backgroundimage = new ResourceLocation(
			References.MODID.toLowerCase() + ":"
					+ "textures/gui/guinexus_teamConfiguration.png");
	private InventoryPlayer player;
	private GuiTextField input;
	private String team;
	private ArrayList<String> members;

	public GUINexus01(InventoryPlayer player, TENexus nexus) {
		super(new ContainerNexus01(player, nexus));
		xSize = 194;
		ySize = 214;
		this.teNexus = nexus;
		this.player = player;
		this.members = (ArrayList<String>) teNexus.team_members.clone();
		this.team = teNexus.team_name;
	}

	@Override
	protected void actionPerformed(GuiButton p_146284_1_) {
		// TODO Auto-generated method stub
		super.actionPerformed(p_146284_1_);
		switch (p_146284_1_.id) {
		case 0:
			if (team.equals("") && input.getText() != "") {
				team = input.getText();
				SimpleNetMessageServer msg = new SimpleNetMessageServer(0,
						this.teNexus.xCoord, this.teNexus.yCoord,
						this.teNexus.zCoord);
				msg.setString(team);
				int zeroX = (this.width - xSize) / 2;
				int zeroY = (this.height - ySize - 30) / 2;
				MineAndConquer.simpleChannel.sendToServer(msg);
				buttonList.set(0, new GuiButton(0, zeroX + 131, zeroY + 17, 57, 20,
					"add"));
			} else {
				String member = input.getText();
				SimpleNetMessageServer msg = new SimpleNetMessageServer(1,
						this.teNexus.xCoord, this.teNexus.yCoord,
						this.teNexus.zCoord);
				msg.setString(member);
				MineAndConquer.simpleChannel.sendToServer(msg);
			}
			break;
		}

	}

	@Override
	public void initGui() {
		// TODO Auto-generated method stub
		super.initGui();
		int zeroX = (this.width - xSize) / 2;
		int zeroY = (this.height - ySize - 30) / 2;
		if (this.team.equals("")) {
			buttonList.add(new GuiButton(0, zeroX + 131, zeroY + 17, 57, 20,
					"team"));
		} else {
			buttonList.add(new GuiButton(0, zeroX + 131, zeroY + 17, 57, 20,
					"add"));
		}

		input = new GuiTextField(fontRendererObj, zeroX + 26, zeroY + 116, 160,
				10);
		input.setFocused(false);
		input.setMaxStringLength(20);
	}

	@Override
	public void updateScreen() {
		// TODO Auto-generated method stub
		super.updateScreen();
		this.members = (ArrayList<String>) teNexus.team_members.clone();
		if (!this.team.equals(teNexus.team_name)) {
			this.team = teNexus.team_name;
		}
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		// TODO Auto-generated method stub
		// super.keyTyped(par1, par2);
		if (input.isFocused()) {
			input.textboxKeyTyped(par1, par2);
		} else {
			super.keyTyped(par1, par2);
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
		input.mouseClicked(x, y, state);

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

		input.drawTextBox();
		for (int i = 0; i < members.size(); i++) {
			fontRendererObj.FONT_HEIGHT = 10;
			fontRendererObj.drawString(members.get(i), zeroX + 27, zeroY + 16
					+ 12 * i, 0);
		}

		if (!team.equals("")) {
			fontRendererObj.drawString(this.team, zeroX + 25, zeroY + 5, 0);
		}

		if (team.equals("")) {
			fontRendererObj.FONT_HEIGHT = 10;
			fontRendererObj.drawString(
					"Enter your team name and press \"team\" button",
					zeroX + 19, zeroY + 215, ((1 << 8) - 1) << 16);
		}

	}

}
