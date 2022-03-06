// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity;

import lotr.common.item.LOTRItemConquestHorn;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.DamageSource;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.IEntityLivingData;
import cpw.mods.fml.common.eventhandler.Event;
import net.minecraft.entity.EntityLiving;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraft.entity.EntityList;
import java.util.Collection;
import net.minecraft.util.WeightedRandom;
import net.minecraft.command.IEntitySelector;
import lotr.common.entity.npc.LOTREntityNPC;
import lotr.common.LOTRMod;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentTranslation;
import java.util.Iterator;
import lotr.common.LOTRLevelData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import lotr.common.LOTRBannerProtection;
import lotr.common.world.spawning.LOTRInvasions;
import net.minecraft.item.ItemStack;
import java.util.ArrayList;
import net.minecraft.world.World;
import lotr.common.fac.LOTRFaction;
import java.util.List;
import net.minecraft.entity.Entity;

public class LOTREntityInvasionSpawner extends Entity
{
    public float spawnerSpin;
    public float prevSpawnerSpin;
    private static double INVASION_RANGE;
    private int mobsRemaining;
    private int timeSinceLastSpawn;
    public boolean spawnsPersistent;
    private List<LOTRFaction> bonusFactions;
    
    public LOTREntityInvasionSpawner(final World world) {
        super(world);
        this.timeSinceLastSpawn = 0;
        this.spawnsPersistent = true;
        this.bonusFactions = new ArrayList<LOTRFaction>();
        this.setSize(1.5f, 1.5f);
        super.renderDistanceWeight = 4.0;
        this.spawnerSpin = super.rand.nextFloat() * 360.0f;
    }
    
    public ItemStack getInvasionItem() {
        return this.getInvasionType().getInvasionIcon();
    }
    
    public void entityInit() {
        super.dataWatcher.addObject(20, (Object)0);
    }
    
    public LOTRInvasions getInvasionType() {
        final int i = super.dataWatcher.getWatchableObjectByte(20);
        final LOTRInvasions type = LOTRInvasions.forID(i);
        if (type != null) {
            return type;
        }
        return LOTRInvasions.HOBBIT;
    }
    
    public void setInvasionType(final LOTRInvasions type) {
        super.dataWatcher.updateObject(20, (Object)(byte)type.ordinal());
    }
    
    public boolean canInvasionSpawnHere() {
        return !LOTRBannerProtection.isProtectedByBanner(super.worldObj, this, LOTRBannerProtection.forInvasionSpawner(this), false) && !LOTREntityNPCRespawner.isSpawnBlocked(this, this.getInvasionType().invasionFaction) && super.worldObj.checkNoEntityCollision(super.boundingBox) && super.worldObj.getCollidingBoundingBoxes((Entity)this, super.boundingBox).isEmpty() && !super.worldObj.isAnyLiquid(super.boundingBox);
    }
    
    private void playHorn() {
        super.worldObj.playSoundAtEntity((Entity)this, "lotr:item.horn", 4.0f, 0.65f + super.rand.nextFloat() * 0.1f);
    }
    
    public void announceInvasionToEnemies() {
        this.playHorn();
        this.mobsRemaining = MathHelper.getRandomIntegerInRange(super.rand, 30, 70);
        final double announceRange = LOTREntityInvasionSpawner.INVASION_RANGE * 2.0;
        final List<EntityPlayer> nearbyPlayers = (List<EntityPlayer>)super.worldObj.getEntitiesWithinAABB((Class)EntityPlayer.class, super.boundingBox.expand(announceRange, announceRange, announceRange));
        for (final EntityPlayer entityplayer : nearbyPlayers) {
            if (LOTRLevelData.getData(entityplayer).getAlignment(this.getInvasionType().invasionFaction) < 0.0f) {
                this.announceInvasionTo(entityplayer);
            }
        }
    }
    
    public void announceInvasionTo(final EntityPlayer entityplayer) {
        entityplayer.addChatMessage((IChatComponent)new ChatComponentTranslation("chat.lotr.invasion.start", new Object[] { this.getInvasionType().invasionName() }));
    }
    
    public void selectAppropriateBonusFactions() {
        if (LOTRFaction.controlZonesEnabled(super.worldObj)) {
            final LOTRFaction invasionFaction = this.getInvasionType().invasionFaction;
            for (final LOTRFaction faction : invasionFaction.getBonusesForKilling()) {
                if (!faction.isolationist && faction.inDefinedControlZone(super.worldObj, super.posX, super.posY, super.posZ, 50)) {
                    this.bonusFactions.add(faction);
                }
            }
            if (this.bonusFactions.isEmpty()) {
                final int nearestRange = 150;
                LOTRFaction nearest = null;
                double nearestDist = Double.MAX_VALUE;
                for (final LOTRFaction faction2 : invasionFaction.getBonusesForKilling()) {
                    if (!faction2.isolationist) {
                        final double dist = faction2.distanceToNearestControlZoneInRange(super.posX, super.posY, super.posZ, nearestRange);
                        if (dist < 0.0 || (nearest != null && dist >= nearestDist)) {
                            continue;
                        }
                        nearest = faction2;
                        nearestDist = dist;
                    }
                }
                if (nearest != null) {
                    this.bonusFactions.add(nearest);
                }
            }
        }
    }
    
