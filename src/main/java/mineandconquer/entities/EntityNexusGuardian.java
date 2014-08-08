package mineandconquer.entities;

import mineandconquer.entities.AI.EntityAINearestAttackableEnemyTarget;
import mineandconquer.tileentities.TENexus;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityNexusGuardian extends EntityMob implements IRangedAttackMob {
	public EntityNexusGuardian(World par1World) {
		super(par1World);
		this.setSize(1, 2.5F);
		this.func_110163_bv();
		this.tasks.addTask(6, new EntityAIWatchClosest(this,
				EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(2, new EntityAINearestAttackableEnemyTarget(
				this, EntityPlayer.class, 0, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
				EntityMob.class, 0, true));
		this.tasks.addTask(4,
				new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F));
		// TODO Auto-generated constructor stub
	}

	public EntityNexusGuardian(World par1World, String team) {
		super(par1World);
		this.tasks.addTask(6, new EntityAIWatchClosest(this,
				EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(2, new EntityAINearestAttackableEnemyTarget(
				this, EntityPlayer.class, 0, true));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this,
				EntityMob.class, 0, true));
		this.tasks.addTask(4,
				new EntityAIArrowAttack(this, 1.0D, 20, 60, 15.0F));

		this.dataWatcher.addObject(12, team);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void applyEntityAttributes() {
		// TODO Auto-generated method stub
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
				.setBaseValue(150);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
				.setBaseValue(4);
		this.getEntityAttribute(SharedMonsterAttributes.movementSpeed)
				.setBaseValue(0);
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
		entityarrow.setDamage(this.getEntityAttribute(
				SharedMonsterAttributes.attackDamage).getBaseValue());

		this.playSound("random.bow", 1.0F,
				1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		this.worldObj.spawnEntityInWorld(entityarrow);
	}

	@Override
	protected boolean isAIEnabled() {
		return true;
	}

	@Override
	public void knockBack(Entity par1Entity, float par2, double par3,
			double par5) {
		return;
	}

	// 빡치게 넉백은 addVelocity를 쓰지 않고 직접적으로 속도를 변화시킨다.

	@Override
	public void addVelocity(double par1, double par3, double par5) {
		return;
	}

	// 속도를 더하는 것을 막음으로써 위치 변화를 막는다

	public void setCoordinate(int x, int y, int z) {
		this.dataWatcher.addObject(14, x);
		this.dataWatcher.addObject(15, y);
		this.dataWatcher.addObject(16, z);
	}

	public void setTeam_name(String team) {
		this.dataWatcher.addObject(12, team);
	}

	public String getTeam_name() {
		String team_name;
		try {
			team_name = this.dataWatcher.getWatchableObjectString(12);
		} catch (NullPointerException e) {
			return null;
		}
		return team_name;
	}

	public void levelup() {
		this.heal(50);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth)
				.setBaseValue(
						this.getEntityAttribute(
								SharedMonsterAttributes.maxHealth)
								.getBaseValue() + 50);
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage)
				.setBaseValue(
						this.getEntityAttribute(
								SharedMonsterAttributes.attackDamage)
								.getBaseValue() + 1);
	}

	@Override
	public void onDeath(DamageSource p_70645_1_) {
		((TENexus) this.worldObj.getTileEntity(
				this.dataWatcher.getWatchableObjectInt(14),
				this.dataWatcher.getWatchableObjectInt(15),
				this.dataWatcher.getWatchableObjectInt(16))).destroy();
		super.onDeath(p_70645_1_);
	}

	@Override
	public void onUpdate() {
		if (!this.worldObj.isRemote) {
			if (this.chunkCoordX != this.dataWatcher.getWatchableObjectInt(14)) {
				this.newPosX = this.dataWatcher.getWatchableObjectInt(14);
			}
			if (this.chunkCoordY != this.dataWatcher.getWatchableObjectInt(15)+1) {
				this.newPosY = this.dataWatcher.getWatchableObjectInt(15)+1;
			}
			if (this.chunkCoordZ != this.dataWatcher.getWatchableObjectInt(16)) {
				this.newPosZ = this.dataWatcher.getWatchableObjectInt(16);
			}
		}
	}

	private TENexus getTileEntity() {
		return (TENexus)this.worldObj.getTileEntity(this.dataWatcher.getWatchableObjectInt(14), this.dataWatcher.getWatchableObjectInt(15), this.dataWatcher.getWatchableObjectInt(16));
	}
	
	@Override
	public void onChunkLoad() {
		// TODO Auto-generated method stub
		super.onChunkLoad();
		this.getTileEntity().setGuardian_entity(this);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag) {
		// TODO Auto-generated method stub
		super.readFromNBT(tag);
		this.dataWatcher.addObject(14, tag.getInteger("x"));
		this.dataWatcher.addObject(15, tag.getInteger("y"));
		this.dataWatcher.addObject(16, tag.getInteger("z"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tag) {
		// TODO Auto-generated method stub
		super.writeToNBT(tag);
		tag.setInteger("x", this.dataWatcher.getWatchableObjectInt(14));
		tag.setInteger("y", this.dataWatcher.getWatchableObjectInt(15));
		tag.setInteger("z", this.dataWatcher.getWatchableObjectInt(16));
		
	}
}
