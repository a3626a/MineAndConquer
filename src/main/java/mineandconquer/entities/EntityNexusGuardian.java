package mineandconquer.entities;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

public class EntityNexusGuardian extends EntityMob implements IRangedAttackMob {

	
	public EntityNexusGuardian(World par1World) {
		super(par1World);
		this.tasks.addTask(6, new EntityAIWatchClosest(this,
				EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		//this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
		//		EntityPlayer.class, 0, true));
		this.tasks.addTask(4,
				new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void applyEntityAttributes() {
		// TODO Auto-generated method stub
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(200);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5);
	}
	
	@Override
	public void attackEntityWithRangedAttack(
			EntityLivingBase par1EntityLivingBase, float par2) {
		EntityArrow entityarrow = new EntityArrow(
				this.worldObj,
				this,
				par1EntityLivingBase,
				1.6F,
				(float) (14 - this.worldObj.difficultySetting.getDifficultyId() * 4));
		int i = EnchantmentHelper.getEnchantmentLevel(
				Enchantment.power.effectId, this.getHeldItem());
		int j = EnchantmentHelper.getEnchantmentLevel(
				Enchantment.punch.effectId, this.getHeldItem());
		entityarrow.setDamage((double) (par2 * 2.0F)
				+ this.rand.nextGaussian()
				* 0.25D
				+ (double) ((float) this.worldObj.difficultySetting
						.getDifficultyId() * 0.11F));

		if (i > 0) {
			entityarrow.setDamage(entityarrow.getDamage() + (double) i * 0.5D
					+ 0.5D);
		}

		if (j > 0) {
			entityarrow.setKnockbackStrength(j);
		}

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId,
				this.getHeldItem()) > 0) {
			entityarrow.setFire(100);
		}

		this.playSound("random.bow", 1.0F,
				1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.worldObj.spawnEntityInWorld(entityarrow);
	}

	@Override
	protected boolean isAIEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void knockBack(Entity par1Entity, float par2, double par3,
			double par5) {
		// TODO Auto-generated method stub
		return;
	}
	// 빡치게 넉백은 addVelocity를 쓰지 않고 직접적으로 속도를 변화시킨다.
	
	@Override
	public void addVelocity(double par1, double par3, double par5) {
		// TODO Auto-generated method stub
		return;
	}
	// 속도를 더하는 것을 막음으로써 위치 변화를 막는다
	
}
