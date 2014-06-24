package mineandconquer.core.handler;

import mineandconquer.MineAndConquer;
import net.minecraft.entity.player.EntityPlayer;

public class ModEventHandler {
	public static void onTeamMemberAdded(String player, String team) {
		MineAndConquer.teamOfPlayer.put(player, team);
	}
}
