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
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ResourceLocation;

public class GUINexus01 extends GuiContainer {

	TENexus teNexus;
	ResourceLocation backgroundimage = new ResourceLocation(
			References.MODID.toLowerCase() + ":"
					+ "textures/gui/guinexus_teamConfiguration.png");
	private InventoryPlayer player;
	private GuiTextField TextField_input;

	public GUINexus01(InventoryPlayer player, TENexus nexus) {
		super(new ContainerNexus01(player, nexus));
		xSize = 194;
		ySize = 214;
		this.teNexus = nexus;
		this.player = player;
	}

	@Override
	protected void actionPerformed(GuiButton but) {
		super.actionPerformed(but);
		// 앞서 말했듯이, id값에 따라 버튼을 구분한다. 파라미터로 주어진 but.id를 하면 해당 버튼의 id를 알 수 있다.
		switch (but.id) {
		case 0:
			if (!this.teNexus.getTeam_members().get(0).equals(this.player.player.getCommandSenderName())) {
				ChatComponentText chat = new ChatComponentText("Only commander can change your team");
				player.player.addChatMessage(chat);
				break;
			}
			if (!this.teNexus.isActive() && !TextField_input.getText().isEmpty()) {
				String team = TextField_input.getText();
				SimpleNetMessageServer msg = new SimpleNetMessageServer(TENexus.MSGTOSERVER.SET_TEAM_NAME.getValue(),
						this.teNexus.xCoord, this.teNexus.yCoord,
						this.teNexus.zCoord);
				msg.setString(team);
				MineAndConquer.simpleChannel.sendToServer(msg);
			}
		    break;
		case 1:
			if (!this.teNexus.getTeam_members().get(0).equals(this.player.player.getCommandSenderName())) {
				ChatComponentText chat = new ChatComponentText("Only commander can change your team");
				player.player.addChatMessage(chat);
				break;
			}
			if (!this.teNexus.isActive() && !TextField_input.getText().isEmpty()) {
				String member = TextField_input.getText();
				SimpleNetMessageServer msg = new SimpleNetMessageServer(TENexus.MSGTOSERVER.ADD_TEAM_MEMBERS.getValue(),
						this.teNexus.xCoord, this.teNexus.yCoord,
						this.teNexus.zCoord);
				msg.setString(member);
				MineAndConquer.simpleChannel.sendToServer(msg);
			}
			break;
		case 2:
			if (!this.teNexus.getTeam_members().get(0).equals(this.player.player.getCommandSenderName())) {
				ChatComponentText chat = new ChatComponentText("Only commander can change your team");
				player.player.addChatMessage(chat);
				break;
			}
			if (!this.teNexus.isActive() && !TextField_input.getText().isEmpty()) {
				String member = TextField_input.getText();
				SimpleNetMessageServer msg = new SimpleNetMessageServer(TENexus.MSGTOSERVER.DEL_TEAM_MEMBERS.getValue(),
						this.teNexus.xCoord, this.teNexus.yCoord,
						this.teNexus.zCoord);
				msg.setString(member);
				MineAndConquer.simpleChannel.sendToServer(msg);
			}
			break;
		case 3:
			if (!this.teNexus.getTeam_members().get(0).equals(this.player.player.getCommandSenderName())) {
				ChatComponentText chat = new ChatComponentText("Only commander can change your team");
				player.player.addChatMessage(chat);
				break;
			}
			if (!this.teNexus.isActive()) {
				SimpleNetMessageServer msg = new SimpleNetMessageServer(TENexus.MSGTOSERVER.EST_TEAM.getValue(),
						this.teNexus.xCoord, this.teNexus.yCoord,
						this.teNexus.zCoord);
				MineAndConquer.simpleChannel.sendToServer(msg);
			}
			break;
		}

	}

	@Override
	public void initGui() {
		super.initGui();
		int zeroX = (this.width - xSize) / 2;
		int zeroY = (this.height - ySize - 30) / 2;
		buttonList.add(new GuiButton(0, zeroX + 131, zeroY + 17, 57, 20,
					"Name"));
		buttonList.add(new GuiButton(1, zeroX + 131, zeroY + 35, 57, 20,
					"Add"));
		buttonList.add(new GuiButton(2, zeroX + 131, zeroY + 53, 57, 20,
				"Delete"));
		buttonList.add(new GuiButton(3, zeroX + 131, zeroY + 71, 57, 20,
				"Establish"));
		// 버튼을 추가한다. 각 파라미터는 앞에서 부터 '버튼 id', '버튼이 놓일 x좌표', '버튼이 놓일 y좌표', '버튼의 좌우크기', '버튼의 상하크기', '버튼에 씌여질 글자'를 전달한다.
		// 이때 버튼 id는 버튼을 눌렸을 때, 어느 버튼이 눌렸는지 구분하기위한 지표이다.
		// actionPerformed 메소드에서 버튼이 눌렸을 때 무엇을 할지 결정한다.
		
		TextField_input = new GuiTextField(fontRendererObj, zeroX + 26, zeroY + 116, 160,
				10);
		TextField_input.setFocused(false);
		TextField_input.setMaxStringLength(20);
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		// super.keyTyped(par1, par2);
		if (TextField_input.isFocused()) {
			TextField_input.textboxKeyTyped(par1, par2);
		} else {
			super.keyTyped(par1, par2);
		}
	}

