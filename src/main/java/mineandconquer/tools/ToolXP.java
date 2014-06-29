package mineandconquer.tools;

import net.minecraft.entity.player.EntityPlayer;

public class ToolXP {

	public static void extractXP(EntityPlayer player, int value) {
		if (value >= player.experienceTotal)
        {
            player.experience=0;
            player.experienceTotal=0;
            player.experienceLevel=0;
            return;
        }

        player.experience -= (float)value / (float)player.xpBarCap();

        for (player.experienceTotal -= value; player.experience <= 0.0F; player.experience /= (float)player.xpBarCap())
        {
        	float par2 = player.experience*player.xpBarCap();
        	player.addExperienceLevel(-1);
        	player.experience = par2+player.xpBarCap();
        }
	}
}
