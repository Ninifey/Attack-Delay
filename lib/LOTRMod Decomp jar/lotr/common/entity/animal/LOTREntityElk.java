// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import lotr.common.LOTRMod;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import java.util.UUID;
import net.minecraft.world.World;
import lotr.common.entity.LOTRRandomSkinEntity;

public class LOTREntityElk extends LOTREntityHorse implements LOTRRandomSkinEntity
{
    public LOTREntityElk(final World world) {
        super(world);
        this.setSize(1.6f, 1.8f);
    }
    
    @Override
    public void setUniqueID(final UUID uuid) {
        ((Entity)this).entityUniqueID = uuid;
    }
    
    @Override
    protected boolean isMountHostile() {
        return true;
    }
    
    @Override
    protected EntityAIBase createMountAttackAI() {
        return new LOTREntityAIAttackOnCollide((EntityCreature)this, 1.25, true);
    }
    
    public int getHorseType() {
        return 0;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
    }
    
    @Override
    protected void onLOTRHorseSpawn() {
        double maxHealth = this.getEntityAttribute(SharedMonsterAttributes.maxHealth).getAttributeValue();
        maxHealth *= 1.0f + ((Entity)this).rand.nextFloat() * 0.5f;
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
    }
    
    @Override
    protected double clampChildHealth(final double health) {
        return MathHelper.clamp_double(health, 16.0, 50.0);
    }
    
    @Override
    protected double clampChildJump(final double jump) {
        return MathHelper.clamp_double(jump, 0.3, 1.0);
    }
    
    @Override
    protected double clampChildSpeed(final double speed) {
        return MathHelper.clamp_double(speed, 0.08, 0.34);
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack itemstack) {
        return itemstack != null && itemstack.getItem() == Items.wheat;
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        for (int hide = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(1 + i), l = 0; l < hide; ++l) {
            this.func_145779_a(Items.leather, 1);
        }
        for (int meat = ((Entity)this).rand.nextInt(3) + ((Entity)this).rand.nextInt(1 + i), j = 0; j < meat; ++j) {
            if (this.isBurning()) {
                this.func_145779_a(LOTRMod.deerCooked, 1);
            }
            else {
                this.func_145779_a(LOTRMod.deerRaw, 1);
            }
        }
    }
    
    protected String getLivingSound() {
        return "lotr:elk.say";
    }
    
    protected String getHurtSound() {
        return "lotr:elk.hurt";
    }
    
    protected String getDeathSound() {
        return "lotr:elk.death";
    }
}
