package mineandconquer.core.proxy;

import mineandconquer.client.render.ModelNexusGuardian;
import mineandconquer.client.render.RenderNexusGuardian;
import mineandconquer.entities.EntityNexusGuardian;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
	public void registerEntityRendering() {
		RenderingRegistry.registerEntityRenderingHandler(EntityNexusGuardian.class, new RenderNexusGuardian(new ModelNexusGuardian(), 0.5F));
	}
}
