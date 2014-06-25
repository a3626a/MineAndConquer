package mineandconquer.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderNexusGuardian extends RenderLiving{

	protected ResourceLocation texture;
	
	public RenderNexusGuardian(ModelBase par1ModelBase, float par2) {
		super(par1ModelBase, par2);
		setEntityTexture();
		// TODO Auto-generated constructor stub
	}

	private void setEntityTexture() {
		// TODO Auto-generated method stub
		texture = new ResourceLocation("mineandconquer:textures/entities/nexusGuardian.png");
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity var1) {
		// TODO Auto-generated method stub
		return texture;
	}

	
	
}
