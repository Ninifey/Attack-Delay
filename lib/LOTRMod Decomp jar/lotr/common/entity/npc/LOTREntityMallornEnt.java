// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.network.LOTRPacketMallornEntHeal;
import lotr.common.LOTRAchievement;
import lotr.common.item.LOTRItemBossTrophy;
import net.minecraft.item.ItemStack;
import net.minecraft.block.BlockFire;
import net.minecraft.util.DamageSource;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PlayerManager;
import net.minecraft.world.WorldServer;
import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import lotr.common.network.LOTRPacketHandler;
import lotr.common.network.LOTRPacketMallornEntSummon;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import lotr.common.LOTRMod;
import lotr.common.entity.projectile.LOTREntityMallornLeafBomb;
import net.minecraft.entity.EntityLivingBase;
import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.EntityAIWander;
import lotr.common.entity.ai.LOTREntityAIBossJumpAttack;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.IRangedAttackMob;
import lotr.common.entity.ai.LOTREntityAIRangedAttack;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.world.World;
import net.minecraft.entity.ai.EntityAIBase;

public class LOTREntityMallornEnt extends LOTREntityEnt implements LOTRBoss
{
    public static float BOSS_SCALE;
    private static int SPAWN_TIME;
    private static int MAX_LEAF_HEALINGS;
    private LeafHealInfo[] leafHealings;
    private EntityAIBase meleeAttackAI;
    private EntityAIBase rangedAttackAI;
    
