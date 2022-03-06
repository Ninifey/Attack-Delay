// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.npc;

import net.minecraft.entity.Entity;
import lotr.common.fac.LOTRFaction;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityGundabadWarg extends LOTREntityWarg
{
    public LOTREntityGundabadWarg(final World world) {
        super(world);
    }
    
    @Override
    public EntityAIBase getWargAttackAI() {
        return new LOTREntityAIAttackOnCollide(this, 1.75, false);
    }
    
    @Override
    public LOTREntityNPC createWargRider() {
        return ((Entity)this).worldObj.rand.nextBoolean() ? new LOTREntityGundabadOrcArcher(((Entity)this).worldObj) : new LOTREntityGundabadOrc(((Entity)this).worldObj);
    }
    
    public LOTRFaction getFaction() {
        return LOTRFaction.GUNDABAD;
    }
    
    public float getAlignmentBonus() {
        return 2.0f;
    }
}
