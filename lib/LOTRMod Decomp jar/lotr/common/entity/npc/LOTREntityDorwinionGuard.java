// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.init.Items;
import net.minecraft.block.Block;
import java.util.Iterator;
import java.util.List;
import net.minecraft.world.biome.BiomeGenBase;
import lotr.common.fac.LOTRAlignmentValues;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.util.MathHelper;
import lotr.common.fac.LOTRFaction;
import lotr.common.world.biome.variant.LOTRBiomeVariant;
import lotr.common.world.biome.LOTRBiomeGenDorwinion;
import lotr.common.world.LOTRWorldChunkManager;
import lotr.common.world.LOTRWorldProvider;
import lotr.common.LOTRAchievement;
import lotr.common.LOTRLevelData;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import lotr.common.LOTRMod;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import lotr.common.LOTRShields;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

public class LOTREntityDorwinionGuard extends LOTREntityDorwinionMan
{
    private static ItemStack[] guardWeapons;
    public int grapeAlert;
    public static final int MAX_GRAPE_ALERT = 3;
    
    public LOTREntityDorwinionGuard(final World world) {
        super(world);
        this.addTargetTasks(true);
        super.npcShield = LOTRShields.ALIGNMENT_DORWINION;
    }
    
    @Override
    protected EntityAIBase createDorwinionAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.4, true);
    }
    
    @Override
    public void setupNPCGender() {
        super.familyInfo.setMale(true);
    }
    
    @Override
    public IEntityLivingData onSpawnWithEgg(IEntityLivingData data) {
        data = super.onSpawnWithEgg(data);
        final int i = ((Entity)this).rand.nextInt(LOTREntityDorwinionGuard.guardWeapons.length);
        super.npcItemsInv.setMeleeWeapon(LOTREntityDorwinionGuard.guardWeapons[i].copy());
        if (((Entity)this).rand.nextInt(8) == 0) {
            super.npcItemsInv.setSpearBackup(super.npcItemsInv.getMeleeWeapon());
            super.npcItemsInv.setMeleeWeapon(new ItemStack(LOTRMod.spearIron));
        }
        super.npcItemsInv.setIdleItem(super.npcItemsInv.getMeleeWeapon());
        this.setCurrentItemOrArmor(1, new ItemStack(LOTRMod.bootsDorwinion));
        this.setCurrentItemOrArmor(2, new ItemStack(LOTRMod.legsDorwinion));
        this.setCurrentItemOrArmor(3, new ItemStack(LOTRMod.bodyDorwinion));
        if (((Entity)this).rand.nextInt(4) != 0) {
            this.setCurrentItemOrArmor(4, new ItemStack(LOTRMod.helmetDorwinion));
        }
        else {
            this.setCurrentItemOrArmor(4, (ItemStack)null);
        }
        return data;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        super.writeEntityToNBT(nbt);
        nbt.setInteger("GrapeAlert", this.grapeAlert);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        super.readEntityFromNBT(nbt);
        this.grapeAlert = nbt.getInteger("GrapeAlert");
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!((Entity)this).worldObj.isClient && this.grapeAlert > 0 && ((Entity)this).ticksExisted % 600 == 0) {
            --this.grapeAlert;
        }
    }
    
    @Override
    public float getAlignmentBonus() {
        return 2.0f;
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        if (!this.isFriendly(entityplayer)) {
            return "dorwinion/guard/hostile";
        }
        if (super.hiredNPCInfo.getHiringPlayer() == entityplayer) {
            return "dorwinion/guard/hired";
        }
        return "dorwinion/guard/friendly";
    }
    
    @Override
    public void onDeath(final DamageSource damagesource) {
        super.onDeath(damagesource);
        if (!((Entity)this).worldObj.isClient && damagesource.getEntity() instanceof EntityPlayer) {
            final EntityPlayer entityplayer = (EntityPlayer)damagesource.getEntity();
            if (this.grapeAlert >= 3) {
                LOTRLevelData.getData(entityplayer).addAchievement(LOTRAchievement.stealDorwinionGrapes);
            }
        }
    }
    
    public static void defendGrapevines(final EntityPlayer entityplayer, final World world, final int i, final int j, final int k) {
        if (!entityplayer.capabilities.isCreativeMode) {
            final BiomeGenBase biome = world.getBiomeGenForCoords(i, k);
            final LOTRBiomeVariant variant = (world.provider instanceof LOTRWorldProvider) ? ((LOTRWorldChunkManager)world.provider.worldChunkMgr).getBiomeVariantAt(i, k) : null;
            if (biome instanceof LOTRBiomeGenDorwinion && variant == LOTRBiomeVariant.VINEYARD) {
                final float alignment = LOTRLevelData.getData(entityplayer).getAlignment(LOTRFaction.DORWINION);
                final boolean evil = alignment < 0.0f;
                final float limit = 2000.0f;
                float chance = (limit - alignment) / limit;
                chance = Math.max(chance, 0.0f);
                chance = Math.min(chance, 1.0f);
                chance *= chance;
                if ((evil || world.rand.nextFloat() < chance) && world.rand.nextInt(4) == 0) {
                    int nearbyGuards = 0;
                    final int spawnRange = 8;
                    final List guardList = world.getEntitiesWithinAABB((Class)LOTREntityDorwinionGuard.class, ((Entity)entityplayer).boundingBox.expand((double)spawnRange, (double)spawnRange, (double)spawnRange));
                    for (final Object obj : guardList) {
                        final LOTREntityDorwinionGuard guard = (LOTREntityDorwinionGuard)obj;
                        if (!guard.hiredNPCInfo.isActive) {
                            ++nearbyGuards;
                        }
                    }
                    if (nearbyGuards < 8) {
                        for (int guardSpawns = 1 + world.rand.nextInt(6), l = 0; l < guardSpawns; ++l) {
                            LOTREntityDorwinionGuard guard = new LOTREntityDorwinionGuard(world);
                            if (world.rand.nextBoolean()) {
                                guard = new LOTREntityDorwinionCrossbower(world);
                            }
                            for (int attempts = 16, a = 0; a < attempts; ++a) {
                                final int i2 = i + MathHelper.getRandomIntegerInRange(world.rand, -spawnRange, spawnRange);
                                final int j2 = j + MathHelper.getRandomIntegerInRange(world.rand, -spawnRange / 2, spawnRange / 2);
                                final int k2 = k + MathHelper.getRandomIntegerInRange(world.rand, -spawnRange, spawnRange);
                                final Block belowBlock = world.getBlock(i2, j2 - 1, k2);
                                final int belowMeta = world.getBlockMetadata(i2, j2 - 1, k2);
                                final boolean belowSolid = belowBlock.isSideSolid((IBlockAccess)world, i2, j2 - 1, k2, ForgeDirection.UP);
                                if (belowSolid && !world.getBlock(i2, j2, k2).isNormalCube() && !world.getBlock(i2, j2 + 1, k2).isNormalCube()) {
                                    guard.setLocationAndAngles(i2 + 0.5, (double)j2, k2 + 0.5, world.rand.nextFloat() * 360.0f, 0.0f);
                                    guard.liftSpawnRestrictions = true;
                                    if (guard.getCanSpawnHere()) {
                                        guard.liftSpawnRestrictions = false;
                                        world.spawnEntityInWorld((Entity)guard);
                                        guard.spawnRidingHorse = false;
                                        guard.onSpawnWithEgg(null);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                final int range = 16;
                final List guardList2 = world.getEntitiesWithinAABB((Class)LOTREntityDorwinionGuard.class, ((Entity)entityplayer).boundingBox.expand((double)range, (double)range, (double)range));
                boolean anyAlert = false;
                for (final Object obj : guardList2) {
                    final LOTREntityDorwinionGuard guard = (LOTREntityDorwinionGuard)obj;
                    if (!guard.hiredNPCInfo.isActive) {
                        if (evil) {
                            guard.setAttackTarget((EntityLivingBase)entityplayer);
                            guard.sendSpeechBank(entityplayer, "dorwinion/guard/grapeAttack");
                            guard.grapeAlert = 3;
                            anyAlert = true;
                        }
                        else {
                            if (world.rand.nextFloat() >= chance) {
                                continue;
                            }
                            final LOTREntityDorwinionGuard lotrEntityDorwinionGuard = guard;
                            ++lotrEntityDorwinionGuard.grapeAlert;
                            if (guard.grapeAlert >= 3) {
                                guard.setAttackTarget((EntityLivingBase)entityplayer);
                                guard.sendSpeechBank(entityplayer, "dorwinion/guard/grapeAttack");
                            }
                            else {
                                guard.sendSpeechBank(entityplayer, "dorwinion/guard/grapeWarn");
                            }
                            anyAlert = true;
                        }
                    }
                }
                if (anyAlert && alignment >= 0.0f) {
                    LOTRLevelData.getData(entityplayer).addAlignment(entityplayer, LOTRAlignmentValues.VINEYARD_STEAL_PENALTY, LOTRFaction.DORWINION, i + 0.5, j + 0.5, k + 0.5);
                }
            }
        }
    }
    
    static {
        LOTREntityDorwinionGuard.guardWeapons = new ItemStack[] { new ItemStack(Items.iron_sword), new ItemStack(LOTRMod.battleaxeIron), new ItemStack(LOTRMod.pikeIron) };
    }
}
