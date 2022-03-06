// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import java.util.Iterator;
import net.minecraft.block.Block;
import lotr.common.LOTRMod;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import java.util.Collection;
import net.minecraft.command.IEntitySelector;
import lotr.common.entity.ai.LOTRNPCTargetSelector;
import net.minecraft.entity.EntityLiving;
import lotr.common.LOTRLevelData;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.util.MathHelper;
import java.util.HashMap;
import org.apache.commons.lang3.tuple.Pair;
import java.util.UUID;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;

public class LOTRBossInfo
{
    private LOTREntityNPC theNPC;
    private LOTRBoss theBoss;
    public EntityPlayer lastAttackingPlayer;
    private Map<UUID, Pair<Integer, Float>> playerHurtTimes;
    private static int PLAYER_HURT_COOLDOWN;
    private static float PLAYER_DAMAGE_THRESHOLD;
    public boolean jumpAttack;
    
    public LOTRBossInfo(final LOTRBoss boss) {
        this.playerHurtTimes = new HashMap<UUID, Pair<Integer, Float>>();
        this.theBoss = boss;
        this.theNPC = (LOTREntityNPC)this.theBoss;
    }
    
    public float getHealthChanceModifier() {
        final float f = 1.0f - this.theNPC.getHealth() / this.theNPC.getMaxHealth();
        return MathHelper.sqrt_float(f);
    }
    
