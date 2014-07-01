package mineandconquer.core.handler;

import mineandconquer.MineAndConquer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ForgeEventHandler {
	
	@SubscribeEvent
	public void EventRespawn(LivingDeathEvent event) {
		if (event.entity instanceof EntityPlayer) {
			System.out.println("Respawn");
			event.setCanceled(true);
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