    public void writeEntityToNBT(final NBTTagCompound nbt) {
        nbt.setString("InvasionType", this.getInvasionType().codeName());
        nbt.setInteger("MobsRemaining", this.mobsRemaining);
        nbt.setInteger("TimeSinceSpawn", this.timeSinceLastSpawn);
        nbt.setBoolean("NPCPersistent", this.spawnsPersistent);
        if (!this.bonusFactions.isEmpty()) {
            final NBTTagList bonusTags = new NBTTagList();
            for (final LOTRFaction f : this.bonusFactions) {
                final String fName = f.codeName();
                bonusTags.appendTag((NBTBase)new NBTTagString(fName));
            }
            nbt.setTag("BonusFactions", (NBTBase)bonusTags);
        }
    }
    
    public void readEntityFromNBT(final NBTTagCompound nbt) {
        LOTRInvasions type = LOTRInvasions.forName(nbt.getString("InvasionType"));
        if (type == null && nbt.hasKey("Faction")) {
            final String factionName = nbt.getString("Faction");
            type = LOTRInvasions.forName(factionName);
        }
        if (type == null || type.invasionMobs.isEmpty()) {
            this.setDead();
        }
        else {
            this.setInvasionType(type);
            this.mobsRemaining = nbt.getInteger("MobsRemaining");
            this.timeSinceLastSpawn = nbt.getInteger("TimeSinceSpawn");
            if (nbt.hasKey("NPCPersistent")) {
                this.spawnsPersistent = nbt.getBoolean("NPCPersistent");
            }
            if (nbt.hasKey("BonusFactions")) {
                final NBTTagList bonusTags = nbt.getTagList("BonusFactions", 8);
                for (int i = 0; i < bonusTags.tagCount(); ++i) {
                    final String fName = bonusTags.getStringTagAt(i);
                    final LOTRFaction f = LOTRFaction.forName(fName);
                    if (f != null) {
                        this.bonusFactions.add(f);
                    }
                }
            }
        }
    }
    
    private void endInvasion() {
        super.worldObj.createExplosion((Entity)this, super.posX, super.posY + super.height / 2.0, super.posZ, 0.0f, false);
        this.setDead();
    }
    
