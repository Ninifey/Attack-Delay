// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.EntityAgeable;
import lotr.common.LOTRMod;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityKineAraw extends LOTREntityAurochs
{
    public static float KINE_SCALE;
    
    public LOTREntityKineAraw(final World world) {
        super(world);
        this.setSize(super.aurochsWidth * LOTREntityKineAraw.KINE_SCALE, super.aurochsHeight * LOTREntityKineAraw.KINE_SCALE);
    }
    
    @Override
    protected EntityAIBase createAurochsAttackAI() {
        return new LOTREntityAIAttackOnCollide((EntityCreature)this, 1.9, true);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(40.0);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(5.0);
    }
    
    @Override
    protected void dropHornItem(final boolean flag, final int i) {
        this.func_145779_a(LOTRMod.kineArawHorn, 1);
    }
    
    @Override
    public EntityCow createChild(final EntityAgeable entity) {
        return new LOTREntityKineAraw(((Entity)this).worldObj);
    }
    
    static {
        LOTREntityKineAraw.KINE_SCALE = 1.15f;
    }
}
