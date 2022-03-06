// 
// Decompiled by Procyon v0.5.36
// 

package lotr.common.item;

import lotr.common.LOTRMod;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.PotionEffect;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import lotr.common.LOTRDamage;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;

public class LOTRPotionPoisonKilling extends Potion
{
    public LOTRPotionPoisonKilling() {
        super(30, true, Potion.poison.getLiquidColor());
        this.setPotionName("potion.lotr.drinkPoison");
        this.setEffectiveness(Potion.poison.getEffectiveness());
        this.setIconIndex(0, 0);
    }
    
    public void performEffect(final EntityLivingBase entity, final int level) {
        entity.attackEntityFrom(LOTRDamage.poisonDrink, 1.0f);
    }
    
    public boolean isReady(final int tick, final int level) {
        final int freq = 5 >> level;
        return freq <= 0 || tick % freq == 0;
    }
    
    @SideOnly(Side.CLIENT)
    public boolean hasStatusIcon() {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public void renderInventoryEffect(final int x, final int y, final PotionEffect effect, final Minecraft mc) {
        LOTRMod.proxy.renderCustomPotionEffect(x, y, effect, mc);
    }
}