    public List getNearbyEnemies() {
        final List enemies = new ArrayList();
        final List players = ((Entity)this.theNPC).worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, ((Entity)this.theNPC).boundingBox.expand(12.0, 6.0, 12.0));
        for (int i = 0; i < players.size(); ++i) {
            final EntityPlayer entityplayer = players.get(i);
            if (!entityplayer.capabilities.isCreativeMode) {
                if (LOTRLevelData.getData(entityplayer).getAlignment(this.theNPC.getFaction()) < 0.0f) {
                    enemies.add(entityplayer);
                }
            }
        }
        enemies.addAll(((Entity)this.theNPC).worldObj.selectEntitiesWithinAABB((Class)EntityLiving.class, ((Entity)this.theNPC).boundingBox.expand(12.0, 6.0, 12.0), (IEntitySelector)new LOTRNPCTargetSelector((EntityLiving)this.theNPC)));
        return enemies;
    }
    
    public void doJumpAttack(final double jumpSpeed) {
        this.jumpAttack = true;
        ((Entity)this.theNPC).motionY = jumpSpeed;
    }
    
    public void doTargetedJumpAttack(final double jumpSpeed) {
        if (!((Entity)this.theNPC).worldObj.isClient && this.lastAttackingPlayer != null && (((Entity)this.lastAttackingPlayer).posY - ((Entity)this.theNPC).posY > 10.0 || this.theNPC.getDistanceSqToEntity((Entity)this.lastAttackingPlayer) > 400.0) && ((Entity)this.theNPC).onGround) {
            this.doJumpAttack(jumpSpeed);
            ((Entity)this.theNPC).motionX = ((Entity)this.lastAttackingPlayer).posX - ((Entity)this.theNPC).posX;
            ((Entity)this.theNPC).motionY = ((Entity)this.lastAttackingPlayer).posY - ((Entity)this.theNPC).posY;
            ((Entity)this.theNPC).motionZ = ((Entity)this.lastAttackingPlayer).posZ - ((Entity)this.theNPC).posZ;
            final LOTREntityNPC theNPC = this.theNPC;
            ((Entity)theNPC).motionX /= 10.0;
            final LOTREntityNPC theNPC2 = this.theNPC;
            ((Entity)theNPC2).motionY /= 10.0;
            final LOTREntityNPC theNPC3 = this.theNPC;
            ((Entity)theNPC3).motionZ /= 10.0;
            if (((Entity)this.theNPC).motionY < jumpSpeed) {
                ((Entity)this.theNPC).motionY = jumpSpeed;
            }
            this.theNPC.getLookHelper().setLookPositionWithEntity((Entity)this.lastAttackingPlayer, 100.0f, 100.0f);
            this.theNPC.getLookHelper().onUpdateLook();
            ((Entity)this.theNPC).rotationYaw = ((EntityLivingBase)this.theNPC).rotationYawHead;
        }
    }
    
    public float onFall(float fall) {
        if (!((Entity)this.theNPC).worldObj.isClient && this.jumpAttack) {
            fall = 0.0f;
            this.jumpAttack = false;
            final List enemies = this.getNearbyEnemies();
            final float attackDamage = (float)this.theNPC.getEntityAttribute(LOTREntityNPC.npcAttackDamage).getAttributeValue();
            for (int i = 0; i < enemies.size(); ++i) {
                final EntityLivingBase entity = enemies.get(i);
                float strength = 12.0f - this.theNPC.getDistanceToEntity((Entity)entity) / 3.0f;
                strength /= 12.0f;
                entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this.theNPC), strength * attackDamage * 3.0f);
                final float knockback = strength * 3.0f;
                entity.addVelocity((double)(-MathHelper.sin(((Entity)this.theNPC).rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f), 0.25 * knockback, (double)(MathHelper.cos(((Entity)this.theNPC).rotationYaw * 3.1415927f / 180.0f) * knockback * 0.5f));
            }
            this.theBoss.onJumpAttackFall();
        }
        return fall;
    }
    
    private void clearSurroundingBlocks() {
        if (LOTRMod.canGrief(((Entity)this.theNPC).worldObj)) {
            final int xzRange = MathHelper.ceiling_float_int(((Entity)this.theNPC).width / 2.0f * 1.5f);
            final int yRange = MathHelper.ceiling_float_int(((Entity)this.theNPC).height * 1.5f);
            final int xzDist = xzRange * xzRange + xzRange * xzRange;
            final int i = MathHelper.floor_double(((Entity)this.theNPC).posX);
            final int j = MathHelper.floor_double(((Entity)this.theNPC).boundingBox.minY);
            final int k = MathHelper.floor_double(((Entity)this.theNPC).posZ);
            for (int i2 = i - xzRange; i2 <= i + xzRange; ++i2) {
                for (int j2 = j; j2 <= j + yRange; ++j2) {
                    for (int k2 = k - xzRange; k2 <= k + xzRange; ++k2) {
                        final int i3 = i2 - i;
                        final int k3 = k2 - k;
                        final int dist = i3 * i3 + k3 * k3;
                        if (dist < xzDist) {
                            final Block block = ((Entity)this.theNPC).worldObj.getBlock(i2, j2, k2);
                            if (block != null && !block.getMaterial().isLiquid()) {
                                final float resistance = block.getExplosionResistance((Entity)this.theNPC, ((Entity)this.theNPC).worldObj, i2, j2, k2, ((Entity)this.theNPC).posX, ((Entity)this.theNPC).boundingBox.minY + ((Entity)this.theNPC).height / 2.0f, ((Entity)this.theNPC).posZ);
                                if (resistance < 2000.0f) {
                                    block.dropBlockAsItemWithChance(((Entity)this.theNPC).worldObj, i2, j2, k2, ((Entity)this.theNPC).worldObj.getBlockMetadata(i2, j2, k2), resistance / 100.0f, 0);
                                    ((Entity)this.theNPC).worldObj.setBlockToAir(i2, j2, k2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void onUpdate() {
        if (this.lastAttackingPlayer != null && (!this.lastAttackingPlayer.isEntityAlive() || this.lastAttackingPlayer.capabilities.isCreativeMode)) {
            this.lastAttackingPlayer = null;
        }
        if (!((Entity)this.theNPC).worldObj.isClient) {
            final Map<UUID, Pair<Integer, Float>> playerHurtTimes_new = new HashMap<UUID, Pair<Integer, Float>>();
            for (final Map.Entry<UUID, Pair<Integer, Float>> entry : this.playerHurtTimes.entrySet()) {
                final UUID player = entry.getKey();
                int time = (int)entry.getValue().getLeft();
                final float damage = (float)entry.getValue().getRight();
                if (--time > 0) {
                    playerHurtTimes_new.put(player, (Pair<Integer, Float>)Pair.of((Object)time, (Object)damage));
                }
            }
            this.playerHurtTimes = playerHurtTimes_new;
        }
        if (!((Entity)this.theNPC).worldObj.isClient && this.jumpAttack && ((Entity)this.theNPC).ticksExisted % 5 == 0) {
            this.clearSurroundingBlocks();
        }
    }
    
    public void onHurt(final DamageSource damagesource, final float f) {
        if (!((Entity)this.theNPC).worldObj.isClient) {
            if (damagesource.getEntity() instanceof EntityPlayer) {
                final EntityPlayer attackingPlayer = (EntityPlayer)damagesource.getEntity();
                if (!attackingPlayer.capabilities.isCreativeMode) {
                    this.lastAttackingPlayer = attackingPlayer;
                }
            }
            final EntityPlayer playerSource = LOTRMod.playerSourceOfDamage(damagesource);
            if (playerSource != null) {
                final UUID player = playerSource.getUniqueID();
                final int time = LOTRBossInfo.PLAYER_HURT_COOLDOWN;
                float totalDamage = f;
                if (this.playerHurtTimes.containsKey(player)) {
                    final Pair<Integer, Float> pair = this.playerHurtTimes.get(player);
                    totalDamage += (float)pair.getRight();
                }
                this.playerHurtTimes.put(player, (Pair<Integer, Float>)Pair.of((Object)time, (Object)totalDamage));
            }
        }
    }
    
    public void onDeath(final DamageSource damagesource) {
        this.onHurt(damagesource, 0.0f);
        if (!((Entity)this.theNPC).worldObj.isClient) {
            for (final Map.Entry<UUID, Pair<Integer, Float>> e : this.playerHurtTimes.entrySet()) {
                final UUID player = e.getKey();
                final Pair<Integer, Float> pair = e.getValue();
                final float damage = (float)pair.getRight();
                if (damage >= LOTRBossInfo.PLAYER_DAMAGE_THRESHOLD) {
                    LOTRLevelData.getData(player).addAchievement(this.theBoss.getBossKillAchievement());
                }
            }
        }
    }
    
    public void writeToNBT(final NBTTagCompound nbt) {
        final NBTTagCompound data = new NBTTagCompound();
        final NBTTagList playerHurtTags = new NBTTagList();
        for (final Map.Entry<UUID, Pair<Integer, Float>> entry : this.playerHurtTimes.entrySet()) {
            final UUID player = entry.getKey();
            final Pair<Integer, Float> pair = entry.getValue();
            final int time = (int)pair.getLeft();
            final float damage = (float)pair.getRight();
            final NBTTagCompound playerTag = new NBTTagCompound();
            playerTag.setString("UUID", player.toString());
            playerTag.setInteger("Time", time);
            playerTag.setFloat("Damage", damage);
            playerHurtTags.appendTag((NBTBase)playerTag);
        }
        data.setTag("PlayerHurtTimes", (NBTBase)playerHurtTags);
        data.setBoolean("JumpAttack", this.jumpAttack);
        nbt.setTag("NPCBossInfo", (NBTBase)data);
    }
    
    public void readFromNBT(final NBTTagCompound nbt) {
        final NBTTagCompound data = nbt.getCompoundTag("NPCBossInfo");
        if (data != null) {
            final NBTTagList playerHurtTags = data.getTagList("PlayerHurtTimes", 10);
            for (int i = 0; i < playerHurtTags.tagCount(); ++i) {
                final NBTTagCompound playerTag = playerHurtTags.getCompoundTagAt(i);
                final UUID player = UUID.fromString(playerTag.getString("UUID"));
                if (player != null) {
                    final int time = playerTag.getInteger("Time");
                    final float damage = playerTag.getFloat("Damage");
                    this.playerHurtTimes.put(player, (Pair<Integer, Float>)Pair.of((Object)time, (Object)damage));
                }
            }
            this.jumpAttack = data.getBoolean("JumpAttack");
        }
    }
    
    static {
        LOTRBossInfo.PLAYER_HURT_COOLDOWN = 600;
        LOTRBossInfo.PLAYER_DAMAGE_THRESHOLD = 40.0f;
    }
}
