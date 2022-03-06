// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.enchant;

import net.minecraft.util.StatCollector;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import java.util.Random;
import java.util.Iterator;
import java.util.Arrays;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.EntityLivingBase;
import java.util.List;

public class LOTREnchantmentBane extends LOTREnchantmentDamage
{
    private List<Class<? extends EntityLivingBase>> entityClasses;
    private EnumCreatureAttribute entityAttribute;
    public final float baneDamage;
    public boolean isAchievable;
    
    private LOTREnchantmentBane(final String s, final float boost) {
        super(s, 0.0f);
        this.isAchievable = true;
        this.baneDamage = boost;
        this.setValueModifier((10.0f + this.baneDamage) / 10.0f);
        this.setPersistsReforge();
        this.setBypassAnvilLimit();
    }
    
    public LOTREnchantmentBane(final String s, final float boost, final Class<? extends EntityLivingBase>... classes) {
        this(s, boost);
        this.entityClasses = Arrays.asList(classes);
    }
    
    public LOTREnchantmentBane(final String s, final float boost, final EnumCreatureAttribute attr) {
        this(s, boost);
        this.entityAttribute = attr;
    }
    
    public LOTREnchantmentBane setUnachievable() {
        this.isAchievable = false;
        return this;
    }
    
    public boolean isEntityType(final EntityLivingBase entity) {
        if (this.entityClasses != null) {
            for (final Class cls : this.entityClasses) {
                if (cls.isAssignableFrom(entity.getClass())) {
                    return true;
                }
            }
        }
        else if (this.entityAttribute != null) {
            return entity.getCreatureAttribute() == this.entityAttribute;
        }
        return false;
    }
    
    @Override
    public float getBaseDamageBoost() {
        return 0.0f;
    }
    
    @Override
    public float getEntitySpecificDamage(final EntityLivingBase entity) {
        if (this.isEntityType(entity)) {
            return this.baneDamage;
        }
        return 0.0f;
    }
    
    public int getRandomKillsRequired(final Random random) {
        return MathHelper.getRandomIntegerInRange(random, 100, 250);
    }
    
    @Override
    public String getDescription(final ItemStack itemstack) {
        return StatCollector.translateToLocalFormatted("lotr.enchant." + super.enchantName + ".desc", new Object[] { this.formatAdditive(this.baneDamage) });
    }
    
    @Override
    public boolean isBeneficial() {
        return true;
    }
}
