// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import net.minecraft.entity.Entity;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

public class LOTRItemDagger extends LOTRItemSword
{
    private DaggerEffect effect;
    
    public LOTRItemDagger(final LOTRMaterial material) {
        this(material, DaggerEffect.NONE);
    }
    
    public LOTRItemDagger(final Item.ToolMaterial material) {
        this(material, DaggerEffect.NONE);
    }
    
    public LOTRItemDagger(final LOTRMaterial material, final DaggerEffect e) {
        this(material.toToolMaterial(), e);
    }
    
    public LOTRItemDagger(final Item.ToolMaterial material, final DaggerEffect e) {
        super(material);
        super.lotrWeaponDamage -= 3.0f;
        this.effect = e;
    }
    
    public DaggerEffect getDaggerEffect() {
        return this.effect;
    }
    
    public boolean hitEntity(final ItemStack itemstack, final EntityLivingBase hitEntity, final EntityLivingBase user) {
        itemstack.damageItem(1, user);
        if (this.effect == DaggerEffect.NONE) {
            return true;
        }
        if (this.effect == DaggerEffect.POISON) {
            applyStandardPoison(hitEntity);
        }
        return true;
    }
    
    public static void applyStandardPoison(final EntityLivingBase entity) {
        final EnumDifficulty difficulty = ((Entity)entity).worldObj.difficultySetting;
        final int duration = 1 + difficulty.getDifficultyId() * 2;
        final PotionEffect poison = new PotionEffect(Potion.poison.id, (duration + Item.itemRand.nextInt(duration)) * 20);
        entity.addPotionEffect(poison);
    }
    
    public enum DaggerEffect
    {
        NONE, 
        POISON;
    }
}
