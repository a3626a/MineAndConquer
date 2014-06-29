package mineandconquer.network;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface SimpleNetReceiver {
	/**
	 * called when server handler takes message for this tile entity
	 */
	
	public void onMessage(int index, SimpleNetMessageServer data);
	
	public void onMessage(int index, SimpleNetMessageClient data);

	
	
}