    public void onUpdate() {
        if (!super.worldObj.isClient && super.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
            this.endInvasion();
            return;
        }
        super.prevPosX = super.posX;
        super.prevPosY = super.posY;
        super.prevPosZ = super.posZ;
        this.prevSpawnerSpin = this.spawnerSpin;
        this.spawnerSpin += 6.0f;
        this.prevSpawnerSpin = MathHelper.wrapAngleTo180_float(this.prevSpawnerSpin);
        this.spawnerSpin = MathHelper.wrapAngleTo180_float(this.spawnerSpin);
        super.motionX = 0.0;
        super.motionY = 0.0;
        super.motionZ = 0.0;
        this.moveEntity(super.motionX, super.motionY, super.motionZ);
        if (!super.worldObj.isClient && LOTRMod.canSpawnMobs(super.worldObj)) {
            final LOTRInvasions invasionType = this.getInvasionType();
            final EntityPlayer entityplayer = super.worldObj.getClosestPlayer(super.posX, super.posY, super.posZ, LOTREntityInvasionSpawner.INVASION_RANGE);
            if (entityplayer != null) {
                ++this.timeSinceLastSpawn;
                if (this.timeSinceLastSpawn >= 2400) {
                    this.endInvasion();
                }
                else if (this.mobsRemaining > 0) {
                    final List nearbyNPCs = super.worldObj.selectEntitiesWithinAABB((Class)LOTREntityNPC.class, super.boundingBox.expand(12.0, 12.0, 12.0), (IEntitySelector)new LOTRNPCSelectByFaction(invasionType.invasionFaction));
                    if (nearbyNPCs.size() < 16 && super.rand.nextInt(160) == 0) {
                        int mobSpawns = MathHelper.getRandomIntegerInRange(super.rand, 1, 6);
                        mobSpawns = Math.min(mobSpawns, this.mobsRemaining);
                        boolean spawnedAnyMobs = false;
                    Label_0667:
                        for (int l = 0; l < mobSpawns; ++l) {
                            final LOTRInvasions.InvasionSpawnEntry entry = (LOTRInvasions.InvasionSpawnEntry)WeightedRandom.getRandomItem(super.rand, (Collection)invasionType.invasionMobs);
                            final Class entityClass = entry.getEntityClass();
                            final String entityName = LOTREntities.getStringFromClass(entityClass);
                            final LOTREntityNPC npc = (LOTREntityNPC)EntityList.createEntityByName(entityName, super.worldObj);
                            for (int attempts = 0; attempts < 40; ++attempts) {
                                final int i = MathHelper.floor_double(super.posX) + MathHelper.getRandomIntegerInRange(super.rand, -6, 6);
                                final int k = MathHelper.floor_double(super.posZ) + MathHelper.getRandomIntegerInRange(super.rand, -6, 6);
                                final int j = MathHelper.floor_double(super.posY) + MathHelper.getRandomIntegerInRange(super.rand, -8, 4);
                                if (super.worldObj.getBlock(i, j - 1, k).isSideSolid((IBlockAccess)super.worldObj, i, j - 1, k, ForgeDirection.UP)) {
                                    npc.setLocationAndAngles(i + 0.5, (double)j, k + 0.5, super.rand.nextFloat() * 360.0f, 0.0f);
                                    npc.liftSpawnRestrictions = true;
                                    final Event.Result canSpawn = ForgeEventFactory.canEntitySpawn((EntityLiving)npc, super.worldObj, (float)((Entity)npc).posX, (float)((Entity)npc).posY, (float)((Entity)npc).posZ);
                                    if (canSpawn == Event.Result.ALLOW || (canSpawn == Event.Result.DEFAULT && npc.getCanSpawnHere())) {
                                        npc.liftSpawnRestrictions = false;
                                        npc.onSpawnWithEgg(null);
                                        npc.isNPCPersistent = false;
                                        if (this.spawnsPersistent) {
                                            npc.isNPCPersistent = true;
                                        }
                                        npc.isInvasionSpawned = true;
                                        npc.killBonusFactions.addAll(this.bonusFactions);
                                        super.worldObj.spawnEntityInWorld((Entity)npc);
                                        npc.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(LOTREntityInvasionSpawner.INVASION_RANGE);
                                        spawnedAnyMobs = true;
                                        --this.mobsRemaining;
                                        if (this.mobsRemaining > 0) {
                                            break;
                                        }
                                        break Label_0667;
                                    }
                                }
                            }
                        }
                        if (spawnedAnyMobs) {
                            this.timeSinceLastSpawn = 0;
                            this.playHorn();
                        }
                    }
                }
            }
            if (this.mobsRemaining <= 0) {
                this.endInvasion();
            }
        }
        else {
            final String particle = super.rand.nextBoolean() ? "smoke" : "flame";
            super.worldObj.spawnParticle(particle, super.posX + (super.rand.nextDouble() - 0.5) * super.width, super.posY + super.rand.nextDouble() * super.height, super.posZ + (super.rand.nextDouble() - 0.5) * super.width, 0.0, 0.0, 0.0);
        }
    }
    
    public boolean canBeCollidedWith() {
        return true;
    }
    
    public void applyEntityCollision(final Entity entity) {
    }
    
    public boolean hitByEntity(final Entity entity) {
        return entity instanceof EntityPlayer && this.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer)entity), 0.0f);
    }
    
    public boolean attackEntityFrom(final DamageSource damagesource, final float f) {
        final Entity entity = damagesource.getEntity();
        if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.isCreativeMode) {
            if (!super.worldObj.isClient) {
                this.endInvasion();
            }
            return true;
        }
        return false;
    }
    
    public boolean interactFirst(final EntityPlayer entityplayer) {
        if (!super.worldObj.isClient && entityplayer.capabilities.isCreativeMode && !this.bonusFactions.isEmpty()) {
            final IChatComponent message = (IChatComponent)new ChatComponentText("");
            for (final LOTRFaction f : this.bonusFactions) {
                if (!message.getSiblings().isEmpty()) {
                    message.appendSibling((IChatComponent)new ChatComponentText(", "));
                }
                message.appendSibling((IChatComponent)new ChatComponentTranslation(f.factionName(), new Object[0]));
            }
            entityplayer.addChatMessage(message);
            return true;
        }
        return false;
    }
    
    public ItemStack getPickedResult(final MovingObjectPosition target) {
        final LOTRInvasions invasionType = this.getInvasionType();
        if (invasionType != null) {
            return LOTRItemConquestHorn.createHorn(invasionType);
        }
        return null;
    }
    
    static {
        LOTREntityInvasionSpawner.INVASION_RANGE = 40.0;
    }
}