    public LOTREntityMallornEnt(final World world) {
        super(world);
        this.meleeAttackAI = new LOTREntityAIAttackOnCollide(this, 2.0, false);
        this.rangedAttackAI = new LOTREntityAIRangedAttack((IRangedAttackMob)this, 1.5, 30, 50, 24.0f);
        this.setSize(super.npcWidth * LOTREntityMallornEnt.BOSS_SCALE, super.npcHeight * LOTREntityMallornEnt.BOSS_SCALE);
        ((EntityLiving)this).tasks.taskEntries.clear();
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
        ((EntityLiving)this).tasks.addTask(0, (EntityAIBase)new LOTREntityAIBossJumpAttack(this, 1.5, 0.02f));
        ((EntityLiving)this).tasks.addTask(2, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)EntityPlayer.class, 12.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest2((EntityLiving)this, (Class)LOTREntityNPC.class, 8.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(4, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, (Class)EntityLiving.class, 10.0f, 0.02f));
        ((EntityLiving)this).tasks.addTask(5, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
        this.resetLeafHealings();
    }
    
    private void resetLeafHealings() {
        this.leafHealings = new LeafHealInfo[LOTREntityMallornEnt.MAX_LEAF_HEALINGS];
        for (int i = 0; i < LOTREntityMallornEnt.MAX_LEAF_HEALINGS; ++i) {
            this.leafHealings[i] = new LeafHealInfo(this, i);
        }
    }
    
    public void entityInit() {
        super.entityInit();
        ((Entity)this).dataWatcher.addObject(22, (Object)0);
        ((Entity)this).dataWatcher.addObject(23, (Object)0);
    }
    
    public int getEntSpawnTick() {
        return ((Entity)this).dataWatcher.getWatchableObjectShort(22);
    }
    
    public void setEntSpawnTick(final int i) {
        ((Entity)this).dataWatcher.updateObject(22, (Object)(short)i);
    }
    
    public boolean hasWeaponShield() {
        return ((Entity)this).dataWatcher.getWatchableObjectByte(23) == 1;
    }
    
    public void setHasWeaponShield(final boolean flag) {
        ((Entity)this).dataWatcher.updateObject(23, (Object)(byte)(flag ? 1 : 0));
    }
    
    public boolean isWeaponShieldActive() {
        return this.hasWeaponShield() && !this.isBurning();
    }
    
    public float getSpawningOffset(final float f) {
        float f2 = (this.getEntSpawnTick() + f) / LOTREntityMallornEnt.SPAWN_TIME;
        f2 = Math.min(f2, 1.0f);
        return (1.0f - f2) * -5.0f;
    }
    
    public boolean shouldBurningPanic() {
        return false;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(8.0);
    }
    
    @Override
    public int getExtraHeadBranches() {
        if (this.hasWeaponShield()) {
            return 0;
        }
        final int max = 8;
        final float healthR = this.getHealth() / this.getMaxHealth();
        int branches = MathHelper.ceiling_float_int(healthR * max);
        branches = MathHelper.clamp_int(branches, 1, max);
        return branches;
    }
    
    @Override
    public float getBaseChanceModifier() {
        return super.bossInfo.getHealthChanceModifier();
    }
    
    public void sendEntBossSpeech(final String speechBank) {
        final List players = ((Entity)this).worldObj.playerEntities;
        final double range = 64.0;
        for (final Object obj : players) {
            final EntityPlayer entityplayer = (EntityPlayer)obj;
            if (this.getDistanceSqToEntity((Entity)entityplayer) <= range * range) {
                this.sendSpeechBank(entityplayer, "ent/mallornEnt/" + speechBank);
            }
        }
    }
    
    public void onAttackModeChange(final AttackMode mode, final boolean mounted) {
        if (mode == AttackMode.IDLE) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            ((EntityLiving)this).tasks.addTask(1, this.meleeAttackAI);
        }
        if (mode == AttackMode.MELEE) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            ((EntityLiving)this).tasks.addTask(1, this.meleeAttackAI);
        }
        if (mode == AttackMode.RANGED) {
            ((EntityLiving)this).tasks.removeTask(this.meleeAttackAI);
            ((EntityLiving)this).tasks.removeTask(this.rangedAttackAI);
            ((EntityLiving)this).tasks.addTask(1, this.rangedAttackAI);
        }
    }
    
    public double getMeleeRange() {
        return 12.0;
    }
    
    public void attackEntityWithRangedAttack(final EntityLivingBase target, final float f) {
        final LOTREntityMallornLeafBomb leaves = new LOTREntityMallornLeafBomb(((Entity)this).worldObj, (EntityLivingBase)this, target);
        leaves.leavesDamage = 6.0f;
        ((Entity)this).worldObj.spawnEntityInWorld((Entity)leaves);
        this.playSound("lotr:ent.mallorn.leafAttack", this.getSoundVolume(), this.getSoundPitch());
        this.swingItem();
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getEntSpawnTick() < LOTREntityMallornEnt.SPAWN_TIME) {
            if (!((Entity)this).worldObj.isClient) {
                this.setEntSpawnTick(this.getEntSpawnTick() + 1);
                if (this.getEntSpawnTick() == LOTREntityMallornEnt.SPAWN_TIME) {
                    super.bossInfo.doJumpAttack(1.5);
                }
            }
            else {
                for (int l = 0; l < 16; ++l) {
                    final double d = ((Entity)this).posX + ((Entity)this).rand.nextGaussian() * ((Entity)this).width * 0.5;
                    final double d2 = ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height + this.getSpawningOffset(0.0f);
                    final double d3 = ((Entity)this).posZ + ((Entity)this).rand.nextGaussian() * ((Entity)this).width * 0.5;
                    LOTRMod.proxy.spawnParticle("mEntSpawn", d, d2, d3, 0.0, 0.0, 0.0);
                }
                for (int leaves = 8, i = 0; i < leaves; ++i) {
                    final float leafR = i / (float)leaves;
                    final float argBase = this.getEntSpawnTick() + leafR;
                    final double r = 3.5;
                    final double up = 0.5;
                    for (final float extra : new float[] { 0.0f, 3.1415927f }) {
                        final float arg = argBase + extra;
                        final double x = ((Entity)this).posX + r * MathHelper.cos(arg);
                        final double z = ((Entity)this).posZ + r * MathHelper.sin(arg);
                        final double y = ((Entity)this).posY + leafR * up;
                        LOTRMod.proxy.spawnParticle("leafGold_40", x, y, z, 0.0, up, 0.0);
                    }
                }
            }
        }
        if (!((Entity)this).worldObj.isClient) {
            float f = this.getBaseChanceModifier();
            f *= 0.05f;
            if (((Entity)this).rand.nextFloat() < f) {
                super.bossInfo.doTargetedJumpAttack(1.5);
            }
        }
        if (!((Entity)this).worldObj.isClient && this.getHealth() < this.getMaxHealth()) {
            for (final LeafHealInfo healing : this.leafHealings) {
                if (!healing.active) {
                    float f2 = this.getBaseChanceModifier();
                    f2 *= 0.02f;
                    if (((Entity)this).rand.nextFloat() < f2) {
                        final int range = 16;
                        final int j = MathHelper.floor_double(((Entity)this).posX);
                        final int k = MathHelper.floor_double(((Entity)this).posY);
                        final int m = MathHelper.floor_double(((Entity)this).posZ);
                        for (int l2 = 0; l2 < 30; ++l2) {
                            final int i2 = j + MathHelper.getRandomIntegerInRange(((Entity)this).rand, -range, range);
                            final int j2 = k + MathHelper.getRandomIntegerInRange(((Entity)this).rand, -range, range);
                            final int k2 = m + MathHelper.getRandomIntegerInRange(((Entity)this).rand, -range, range);
                            final Block block = ((Entity)this).worldObj.getBlock(i2, j2, k2);
                            if (block instanceof BlockLeavesBase) {
                                healing.active = true;
                                healing.leafX = i2;
                                healing.leafY = j2;
                                healing.leafZ = k2;
                                healing.healTime = 15 + ((Entity)this).rand.nextInt(15);
                                this.sendHealInfoToWatchers(healing);
                                break;
                            }
                        }
                    }
                }
            }
        }
        for (final LeafHealInfo healing : this.leafHealings) {
            if (healing.active) {
                final int leafX = healing.leafX;
                final int leafY = healing.leafY;
                final int leafZ = healing.leafZ;
                final Block block2 = ((Entity)this).worldObj.getBlock(leafX, leafY, leafZ);
                final int meta = ((Entity)this).worldObj.getBlockMetadata(leafX, leafY, leafZ);
                if (block2 instanceof BlockLeavesBase) {
                    if (!((Entity)this).worldObj.isClient) {
                        if (((Entity)this).ticksExisted % 20 == 0) {
                            this.heal(2.0f);
                            healing.healTime--;
                            if (this.getHealth() >= this.getMaxHealth() || healing.healTime <= 0) {
                                healing.active = false;
                                this.sendHealInfoToWatchers(healing);
                            }
                        }
                    }
                    else {
                        final double d4 = leafX + 0.5f;
                        final double d5 = leafY + 0.5f;
                        final double d6 = leafZ + 0.5f;
                        double d7 = ((Entity)this).posX - d4;
                        double d8 = ((Entity)this).posY + ((Entity)this).height * 0.9 - d5;
                        double d9 = ((Entity)this).posZ - d6;
                        d7 /= 25.0;
                        d8 /= 25.0;
                        d9 /= 25.0;
                        LOTRMod.proxy.spawnParticle("mEntHeal_" + Block.getIdFromBlock(block2) + "_" + meta, d4, d5, d6, d7, d8, d9);
                    }
                }
                else if (!((Entity)this).worldObj.isClient) {
                    healing.active = false;
                    this.sendHealInfoToWatchers(healing);
                }
            }
        }
        if (!((Entity)this).worldObj.isClient) {
            if (this.getHealth() < this.getMaxHealth() && ((Entity)this).rand.nextInt(50) == 0) {
                this.trySummonEnts();
            }
        }
        else if (this.getEntSpawnTick() >= LOTREntityMallornEnt.SPAWN_TIME) {
            for (int i3 = 0; i3 < 2; ++i3) {
                final double d = ((Entity)this).posX + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width;
                final double d2 = ((Entity)this).posY + ((Entity)this).height + ((Entity)this).rand.nextDouble() * ((Entity)this).height * 0.5;
                final double d3 = ((Entity)this).posZ + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)this).width;
                final double d10 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.2, 0.2);
                final double d11 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.2, 0.0);
                final double d12 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.2, 0.2);
                final int time = 30 + ((Entity)this).rand.nextInt(30);
                LOTRMod.proxy.spawnParticle("leafGold_" + time, d, d2, d3, d10, d11, d12);
            }
        }
    }
    
    private void trySummonEnts() {
        float f = this.getBaseChanceModifier();
        f *= 0.5f;
        final List nearbyTrees = ((Entity)this).worldObj.getEntitiesWithinAABB((Class)LOTREntityTree.class, ((Entity)this).boundingBox.expand(24.0, 8.0, 24.0));
        final int maxNearbyTrees = 6;
        final float nearbyModifier = (maxNearbyTrees - nearbyTrees.size()) / (float)maxNearbyTrees;
        f *= nearbyModifier;
        if (((Entity)this).rand.nextFloat() < f) {
            final LOTREntityTree tree = (((Entity)this).rand.nextInt(3) == 0) ? new LOTREntityHuorn(((Entity)this).worldObj) : new LOTREntityEnt(((Entity)this).worldObj);
            final int range = 12;
            final int i = MathHelper.floor_double(((Entity)this).posX);
            final int j = MathHelper.floor_double(((Entity)this).posY);
            final int k = MathHelper.floor_double(((Entity)this).posZ);
            for (int l = 0; l < 30; ++l) {
                final int i2 = i + MathHelper.getRandomIntegerInRange(((Entity)this).rand, -range, range);
                final int j2 = j + MathHelper.getRandomIntegerInRange(((Entity)this).rand, -range, range);
                final int k2 = k + MathHelper.getRandomIntegerInRange(((Entity)this).rand, -range, range);
                if (((Entity)this).worldObj.getBlock(i2, j2 - 1, k2).isNormalCube() && !((Entity)this).worldObj.getBlock(i2, j2, k2).isNormalCube() && !((Entity)this).worldObj.getBlock(i2, j2 + 1, k2).isNormalCube()) {
                    tree.setLocationAndAngles(i2 + 0.5, (double)j2, k2 + 0.5, ((Entity)this).rand.nextFloat() * 360.0f, 0.0f);
                    tree.liftSpawnRestrictions = true;
                    if (tree.getCanSpawnHere()) {
                        tree.liftSpawnRestrictions = false;
                        tree.onSpawnWithEgg(null);
                        ((Entity)this).worldObj.spawnEntityInWorld((Entity)tree);
                        this.sendEntSummon(tree);
                        ((Entity)this).worldObj.playSoundAtEntity((Entity)tree, "lotr:ent.mallorn.summonEnt", this.getSoundVolume(), this.getSoundPitch());
                        break;
                    }
                }
            }
        }
    }
    
    private void sendEntSummon(final LOTREntityTree tree) {
        final LOTRPacketMallornEntSummon packet = new LOTRPacketMallornEntSummon(this.getEntityId(), tree.getEntityId());
        LOTRPacketHandler.networkWrapper.sendToAllAround((IMessage)packet, LOTRPacketHandler.nearEntity((Entity)tree, 64.0));
    }
    
    public void onPlayerStartTracking(final EntityPlayerMP entityplayer) {
        super.onPlayerStartTracking(entityplayer);
        for (final LeafHealInfo healing : this.leafHealings) {
            healing.sendData(entityplayer);
        }
    }
    
    private void sendHealInfoToWatchers(final LeafHealInfo healing) {
        final int x = MathHelper.floor_double(((Entity)this).posX) >> 4;
        final int z = MathHelper.floor_double(((Entity)this).posZ) >> 4;
        final PlayerManager playermanager = ((WorldServer)((Entity)this).worldObj).getPlayerManager();
        final List players = ((Entity)this).worldObj.playerEntities;
        for (final Object obj : players) {
            final EntityPlayerMP entityplayer = (EntityPlayerMP)obj;
            if (playermanager.isPlayerWatchingChunk(entityplayer, x, z)) {
                healing.sendData(entityplayer);
            }
        }
    }
    
    public void receiveClientHealing(final NBTTagCompound data) {
        final LeafHealInfo healing = new LeafHealInfo(this, 0);
        healing.receiveData(data);
        this.leafHealings[healing.slot] = healing;
    }
    
    public void spawnEntSummonParticles(final LOTREntityTree tree) {
        final int type = tree.getTreeType();
        final Block leafBlock = LOTREntityTree.LEAF_BLOCKS[type];
        final int leafMeta = LOTREntityTree.LEAF_META[type];
        for (int particles = 60, l = 0; l < particles; ++l) {
            final float t = l / (float)particles;
            LOTRMod.proxy.spawnParticle("mEntSummon_" + this.getEntityId() + "_" + tree.getEntityId() + "_" + t + "_" + Block.getIdFromBlock(leafBlock) + "_" + leafMeta, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        }
        for (int l = 0; l < 120; ++l) {
            final double d = ((Entity)tree).posX + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)tree).width;
            final double d2 = ((Entity)tree).posY + ((Entity)tree).height * 0.5;
            final double d3 = ((Entity)tree).posZ + (((Entity)this).rand.nextDouble() - 0.5) * ((Entity)tree).width;
            final double d4 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.4, 0.4);
            final double d5 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.4, 0.4);
            final double d6 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.4, 0.4);
            LOTRMod.proxy.spawnParticle("mEntHeal_" + Block.getIdFromBlock(leafBlock) + "_" + leafMeta, d, d2, d3, d4, d5, d6);
        }
    }
    
    protected boolean isMovementBlocked() {
        return this.getEntSpawnTick() < LOTREntityMallornEnt.SPAWN_TIME || super.isMovementBlocked();
    }
    
    @Override
    public void onJumpAttackFall() {
        ((Entity)this).worldObj.setEntityState((Entity)this, (byte)20);
        this.playSound("lotr:troll.rockSmash", 1.5f, 0.75f);
    }
    
    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(final byte b) {
        if (b == 20) {
            for (int i = 0; i < 360; i += 2) {
                final float angle = (float)Math.toRadians(i);
                final double distance = 2.0;
                final double d = distance * MathHelper.sin(angle);
                final double d2 = distance * MathHelper.cos(angle);
                LOTRMod.proxy.spawnParticle("mEntJumpSmash", ((Entity)this).posX + d, ((Entity)this).boundingBox.minY + 0.1, ((Entity)this).posZ + d2, d * 0.2, 0.2, d2 * 0.2);
            }
        }
        if (b == 21) {
            for (int i = 0; i < 200; ++i) {
                final double d3 = ((Entity)this).posX;
                final double d4 = ((Entity)this).posY + ((Entity)this).height * 0.5f;
                final double d5 = ((Entity)this).posZ;
                final double d6 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.1, 0.1);
                final double d7 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.1, 0.1);
                final double d8 = MathHelper.getRandomDoubleInRange(((Entity)this).rand, -0.1, 0.1);
                final int time = 40 + ((Entity)this).rand.nextInt(30);
                LOTRMod.proxy.spawnParticle("leafGold_" + time, d3, d4, d5, d6, d7, d8);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        final NBTTagList leafHealingTags = new NBTTagList();
        for (int i = 0; i < this.leafHealings.length; ++i) {
            final LeafHealInfo healing = this.leafHealings[i];
            final NBTTagCompound healTag = new NBTTagCompound();
            healing.writeToNBT(healTag);
            leafHealingTags.appendTag((NBTBase)healTag);
        }
        nbt.setTag("LeafHealings", (NBTBase)leafHealingTags);
        nbt.setInteger("EntSpawnTick", this.getEntSpawnTick());
        nbt.setBoolean("EntWeaponShield", this.hasWeaponShield());
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.resetLeafHealings();
        final NBTTagList leafHealingTags = nbt.getTagList("LeafHealings", 10);
        for (int i = 0; i < leafHealingTags.tagCount(); ++i) {
            final NBTTagCompound healTag = leafHealingTags.getCompoundTagAt(i);
            final int slot = healTag.getByte("Slot");
            if (slot >= 0 && slot < this.leafHealings.length) {
                final LeafHealInfo healing = this.leafHealings[slot];
                healing.readFromNBT(healTag);
            }
        }
        this.setEntSpawnTick(nbt.getInteger("EntSpawnTick"));
        this.setHasWeaponShield(nbt.getBoolean("EntWeaponShield"));
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damagesource, float f) {
        if (this.getEntSpawnTick() < LOTREntityMallornEnt.SPAWN_TIME) {
            return false;
        }
        if (LOTRMod.playerSourceOfDamage(damagesource) == null && f > 1.0f) {
            f = 1.0f;
        }
        if (!this.isTreeEffectiveDamage(damagesource)) {
            f *= 0.5f;
        }
        if (this.isWeaponShieldActive() && !damagesource.isFireDamage()) {
            f = 0.0f;
        }
        final boolean flag = super.attackEntityFrom(damagesource, f);
        return flag;
    }
    
    @Override
    protected boolean doTreeDamageCalculation() {
        return false;
    }
    
    protected void damageEntity(final DamageSource damagesource, final float f) {
        super.damageEntity(damagesource, f);
        if (!((Entity)this).worldObj.isClient && !this.hasWeaponShield() && this.getHealth() <= 0.0f) {
            this.setHasWeaponShield(true);
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
            this.setHealth(this.getMaxHealth());
            this.sendEntBossSpeech("shield");
        }
    }
    
    @Override
    public void onDeath(final DamageSource damagesource) {
        if (!((Entity)this).worldObj.isClient) {
            ((Entity)this).worldObj.setEntityState((Entity)this, (byte)21);
            final int fireRange = 12;
            final int i = MathHelper.floor_double(((Entity)this).posX);
            final int j = MathHelper.floor_double(((Entity)this).posY);
            final int k = MathHelper.floor_double(((Entity)this).posZ);
            for (int i2 = i - fireRange; i2 <= i + fireRange; ++i2) {
                for (int j2 = j - fireRange; j2 <= j + fireRange; ++j2) {
                    for (int k2 = k - fireRange; k2 <= k + fireRange; ++k2) {
                        final Block block = ((Entity)this).worldObj.getBlock(i2, j2, k2);
                        if (block instanceof BlockFire) {
                            ((Entity)this).worldObj.setBlockToAir(i2, j2, k2);
                        }
                    }
                }
            }
        }
        super.onDeath(damagesource);
    }
    
    public void dropFewItems(final boolean flag, final int i) {
        super.dropFewItems(flag, i);
        int dropped;
        for (int wood = MathHelper.getRandomIntegerInRange(((Entity)this).rand, 20, 30 + i * 20); wood > 0; wood -= dropped) {
            dropped = Math.min(20, wood);
            this.entityDropItem(new ItemStack(LOTRMod.wood, dropped, 1), 0.0f);
        }
        int dropped2;
        for (int sticks = MathHelper.getRandomIntegerInRange(((Entity)this).rand, 30, 40 + i * 20); sticks > 0; sticks -= dropped2) {
            dropped2 = Math.min(20, sticks);
            this.entityDropItem(new ItemStack(LOTRMod.mallornStick, dropped2), 0.0f);
        }
        this.entityDropItem(new ItemStack(LOTRMod.bossTrophy, 1, LOTRItemBossTrophy.TrophyType.MALLORN_ENT.trophyID), 0.0f);
        float maceChance = 0.3f;
        maceChance += i * 0.1f;
        if (((Entity)this).rand.nextFloat() < maceChance) {
            this.func_145779_a(LOTRMod.maceMallornCharred, 1);
        }
    }
    
    @Override
    public LOTRAchievement getBossKillAchievement() {
        return LOTRAchievement.killMallornEnt;
    }
    
    @Override
    public float getAlignmentBonus() {
        return 50.0f;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 100;
    }
    
    @Override
    protected LOTRAchievement getTalkAchievement() {
        return null;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        return null;
    }
    
    static {
        LOTREntityMallornEnt.BOSS_SCALE = 1.5f;
        LOTREntityMallornEnt.SPAWN_TIME = 150;
        LOTREntityMallornEnt.MAX_LEAF_HEALINGS = 5;
    }
    
    private static class LeafHealInfo
    {
        private LOTREntityMallornEnt theEnt;
        private int slot;
        private boolean active;
        private int leafX;
        private int leafY;
        private int leafZ;
        private int healTime;
        
        public LeafHealInfo(final LOTREntityMallornEnt ent, final int i) {
            this.theEnt = ent;
            this.slot = i;
        }
        
        public void writeToNBT(final NBTTagCompound nbt) {
            nbt.setByte("Slot", (byte)this.slot);
            nbt.setBoolean("Active", this.active);
            nbt.setInteger("X", this.leafX);
            nbt.setInteger("Y", this.leafY);
            nbt.setInteger("Z", this.leafZ);
            nbt.setShort("Time", (short)this.healTime);
        }
        
        public void readFromNBT(final NBTTagCompound nbt) {
            this.slot = nbt.getByte("Slot");
            this.active = nbt.getBoolean("Active");
            this.leafX = nbt.getInteger("X");
            this.leafY = nbt.getInteger("Y");
            this.leafZ = nbt.getInteger("Z");
            this.healTime = nbt.getShort("healTime");
        }
        
        public void sendData(final EntityPlayerMP entityplayer) {
            final NBTTagCompound nbt = new NBTTagCompound();
            this.writeToNBT(nbt);
            final LOTRPacketMallornEntHeal packet = new LOTRPacketMallornEntHeal(this.theEnt.getEntityId(), nbt);
            LOTRPacketHandler.networkWrapper.sendTo((IMessage)packet, entityplayer);
        }
        
        public void receiveData(final NBTTagCompound nbt) {
            this.readFromNBT(nbt);
        }
    }
}
