// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import lotr.common.LOTRAchievement;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityUtumnoTroll extends LOTREntityTroll
{
    public LOTREntityUtumnoTroll(final World world) {
        super(world);
    }
    
    @Override
    public float getTrollScale() {
        return 1.5f;
    }
    
    @Override
    public EntityAIBase getTrollAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 2.0, false);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(32.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(60.0);
        this.getEntityAttribute(LOTREntityNPC.npcAttackDamage).setBaseValue(7.0);
    }
    
    @Override
    public LOTRFaction getFaction() {
        return LOTRFaction.UTUMNO;
    }
    
    @Override
    protected boolean hasTrollName() {
        return false;
    }
    
    @Override
    protected boolean canTrollBeTickled(final EntityPlayer entityplayer) {
        return false;
    }
    
    @Override
    public void onTrollDeathBySun() {
        ((Entity)this).worldObj.playSoundAtEntity((Entity)this, "lotr:troll.transform", this.getSoundVolume(), this.getSoundPitch());
        ((Entity)this).worldObj.setEntityState((Entity)this, (byte)15);
        this.setDead();
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void handleHealthUpdate(final byte b) {
        if (b == 15) {
            super.handleHealthUpdate(b);
            for (int l = 0; l < 64; ++l) {
                LOTRMod.proxy.spawnParticle("largeStone", ((Entity)this).posX + ((Entity)this).rand.nextGaussian() * ((Entity)this).width * 0.5, ((Entity)this).posY + ((Entity)this).rand.nextDouble() * ((Entity)this).height, ((Entity)this).posZ + ((Entity)this).rand.nextGaussian() * ((Entity)this).width * 0.5, 0.0, 0.0, 0.0);
            }
        }
        else {
            super.handleHealthUpdate(b);
        }
    }
    
    @Override
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killUtumnoTroll;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityplayer) {
        return 5 + ((Entity)this).rand.nextInt(6);
    }
    
    @Override
    public String getSpeechBank(final EntityPlayer entityplayer) {
        return null;
    }
}