	/**
	 * 
	 */
	@Override
	protected void mouseClicked(int x, int y, int state) {
		super.mouseClicked(x, y, state);
		int zeroX = (this.width - xSize) / 2;
		int zeroY = (this.height - ySize - 30) / 2;
		if (this.teNexus.isActive() && x >= zeroX && x <= zeroX + 17 && y >= zeroY + 24 && y <= zeroY + 45) {

			SimpleNetMessageServer msg = new SimpleNetMessageServer(TENexus.MSGTOSERVER.OPENGUI_NEXUS02.getValue(),
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(this.player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);

			(this.player.player).openGui(MineAndConquer.instance,
					Strings.GuiNexusID02, teNexus.getWorldObj(),
					teNexus.xCoord, teNexus.yCoord, teNexus.zCoord);

		}
		if (this.teNexus.isActive() && x >= zeroX && x <= zeroX + 17 && y >= zeroY + 48 && y <= zeroY + 69) {

			SimpleNetMessageServer msg = new SimpleNetMessageServer(TENexus.MSGTOSERVER.OPENGUI_NEXUS03.getValue(),
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(this.player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);

			(this.player.player).openGui(MineAndConquer.instance,
					Strings.GuiNexusID03, teNexus.getWorldObj(),
					teNexus.xCoord, teNexus.yCoord, teNexus.zCoord);

		}
		if (this.teNexus.isActive() && x >= zeroX && x <= zeroX + 17 && y >= zeroY + 72 && y <= zeroY + 93) {

			SimpleNetMessageServer msg = new SimpleNetMessageServer(TENexus.MSGTOSERVER.OPENGUI_NEXUS04.getValue(),
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(this.player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);
			(this.player.player).openGui(MineAndConquer.instance,
					Strings.GuiNexusID04, teNexus.getWorldObj(),
					teNexus.xCoord, teNexus.yCoord, teNexus.zCoord);
		}
		if (this.teNexus.isActive() && x >= zeroX && x <= zeroX + 17 && y >= zeroY + 96 && y <= zeroY + 117) {

			SimpleNetMessageServer msg = new SimpleNetMessageServer(TENexus.MSGTOSERVER.OPENGUI_NEXUS05.getValue(),
					this.teNexus.xCoord, this.teNexus.yCoord,
					this.teNexus.zCoord);
			msg.setString(this.player.player.getCommandSenderName());
			MineAndConquer.simpleChannel.sendToServer(msg);
			(this.player.player).openGui(MineAndConquer.instance,
					Strings.GuiNexusID05, teNexus.getWorldObj(),
					teNexus.xCoord, teNexus.yCoord, teNexus.zCoord);
		}
		TextField_input.mouseClicked(x, y, state);

	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
			int var3) {
		int zeroX = (this.width - xSize) / 2;
		int zeroY = (this.height - ySize - 30) / 2;
		this.mc.getTextureManager().bindTexture(backgroundimage);
		drawTexturedModalRect(zeroX, zeroY, 0, 0, xSize, ySize);

		TextField_input.drawTextBox();
		for (int i = 0; i < this.teNexus.getTeam_members().size(); i++) {
			fontRendererObj.FONT_HEIGHT = 10;
			fontRendererObj.drawString(this.teNexus.getTeam_members().get(i), zeroX + 27, zeroY + 16
					+ 12 * i, 0);
		}

		if (!this.teNexus.getTeam_name().isEmpty()) {
			fontRendererObj.drawString(this.teNexus.getTeam_name(), zeroX + 25, zeroY + 5, 0);
		}

		if (this.teNexus.getTeam_name().isEmpty()) {
			fontRendererObj.FONT_HEIGHT = 10;
			fontRendererObj.drawString(
					"Enter your team name and press \"Name\" button",
					zeroX + 19, zeroY + 215, 255 << 16);
		}

	}

}
