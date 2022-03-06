// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.LOTRAchievement;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityUtumnoWarg extends LOTREntityWarg
{
    public LOTREntityUtumnoWarg(final World world) {
        super(world);
    }
    
    @Override
    public EntityAIBase getWargAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.7, true);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    public LOTREntityNPC createWargRider() {
        return ((Entity)this).worldObj.rand.nextBoolean() ? new LOTREntityUtumnoOrcArcher(((Entity)this).worldObj) : new LOTREntityUtumnoOrc(((Entity)this).worldObj);
    }
    
    @Override
    public boolean canWargBeRidden() {
        return false;
    }
    
    public LOTRFaction getFaction() {
        return LOTRFaction.UTUMNO;
    }
    
    protected LOTRAchievement getKillAchievement() {
        return LOTRAchievement.killUtumnoWarg;
    }
}
