// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.entity.animal;

import net.minecraft.entity.Entity;
import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.EntityCreature;
import lotr.common.entity.ai.LOTREntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.world.World;

public class LOTREntityWildBoar extends LOTREntityHorse
{
    public LOTREntityWildBoar(final World world) {
        super(world);
        this.setSize(0.9f, 0.8f);
    }
    
    @Override
    protected boolean isMountHostile() {
        return true;
    }
    
    @Override
    protected EntityAIBase createMountAttackAI() {
        return new LOTREntityAIAttackOnCollide((EntityCreature)this, 1.2, true);
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
        maxHealth = Math.min(maxHealth, 25.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(maxHealth);
        double speed = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
        speed *= 1.0;
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(speed);
    }
    
    @Override
    protected double clampChildHealth(final double health) {
        return MathHelper.clamp_double(health, 10.0, 30.0);
    }
    
    @Override
    protected double clampChildJump(final double jump) {
        return MathHelper.clamp_double(jump, 0.3, 1.0);
    }
    
    @Override
    protected double clampChildSpeed(final double speed) {
        return MathHelper.clamp_double(speed, 0.08, 0.35);
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack itemstack) {
        return itemstack != null && itemstack.getItem() == Items.carrot;
    }
    
    protected void dropFewItems(final boolean flag, final int i) {
        for (int meat = ((Entity)this).rand.nextInt(3) + 1 + ((Entity)this).rand.nextInt(1 + i), l = 0; l < meat; ++l) {
            if (this.isBurning()) {
                this.func_145779_a(Items.cooked_porkchop, 1);
            }
            else {
                this.func_145779_a(Items.porkchop, 1);
            }
        }
    }
    
    protected String getLivingSound() {
        return "mob.pig.say";
    }
    
    protected String getHurtSound() {
        return "mob.pig.say";
    }
    
    protected String getDeathSound() {
        return "mob.pig.death";
    }
    
    protected String getAngrySoundName() {
        return "mob.pig.say";
    }
    
    protected void func_145780_a(final int i, final int j, final int k, final Block block) {
        this.playSound("mob.pig.step", 0.15f, 1.0f);
    }
}
