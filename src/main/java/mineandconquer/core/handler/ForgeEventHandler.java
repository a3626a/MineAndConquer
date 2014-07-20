package mineandconquer.core.handler;

import mineandconquer.MineAndConquer;
import mineandconquer.network.SimpleNetMessageServer;
import mineandconquer.tileentities.TENexus;
import mineandconquer.tools.Coordinate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.world.WorldEvent.Unload;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {
	
	@SubscribeEvent
	public void EventWorldLoad(Unload event) {
		MineAndConquer.coorOfTeam.clear();
		MineAndConquer.teamOfPlayer.clear();
	}
	
	@SubscribeEvent
	public void EventRespawn(LivingDeathEvent event) {
		if (event.entity instanceof EntityPlayer) {
			String player = ((EntityPlayer)(event.entity)).getCommandSenderName();
			
			if (MineAndConquer.teamOfPlayer.containsKey(player)) {
				String team = MineAndConquer.teamOfPlayer.get(player);
				Coordinate coor = MineAndConquer.coorOfTeam.get(team);
  				SimpleNetMessageServer msg = new SimpleNetMessageServer(
						TENexus.MSGTOSERVER.DEATH.getValue(), coor.getX(),
						coor.getY(), coor.getZ());
				msg.setString(player);
				MineAndConquer.simpleChannel.sendToServer(msg);
			}
		}
	}
	
	@SubscribeEvent
	public void EventPVP(LivingAttackEvent event) {
		if (event.entity instanceof EntityPlayer
				&& event.source.getEntity() instanceof EntityPlayer) {
			if (MineAndConquer.teamOfPlayer
					.containsKey(((EntityPlayer) event.entity)
							.getCommandSenderName())
					&& MineAndConquer.teamOfPlayer
							.containsKey(((EntityPlayer) event.source
									.getEntity()).getCommandSenderName())
					&& MineAndConquer.teamOfPlayer
							.get(((EntityPlayer) event.entity)
									.getCommandSenderName()) == MineAndConquer.teamOfPlayer
							.get(((EntityPlayer) event.source.getEntity())
									.getCommandSenderName())) {
				event.setCanceled(true);
			}
		}
	}
}
